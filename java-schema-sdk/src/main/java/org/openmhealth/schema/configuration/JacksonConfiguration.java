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

package org.openmhealth.schema.configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.openmhealth.schema.serializer.Rfc3339OffsetDateTimeSerializer;

import java.time.OffsetDateTime;


/**
 * A Jackson {@link ObjectMapper} configuration that matches schema conventions.
 *
 * @author Emerson Farrugia
 */
public class JacksonConfiguration {

    public static ObjectMapper newObjectMapper() {

        ObjectMapper objectMapper = new ObjectMapper();

        // we represent JSON numbers as Java BigDecimals
        objectMapper.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);

        // we serialize dates, date times, and times as strings, not numbers
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // we preserve time zone offsets when deserializing
        objectMapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);

        // we default to the ISO8601 format for JSR-310 and support Optional
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.registerModule(new Jdk8Module());

        // but we have to explicitly support the RFC3339 format over ISO8601 to make JSON Schema happy, specifically to
        // prevent the truncation of zero second fields
        SimpleModule rfc3339Module = new SimpleModule("rfc3339Module");
        rfc3339Module.addSerializer(new Rfc3339OffsetDateTimeSerializer(OffsetDateTime.class));
        objectMapper.registerModule(rfc3339Module);

        return objectMapper;
    }
}
