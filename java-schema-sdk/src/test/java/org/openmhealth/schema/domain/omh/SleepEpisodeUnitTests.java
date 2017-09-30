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

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.openmhealth.schema.domain.omh.DescriptiveStatistic.AVERAGE;
import static org.openmhealth.schema.domain.omh.DurationUnit.HOUR;
import static org.openmhealth.schema.domain.omh.DurationUnit.MINUTE;
import static org.openmhealth.schema.domain.omh.PercentUnit.PERCENT;
import static org.openmhealth.schema.domain.omh.TimeFrameFactory.FIXED_NIGHT_TIME_HOURS;


/**
 * @author Emerson Farrugia
 */
public class SleepEpisodeUnitTests extends SerializationUnitTests {

    public static final String SCHEMA_FILENAME = "schema/omh/sleep-episode-1.0.json";

    @Test(expectedExceptions = NullPointerException.class)
    public void constructorShouldThrowExceptionOnUndefinedEffectiveTimeFrame() {

        new SleepEpisode.Builder((TimeFrame) null);
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void buildShouldThrowExceptionOnSettingDescriptiveStatistic() {

        new SleepEpisode.Builder(FIXED_NIGHT_TIME_HOURS).setDescriptiveStatistic(AVERAGE);
    }

    @Test
    public void buildShouldConstructMeasureUsingOnlyRequiredProperties() {

        SleepEpisode sleepEpisode = new SleepEpisode.Builder(FIXED_NIGHT_TIME_HOURS).build();

        assertThat(sleepEpisode, notNullValue());
        assertThat(sleepEpisode.getLatencyToSleepOnset(), nullValue());
        assertThat(sleepEpisode.getLatencyToArising(), nullValue());
        assertThat(sleepEpisode.getTotalSleepTime(), nullValue());
        assertThat(sleepEpisode.getNumberOfAwakenings(), nullValue());
        assertThat(sleepEpisode.getMainSleep(), nullValue());
        assertThat(sleepEpisode.getSleepMaintenanceEfficiencyPercentage(), nullValue());
        assertThat(sleepEpisode.getEffectiveTimeFrame(), equalTo(FIXED_NIGHT_TIME_HOURS));
        assertThat(sleepEpisode.getUserNotes(), nullValue());
    }

    @Test
    public void buildShouldConstructMeasureUsingOptionalProperties() {

        DurationUnitValue twentyFourMinutes = new DurationUnitValue(MINUTE, 24);
        DurationUnitValue oneHour = new DurationUnitValue(HOUR, 1);
        DurationUnitValue sixPointThreeHours = new DurationUnitValue(HOUR, 6.3);
        TypedUnitValue<PercentUnit> eightySevenPercent = new TypedUnitValue<>(PERCENT, 87);

        SleepEpisode sleepEpisode = new SleepEpisode.Builder(FIXED_NIGHT_TIME_HOURS)
                .setLatencyToSleepOnset(twentyFourMinutes)
                .setLatencyToArising(oneHour)
                .setTotalSleepTime(sixPointThreeHours)
                .setNumberOfAwakenings(3)
                .setMainSleep(true)
                .setSleepMaintenanceEfficiencyPercentage(eightySevenPercent)
                .setUserNotes("slept well")
                .build();

        assertThat(sleepEpisode, notNullValue());
        assertThat(sleepEpisode.getLatencyToSleepOnset(), equalTo(twentyFourMinutes));
        assertThat(sleepEpisode.getLatencyToArising(), equalTo(oneHour));
        assertThat(sleepEpisode.getTotalSleepTime(), equalTo(sixPointThreeHours));
        assertThat(sleepEpisode.getNumberOfAwakenings(), equalTo(3));
        assertThat(sleepEpisode.getMainSleep(), equalTo(true));
        assertThat(sleepEpisode.getSleepMaintenanceEfficiencyPercentage(), equalTo(eightySevenPercent));
        assertThat(sleepEpisode.getEffectiveTimeFrame(), equalTo(FIXED_NIGHT_TIME_HOURS));
        assertThat(sleepEpisode.getUserNotes(), equalTo("slept well"));
    }

    @Override
    protected String getSchemaFilename() {
        return SCHEMA_FILENAME;
    }

    @Test
    public void measureShouldSerializeCorrectly() throws Exception {

        SleepEpisode sleepEpisode = new SleepEpisode.Builder(TimeInterval.ofStartDateTimeAndDuration(
                OffsetDateTime.parse("2016-02-05T21:35:00Z"), new DurationUnitValue(HOUR, 9)))
                .setLatencyToSleepOnset(new DurationUnitValue(MINUTE, 17.5))
                .setLatencyToArising(new DurationUnitValue(MINUTE, 5.2))
                .setTotalSleepTime(new DurationUnitValue(HOUR, 8.5))
                .setNumberOfAwakenings(1)
                .setMainSleep(true)
                .setSleepMaintenanceEfficiencyPercentage(new TypedUnitValue<>(PERCENT, 94.4))
                .setUserNotes("slept well")
                .build();

        String document = "{\n" +
                "    \"effective_time_frame\": {\n" +
                "        \"time_interval\": {\n" +
                "            \"start_date_time\": \"2016-02-05T21:35:00Z\",\n" +
                "            \"duration\": {\n" +
                "                \"value\": 9,\n" +
                "                \"unit\": \"h\"\n" +
                "            }\n" +
                "        }\n" +
                "    },\n" +
                "    \"latency_to_sleep_onset\": {\n" +
                "        \"value\": 17.5,\n" +
                "        \"unit\": \"min\"\n" +
                "    },\n" +
                "    \"latency_to_arising\": {\n" +
                "        \"value\": 5.2,\n" +
                "        \"unit\": \"min\"\n" +
                "    },\n" +
                "    \"total_sleep_time\": {\n" +
                "        \"value\": 8.5,\n" +
                "        \"unit\": \"h\"\n" +
                "    },\n" +
                "    \"number_of_awakenings\": 1,\n" +
                "    \"is_main_sleep\": true,\n" +
                "    \"sleep_maintenance_efficiency_percentage\": {\n" +
                "        \"value\": 94.4,\n" +
                "        \"unit\": \"%\"\n" +
                "    },\n" +
                "    \"user_notes\": \"slept well\"\n" +
                "}";

        serializationShouldCreateValidDocument(sleepEpisode, document);
        deserializationShouldCreateValidObject(document, sleepEpisode);
    }
}