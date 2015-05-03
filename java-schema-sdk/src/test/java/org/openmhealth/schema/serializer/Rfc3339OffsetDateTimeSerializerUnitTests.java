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

package org.openmhealth.schema.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.OffsetDateTime;

import static java.time.ZoneOffset.UTC;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


/**
 * A suite of unit tests for the RFC 3339 {@link OffsetDateTime} serializer.
 *
 * @author Emerson Farrugia
 */
public class Rfc3339OffsetDateTimeSerializerUnitTests {

    private ObjectMapper objectMapper;

    @BeforeClass
    public void initializeObjectMapper() throws IOException {

        objectMapper = new ObjectMapper();

        SimpleModule rfc3339Module = new SimpleModule("rfc3339Module");
        rfc3339Module.addSerializer(new Rfc3339OffsetDateTimeSerializer(OffsetDateTime.class));
        objectMapper.registerModule(rfc3339Module);
    }

    @Test
    public void serializeShouldNotAppendSecondFieldWhenInstantHasNonZeroSeconds() throws IOException {

        OffsetDateTime instant = OffsetDateTime.of(2013, 2, 5, 7, 35, 12, 0, UTC);

        // since we're testing using ObjectMapper instead of the serializer, the string will get wrapped in quotes
        assertThat(objectMapper.writeValueAsString(instant), equalTo("\"2013-02-05T07:35:12Z\""));
    }

    @Test
    public void serializeShouldAppendSecondFieldWhenInstantSecondsAreZero() throws IOException {

        OffsetDateTime instant = OffsetDateTime.of(2013, 2, 5, 7, 35, 0, 0, UTC);

        assertThat(objectMapper.writeValueAsString(instant), equalTo("\"2013-02-05T07:35:00Z\""));
    }

    @Test
    public void serializeShouldNotAppendSecondFieldWhenInstantHasNonZeroNanoseconds() throws IOException {

        OffsetDateTime instant = OffsetDateTime.of(2013, 2, 5, 7, 35, 0, 123_000_000, UTC);

        assertThat(objectMapper.writeValueAsString(instant), equalTo("\"2013-02-05T07:35:00.123Z\""));
    }
}