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

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.OffsetDateTime;
import java.util.UUID;

import static java.time.ZoneOffset.UTC;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;


/**
 * @author Emerson Farrugia
 */
public class DataPointHeaderUnitTests extends SerializationUnitTests {

    public static final String SCHEMA_FILENAME = "schema/omh/header-1.0.json";

    private String id;
    private SchemaId bodySchemaId;
    private OffsetDateTime creationDateTime;

    @BeforeTest
    public void initializeFixture() {

        id = UUID.randomUUID().toString();
        bodySchemaId = new SchemaId("omh", "body-weight", "1.0");
        creationDateTime = OffsetDateTime.now();
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void constructorShouldThrowExceptionOnUndefinedId() {

        new DataPointHeader.Builder(null, bodySchemaId, creationDateTime);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void constructorShouldThrowExceptionOnEmptyId() {

        new DataPointHeader.Builder("", bodySchemaId, creationDateTime);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void constructorShouldThrowExceptionOnUndefinedSchemaId() {

        new DataPointHeader.Builder(id, null, creationDateTime);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void constructorShouldThrowExceptionOnUndefinedCreationDateTime() {

        new DataPointHeader.Builder(id, bodySchemaId, null);
    }

    @Test
    public void buildShouldConstructObjectUsingOnlyRequiredProperties() {

        DataPointHeader header = new DataPointHeader.Builder(id, bodySchemaId, creationDateTime).build();

        assertThat(header, notNullValue());
        assertThat(header.getId(), equalTo(id));
        assertThat(header.getBodySchemaId(), equalTo(bodySchemaId));
        assertThat(header.getCreationDateTime(), equalTo(creationDateTime));
        assertThat(header.getAcquisitionProvenance(), nullValue());
        assertThat(header.getUserId(), nullValue());
    }

    @Test
    public void buildShouldConstructObjectUsingOptionalProperties() {

        DataPointAcquisitionProvenance acquisitionProvenance =
                new DataPointAcquisitionProvenance.Builder("wrist strap").build();

        DataPointHeader header = new DataPointHeader.Builder(id, bodySchemaId, creationDateTime)
                .setAcquisitionProvenance(acquisitionProvenance)
                .setUserId("user1")
                .build();

        assertThat(header, notNullValue());
        assertThat(header.getId(), equalTo(id));
        assertThat(header.getBodySchemaId(), equalTo(bodySchemaId));
        assertThat(header.getCreationDateTime(), equalTo(creationDateTime));
        assertThat(header.getAcquisitionProvenance(), equalTo(acquisitionProvenance));
        assertThat(header.getUserId(), equalTo("user1"));
    }

    @Override
    protected String getSchemaFilename() {
        return SCHEMA_FILENAME;
    }

    @Test
    public void objectHavingRequiredPropertiesShouldSerializeCorrectly() throws Exception {

        String id = "123e4567-e89b-12d3-a456-426655440000";
        SchemaId schemaId = new SchemaId("omh", "physical-activity", "1.1.RC1");
        OffsetDateTime creationDateTime = OffsetDateTime.of(2013, 2, 5, 7, 30, 0, 0, UTC);

        DataPointHeader header = new DataPointHeader.Builder(id, schemaId, creationDateTime)
                .build();

        String document = "{\n" +
                "    \"id\": \"123e4567-e89b-12d3-a456-426655440000\",\n" +
                "    \"creation_date_time\": \"2013-02-05T07:30:00Z\",\n" +
                "    \"schema_id\": {\n" +
                "        \"namespace\": \"omh\",\n" +
                "        \"name\": \"physical-activity\",\n" +
                "        \"version\": \"1.1.RC1\"\n" +
                "    }\n" +
                "}";

        serializationShouldCreateValidDocument(header, document);
        deserializationShouldCreateValidObject(document, header);
    }

    @Test
    public void objectHavingOptionalPropertiesShouldSerializeCorrectly() throws Exception {

        String id = "123e4567-e89b-12d3-a456-426655440000";
        SchemaId schemaId = new SchemaId("omh", "physical-activity", "1.1.RC1");
        OffsetDateTime creationDateTime = OffsetDateTime.of(2013, 2, 5, 7, 30, 0, 0, UTC);

        DataPointHeader header = new DataPointHeader.Builder(id, schemaId, creationDateTime)
                .setAcquisitionProvenance(
                        new DataPointAcquisitionProvenance.Builder("RunKeeper")
                                .setSourceCreationDateTime(OffsetDateTime.of(2013, 2, 5, 7, 25, 0, 0, UTC))
                                .setModality(DataPointModality.SELF_REPORTED)
                                .build()
                )
                .setUserId("user1")
                .build();

        String document = "{\n" +
                "    \"id\": \"123e4567-e89b-12d3-a456-426655440000\",\n" +
                "    \"creation_date_time\": \"2013-02-05T07:30:00Z\",\n" +
                "    \"schema_id\": {\n" +
                "        \"namespace\": \"omh\",\n" +
                "        \"name\": \"physical-activity\",\n" +
                "        \"version\": \"1.1.RC1\"\n" +
                "    },\n" +
                "    \"acquisition_provenance\": {\n" +
                "        \"source_name\": \"RunKeeper\",\n" +
                "        \"source_creation_date_time\": \"2013-02-05T07:25:00Z\",\n" +
                "        \"modality\": \"self-reported\"\n" +
                "    },\n" +
                "    \"user_id\": \"user1\"\n" +
                "}";

        serializationShouldCreateValidDocument(header, document);
        deserializationShouldCreateValidObject(document, header);
    }
}