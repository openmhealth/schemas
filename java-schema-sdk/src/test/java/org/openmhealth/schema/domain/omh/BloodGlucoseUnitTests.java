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
import static org.openmhealth.schema.domain.omh.BloodGlucoseUnit.MILLIGRAMS_PER_DECILITER;
import static org.openmhealth.schema.domain.omh.BloodSpecimenType.PLASMA;
import static org.openmhealth.schema.domain.omh.BloodSpecimenType.WHOLE_BLOOD;
import static org.openmhealth.schema.domain.omh.DescriptiveStatistic.MEDIAN;
import static org.openmhealth.schema.domain.omh.DescriptiveStatistic.MINIMUM;
import static org.openmhealth.schema.domain.omh.TemporalRelationshipToMeal.FASTING;
import static org.openmhealth.schema.domain.omh.TemporalRelationshipToSleep.BEFORE_SLEEPING;
import static org.openmhealth.schema.domain.omh.TemporalRelationshipToSleep.ON_WAKING;
import static org.openmhealth.schema.domain.omh.TimeFrameFactory.FIXED_MONTH;


/**
 * @author Emerson Farrugia
 */
public class BloodGlucoseUnitTests extends SerializationUnitTests {

    public static final String SCHEMA_FILENAME = "schema/omh/blood-glucose-1.0.json";


    @Test(expectedExceptions = NullPointerException.class)
    public void constructorShouldThrowExceptionOnUndefinedBloodGlucose() {

        new BloodGlucose.Builder(null);
    }

    @Test
    public void buildShouldConstructMeasureUsingOnlyRequiredProperties() {

        TypedUnitValue<BloodGlucoseUnit> bloodGlucoseLevel = new TypedUnitValue<>(MILLIGRAMS_PER_DECILITER, 110);

        BloodGlucose bloodGlucose = new BloodGlucose.Builder(bloodGlucoseLevel).build();

        assertThat(bloodGlucose, notNullValue());
        assertThat(bloodGlucose.getBloodGlucose(), equalTo(bloodGlucoseLevel));
        assertThat(bloodGlucose.getBloodSpecimenType(), nullValue());
        assertThat(bloodGlucose.getTemporalRelationshipToMeal(), nullValue());
        assertThat(bloodGlucose.getTemporalRelationshipToSleep(), nullValue());
        assertThat(bloodGlucose.getEffectiveTimeFrame(), nullValue());
        assertThat(bloodGlucose.getDescriptiveStatistic(), nullValue());
        assertThat(bloodGlucose.getUserNotes(), nullValue());
    }

    @Test
    public void buildShouldConstructMeasureUsingOptionalProperties() {

        TypedUnitValue<BloodGlucoseUnit> bloodGlucoseLevel = new TypedUnitValue<>(MILLIGRAMS_PER_DECILITER, 110);

        BloodGlucose bloodGlucose = new BloodGlucose.Builder(bloodGlucoseLevel)
                .setBloodSpecimenType(WHOLE_BLOOD)
                .setTemporalRelationshipToMeal(FASTING)
                .setTemporalRelationshipToSleep(BEFORE_SLEEPING)
                .setEffectiveTimeFrame(FIXED_MONTH)
                .setDescriptiveStatistic(MEDIAN)
                .setUserNotes("feeling fine")
                .build();

        assertThat(bloodGlucose, notNullValue());
        assertThat(bloodGlucose.getBloodGlucose(), equalTo(bloodGlucoseLevel));
        assertThat(bloodGlucose.getBloodSpecimenType(), equalTo(WHOLE_BLOOD));
        assertThat(bloodGlucose.getTemporalRelationshipToMeal(), equalTo(FASTING));
        assertThat(bloodGlucose.getTemporalRelationshipToSleep(), equalTo(BEFORE_SLEEPING));
        assertThat(bloodGlucose.getEffectiveTimeFrame(), equalTo(FIXED_MONTH));
        assertThat(bloodGlucose.getDescriptiveStatistic(), equalTo(MEDIAN));
        assertThat(bloodGlucose.getUserNotes(), equalTo("feeling fine"));
    }

    @Override
    protected String getSchemaFilename() {
        return SCHEMA_FILENAME;
    }

    @Test
    public void measureShouldSerializeCorrectly() throws Exception {

        BloodGlucose measure = new BloodGlucose.Builder(new TypedUnitValue<>(MILLIGRAMS_PER_DECILITER, 120))
                .setBloodSpecimenType(PLASMA)
                .setTemporalRelationshipToMeal(FASTING)
                .setTemporalRelationshipToSleep(ON_WAKING)
                .setEffectiveTimeFrame(FIXED_MONTH)
                .setDescriptiveStatistic(MINIMUM)
                .setUserNotes("feeling fine")
                .build();

        String document = "{\n" +
                "    \"blood_glucose\": {\n" +
                "        \"unit\": \"mg/dL\",\n" +
                "        \"value\": 120\n" +
                "    },\n" +
                "    \"effective_time_frame\": {\n" +
                "        \"time_interval\": {\n" +
                "            \"start_date_time\": \"2015-10-01T00:00:00-07:00\",\n" +
                "            \"end_date_time\": \"2015-11-01T00:00:00-07:00\"\n" +
                "        }\n" +
                "    },\n" +
                "    \"blood_specimen_type\": \"plasma\",\n" +
                "    \"temporal_relationship_to_meal\": \"fasting\",\n" +
                "    \"temporal_relationship_to_sleep\": \"on waking\",\n" +
                "    \"descriptive_statistic\": \"minimum\",\n" +
                "    \"user_notes\": \"feeling fine\"\n" +
                "}";

        serializationShouldCreateValidDocument(measure, document);
        deserializationShouldCreateValidObject(document, measure);
    }
}