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

import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;


/**
 * @author Emerson Farrugia
 */
public class SchemaIdUnitTests extends SerializationUnitTests {

    public static final String SCHEMA_FILENAME = "schema/omh/schema-id-1.0.json";

    private final static String TEST_NAMESPACE = "test";
    private final static String TEST_NAME = "test";
    private final static String TEST_VERSION = "1.3";

    @Test(expectedExceptions = NullPointerException.class)
    public void constructorShouldThrowExceptionOnUndefinedNamespace() {

        new SchemaId(null, TEST_NAME, TEST_VERSION);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void constructorShouldThrowExceptionOnEmptyNamespace() {

        new SchemaId("", TEST_NAME, TEST_VERSION);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void constructorShouldThrowExceptionOnUndefinedName() {

        new SchemaId(TEST_NAMESPACE, null, TEST_VERSION);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void constructorShouldThrowExceptionOnEmptyName() {

        new SchemaId(TEST_NAMESPACE, "", TEST_VERSION);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void constructorShouldThrowExceptionOnEmptyVersion() {

        new SchemaId(TEST_NAMESPACE, TEST_NAME, "");
    }

    @Test
    public void constructorShouldWork() {

        SchemaId schemaId = new SchemaId(TEST_NAMESPACE, TEST_NAME, TEST_VERSION);

        assertThat(schemaId, notNullValue());
        assertThat(schemaId.getNamespace(), equalTo(TEST_NAMESPACE));
        assertThat(schemaId.getName(), equalTo(TEST_NAME));
        assertThat(schemaId.getVersion(), equalTo(new SchemaVersion(1, 3)));
    }

    @Override
    protected String getSchemaFilename() {
        return SCHEMA_FILENAME;
    }

    @Test
    public void objectShouldSerializeCorrectly() throws Exception {

        SchemaId schemaId = new SchemaId(TEST_NAMESPACE, TEST_NAME, TEST_VERSION);

        String document = "{\n" +
                "    \"namespace\": \"test\",\n" +
                "    \"name\": \"test\",\n" +
                "    \"version\": \"1.3\"\n" +
                "}";

        serializationShouldCreateValidDocument(schemaId, document);
        deserializationShouldCreateValidObject(document, schemaId);
    }
}