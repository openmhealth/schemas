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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.openmhealth.schema.domain.omh.DescriptiveStatistic.MAXIMUM;
import static org.openmhealth.schema.domain.omh.PercentUnit.PERCENT;
import static org.openmhealth.schema.domain.omh.TimeFrameFactory.FIXED_MONTH;
import static org.openmhealth.schema.domain.omh.TimeFrameFactory.FIXED_POINT_IN_TIME;
import static org.openmhealth.schema.domain.omh.TimeInterval.ofStartDateTimeAndEndDateTime;


/**
 * @author Chris Schaefbauer
 */
public class BodyFatPercentageUnitTests extends SerializationUnitTests {

    private static final String SCHEMA_FILENAME = "schema/omh/body-fat-percentage-1.0.json";

    @Override
    public String getSchemaFilename() {
        return SCHEMA_FILENAME;
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void constructorShouldThrowExceptionWhenBodyTemperatureIsNull() {

        new BodyFatPercentage.Builder(null);
    }

    @Test
    public void buildShouldConstructMeasureUsingOnlyRequiredProperties() {

        BodyFatPercentage bodyFatPercentage = new BodyFatPercentage.Builder(new TypedUnitValue<>(PERCENT, 22.3))
                .build();

        assertThat(bodyFatPercentage, notNullValue());
        assertThat(bodyFatPercentage.getBodyFatPercentage().getTypedUnit(), equalTo(PERCENT));
        assertThat(bodyFatPercentage.getBodyFatPercentage().getValue().doubleValue(), equalTo(22.3));

        assertThat(bodyFatPercentage.getEffectiveTimeFrame(), nullValue());
        assertThat(bodyFatPercentage.getDescriptiveStatistic(), nullValue());
        assertThat(bodyFatPercentage.getUserNotes(), nullValue());
    }

    @Test
    public void buildShouldConstructMeasureUsingOptionalProperties() {

        BodyFatPercentage bodyFatPercentage = new BodyFatPercentage.Builder(new TypedUnitValue<>(PERCENT, 31))
                .setDescriptiveStatistic(MAXIMUM)
                .setEffectiveTimeFrame(FIXED_POINT_IN_TIME)
                .setUserNotes("some note")
                .build();

        assertThat(bodyFatPercentage, notNullValue());
        assertThat(bodyFatPercentage.getBodyFatPercentage().getTypedUnit(), equalTo(PERCENT));
        assertThat(bodyFatPercentage.getBodyFatPercentage().getValue().intValue(), equalTo(31));
        assertThat(bodyFatPercentage.getDescriptiveStatistic(), equalTo(MAXIMUM));
        assertThat(bodyFatPercentage.getEffectiveTimeFrame(), equalTo(FIXED_POINT_IN_TIME));
        assertThat(bodyFatPercentage.getUserNotes(), equalTo("some note"));
    }

    @Test
    public void measureShouldSerializeCorrectly() throws Exception {

        BodyFatPercentage bodyFatPercentage = new BodyFatPercentage.Builder(new TypedUnitValue<>(PERCENT, 16))
                .setDescriptiveStatistic(MAXIMUM)
                .setEffectiveTimeFrame(FIXED_MONTH)
                .build();

        String expectedDocument = "{\n" +
                "    \"body_fat_percentage\": {\n" +
                "        \"value\": 16,\n" +
                "        \"unit\": \"%\"\n" +
                "    },\n" +
                "    \"effective_time_frame\": {\n" +
                "        \"time_interval\": {\n" +
                "            \"start_date_time\": \"2015-10-01T00:00:00-07:00\",\n" +
                "            \"end_date_time\": \"2015-11-01T00:00:00-07:00\"\n" +
                "        }\n" +
                "    },\n" +
                "    \"descriptive_statistic\": \"maximum\"\n" +
                "}";

        serializationShouldCreateValidDocument(bodyFatPercentage, expectedDocument);
        deserializationShouldCreateValidObject(expectedDocument, bodyFatPercentage);
    }

    @Test
    public void equalsShouldReturnTrueWhenSameValuesForAllValues() {

        BodyFatPercentage bodyFatPercentage = new BodyFatPercentage.Builder(new TypedUnitValue<>(PERCENT, 16))
                .setDescriptiveStatistic(MAXIMUM)
                .setEffectiveTimeFrame(ofStartDateTimeAndEndDateTime(OffsetDateTime.parse("2013-01-01T00:00:00Z"),
                        OffsetDateTime.parse("2013-12-31T23:59:59Z")))
                .build();

        BodyFatPercentage sameBodyFatPercentage = new BodyFatPercentage.Builder(new TypedUnitValue<>(PERCENT, 16))
                .setDescriptiveStatistic(MAXIMUM)
                .setEffectiveTimeFrame(ofStartDateTimeAndEndDateTime(OffsetDateTime.parse("2013-01-01T00:00:00Z"),
                        OffsetDateTime.parse("2013-12-31T23:59:59Z")))
                .build();

        assertThat(bodyFatPercentage.equals(sameBodyFatPercentage), is(true));
    }

    @Test
    public void equalsShouldReturnFalseWhenRequiredValuesAreDifferent() {

        BodyFatPercentage bodyFatPercentage = new BodyFatPercentage.Builder(new TypedUnitValue<>(PERCENT, 16))
                .setDescriptiveStatistic(MAXIMUM)
                .setEffectiveTimeFrame(ofStartDateTimeAndEndDateTime(OffsetDateTime.parse("2013-01-01T00:00:00Z"),
                        OffsetDateTime.parse("2013-12-31T23:59:59Z")))
                .build();

        BodyFatPercentage withDifferentBodyFatPercentage =
                new BodyFatPercentage.Builder(new TypedUnitValue<>(PERCENT, 24))
                        .setDescriptiveStatistic(MAXIMUM)
                        .setEffectiveTimeFrame(
                                ofStartDateTimeAndEndDateTime(OffsetDateTime.parse("2013-01-01T00:00:00Z"),
                                        OffsetDateTime.parse("2013-12-31T23:59:59Z")))
                        .build();

        assertThat(bodyFatPercentage.equals(withDifferentBodyFatPercentage), is(false));
    }
}
