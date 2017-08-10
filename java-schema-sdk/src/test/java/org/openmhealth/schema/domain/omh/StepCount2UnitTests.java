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

import static java.math.BigDecimal.TEN;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.openmhealth.schema.domain.omh.DescriptiveStatistic.AVERAGE;
import static org.openmhealth.schema.domain.omh.DescriptiveStatisticDenominator.DAY;
import static org.openmhealth.schema.domain.omh.TimeFrameFactory.FIXED_DAY;
import static org.openmhealth.schema.domain.omh.TimeFrameFactory.FIXED_MONTH;


/**
 * @author Emerson Farrugia
 */
public class StepCount2UnitTests extends SerializationUnitTests {

    public static final String SCHEMA_FILENAME = "schema/omh/step-count-2.0.json";


    @Test(expectedExceptions = NullPointerException.class)
    public void constructorShouldThrowExceptionOnUndefinedStepCount() {

        new StepCount2.Builder(null, FIXED_MONTH);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void constructorShouldThrowExceptionOnUndefinedEffectiveTimeFrame() {

        new StepCount2.Builder(TEN, (TimeFrame) null);
    }

    @Test
    public void buildShouldConstructMeasureUsingOnlyRequiredProperties() {

        StepCount2 stepCount = new StepCount2.Builder(TEN, FIXED_DAY).build();

        assertThat(stepCount, notNullValue());
        assertThat(stepCount.getStepCount(), equalTo(TEN));
        assertThat(stepCount.getEffectiveTimeFrame(), equalTo(FIXED_DAY));
        assertThat(stepCount.getDescriptiveStatistic(), nullValue());
        assertThat(stepCount.getDescriptiveStatisticDenominator(), nullValue());
        assertThat(stepCount.getUserNotes(), nullValue());
    }

    @Test
    public void buildShouldConstructMeasureUsingOptionalProperties() {

        StepCount2 stepCount = new StepCount2.Builder(TEN, FIXED_MONTH)
                .setDescriptiveStatistic(AVERAGE)
                .setDescriptiveStatisticDenominator(DAY)
                .setUserNotes("feeling fine")
                .build();

        assertThat(stepCount, notNullValue());
        assertThat(stepCount.getStepCount(), equalTo(TEN));
        assertThat(stepCount.getEffectiveTimeFrame(), equalTo(FIXED_MONTH));
        assertThat(stepCount.getDescriptiveStatistic(), equalTo(AVERAGE));
        assertThat(stepCount.getDescriptiveStatisticDenominator(), equalTo(DAY));
        assertThat(stepCount.getUserNotes(), equalTo("feeling fine"));
    }

    @Override
    protected String getSchemaFilename() {
        return SCHEMA_FILENAME;
    }

    @Test
    public void measureShouldSerializeCorrectly() throws Exception {

        StepCount2 stepCount = new StepCount2.Builder(6000L, FIXED_MONTH)
                .setDescriptiveStatistic(AVERAGE)
                .setDescriptiveStatisticDenominator(DAY)
                .setUserNotes("feeling fine")
                .build();

        String document = "{\n" +
                "    \"step_count\": 6000,\n" +
                "    \"effective_time_frame\": {\n" +
                "        \"time_interval\": {\n" +
                "            \"start_date_time\": \"2015-10-01T00:00:00-07:00\",\n" +
                "            \"end_date_time\": \"2015-11-01T00:00:00-07:00\"\n" +
                "        }\n" +
                "    },\n" +
                "    \"descriptive_statistic\": \"average\",\n" +
                "    \"descriptive_statistic_denominator\": \"d\",\n" +
                "    \"user_notes\": \"feeling fine\"\n" +
                "}";

        serializationShouldCreateValidDocument(stepCount, document);
        deserializationShouldCreateValidObject(document, stepCount);
    }
}