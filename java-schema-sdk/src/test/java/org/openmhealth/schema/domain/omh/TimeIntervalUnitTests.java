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

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static java.math.BigDecimal.ONE;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.openmhealth.schema.domain.omh.DurationUnit.HOUR;
import static org.openmhealth.schema.domain.omh.PartOfDay.MORNING;


/**
 * @author Emerson Farrugia
 */
public class TimeIntervalUnitTests extends SerializationUnitTests {

    public static final String SCHEMA_FILENAME = "schema/omh/time-interval-1.0.json";

    @Test(expectedExceptions = NullPointerException.class)
    public void ofStartDateTimeAndEndDateTimeShouldThrowExceptionOnUndefinedStartDateTime() throws Exception {

        TimeInterval.ofStartDateTimeAndEndDateTime(null, OffsetDateTime.now());
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void ofStartDateTimeAndEndDateTimeShouldThrowExceptionOnUndefinedEndDateTime() throws Exception {

        TimeInterval.ofStartDateTimeAndEndDateTime(OffsetDateTime.now(), null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void ofStartDateTimeAndEndDateTimeShouldThrowExceptionOnReversedDateTimes() throws Exception {

        TimeInterval.ofStartDateTimeAndEndDateTime(OffsetDateTime.now(), OffsetDateTime.now().minusDays(1));
    }

    @Test
    public void ofStartDateTimeAndEndDateTimeShouldConstructTimeInterval() throws Exception {

        OffsetDateTime startDateTime = OffsetDateTime.now().minusDays(1);
        OffsetDateTime endDateTime = OffsetDateTime.now();

        TimeInterval timeInterval = TimeInterval.ofStartDateTimeAndEndDateTime(startDateTime, endDateTime);

        assertThat(timeInterval, notNullValue());
        assertThat(timeInterval.getStartDateTime(), equalTo(startDateTime));
        assertThat(timeInterval.getEndDateTime(), equalTo(endDateTime));
        assertThat(timeInterval.getDuration(), nullValue());
        assertThat(timeInterval.getDate(), nullValue());
        assertThat(timeInterval.getPartOfDay(), nullValue());
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void ofStartDateTimeAndDurationShouldThrowExceptionOnUndefinedStartDateTime() throws Exception {

        TimeInterval.ofStartDateTimeAndDuration(null, new DurationUnitValue(HOUR, ONE));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void ofStartDateTimeAndDurationShouldThrowExceptionOnUndefinedDuration() throws Exception {

        TimeInterval.ofStartDateTimeAndDuration(OffsetDateTime.now(), null);
    }

    @Test
    public void ofStartDateTimeAndDurationShouldConstructTimeInterval() throws Exception {

        OffsetDateTime startDateTime = OffsetDateTime.now().minusDays(1);
        DurationUnitValue duration = new DurationUnitValue(HOUR, ONE);

        TimeInterval timeInterval = TimeInterval.ofStartDateTimeAndDuration(startDateTime, duration);

        assertThat(timeInterval, notNullValue());
        assertThat(timeInterval.getStartDateTime(), equalTo(startDateTime));
        assertThat(timeInterval.getEndDateTime(), nullValue());
        assertThat(timeInterval.getDuration(), equalTo(duration));
        assertThat(timeInterval.getDate(), nullValue());
        assertThat(timeInterval.getPartOfDay(), nullValue());
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void ofEndDateTimeAndDurationShouldThrowExceptionOnUndefinedEndDateTime() throws Exception {

        TimeInterval.ofEndDateTimeAndDuration(null, new DurationUnitValue(HOUR, ONE));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void ofEndDateTimeAndDurationShouldThrowExceptionOnUndefinedDuration() throws Exception {

        TimeInterval.ofEndDateTimeAndDuration(OffsetDateTime.now(), null);
    }

    @Test
    public void ofEndDateTimeAndDurationShouldConstructTimeInterval() throws Exception {

        OffsetDateTime endDateTime = OffsetDateTime.now().minusDays(1);
        DurationUnitValue duration = new DurationUnitValue(HOUR, ONE);

        TimeInterval timeInterval = TimeInterval.ofEndDateTimeAndDuration(endDateTime, duration);

        assertThat(timeInterval, notNullValue());
        assertThat(timeInterval.getStartDateTime(), nullValue());
        assertThat(timeInterval.getEndDateTime(), equalTo(endDateTime));
        assertThat(timeInterval.getDuration(), equalTo(duration));
        assertThat(timeInterval.getDate(), nullValue());
        assertThat(timeInterval.getPartOfDay(), nullValue());
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void ofDateAndPartOfDayShouldThrowExceptionOnUndefinedDate() throws Exception {

        TimeInterval.ofDateAndPartOfDay(null, MORNING);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void ofDateAndPartOfDayShouldThrowExceptionOnUndefinedPartOfDay() throws Exception {

        TimeInterval.ofDateAndPartOfDay(LocalDate.now(), null);
    }

    @Test
    public void ofDateAndPartOfDayShouldConstructTimeInterval() throws Exception {

        LocalDate date = LocalDate.now();
        PartOfDay partOfDay = MORNING;

        TimeInterval timeInterval = TimeInterval.ofDateAndPartOfDay(date, partOfDay);

        assertThat(timeInterval, notNullValue());
        assertThat(timeInterval.getStartDateTime(), nullValue());
        assertThat(timeInterval.getEndDateTime(), nullValue());
        assertThat(timeInterval.getDuration(), nullValue());
        assertThat(timeInterval.getDate(), equalTo(date));
        assertThat(timeInterval.getPartOfDay(), equalTo(partOfDay));
    }

    @Override
    protected String getSchemaFilename() {
        return SCHEMA_FILENAME;
    }

    @Test
    public void ofStartDateTimeAndEndDateTimeShouldSerializeCorrectlyWithoutOffsets() throws Exception {

        OffsetDateTime startDateTime = OffsetDateTime.of(2013, 2, 5, 7, 25, 0, 0, ZoneOffset.UTC);
        OffsetDateTime endDateTime = OffsetDateTime.of(2013, 2, 5, 7, 35, 20, 0, ZoneOffset.UTC);

        TimeInterval timeInterval = TimeInterval.ofStartDateTimeAndEndDateTime(startDateTime, endDateTime);

        String document = "{\n" +
                "    \"start_date_time\": \"2013-02-05T07:25:00Z\",\n" +
                "    \"end_date_time\": \"2013-02-05T07:35:20Z\"\n" +
                "}";

        serializationShouldCreateValidDocument(timeInterval, document);
        deserializationShouldCreateValidObject(document, timeInterval);
    }

    @Test
    public void ofStartDateTimeAndEndDateTimeShouldSerializeCorrectlyWithOffsets() throws Exception {

        OffsetDateTime startDateTime = OffsetDateTime.of(2013, 2, 5, 7, 25, 0, 0, ZoneOffset.ofHours(1));
        OffsetDateTime endDateTime = OffsetDateTime.of(2013, 2, 5, 7, 35, 20, 0, ZoneOffset.ofHours(-1));

        TimeInterval timeInterval = TimeInterval.ofStartDateTimeAndEndDateTime(startDateTime, endDateTime);

        String document = "{\n" +
                "    \"start_date_time\": \"2013-02-05T07:25:00+01:00\",\n" +
                "    \"end_date_time\": \"2013-02-05T07:35:20-01:00\"\n" +
                "}";

        serializationShouldCreateValidDocument(timeInterval, document);
        deserializationShouldCreateValidObject(document, timeInterval);
    }

    @Test
    public void ofStartDateTimeAndDurationShouldSerializeCorrectlyWithoutOffsets() throws Exception {

        OffsetDateTime startDateTime = OffsetDateTime.of(2013, 2, 5, 7, 25, 0, 0, ZoneOffset.UTC);
        DurationUnitValue duration = new DurationUnitValue(DurationUnit.DAY, ONE);

        TimeInterval timeInterval = TimeInterval.ofStartDateTimeAndDuration(startDateTime, duration);

        String document = "{\n" +
                "    \"start_date_time\": \"2013-02-05T07:25:00Z\",\n" +
                "    \"duration\": {\n" +
                "        \"value\": 1,\n" +
                "        \"unit\": \"d\"\n" +
                "    }\n" +
                "}";

        serializationShouldCreateValidDocument(timeInterval, document);
        deserializationShouldCreateValidObject(document, timeInterval);
    }

    @Test
    public void ofStartDateTimeAndDurationShouldSerializeCorrectlyWithOffsets() throws Exception {

        OffsetDateTime startDateTime = OffsetDateTime.of(2013, 2, 5, 7, 25, 0, 0, ZoneOffset.ofHours(1));
        DurationUnitValue duration = new DurationUnitValue(DurationUnit.DAY, ONE);

        TimeInterval timeInterval = TimeInterval.ofStartDateTimeAndDuration(startDateTime, duration);

        String document = "{\n" +
                "    \"start_date_time\": \"2013-02-05T07:25:00+01:00\",\n" +
                "    \"duration\": {\n" +
                "        \"value\": 1,\n" +
                "        \"unit\": \"d\"\n" +
                "    }\n" +
                "}";

        serializationShouldCreateValidDocument(timeInterval, document);
        deserializationShouldCreateValidObject(document, timeInterval);
    }

    @Test
    public void ofEndDateTimeAndDurationShouldSerializeCorrectlyWithoutOffsets() throws Exception {

        OffsetDateTime endDateTime = OffsetDateTime.of(2013, 2, 5, 7, 25, 0, 0, ZoneOffset.UTC);
        DurationUnitValue duration = new DurationUnitValue(DurationUnit.DAY, ONE);

        TimeInterval timeInterval = TimeInterval.ofEndDateTimeAndDuration(endDateTime, duration);

        String document = "{\n" +
                "    \"end_date_time\": \"2013-02-05T07:25:00Z\",\n" +
                "    \"duration\": {\n" +
                "        \"value\": 1,\n" +
                "        \"unit\": \"d\"\n" +
                "    }\n" +
                "}";

        serializationShouldCreateValidDocument(timeInterval, document);
        deserializationShouldCreateValidObject(document, timeInterval);
    }

    @Test
    public void ofEndDateTimeAndDurationShouldSerializeCorrectlyWithOffsets() throws Exception {

        OffsetDateTime endDateTime = OffsetDateTime.of(2013, 2, 5, 7, 25, 0, 0, ZoneOffset.ofHours(1));
        DurationUnitValue duration = new DurationUnitValue(DurationUnit.DAY, ONE);

        TimeInterval timeInterval = TimeInterval.ofEndDateTimeAndDuration(endDateTime, duration);

        String document = "{\n" +
                "    \"end_date_time\": \"2013-02-05T07:25:00+01:00\",\n" +
                "    \"duration\": {\n" +
                "        \"value\": 1,\n" +
                "        \"unit\": \"d\"\n" +
                "    }\n" +
                "}";

        serializationShouldCreateValidDocument(timeInterval, document);
        deserializationShouldCreateValidObject(document, timeInterval);
    }

    @Test
    public void ofDateAndPartOfDayShouldSerializeCorrectly() throws Exception {

        LocalDate date = LocalDate.of(2013, 2, 5);
        PartOfDay partOfDay = MORNING;

        TimeInterval timeInterval = TimeInterval.ofDateAndPartOfDay(date, partOfDay);

        String document = "{\n" +
                "    \"date\": \"2013-02-05\",\n" +
                "    \"part_of_day\": \"morning\"\n" +
                "}";

        serializationShouldCreateValidDocument(timeInterval, document);
        deserializationShouldCreateValidObject(document, timeInterval);
    }
}
