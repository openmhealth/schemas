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

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static java.time.ZoneOffset.UTC;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.openmhealth.schema.domain.omh.DescriptiveStatistic.MAXIMUM;
import static org.openmhealth.schema.domain.omh.DurationUnit.*;


/**
 * @author Emerson Farrugia
 */
public class SleepDurationUnitTests extends SerializationUnitTests {

    public static final String SCHEMA_FILENAME = "schema/omh/sleep-duration-1.0.json";

    @Test(expectedExceptions = NullPointerException.class)
    public void constructorShouldThrowExceptionOnUndefinedSleepDuration() {

        new SleepDuration.Builder(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void constructorShouldThrowExceptionOnInvalidDurationUnit() {

        new SleepDuration.Builder(new DurationUnitValue(WEEK, ONE));
    }

    @Test
    public void buildShouldConstructMeasureUsingOnlyRequiredProperties() {

        DurationUnitValue durationUnitValue = new DurationUnitValue(HOUR, TEN);

        SleepDuration sleepDuration = new SleepDuration.Builder(durationUnitValue).build();

        assertThat(sleepDuration, notNullValue());
        assertThat(sleepDuration.getSleepDuration(), equalTo(durationUnitValue));
        assertThat(sleepDuration.getEffectiveTimeFrame(), nullValue());
        assertThat(sleepDuration.getDescriptiveStatistic(), nullValue());
        assertThat(sleepDuration.getUserNotes(), nullValue());
    }

    @Test
    public void buildShouldConstructMeasureUsingOptionalProperties() {

        DurationUnitValue durationUnitValue = new DurationUnitValue(HOUR, TEN);

        TimeInterval effectiveTimeInterval = TimeInterval.ofEndDateTimeAndDuration(
                OffsetDateTime.now(),
                new DurationUnitValue(DAY, TEN));

        SleepDuration sleepDuration = new SleepDuration.Builder(durationUnitValue)
                .setEffectiveTimeFrame(effectiveTimeInterval)
                .setDescriptiveStatistic(MAXIMUM)
                .setUserNotes("feeling fine")
                .build();

        assertThat(sleepDuration, notNullValue());
        assertThat(sleepDuration.getSleepDuration(), equalTo(durationUnitValue));
        assertThat(sleepDuration.getEffectiveTimeFrame(), equalTo(new TimeFrame(effectiveTimeInterval)));
        assertThat(sleepDuration.getDescriptiveStatistic(), equalTo(MAXIMUM));
        assertThat(sleepDuration.getUserNotes(), equalTo("feeling fine"));
    }

    @Override
    protected String getSchemaFilename() {
        return SCHEMA_FILENAME;
    }

    @Test
    public void measureShouldSerializeCorrectly() throws Exception {

        SleepDuration sleepDuration = new SleepDuration.Builder(new DurationUnitValue(HOUR, BigDecimal.valueOf(6)))
                .setEffectiveTimeFrame(TimeInterval.ofStartDateTimeAndEndDateTime(
                        OffsetDateTime.of(2013, 2, 5, 20, 35, 0, 0, UTC),
                        OffsetDateTime.of(2013, 2, 6, 6, 35, 0, 0, UTC)
                ))
                .setUserNotes("slept well")
                .build();

        String document = "{\n" +
                "    \"sleep_duration\": {\n" +
                "        \"value\": 6,\n" +
                "        \"unit\": \"h\"\n" +
                "    },\n" +
                "    \"effective_time_frame\": {\n" +
                "        \"time_interval\": {\n" +
                "            \"start_date_time\": \"2013-02-05T20:35:00Z\",\n" +
                "            \"end_date_time\": \"2013-02-06T06:35:00Z\"\n" +
                "        }\n" +
                "    },\n" +
                "    \"user_notes\": \"slept well\"\n" +
                "}";

        serializationShouldCreateValidDocument(sleepDuration, document);
        deserializationShouldCreateValidObject(document, sleepDuration);
    }
}