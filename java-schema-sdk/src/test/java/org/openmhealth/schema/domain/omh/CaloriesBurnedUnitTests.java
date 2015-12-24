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

import static java.time.ZoneOffset.UTC;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.openmhealth.schema.domain.omh.DescriptiveStatistic.MEDIAN;
import static org.openmhealth.schema.domain.omh.KcalUnit.KILOCALORIE;
import static org.openmhealth.schema.domain.omh.TimeFrameFactory.OCTOBER;


/**
 * @author Emerson Farrugia
 */
public class CaloriesBurnedUnitTests extends SerializationUnitTests {

    public static final String SCHEMA_FILENAME = "schema/omh/calories-burned-1.0.json";


    @Test(expectedExceptions = NullPointerException.class)
    public void constructorShouldThrowExceptionOnUndefinedCaloriesBurned() {

        new CaloriesBurned.Builder(null);
    }

    @Test
    public void buildShouldConstructMeasureUsingOnlyRequiredProperties() {

        KcalUnitValue kcalBurned = new KcalUnitValue(KILOCALORIE, 200);

        CaloriesBurned caloriesBurned = new CaloriesBurned.Builder(kcalBurned).build();

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

        CaloriesBurned caloriesBurned = new CaloriesBurned.Builder(kcalBurned)
                .setActivityName("swimming")
                .setEffectiveTimeFrame(OCTOBER)
                .setDescriptiveStatistic(MEDIAN)
                .setUserNotes("feeling fine")
                .build();

        assertThat(caloriesBurned, notNullValue());
        assertThat(caloriesBurned.getKcalBurned(), equalTo(kcalBurned));
        assertThat(caloriesBurned.getActivityName(), equalTo("swimming"));
        assertThat(caloriesBurned.getEffectiveTimeFrame(), equalTo(OCTOBER));
        assertThat(caloriesBurned.getDescriptiveStatistic(), equalTo(MEDIAN));
        assertThat(caloriesBurned.getUserNotes(), equalTo("feeling fine"));
    }

    @Override
    protected String getSchemaFilename() {
        return SCHEMA_FILENAME;
    }

    @Test
    public void measureShouldSerializeCorrectly() throws Exception {

        TimeInterval effectiveTimeInterval = TimeInterval.ofStartDateTimeAndEndDateTime(
                OffsetDateTime.of(2013, 2, 5, 6, 25, 0, 0, UTC),
                OffsetDateTime.of(2013, 2, 5, 7, 25, 0, 0, UTC)
        );

        CaloriesBurned measure = new CaloriesBurned.Builder(new KcalUnitValue(KILOCALORIE, 160))
                .setActivityName("walking")
                .setEffectiveTimeFrame(effectiveTimeInterval)
                .build();

        String document = "{\n" +
                "    \"kcal_burned\": {\n" +
                "        \"value\": 160,\n" +
                "        \"unit\": \"kcal\"\n" +
                "    },\n" +
                "    \"effective_time_frame\": {\n" +
                "        \"time_interval\": {\n" +
                "            \"start_date_time\": \"2013-02-05T06:25:00Z\",\n" +
                "            \"end_date_time\": \"2013-02-05T07:25:00Z\"\n" +
                "        }\n" +
                "    },\n" +
                "    \"activity_name\": \"walking\"\n" +
                "}";

        serializationShouldCreateValidDocument(measure, document);
        deserializationShouldCreateValidObject(document, measure);
    }
}