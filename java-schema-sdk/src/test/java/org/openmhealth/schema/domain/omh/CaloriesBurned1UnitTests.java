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
import static org.openmhealth.schema.domain.omh.DescriptiveStatistic.MEDIAN;
import static org.openmhealth.schema.domain.omh.KcalUnit.KILOCALORIE;
import static org.openmhealth.schema.domain.omh.TimeFrameFactory.FIXED_MONTH;


/**
 * @author Emerson Farrugia
 */
public class CaloriesBurned1UnitTests extends SerializationUnitTests {

    public static final String SCHEMA_FILENAME = "schema/omh/calories-burned-1.0.json";


    @Test(expectedExceptions = NullPointerException.class)
    public void constructorShouldThrowExceptionOnUndefinedCaloriesBurned() {

        new CaloriesBurned1.Builder(null);
    }

    @Test
    public void buildShouldConstructMeasureUsingOnlyRequiredProperties() {

        KcalUnitValue kcalBurned = new KcalUnitValue(KILOCALORIE, 200);

        CaloriesBurned1 caloriesBurned = new CaloriesBurned1.Builder(kcalBurned).build();

        assertThat(caloriesBurned, notNullValue());
        assertThat(caloriesBurned.getKcalBurned(), equalTo(kcalBurned));
        assertThat(caloriesBurned.getActivityName(), nullValue());
        assertThat(caloriesBurned.getEffectiveTimeFrame(), nullValue());
        assertThat(caloriesBurned.getDescriptiveStatistic(), nullValue());
        assertThat(caloriesBurned.getUserNotes(), nullValue());
    }

    @Test
    public void buildShouldConstructMeasureUsingOptionalProperties() {

        KcalUnitValue kcalBurned = new KcalUnitValue(KILOCALORIE, 800);

        CaloriesBurned1 caloriesBurned = new CaloriesBurned1.Builder(kcalBurned)
                .setActivityName("swimming")
                .setEffectiveTimeFrame(FIXED_MONTH)
                .setDescriptiveStatistic(MEDIAN)
                .setUserNotes("feeling fine")
                .build();

        assertThat(caloriesBurned, notNullValue());
        assertThat(caloriesBurned.getKcalBurned(), equalTo(kcalBurned));
        assertThat(caloriesBurned.getActivityName(), equalTo("swimming"));
        assertThat(caloriesBurned.getEffectiveTimeFrame(), equalTo(FIXED_MONTH));
        assertThat(caloriesBurned.getDescriptiveStatistic(), equalTo(MEDIAN));
        assertThat(caloriesBurned.getUserNotes(), equalTo("feeling fine"));
    }

    @Override
    protected String getSchemaFilename() {
        return SCHEMA_FILENAME;
    }

    @Test
    public void measureShouldSerializeCorrectly() throws Exception {

        CaloriesBurned1 measure = new CaloriesBurned1.Builder(new KcalUnitValue(KILOCALORIE, 160))
                .setActivityName("walking")
                .setEffectiveTimeFrame(FIXED_MONTH)
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
                "    \"activity_name\": \"walking\"\n" +
                "}";

        serializationShouldCreateValidDocument(measure, document);
        deserializationShouldCreateValidObject(document, measure);
    }
}