/*
 * Copyright 2016 Open mHealth
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openmhealth.schema.domain.omh;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Map;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;


/**
 * @author Emerson Farrugia
 */
public class AdditionalPropertySupportUnitTests extends SerializationUnitTests {

    public static final String SCHEMA_FILENAME = "schema/omh/unit-value-1.0.json";

    private UnitValue unitValue;

    @Override
    protected String getSchemaFilename() {
        return SCHEMA_FILENAME;
    }

    @BeforeTest
    public void initializeFixture() {
        unitValue = new UnitValue("cups", 3);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void getAdditionalPropertyShouldThrowExceptionOnUndefinedName() {

        unitValue.getAdditionalProperty(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void getAdditionalPropertyShouldThrowExceptionOnEmptyName() {

        unitValue.getAdditionalProperty("");
    }

    @Test
    public void getAdditionalPropertyShouldReturnKnownProperties() {

        unitValue.setAdditionalProperty("foo", "bar");

        assertThat(unitValue.getAdditionalProperty("foo"), equalTo(Optional.of("bar")));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void setAdditionalPropertyShouldThrowExceptionOnUndefinedName() {

        unitValue.setAdditionalProperty(null, "bar");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void setAdditionalPropertyShouldThrowExceptionOnEmptyName() {

        unitValue.setAdditionalProperty("", "bar");
    }

    @Test
    public void setAdditionalPropertyShouldCreateRootProperty() {

        // before: {}
        //  after: { "foo" : 2 }
        unitValue.setAdditionalProperty("foo", 2L);

        assertThat(unitValue.getAdditionalProperty("foo"), equalTo(Optional.of(2L)));
    }

    @Test
    public void setAdditionalPropertyShouldOverrideRootProperty() {

        unitValue.setAdditionalProperty("foo", 2L);

        // before: { "foo" : 2 }
        //  after: { "foo" : "bar" }
        unitValue.setAdditionalProperty("foo", "bar");

        assertThat(unitValue.getAdditionalProperty("foo"), equalTo(Optional.of("bar")));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void setAdditionalPropertyShouldCreateNestedProperties() {

        // before: { }
        //  after: { "foo" : { "bar" : 2 } }
        unitValue.setAdditionalProperty("foo.bar", 2L);

        // before: { "foo" : { "bar" : 2 } }
        //  after: { "foo" : { "bar" : 2, "cafe" : "ideal } }
        unitValue.setAdditionalProperty("foo.cafe", "ideal");

        Optional<Object> fooProperty = unitValue.getAdditionalProperty("foo");

        assertThat(fooProperty, notNullValue());
        assertThat(fooProperty.isPresent(), equalTo(true));

        Map<String, Object> fooMap = (Map<String, Object>) fooProperty.get();

        assertThat(fooMap.get("bar"), equalTo(2L));
        assertThat(fooMap.get("cafe"), equalTo("ideal"));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void setAdditionalPropertyShouldOverrideNestedProperty() {

        unitValue.setAdditionalProperty("foo", 2L);

        // before: { "foo" : 2 }
        //  after: { "foo" : { "cafe" : "ideal" } }
        unitValue.setAdditionalProperty("foo.cafe", "ideal");

        Optional<Object> fooProperty = unitValue.getAdditionalProperty("foo");

        assertThat(fooProperty, notNullValue());
        assertThat(fooProperty.isPresent(), equalTo(true));

        Map<String, Object> fooMap = (Map<String, Object>) fooProperty.get();

        assertThat(fooMap.get("cafe"), equalTo("ideal"));
    }


    @Test
    public void getAdditionalPropertiesShouldNotReturnNull() {

        assertThat(unitValue.getAdditionalProperties(), notNullValue());
    }

    @Test
    public void additionalPropertiesShouldSerializeCorrectly() throws Exception {

        unitValue.setAdditionalProperty("foo", "bar");
        unitValue.setAdditionalProperty("baz.cafe", "ideal");

        String document = "{\n" +
                "    \"value\": 3,\n" +
                "    \"unit\": \"cups\",\n" +
                "    \"foo\": \"bar\",\n" +
                "    \"baz\": {\n" +
                "      \"cafe\": \"ideal\"\n" +
                "    }\n" +
                "}";

        serializationShouldCreateValidDocument(unitValue, document);
        deserializationShouldCreateValidObject(document, unitValue);
    }
}