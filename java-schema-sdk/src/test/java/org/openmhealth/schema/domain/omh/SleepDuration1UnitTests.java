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

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.openmhealth.schema.domain.omh.DescriptiveStatistic.MAXIMUM;
import static org.openmhealth.schema.domain.omh.DurationUnit.HOUR;
import static org.openmhealth.schema.domain.omh.DurationUnit.WEEK;
import static org.openmhealth.schema.domain.omh.TimeFrameFactory.FIXED_DAY;
import static org.openmhealth.schema.domain.omh.TimeFrameFactory.FIXED_MONTH;


/**
 * @author Emerson Farrugia
 */
public class SleepDuration1UnitTests extends SerializationUnitTests {

    public static final String SCHEMA_FILENAME = "schema/omh/sleep-duration-1.0.json";

    @Test(expectedExceptions = NullPointerException.class)
    public void constructorShouldThrowExceptionOnUndefinedSleepDuration() {

        new SleepDuration1.Builder(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void constructorShouldThrowExceptionOnInvalidDurationUnit() {

        new SleepDuration1.Builder(new DurationUnitValue(WEEK, ONE));
    }

    @Test
    public void buildShouldConstructMeasureUsingOnlyRequiredProperties() {

        DurationUnitValue durationUnitValue = new DurationUnitValue(HOUR, TEN);

        SleepDuration1 sleepDuration = new SleepDuration1.Builder(durationUnitValue).build();

        assertThat(sleepDuration, notNullValue());
        assertThat(sleepDuration.getSleepDuration(), equalTo(durationUnitValue));
        assertThat(sleepDuration.getEffectiveTimeFrame(), nullValue());
        assertThat(sleepDuration.getDescriptiveStatistic(), nullValue());
        assertThat(sleepDuration.getUserNotes(), nullValue());
    }

    @Test
    public void buildShouldConstructMeasureUsingOptionalProperties() {

        DurationUnitValue durationUnitValue = new DurationUnitValue(HOUR, TEN);

        SleepDuration1 sleepDuration = new SleepDuration1.Builder(durationUnitValue)
                .setEffectiveTimeFrame(FIXED_MONTH)
                .setDescriptiveStatistic(MAXIMUM)
                .setUserNotes("feeling fine")
                .build();

        assertThat(sleepDuration, notNullValue());
        assertThat(sleepDuration.getSleepDuration(), equalTo(durationUnitValue));
        assertThat(sleepDuration.getEffectiveTimeFrame(), equalTo(FIXED_MONTH));
        assertThat(sleepDuration.getDescriptiveStatistic(), equalTo(MAXIMUM));
        assertThat(sleepDuration.getUserNotes(), equalTo("feeling fine"));
    }

    @Override
    protected String getSchemaFilename() {
        return SCHEMA_FILENAME;
    }

    @Test
    public void measureShouldSerializeCorrectly() throws Exception {

        SleepDuration1 sleepDuration = new SleepDuration1.Builder(new DurationUnitValue(HOUR, BigDecimal.valueOf(6)))
                .setEffectiveTimeFrame(FIXED_DAY)
                .setUserNotes("slept well")
                .build();

        String document = "{\n" +
                "    \"sleep_duration\": {\n" +
                "        \"value\": 6,\n" +
                "        \"unit\": \"h\"\n" +
                "    },\n" +
                "    \"effective_time_frame\": {\n" +
                "        \"time_interval\": {\n" +
                "            \"start_date_time\": \"2015-10-21T00:00:00-07:00\",\n" +
                "            \"end_date_time\": \"2015-10-22T00:00:00-07:00\"\n" +
                "        }\n" +
                "    },\n" +
                "    \"user_notes\": \"slept well\"\n" +
                "}";

        serializationShouldCreateValidDocument(sleepDuration, document);
        deserializationShouldCreateValidObject(document, sleepDuration);
    }
}