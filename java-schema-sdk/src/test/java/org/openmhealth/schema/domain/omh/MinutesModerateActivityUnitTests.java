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
import static org.openmhealth.schema.domain.omh.DurationUnit.MINUTE;
import static org.openmhealth.schema.domain.omh.TimeFrameFactory.FIXED_MONTH;


/**
 * @author Emerson Farrugia
 */
public class MinutesModerateActivityUnitTests extends SerializationUnitTests {

    public static final String SCHEMA_FILENAME = "schema/omh/minutes-moderate-activity-1.0.json";

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void constructorShouldThrowExceptionOnInvalidDurationUnit() {

        new MinutesModerateActivity.Builder(new DurationUnitValue(HOUR, ONE));
    }

    @Test
    public void buildShouldConstructMeasureUsingOnlyRequiredProperties() {

        DurationUnitValue durationUnitValue = new DurationUnitValue(MINUTE, TEN);

        MinutesModerateActivity minutesModerateActivity =
                new MinutesModerateActivity.Builder(durationUnitValue).build();

        assertThat(minutesModerateActivity, notNullValue());
        assertThat(minutesModerateActivity.getMinutesModerateActivity(), equalTo(durationUnitValue));
        assertThat(minutesModerateActivity.getEffectiveTimeFrame(), nullValue());
        assertThat(minutesModerateActivity.getDescriptiveStatistic(), nullValue());
        assertThat(minutesModerateActivity.getUserNotes(), nullValue());
    }

    @Test
    public void buildShouldConstructMeasureUsingOptionalProperties() {

        DurationUnitValue durationUnitValue = new DurationUnitValue(MINUTE, TEN);

        MinutesModerateActivity minutesModerateActivity = new MinutesModerateActivity.Builder(durationUnitValue)
                .setEffectiveTimeFrame(FIXED_MONTH)
                .setDescriptiveStatistic(MAXIMUM)
                .setUserNotes("feeling fine")
                .build();

        assertThat(minutesModerateActivity, notNullValue());
        assertThat(minutesModerateActivity.getMinutesModerateActivity(), equalTo(durationUnitValue));
        assertThat(minutesModerateActivity.getEffectiveTimeFrame(), equalTo(FIXED_MONTH));
        assertThat(minutesModerateActivity.getDescriptiveStatistic(), equalTo(MAXIMUM));
        assertThat(minutesModerateActivity.getUserNotes(), equalTo("feeling fine"));
    }

    @Override
    protected String getSchemaFilename() {
        return SCHEMA_FILENAME;
    }

    @Test
    public void measureShouldSerializeCorrectly() throws Exception {

        MinutesModerateActivity measure =
                new MinutesModerateActivity.Builder(new DurationUnitValue(MINUTE, BigDecimal.valueOf(60)))
                        .setEffectiveTimeFrame(FIXED_MONTH)
                        .build();

        String document = "{\n" +
                "    \"minutes_moderate_activity\": {\n" +
                "        \"value\": 60,\n" +
                "        \"unit\": \"min\"\n" +
                "    },\n" +
                "    \"effective_time_frame\": {\n" +
                "        \"time_interval\": {\n" +
                "            \"start_date_time\": \"2015-10-01T00:00:00-07:00\",\n" +
                "            \"end_date_time\": \"2015-11-01T00:00:00-07:00\"\n" +
                "        }\n" +
                "    }\n" +
                "}";

        serializationShouldCreateValidDocument(measure, document);
        deserializationShouldCreateValidObject(document, measure);
    }
}