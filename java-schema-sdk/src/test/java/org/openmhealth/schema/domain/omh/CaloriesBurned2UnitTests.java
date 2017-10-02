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

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.openmhealth.schema.domain.omh.DescriptiveStatistic.AVERAGE;
import static org.openmhealth.schema.domain.omh.DescriptiveStatistic.MEDIAN;
import static org.openmhealth.schema.domain.omh.DescriptiveStatisticDenominator.DAY;
import static org.openmhealth.schema.domain.omh.KcalUnit.KILOCALORIE;
import static org.openmhealth.schema.domain.omh.TimeFrameFactory.FIXED_DAY;
import static org.openmhealth.schema.domain.omh.TimeFrameFactory.FIXED_MONTH;


/**
 * @author Emerson Farrugia
 */
public class CaloriesBurned2UnitTests extends SerializationUnitTests {

    public static final String SCHEMA_FILENAME = "schema/omh/calories-burned-2.0.json";


    @Test(expectedExceptions = NullPointerException.class)
    public void constructorShouldThrowExceptionOnUndefinedCaloriesBurned() {

        new CaloriesBurned2.Builder(null, FIXED_MONTH);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void constructorShouldThrowExceptionOnUndefinedEffectiveTimeFrame() {

        new CaloriesBurned2.Builder(KILOCALORIE.newUnitValue(100), (TimeFrame) null);
    }

    @Test
    public void buildShouldConstructMeasureUsingOnlyRequiredProperties() {

        KcalUnitValue kcalBurned = KILOCALORIE.newUnitValue(200);

        CaloriesBurned2 caloriesBurned = new CaloriesBurned2.Builder(kcalBurned, FIXED_DAY).build();

        assertThat(caloriesBurned, notNullValue());
        assertThat(caloriesBurned.getKcalBurned(), equalTo(kcalBurned));
        assertThat(caloriesBurned.getEffectiveTimeFrame(), equalTo(FIXED_DAY));
        assertThat(caloriesBurned.getActivityName(), nullValue());
        assertThat(caloriesBurned.getDescriptiveStatistic(), nullValue());
        assertThat(caloriesBurned.getDescriptiveStatisticDenominator(), nullValue());
        assertThat(caloriesBurned.getUserNotes(), nullValue());
    }

    @Test
    public void buildShouldConstructMeasureUsingOptionalProperties() {

        KcalUnitValue kcalBurned = KILOCALORIE.newUnitValue(800);

        CaloriesBurned2 caloriesBurned = new CaloriesBurned2.Builder(kcalBurned, FIXED_MONTH)
                .setActivityName("swimming")
                .setDescriptiveStatistic(MEDIAN)
                .setDescriptiveStatisticDenominator(DAY)
                .setUserNotes("feeling fine")
                .build();

        assertThat(caloriesBurned, notNullValue());
        assertThat(caloriesBurned.getKcalBurned(), equalTo(kcalBurned));
        assertThat(caloriesBurned.getEffectiveTimeFrame(), equalTo(FIXED_MONTH));
        assertThat(caloriesBurned.getActivityName(), equalTo("swimming"));
        assertThat(caloriesBurned.getDescriptiveStatistic(), equalTo(MEDIAN));
        assertThat(caloriesBurned.getDescriptiveStatisticDenominator(), equalTo(DAY));
        assertThat(caloriesBurned.getUserNotes(), equalTo("feeling fine"));
    }

    @Override
    protected String getSchemaFilename() {
        return SCHEMA_FILENAME;
    }

    @Test
    public void measureShouldSerializeCorrectly() throws Exception {

        CaloriesBurned2 measure = new CaloriesBurned2.Builder(KILOCALORIE.newUnitValue(160), FIXED_MONTH)
                .setActivityName("walking")
                .setDescriptiveStatistic(AVERAGE)
                .setDescriptiveStatisticDenominator(DAY)
                .setUserNotes("feeling fine")
                .build();

        String document = "{\n" +
                "    \"kcal_burned\": {\n" +
                "        \"value\": 160,\n" +
                "        \"unit\": \"kcal\"\n" +
                "    },\n" +
                "    \"effective_time_frame\": {\n" +
                "        \"time_interval\": {\n" +
                "            \"start_date_time\": \"2015-10-01T00:00:00-07:00\",\n" +
                "            \"end_date_time\": \"2015-11-01T00:00:00-07:00\"\n" +
                "        }\n" +
                "    },\n" +
                "    \"activity_name\": \"walking\",\n" +
                "    \"descriptive_statistic\": \"average\",\n" +
                "    \"descriptive_statistic_denominator\": \"d\",\n" +
                "    \"user_notes\": \"feeling fine\"\n" +
                "}";

        serializationShouldCreateValidDocument(measure, document);
        deserializationShouldCreateValidObject(document, measure);
    }
}