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

import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;


/**
 * @author Emerson Farrugia
 */
public class FreeFormMeasureUnitTests extends SerializationUnitTests {

    @Test(expectedExceptions = NullPointerException.class)
    public void constructorShouldThrowExceptionOnUndefinedSchemaNamespace() {

        new FreeFormMeasure.Builder(null, "body-weight", "1.0");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void constructorShouldThrowExceptionOnInvalidSchemaNamespace() {

        new FreeFormMeasure.Builder("%", "body-weight", "1.0");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void constructorShouldThrowExceptionOnUndefinedSchemaName() {

        new FreeFormMeasure.Builder("omh", null, "1.0");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void constructorShouldThrowExceptionOnInvalidSchemaName() {

        new FreeFormMeasure.Builder("omh", "%", "1.0");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void constructorShouldThrowExceptionOnUndefinedSchemaVersion() {

        new FreeFormMeasure.Builder("omh", "body-weight", null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void constructorShouldThrowExceptionOnInvalidSchemaVersion() {

        new FreeFormMeasure.Builder("omh", "body-weight", "%");
    }

    @Test
    public void buildShouldConstructMeasureUsingOnlyRequiredProperties() {

        FreeFormMeasure measure = new FreeFormMeasure.Builder("omh", "body-weight", "1.0").build();

        assertThat(measure, notNullValue());
        assertThat(measure.getSchemaId(), equalTo(BodyWeight.SCHEMA_ID));
        assertThat(measure.getEffectiveTimeFrame(), nullValue());
        assertThat(measure.getDescriptiveStatistic(), nullValue());
        assertThat(measure.getUserNotes(), nullValue());
    }

    @Override
    protected String getSchemaFilename() {

        // the serialization tests will work with the body weight schema as a use case
        return BodyWeightUnitTests.SCHEMA_FILENAME;
    }

    @Test
    public void measureShouldSerializeCorrectly() throws Exception {

        FreeFormMeasure measure = new FreeFormMeasure.Builder("omh", "body-weight", "1.0").build();

        measure.setAdditionalProperty("body_weight.value", 50L);
        measure.setAdditionalProperty("body_weight.unit", "kg");
        measure.setAdditionalProperty("effective_time_frame.time_interval.start_date_time", "2015-10-01T00:00:00Z");
        measure.setAdditionalProperty("effective_time_frame.time_interval.end_date_time", "2015-11-01T00:00:00Z");
        measure.setAdditionalProperty("descriptive_statistic", "minimum");

        String document = "{\n" +
                "    \"body_weight\": {\n" +
                "        \"value\": 50,\n" +
                "        \"unit\": \"kg\"\n" +
                "    },\n" +
                "    \"effective_time_frame\": {\n" +
                "        \"time_interval\": {\n" +
                "            \"start_date_time\": \"2015-10-01T00:00:00Z\",\n" +
                "            \"end_date_time\": \"2015-11-01T00:00:00Z\"\n" +
                "        }\n" +
                "    },\n" +
                "    \"descriptive_statistic\": \"minimum\"\n" +
                "}";

        serializationShouldCreateValidDocument(measure, document);
    }
}