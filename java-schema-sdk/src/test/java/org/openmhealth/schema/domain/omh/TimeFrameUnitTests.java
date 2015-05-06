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

import java.time.OffsetDateTime;

import static java.math.BigDecimal.TEN;
import static java.time.ZoneOffset.UTC;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.openmhealth.schema.domain.omh.DurationUnit.DAY;


/**
 * @author Emerson Farrugia
 */
public class TimeFrameUnitTests extends SerializationUnitTests {

    public static final String SCHEMA_FILENAME = "schema/omh/time-frame-1.0.json";

    @Test
    public void constructorShouldConstructTimeFrameWithTimeInterval() throws Exception {

        OffsetDateTime startDateTime = OffsetDateTime.now().minusDays(1);
        OffsetDateTime endDateTime = OffsetDateTime.now();
        TimeInterval timeInterval = TimeInterval.ofStartDateTimeAndEndDateTime(startDateTime, endDateTime);

        TimeFrame timeFrame = new TimeFrame(timeInterval);

        assertThat(timeFrame, notNullValue());
        assertThat(timeFrame.getTimeInterval(), equalTo(timeInterval));
        assertThat(timeFrame.getDateTime(), nullValue());
    }

    @Test
    public void constructorShouldConstructTimeFrameWithDateTime() throws Exception {

        OffsetDateTime dateTime = OffsetDateTime.now().minusDays(1);

        TimeFrame timeFrame = new TimeFrame(dateTime);

        assertThat(timeFrame, notNullValue());
        assertThat(timeFrame.getTimeInterval(), nullValue());
        assertThat(timeFrame.getDateTime(), equalTo(dateTime));
    }

    @Override
    protected String getSchemaFilename() {
        return SCHEMA_FILENAME;
    }

    @Test
    public void dateTimeTimeFrameShouldSerializeCorrectly() throws Exception {

        OffsetDateTime dateTime = OffsetDateTime.of(2013, 2, 5, 7, 25, 0, 123_000_000, UTC);

        TimeFrame timeFrame = new TimeFrame(dateTime);

        String document = "{\n" +
                "    \"date_time\": \"2013-02-05T07:25:00.123Z\"\n" +
                "}";

        serializationShouldCreateValidDocument(timeFrame, document);
        deserializationShouldCreateValidObject(document, timeFrame);
    }

    @Test
    public void timeIntervalTimeFrameShouldSerializeCorrectly() throws Exception {

        TimeInterval timeInterval = TimeInterval.ofEndDateTimeAndDuration(
                OffsetDateTime.of(2013, 2, 5, 7, 35, 0, 0, UTC),
                new DurationUnitValue(DAY, TEN));

        TimeFrame timeFrame = new TimeFrame(timeInterval);

        String document = "{\n" +
                "    \"time_interval\": {\n" +
                "        \"duration\": {\n" +
                "            \"value\": 10,\n" +
                "            \"unit\": \"d\"\n" +
                "        },\n" +
                "        \"end_date_time\": \"2013-02-05T07:35:00Z\"\n" +
                "    }\n" +
                "}";

        serializationShouldCreateValidDocument(timeFrame, document);
        deserializationShouldCreateValidObject(document, timeFrame);
    }
}