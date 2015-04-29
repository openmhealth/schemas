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

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import org.testng.annotations.Test;

import java.io.IOException;

import static java.math.BigDecimal.TEN;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.openmhealth.schema.domain.LengthUnit.METER;


/**
 * @author Emerson Farrugia
 */
public class LengthUnitValueUnitTests extends AbstractUnitValueUnitTests {

    public static final String SCHEMA_FILENAME = "schema/omh/length-unit-value-1.0.json";

    @Override
    protected String getSchemaFilename() {
        return SCHEMA_FILENAME;
    }

    @Test
    public void serializationShouldCreateValidDocument() throws IOException, ProcessingException {

        LengthUnitValue value = new LengthUnitValue(METER, TEN);

        String valueAsString = objectMapper.writeValueAsString(value);
        JsonNode valueAsTree = objectMapper.readTree(valueAsString);

        ProcessingReport report = schema.validate(valueAsTree);

        assertThat(report.isSuccess(), equalTo(true));
    }

    @Test
    public void deserializationShouldCreateValidObject() throws IOException, ProcessingException {

        String valueAsString = "{\"unit\":\"m\",\"value\":10}";

        LengthUnitValue value = objectMapper.readValue(valueAsString, LengthUnitValue.class);

        assertThat(value, notNullValue());
        assertThat(value.getTypedUnit(), equalTo(METER));
        assertThat(value.getValue(), equalTo(TEN));
    }

}
