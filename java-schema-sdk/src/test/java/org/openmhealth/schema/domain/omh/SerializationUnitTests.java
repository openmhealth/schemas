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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.annotation.Nullable;
import java.io.File;
import java.util.Collections;
import java.util.Iterator;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.openmhealth.schema.configuration.JacksonConfiguration.newObjectMapper;


/**
 * A base class for serialization and deserialization unit tests.
 *
 * @author Emerson Farrugia
 */
public abstract class SerializationUnitTests {

    protected static final ObjectMapper objectMapper = newObjectMapper();
    private static final JsonSchemaFactory jsonSchemaFactory = JsonSchemaFactory.byDefault();

    protected static JsonSchema schema;


    @BeforeClass
    public void loadSchema() throws ProcessingException {
        schema = jsonSchemaFactory.getJsonSchema(getSchemaUri());
    }

    /**
     * @return the URI of the schema corresponding to the class under test
     */
    protected String getSchemaUri() {
        return new File(getSchemaFilename()).toURI().toString();
    }

    /**
     * This convenience method is intentionally not abstract. If the method is not overridden,
     * the {@link SerializationUnitTests#getSchemaUri()} should be overridden to provide the schema URI.
     *
     * @return the filename of the schema corresponding to the class under test
     */
    @Nullable
    protected String getSchemaFilename() {
        return null;
    }

    /**
     * A parameterized test that checks if objects are serialized correctly.
     */
    @Test(dataProvider = "expectedDocumentProvider")
    public void serializationShouldCreateValidDocument(Object object, String expectedDocument) throws Exception {

        String documentAsString = objectMapper.writeValueAsString(object);
        JsonNode documentNode = objectMapper.readTree(documentAsString);

        ProcessingReport report = schema.validate(documentNode);
        assertThat(report.isSuccess(), equalTo(true));

        JsonNode expectedDocumentNode = objectMapper.readTree(expectedDocument);
        assertThat(documentNode, equalTo(expectedDocumentNode));
    }

    @DataProvider(name = "expectedDocumentProvider")
    protected Iterator<Object[]> newExpectedDocumentProvider() {

        return Collections.emptyIterator();
    }

    /**
     * A parameterized test that checks if objects are deserialized correctly.
     */
    @Test(dataProvider = "expectedObjectProvider")
    public void deserializationShouldCreateValidObject(String document, Object expectedObject) throws Exception {

        Object object = objectMapper.readValue(document, expectedObject.getClass());

        assertThat(object, notNullValue());
        assertThat(object, equalTo(expectedObject));
    }

    @DataProvider(name = "expectedObjectProvider")
    protected Iterator<Object[]> newExpectedObjectProvider() {

        return Collections.emptyIterator();
    }
}
