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
import static org.openmhealth.schema.domain.omh.DescriptiveStatistic.MINIMUM;
import static org.openmhealth.schema.domain.omh.DescriptiveStatisticDenominator.DAY;
import static org.openmhealth.schema.domain.omh.DurationUnit.HOUR;
import static org.openmhealth.schema.domain.omh.DurationUnit.WEEK;
import static org.openmhealth.schema.domain.omh.TimeFrameFactory.FIXED_DAY;
import static org.openmhealth.schema.domain.omh.TimeFrameFactory.FIXED_MONTH;


/**
 * @author Emerson Farrugia
 */
public class SleepDuration2UnitTests extends SerializationUnitTests {

    public static final String SCHEMA_FILENAME = "schema/omh/sleep-duration-2.0.json";

    @Test(expectedExceptions = NullPointerException.class)
    public void constructorShouldThrowExceptionOnUndefinedSleepDuration() {

        new SleepDuration2.Builder(null, FIXED_DAY);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void constructorShouldThrowExceptionOnInvalidDurationUnit() {

        new SleepDuration2.Builder(new DurationUnitValue(WEEK, ONE), FIXED_MONTH);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void constructorShouldThrowExceptionOnUndefinedEffectiveTimeFrame() {

        new SleepDuration2.Builder(new DurationUnitValue(HOUR, TEN), (TimeFrame) null);
    }

    @Test
    public void buildShouldConstructMeasureUsingOnlyRequiredProperties() {

        DurationUnitValue durationUnitValue = new DurationUnitValue(HOUR, TEN);

        SleepDuration2 sleepDuration = new SleepDuration2.Builder(durationUnitValue, FIXED_DAY).build();

        assertThat(sleepDuration, notNullValue());
        assertThat(sleepDuration.getSleepDuration(), equalTo(durationUnitValue));
        assertThat(sleepDuration.getEffectiveTimeFrame(), equalTo(FIXED_DAY));
        assertThat(sleepDuration.getDescriptiveStatistic(), nullValue());
        assertThat(sleepDuration.getDescriptiveStatisticDenominator(), nullValue());
        assertThat(sleepDuration.getUserNotes(), nullValue());
    }

    @Test
    public void buildShouldConstructMeasureUsingOptionalProperties() {

        DurationUnitValue durationUnitValue = new DurationUnitValue(HOUR, TEN);

        SleepDuration2 sleepDuration = new SleepDuration2.Builder(durationUnitValue, FIXED_MONTH)
                .setDescriptiveStatistic(MAXIMUM)
                .setDescriptiveStatisticDenominator(DAY)
                .setUserNotes("feeling fine")
                .build();

        assertThat(sleepDuration, notNullValue());
        assertThat(sleepDuration.getSleepDuration(), equalTo(durationUnitValue));
        assertThat(sleepDuration.getEffectiveTimeFrame(), equalTo(FIXED_MONTH));
        assertThat(sleepDuration.getDescriptiveStatistic(), equalTo(MAXIMUM));
        assertThat(sleepDuration.getDescriptiveStatisticDenominator(), equalTo(DAY));
        assertThat(sleepDuration.getUserNotes(), equalTo("feeling fine"));
    }

    @Override
    protected String getSchemaFilename() {
        return SCHEMA_FILENAME;
    }

    @Test
    public void measureShouldSerializeCorrectly() throws Exception {

        DurationUnitValue durationUnitValue = new DurationUnitValue(HOUR, BigDecimal.valueOf(6.2));

        SleepDuration2 sleepDuration = new SleepDuration2.Builder(durationUnitValue, FIXED_MONTH)
                .setDescriptiveStatistic(MINIMUM)
                .setDescriptiveStatisticDenominator(DAY)
                .setUserNotes("slept well")
                .build();

        String document = "{\n" +
                "    \"sleep_duration\": {\n" +
                "        \"value\": 6.2,\n" +
                "        \"unit\": \"h\"\n" +
                "    },\n" +
                "    \"effective_time_frame\": {\n" +
                "        \"time_interval\": {\n" +
                "            \"start_date_time\": \"2015-10-01T00:00:00-07:00\",\n" +
                "            \"end_date_time\": \"2015-11-01T00:00:00-07:00\"\n" +
                "        }\n" +
                "    },\n" +
                "    \"descriptive_statistic\": \"minimum\",\n" +
                "    \"descriptive_statistic_denominator\": \"d\",\n" +
                "    \"user_notes\": \"slept well\"\n" +
                "}";

        serializationShouldCreateValidDocument(sleepDuration, document);
        deserializationShouldCreateValidObject(document, sleepDuration);
    }
}