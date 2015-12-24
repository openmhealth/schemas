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

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.openmhealth.schema.domain.omh.BloodPressureUnit.MM_OF_MERCURY;
import static org.openmhealth.schema.domain.omh.DescriptiveStatistic.MEDIAN;
import static org.openmhealth.schema.domain.omh.DescriptiveStatistic.MINIMUM;
import static org.openmhealth.schema.domain.omh.PositionDuringMeasurement.LYING_DOWN;
import static org.openmhealth.schema.domain.omh.PositionDuringMeasurement.SITTING;
import static org.openmhealth.schema.domain.omh.TimeFrameFactory.FIXED_MONTH;


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

        BloodPressure bloodPressure = new BloodPressure.Builder(systolicBloodPressure, diastolicBloodPressure)
                .setPositionDuringMeasurement(LYING_DOWN)
                .setEffectiveTimeFrame(FIXED_MONTH)
                .setDescriptiveStatistic(MEDIAN)
                .setUserNotes("feeling fine")
                .build();

        assertThat(bloodPressure, notNullValue());
        assertThat(bloodPressure.getSystolicBloodPressure(), equalTo(systolicBloodPressure));
        assertThat(bloodPressure.getDiastolicBloodPressure(), equalTo(diastolicBloodPressure));
        assertThat(bloodPressure.getPositionDuringMeasurement(), equalTo(LYING_DOWN));
        assertThat(bloodPressure.getEffectiveTimeFrame(), equalTo(FIXED_MONTH));
        assertThat(bloodPressure.getDescriptiveStatistic(), equalTo(MEDIAN));
        assertThat(bloodPressure.getUserNotes(), equalTo("feeling fine"));
    }

    @Override
    protected String getSchemaFilename() {
        return SCHEMA_FILENAME;
    }

    @Test
    public void measureShouldSerializeCorrectly() throws Exception {

        BloodPressure bloodPressure = new BloodPressure.Builder(systolicBloodPressure, diastolicBloodPressure)
                .setPositionDuringMeasurement(SITTING)
                .setEffectiveTimeFrame(FIXED_MONTH)
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
                "            \"start_date_time\": \"2015-10-01T00:00:00-07:00\",\n" +
                "            \"end_date_time\": \"2015-11-01T00:00:00-07:00\"\n" +
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