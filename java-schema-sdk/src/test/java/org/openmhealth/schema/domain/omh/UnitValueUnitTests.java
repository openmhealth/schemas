/*
 * Copyright 2015 Open mHealth
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

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;


/**
 * @author Emerson Farrugia
 */
public class UnitValueUnitTests extends DataProvidingSerializationUnitTests {

    public static final String SCHEMA_FILENAME = "schema/omh/unit-value-1.0.json";

    @Test(expectedExceptions = NullPointerException.class)
    public void constructorShouldThrowExceptionOnUndefinedUnit() {

        new UnitValue(null, ONE);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void constructorShouldThrowExceptionOnEmptyUnit() {

        new UnitValue("", ONE);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void constructorShouldThrowExceptionOnUndefinedValue() {

        new UnitValue("g", null);
    }

    @Test
    public void constructorShouldWork() {

        UnitValue unitValue = new UnitValue("g", ONE);

        assertThat(unitValue, notNullValue());
        assertThat(unitValue.getUnit(), equalTo("g"));
        assertThat(unitValue.getValue(), equalTo(ONE));
    }

    @Override
    protected String getSchemaFilename() {
        return SCHEMA_FILENAME;
    }

    @BeforeClass
    public void addSerializationTests() {

        addSerializationTest("{\"unit\":\"trees\",\"value\":10}", new UnitValue("trees", TEN));
    }
}
