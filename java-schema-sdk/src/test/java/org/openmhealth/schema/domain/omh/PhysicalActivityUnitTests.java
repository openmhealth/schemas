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
import java.time.LocalDate;

import static java.math.BigDecimal.ONE;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.openmhealth.schema.domain.omh.DescriptiveStatistic.MINIMUM;
import static org.openmhealth.schema.domain.omh.KcalUnit.KILOCALORIE;
import static org.openmhealth.schema.domain.omh.LengthUnit.KILOMETER;
import static org.openmhealth.schema.domain.omh.LengthUnit.MILE;
import static org.openmhealth.schema.domain.omh.PartOfDay.MORNING;
import static org.openmhealth.schema.domain.omh.PhysicalActivity.SelfReportedIntensity.LIGHT;
import static org.openmhealth.schema.domain.omh.PhysicalActivity.SelfReportedIntensity.MODERATE;
import static org.openmhealth.schema.domain.omh.TimeFrameFactory.FIXED_MONTH;


/**
 * @author Emerson Farrugia
 */
public class PhysicalActivityUnitTests extends SerializationUnitTests {

    public static final String SCHEMA_FILENAME = "schema/omh/physical-activity-1.2.json";

    @Test(expectedExceptions = NullPointerException.class)
    public void constructorShouldThrowExceptionOnUndefinedActivityName() {

        new PhysicalActivity.Builder(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void constructorShouldThrowExceptionOnEmptyActivityName() {

        new PhysicalActivity.Builder("");
    }

    @Test
    public void buildShouldConstructMeasureUsingOnlyRequiredProperties() {

        PhysicalActivity physicalActivity = new PhysicalActivity.Builder("walking").build();

        assertThat(physicalActivity, notNullValue());
        assertThat(physicalActivity.getActivityName(), equalTo("walking"));
        assertThat(physicalActivity.getDistance(), nullValue());
        assertThat(physicalActivity.getReportedActivityIntensity(), nullValue());
        assertThat(physicalActivity.getEffectiveTimeFrame(), nullValue());
        assertThat(physicalActivity.getDescriptiveStatistic(), nullValue());
        assertThat(physicalActivity.getUserNotes(), nullValue());
        assertThat(physicalActivity.getCaloriesBurned(), nullValue());
    }

    @Test
    public void buildShouldConstructMeasureUsingOptionalProperties() {

        LengthUnitValue distance = new LengthUnitValue(KILOMETER, ONE);
        KcalUnitValue caloriesBurned = new KcalUnitValue(KILOCALORIE, 15.7);

        PhysicalActivity physicalActivity = new PhysicalActivity.Builder("walking")
                .setDistance(distance)
                .setReportedActivityIntensity(LIGHT)
                .setEffectiveTimeFrame(FIXED_MONTH)
                .setDescriptiveStatistic(MINIMUM)
                .setUserNotes("feeling fine")
                .setCaloriesBurned(caloriesBurned)
                .build();

        assertThat(physicalActivity, notNullValue());
        assertThat(physicalActivity.getActivityName(), equalTo("walking"));
        assertThat(physicalActivity.getDistance(), equalTo(distance));
        assertThat(physicalActivity.getReportedActivityIntensity(), equalTo(LIGHT));
        assertThat(physicalActivity.getEffectiveTimeFrame(), equalTo(FIXED_MONTH));
        assertThat(physicalActivity.getDescriptiveStatistic(), equalTo(MINIMUM));
        assertThat(physicalActivity.getUserNotes(), equalTo("feeling fine"));
        assertThat(physicalActivity.getCaloriesBurned(), equalTo(caloriesBurned));
    }

    @Override
    protected String getSchemaFilename() {
        return SCHEMA_FILENAME;
    }

    @Test
    public void measureShouldSerializeCorrectly() throws Exception {

        PhysicalActivity physicalActivity = new PhysicalActivity.Builder("walking")
                .setDistance(new LengthUnitValue(MILE, BigDecimal.valueOf(1.5)))
                .setReportedActivityIntensity(MODERATE)
                .setEffectiveTimeFrame(FIXED_MONTH)
                .setCaloriesBurned(new KcalUnitValue(KILOCALORIE, 25.1))
                .build();

        String document = "{\n" +
                "    \"activity_name\": \"walking\",\n" +
                "    \"distance\": {\n" +
                "        \"value\": 1.5,\n" +
                "        \"unit\": \"mi\"\n" +
                "    },\n" +
                "    \"reported_activity_intensity\": \"moderate\",\n" +
                "    \"effective_time_frame\": {\n" +
                "        \"time_interval\": {\n" +
                "            \"start_date_time\": \"2015-10-01T00:00:00-07:00\",\n" +
                "            \"end_date_time\": \"2015-11-01T00:00:00-07:00\"\n" +
                "        }\n" +
                "    },\n" +
                "    \"kcal_burned\": {\n" +
                "        \"value\": 25.1,\n" +
                "        \"unit\": \"kcal\"\n" +
                "    }\n" +
                "}";

        serializationShouldCreateValidDocument(physicalActivity, document);
        deserializationShouldCreateValidObject(document, physicalActivity);
    }

    @Test
    public void equalsShouldReturnCorrectComparison() {

        PhysicalActivity physicalActivity = new PhysicalActivity.Builder("walking")
                .setDistance(new LengthUnitValue(MILE, BigDecimal.valueOf(1.5)))
                .setReportedActivityIntensity(MODERATE)
                .setEffectiveTimeFrame(TimeInterval.ofDateAndPartOfDay(LocalDate.of(2013, 2, 5), MORNING))
                .setCaloriesBurned(new KcalUnitValue(KILOCALORIE, 25.1))
                .build();

        PhysicalActivity samePhysicalActivity = new PhysicalActivity.Builder("walking")
                .setDistance(new LengthUnitValue(MILE, BigDecimal.valueOf(1.5)))
                .setReportedActivityIntensity(MODERATE)
                .setEffectiveTimeFrame(TimeInterval.ofDateAndPartOfDay(LocalDate.of(2013, 2, 5), MORNING))
                .setCaloriesBurned(new KcalUnitValue(KILOCALORIE, 25.1))
                .build();

        assertThat(physicalActivity.equals(samePhysicalActivity), is(true));

        PhysicalActivity differentPhysicalActivity = new PhysicalActivity.Builder("running")
                .setDistance(new LengthUnitValue(MILE, BigDecimal.valueOf(2.5)))
                .setReportedActivityIntensity(LIGHT)
                .setEffectiveTimeFrame(TimeInterval.ofDateAndPartOfDay(LocalDate.of(2014, 2, 5), MORNING))
                .setCaloriesBurned(new KcalUnitValue(KILOCALORIE, 100))
                .build();

        assertThat(physicalActivity.equals(differentPhysicalActivity), is(false));
    }
}