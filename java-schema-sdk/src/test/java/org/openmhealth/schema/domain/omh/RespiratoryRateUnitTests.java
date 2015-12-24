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

import org.openmhealth.schema.domain.omh.RespiratoryRate.RespirationUnit;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.openmhealth.schema.domain.omh.DescriptiveStatistic.MAXIMUM;
import static org.openmhealth.schema.domain.omh.RespiratoryRate.RespirationUnit.BREATHS_PER_MINUTE;
import static org.openmhealth.schema.domain.omh.TemporalRelationshipToPhysicalActivity.*;
import static org.openmhealth.schema.domain.omh.TimeFrameFactory.FIXED_POINT_IN_TIME;


/**
 * @author Chris Schaefbauer
 */
public class RespiratoryRateUnitTests extends SerializationUnitTests {

    public static final String SCHEMA_FILENAME = "schema/omh/respiratory-rate-1.0.json";

    @Override
    public String getSchemaFilename() {
        return SCHEMA_FILENAME;
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void constructorShouldThrowExceptionOnUndefinedRespiratoryRate() {

        new RespiratoryRate.Builder(null);
    }

    @Test
    public void buildShouldConstructMeasureCorrectlyUsingOnlyRequiredProperties() {

        RespiratoryRate respiratoryRate = new RespiratoryRate.Builder(new TypedUnitValue<>(BREATHS_PER_MINUTE, 20))
                .build();

        assertThat(respiratoryRate, notNullValue());
        assertThat(respiratoryRate.getRespiratoryRate(), notNullValue());
        assertThat(respiratoryRate.getRespiratoryRate().getTypedUnit(), equalTo(BREATHS_PER_MINUTE));
        assertThat(respiratoryRate.getRespiratoryRate().getValue(), equalTo(BigDecimal.valueOf(20)));

        assertThat(respiratoryRate.getEffectiveTimeFrame(), nullValue());
        assertThat(respiratoryRate.getUserNotes(), nullValue());
        assertThat(respiratoryRate.getDescriptiveStatistic(), nullValue());
        assertThat(respiratoryRate.getTemporalRelationshipToPhysicalActivity(), nullValue());
    }

    @Test
    public void buildShouldConstructMeasureCorrectlyUsingOptionalProperties() {

        RespiratoryRate respiratoryRate = new RespiratoryRate.Builder(new TypedUnitValue<>(BREATHS_PER_MINUTE, 15.5))
                .setDescriptiveStatistic(MAXIMUM)
                .setEffectiveTimeFrame(FIXED_POINT_IN_TIME)
                .setUserNotes("some note")
                .setTemporalRelationshipToPhysicalActivity(AFTER_EXERCISE)
                .build();

        assertThat(respiratoryRate, notNullValue());
        assertThat(respiratoryRate.getRespiratoryRate(), notNullValue());
        assertThat(respiratoryRate.getRespiratoryRate().getTypedUnit(), equalTo(BREATHS_PER_MINUTE));
        assertThat(respiratoryRate.getRespiratoryRate().getValue().doubleValue(), equalTo(15.5));
        assertThat(respiratoryRate.getEffectiveTimeFrame(), equalTo(FIXED_POINT_IN_TIME));
        assertThat(respiratoryRate.getDescriptiveStatistic(), equalTo(MAXIMUM));
        assertThat(respiratoryRate.getUserNotes(), equalTo("some note"));
        assertThat(respiratoryRate.getTemporalRelationshipToPhysicalActivity(), equalTo(AFTER_EXERCISE));
    }

    @Test
    public void measureShouldSerializeCorrectly() throws Exception {

        RespiratoryRate respiratoryRate = new RespiratoryRate.Builder(new TypedUnitValue<>(BREATHS_PER_MINUTE, 12))
                .setEffectiveTimeFrame(FIXED_POINT_IN_TIME)
                .setTemporalRelationshipToPhysicalActivity(AT_REST)
                .build();

        String expectedDocument = "{\n" +
                "    \"respiratory_rate\": {\n" +
                "        \"value\": 12,\n" +
                "        \"unit\": \"breaths/min\"\n" +
                "    },\n" +
                "    \"effective_time_frame\": {\n" +
                "        \"date_time\": \"2015-10-21T16:29:00-07:00\"\n" +
                "    },\n" +
                "    \"temporal_relationship_to_physical_activity\": \"at rest\"\n" +
                "}";

        serializationShouldCreateValidDocument(respiratoryRate, expectedDocument);
        deserializationShouldCreateValidObject(expectedDocument, respiratoryRate);
    }

    @Test
    public void equalsShouldReturnTrueWhenSameValuesForAllValues() {

        RespiratoryRate respiratoryRate = new RespiratoryRate.Builder(new TypedUnitValue<>(BREATHS_PER_MINUTE, 12))
                .setEffectiveTimeFrame(OffsetDateTime.parse("2013-02-05T07:25:00Z"))
                .setTemporalRelationshipToPhysicalActivity(AT_REST)
                .build();

        RespiratoryRate sameRespiratoryRate = new RespiratoryRate.Builder(new TypedUnitValue<>(BREATHS_PER_MINUTE, 12))
                .setEffectiveTimeFrame(OffsetDateTime.parse("2013-02-05T07:25:00Z"))
                .setTemporalRelationshipToPhysicalActivity(AT_REST)
                .build();

        assertThat(respiratoryRate.equals(sameRespiratoryRate), is(true));
    }

    @Test
    public void equalsShouldReturnFalseWhenDifferentRequiredValues() {

        OffsetDateTime offsetDateTime = OffsetDateTime.parse("2013-02-05T07:25:00Z");

        RespiratoryRate respiratoryRate = new RespiratoryRate.Builder(new TypedUnitValue<>(BREATHS_PER_MINUTE, 12))
                .setEffectiveTimeFrame(offsetDateTime)
                .setTemporalRelationshipToPhysicalActivity(AT_REST)
                .build();

        RespiratoryRate withDifferentRequiredValues =
                new RespiratoryRate.Builder(new TypedUnitValue<>(BREATHS_PER_MINUTE, 12.5))
                        .setEffectiveTimeFrame(offsetDateTime)
                        .setTemporalRelationshipToPhysicalActivity(AT_REST)
                        .build();

        assertThat(respiratoryRate.equals(withDifferentRequiredValues), is(false));
    }

    @Test
    public void equalsShouldReturnFalseWhenDifferentTemporalRelationshipToActivity() {

        TypedUnitValue<RespirationUnit> respirationUnitValue = new TypedUnitValue<>(BREATHS_PER_MINUTE, 12);

        OffsetDateTime offsetDateTime = OffsetDateTime.parse("2013-02-05T07:25:00Z");

        RespiratoryRate respiratoryRate = new RespiratoryRate.Builder(respirationUnitValue)
                .setEffectiveTimeFrame(offsetDateTime)
                .setTemporalRelationshipToPhysicalActivity(AT_REST)
                .build();

        RespiratoryRate withDifferentOptionalValues = new RespiratoryRate.Builder(respirationUnitValue)
                .setEffectiveTimeFrame(offsetDateTime)
                .setTemporalRelationshipToPhysicalActivity(BEFORE_EXERCISE)
                .build();

        assertThat(respiratoryRate.equals(withDifferentOptionalValues), is(false));
    }
}
