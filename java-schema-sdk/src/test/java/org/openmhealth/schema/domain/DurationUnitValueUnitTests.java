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

package org.openmhealth.schema.domain;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import org.testng.annotations.Test;

import java.io.IOException;

import static java.math.BigDecimal.TEN;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.openmhealth.schema.domain.DurationUnit.SECOND;


/**
 * @author Emerson Farrugia
 */
public class DurationUnitValueUnitTests extends AbstractUnitValueUnitTests {

    public static final String SCHEMA_FILENAME = "schema/omh/duration-unit-value-1.0.json";

    @Override
    protected String getSchemaFilename() {
        return SCHEMA_FILENAME;
    }

    @Override
    protected UnitValue newUnitValue() {
        return new DurationUnitValue(SECOND, TEN);
    }

    @Test
    public void deserializationShouldCreateValidObject() throws IOException, ProcessingException {

        String valueAsString = "{\"unit\":\"sec\",\"value\":10}";

        DurationUnitValue value = objectMapper.readValue(valueAsString, DurationUnitValue.class);

        assertThat(value, notNullValue());
        assertThat(value.getTypedUnit(), equalTo(SECOND));
        assertThat(value.getValue(), equalTo(TEN));
    }
}
