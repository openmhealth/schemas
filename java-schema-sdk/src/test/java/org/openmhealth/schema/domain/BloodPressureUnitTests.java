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

package org.openmhealth.schema.domain;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static java.math.BigDecimal.TEN;
import static java.time.ZoneOffset.UTC;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.openmhealth.schema.domain.BloodPressureUnit.MM_OF_MERCURY;
import static org.openmhealth.schema.domain.DescriptiveStatistic.MEDIAN;
import static org.openmhealth.schema.domain.DescriptiveStatistic.MINIMUM;
import static org.openmhealth.schema.domain.DurationUnit.DAY;
import static org.openmhealth.schema.domain.PositionDuringMeasurement.LYING_DOWN;
import static org.openmhealth.schema.domain.PositionDuringMeasurement.SITTING;


/**
 * @author Emerson Farrugia
 */
public class BloodPressureUnitTests extends SerializationUnitTests {

    public static final String SCHEMA_FILENAME = "schema/omh/blood-pressure-1.0.json";

    private SystolicBloodPressure systolicBloodPressure;
    private DiastolicBloodPressure diastolicBloodPressure;

    @BeforeTest
    public void initializeFixture() {
        systolicBloodPressure = new SystolicBloodPressure(MM_OF_MERCURY, BigDecimal.valueOf(120));
        diastolicBloodPressure = new DiastolicBloodPressure(MM_OF_MERCURY, BigDecimal.valueOf(80));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void constructorShouldThrowExceptionOnUndefinedSystolicBloodPressure() {

        new BloodPressure.Builder(null, diastolicBloodPressure);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void constructorShouldThrowExceptionOnUndefinedDiastolicBloodPressure() {

        new BloodPressure.Builder(systolicBloodPressure, null);
    }

    @Test
    public void buildShouldConstructMeasureUsingOnlyRequiredProperties() {

        BloodPressure bloodPressure = new BloodPressure.Builder(systolicBloodPressure, diastolicBloodPressure).build();

        assertThat(bloodPressure, notNullValue());
        assertThat(bloodPressure.getSystolicBloodPressure(), equalTo(systolicBloodPressure));
        assertThat(bloodPressure.getDiastolicBloodPressure(), equalTo(diastolicBloodPressure));
        assertThat(bloodPressure.getPositionDuringMeasurement(), nullValue());
        assertThat(bloodPressure.getEffectiveTimeFrame(), nullValue());
        assertThat(bloodPressure.getDescriptiveStatistic(), nullValue());
        assertThat(bloodPressure.getUserNotes(), nullValue());
    }

    @Test
    public void buildShouldConstructMeasureUsingOptionalProperties() {

        TimeInterval effectiveTimeInterval = TimeInterval.ofEndDateTimeAndDuration(
                OffsetDateTime.now(),
                new DurationUnitValue(DAY, TEN));

        BloodPressure bloodPressure = new BloodPressure.Builder(systolicBloodPressure, diastolicBloodPressure)
                .setPositionDuringMeasurement(LYING_DOWN)
                .setEffectiveTimeFrame(effectiveTimeInterval)
                .setDescriptiveStatistic(MEDIAN)
                .setUserNotes("feeling fine")
                .build();

        assertThat(bloodPressure, notNullValue());
        assertThat(bloodPressure.getSystolicBloodPressure(), equalTo(systolicBloodPressure));
        assertThat(bloodPressure.getDiastolicBloodPressure(), equalTo(diastolicBloodPressure));
        assertThat(bloodPressure.getPositionDuringMeasurement(), equalTo(LYING_DOWN));
        assertThat(bloodPressure.getEffectiveTimeFrame(), equalTo(new TimeFrame(effectiveTimeInterval)));
        assertThat(bloodPressure.getDescriptiveStatistic(), equalTo(MEDIAN));
        assertThat(bloodPressure.getUserNotes(), equalTo("feeling fine"));
    }

    @Override
    protected String getSchemaFilename() {
        return SCHEMA_FILENAME;
    }

    @Test
    public void measureShouldSerializeCorrectly() throws Exception {

        TimeInterval effectiveTimeInterval = TimeInterval.ofStartDateTimeAndEndDateTime(
                OffsetDateTime.of(2013, 2, 5, 7, 25, 0, 0, UTC),
                OffsetDateTime.of(2013, 6, 5, 7, 25, 0, 0, UTC)
        );

        BloodPressure bloodPressure = new BloodPressure.Builder(systolicBloodPressure, diastolicBloodPressure)
                .setPositionDuringMeasurement(SITTING)
                .setEffectiveTimeFrame(effectiveTimeInterval)
                .setDescriptiveStatistic(MINIMUM)
                .setUserNotes("I felt quite dizzy")
                .build();

        String document = "{\n" +
                "    \"systolic_blood_pressure\": {\n" +
                "        \"value\": 120,\n" +
                "        \"unit\": \"mmHg\"\n" +
                "    },\n" +
                "    \"diastolic_blood_pressure\": {\n" +
                "        \"value\": 80,\n" +
                "        \"unit\": \"mmHg\"\n" +
                "    },\n" +
                "    \"effective_time_frame\": {\n" +
                "        \"time_interval\": {\n" +
                "            \"start_date_time\": \"2013-02-05T07:25:00Z\",\n" +
                "            \"end_date_time\": \"2013-06-05T07:25:00Z\"\n" +
                "        }\n" +
                "    },\n" +
                "    \"position_during_measurement\": \"sitting\",\n" +
                "    \"descriptive_statistic\": \"minimum\",\n" +
                "    \"user_notes\": \"I felt quite dizzy\"\n" +
                "}";

        serializationShouldCreateValidDocument(bloodPressure, document);
        deserializationShouldCreateValidObject(document, bloodPressure);
    }
}