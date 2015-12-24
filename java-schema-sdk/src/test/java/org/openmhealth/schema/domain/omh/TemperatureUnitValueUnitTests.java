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

import static org.openmhealth.schema.domain.omh.TemperatureUnit.*;


/**
 * @author Chris Schaefbauer
 */
public class TemperatureUnitValueUnitTests extends DataProvidingSerializationUnitTests {

    public static final String SCHEMA_FILENAME = "schema/omh/temperature-unit-value-1.0.json";

    @Override
    protected String getSchemaFilename() {
        return SCHEMA_FILENAME;
    }

    @BeforeClass
    public void addSerializationTests() {

        addSerializationTest("{\"unit\":\"F\",\"value\":98.9}", new TemperatureUnitValue(FAHRENHEIT, 98.9));
        addSerializationTest("{\"unit\":\"C\",\"value\":37}", new TemperatureUnitValue(CELSIUS, 37L));
        addSerializationTest("{\"unit\":\"K\",\"value\":310}", new TemperatureUnitValue(KELVIN, 310L));
    }
}
