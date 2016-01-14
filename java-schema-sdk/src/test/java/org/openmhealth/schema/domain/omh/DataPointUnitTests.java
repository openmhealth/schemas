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

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static java.time.ZoneOffset.UTC;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.openmhealth.schema.domain.omh.LengthUnit.MILE;
import static org.openmhealth.schema.domain.omh.PhysicalActivity.SelfReportedIntensity.MODERATE;
import static org.openmhealth.schema.domain.omh.TimeFrameFactory.FIXED_MONTH;


/**
 * @author Emerson Farrugia
 */
public class DataPointUnitTests extends SerializationUnitTests {

    public static final String SCHEMA_FILENAME = "schema/omh/data-point-1.0.json";

    private DataPointHeader header;

    @BeforeTest
    public void initializeFixture() {

        String id = "123e4567-e89b-12d3-a456-426655440000";
        SchemaId schemaId = new SchemaId("omh", "physical-activity", "1.0");
        OffsetDateTime creationDateTime = OffsetDateTime.of(2013, 2, 5, 7, 30, 0, 0, UTC);

        header = new DataPointHeader.Builder(id, schemaId, creationDateTime)
                .setAcquisitionProvenance(
                        new DataPointAcquisitionProvenance.Builder("RunKeeper")
                                .setSourceCreationDateTime(OffsetDateTime.of(2013, 2, 5, 7, 25, 0, 0, UTC))
                                .setModality(DataPointModality.SENSED)
                                .build()
                )
                .build();
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void constructorShouldThrowExceptionOnUndefinedHeader() {

        new DataPoint<>(null, Collections.emptyMap());
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void constructorShouldThrowExceptionOnUndefinedBody() {

        new DataPoint<>(header, null);
    }

    @Test
    public void constructorShouldWork() {

        Map<String,String> body = new HashMap<>();
        body.put("key", "value");

        DataPoint dataPoint = new DataPoint<>(header, body);

        assertThat(dataPoint, notNullValue());
        assertThat(dataPoint.getHeader(), equalTo(header));
        assertThat(dataPoint.getBody(), equalTo(body));
    }

    @Override
    protected String getSchemaFilename() {
        return SCHEMA_FILENAME;
    }

    @Test
    public void objectShouldSerializeCorrectly() throws Exception {

        PhysicalActivity physicalActivity = new PhysicalActivity.Builder("walking")
                .setDistance(new LengthUnitValue(MILE, BigDecimal.valueOf(1.5)))
                .setReportedActivityIntensity(MODERATE)
                .setEffectiveTimeFrame(FIXED_MONTH)
                .build();

        DataPoint<Object> dataPoint = new DataPoint<>(header, physicalActivity);

        String document = "{\n" +
                "    \"header\": {\n" +
                "        \"id\": \"123e4567-e89b-12d3-a456-426655440000\",\n" +
                "        \"creation_date_time\": \"2013-02-05T07:30:00Z\",\n" +
                "        \"schema_id\": {\n" +
                "            \"namespace\": \"omh\",\n" +
                "            \"name\": \"physical-activity\",\n" +
                "            \"version\": \"1.0\"\n" +
                "        },\n" +
                "        \"acquisition_provenance\": {\n" +
                "            \"source_name\": \"RunKeeper\",\n" +
                "            \"source_creation_date_time\": \"2013-02-05T07:25:00Z\",\n" +
                "            \"modality\": \"sensed\"\n" +
                "        }\n" +
                "    },\n" +
                "    \"body\": {\n" +
                "        \"activity_name\": \"walking\",\n" +
                "        \"distance\": {\n" +
                "            \"value\": 1.5,\n" +
                "            \"unit\": \"mi\"\n" +
                "        },\n" +
                "        \"reported_activity_intensity\": \"moderate\",\n" +
                "        \"effective_time_frame\": {\n" +
                "            \"time_interval\": {\n" +
                "                \"start_date_time\": \"2015-10-01T00:00:00-07:00\",\n" +
                "                \"end_date_time\": \"2015-11-01T00:00:00-07:00\"\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "}";

        serializationShouldCreateValidDocument(dataPoint, document);

        // it's not yet clear whether it's necessary to deserialize into a typed data point. doing so is possible
        // but not simple, and requires a @JsonTypeResolver implementation to resolve the type of the body
        // deserializationShouldCreateValidObject(document, dataPoint);
    }
}