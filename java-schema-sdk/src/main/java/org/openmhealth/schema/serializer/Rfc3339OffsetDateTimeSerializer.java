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

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.OffsetDateTimeSerializer;

import java.io.IOException;
import java.time.OffsetDateTime;


/**
 * This class serializes {@link OffsetDateTime} values to {@link String}s according to <a
 * href="https://www.ietf.org/rfc/rfc3339.txt">RFC 3339</a>. The default {@link OffsetDateTimeSerializer} is not
 * sufficient because it truncates zero second fields.
 *
 * @author Emerson Farrugia
 */
public class Rfc3339OffsetDateTimeSerializer extends StdSerializer<OffsetDateTime> {

    public Rfc3339OffsetDateTimeSerializer(Class<OffsetDateTime> t) {
        super(t);
    }

    @Override
    public void serialize(OffsetDateTime instant, JsonGenerator generator, SerializerProvider provider)
            throws IOException {

        StringBuilder builder = new StringBuilder();

        builder.append(instant.toLocalDateTime().toString());

        if (instant.getSecond() == 0 && instant.getNano() == 0) {
            builder.append(":00");
        }

        builder.append(instant.getOffset().toString());

        generator.writeString(builder.toString());
    }
}
