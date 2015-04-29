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

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import org.testng.annotations.BeforeClass;

import javax.annotation.Nullable;
import java.io.File;


/**
 * A base class for unit value unit tests.
 *
 * @author Emerson Farrugia
 */
public abstract class AbstractUnitValueUnitTests {

    protected static final ObjectMapper objectMapper = new ObjectMapper();
    private static final JsonSchemaFactory jsonSchemaFactory = JsonSchemaFactory.byDefault();

    protected static JsonSchema schema;

    @BeforeClass
    public void initializeObjectMapper() {
        objectMapper.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);
    }

    @BeforeClass
    public void loadSchema() throws ProcessingException {
        schema = jsonSchemaFactory.getJsonSchema(getSchemaUri());
    }

    /**
     * @return the URI of the unit value schema corresponding to the class under test
     */
    protected String getSchemaUri() {
        return new File(getSchemaFilename()).toURI().toString();
    }

    /**
     * @return the filename of the unit value schema corresponding to the class under test
     */
    @Nullable
    protected String getSchemaFilename() {
        return null;
    }
}
