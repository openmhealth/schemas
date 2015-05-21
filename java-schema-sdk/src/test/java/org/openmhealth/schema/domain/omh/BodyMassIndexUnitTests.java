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

import static java.math.BigDecimal.TEN;
import static java.time.ZoneOffset.UTC;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.openmhealth.schema.domain.omh.BodyMassIndexUnit.KILOGRAMS_PER_SQUARE_METER;
import static org.openmhealth.schema.domain.omh.DescriptiveStatistic.AVERAGE;
import static org.openmhealth.schema.domain.omh.DescriptiveStatistic.MAXIMUM;
import static org.openmhealth.schema.domain.omh.DurationUnit.DAY;


/**
 * @author Emerson Farrugia
 */
public class BodyMassIndexUnitTests extends SerializationUnitTests {

    public static final String SCHEMA_FILENAME = "schema/omh/body-mass-index-1.0.json";

    @Test
    public void buildShouldConstructMeasureUsingOnlyRequiredProperties() {

        TypedUnitValue<BodyMassIndexUnit> bmiValue = new TypedUnitValue<>(KILOGRAMS_PER_SQUARE_METER, 20);

        BodyMassIndex bmi = new BodyMassIndex.Builder(bmiValue).build();

        assertThat(bmi, notNullValue());
        assertThat(bmi.getBodyMassIndex(), equalTo(bmiValue));
        assertThat(bmi.getEffectiveTimeFrame(), nullValue());
        assertThat(bmi.getDescriptiveStatistic(), nullValue());
        assertThat(bmi.getUserNotes(), nullValue());
    }

    @Test
    public void buildShouldConstructMeasureUsingOptionalProperties() {

        TypedUnitValue<BodyMassIndexUnit> bmiValue = new TypedUnitValue<>(KILOGRAMS_PER_SQUARE_METER, 20);

        TimeInterval effectiveTimeInterval = TimeInterval.ofEndDateTimeAndDuration(
                OffsetDateTime.now(),
                new DurationUnitValue(DAY, TEN));

        BodyMassIndex bmi = new BodyMassIndex.Builder(bmiValue)
                .setEffectiveTimeFrame(effectiveTimeInterval)
                .setDescriptiveStatistic(AVERAGE)
                .setUserNotes("feeling fine")
                .build();

        assertThat(bmi, notNullValue());
        assertThat(bmi.getBodyMassIndex(), equalTo(bmiValue));
        assertThat(bmi.getEffectiveTimeFrame(), equalTo(new TimeFrame(effectiveTimeInterval)));
        assertThat(bmi.getDescriptiveStatistic(), equalTo(AVERAGE));
        assertThat(bmi.getUserNotes(), equalTo("feeling fine"));
    }

    @Override
    protected String getSchemaFilename() {
        return SCHEMA_FILENAME;
    }

    @Test
    public void measureShouldSerializeCorrectly() throws Exception {

        TimeInterval effectiveTimeInterval = TimeInterval.ofStartDateTimeAndEndDateTime(
                OffsetDateTime.of(2013, 1, 1, 0, 0, 0, 0, UTC),
                OffsetDateTime.of(2013, 12, 31, 23, 59, 59, 0, UTC)
        );

        BodyMassIndex bmi = new BodyMassIndex.Builder(new TypedUnitValue<>(KILOGRAMS_PER_SQUARE_METER, 16))
                .setEffectiveTimeFrame(effectiveTimeInterval)
                .setDescriptiveStatistic(MAXIMUM)
                .setUserNotes("I felt fine")
                .build();

        String document = "{\n" +
                "    \"body_mass_index\": {\n" +
                "        \"value\": 16,\n" +
                "        \"unit\": \"kg/m2\"\n" +
                "    },\n" +
                "    \"effective_time_frame\": {\n" +
                "        \"time_interval\": {\n" +
                "            \"start_date_time\": \"2013-01-01T00:00:00Z\",\n" +
                "            \"end_date_time\": \"2013-12-31T23:59:59Z\"\n" +
                "        }\n" +
                "    },\n" +
                "    \"descriptive_statistic\": \"maximum\",\n" +
                "    \"user_notes\": \"I felt fine\"\n" +
                "}";

        serializationShouldCreateValidDocument(bmi, document);
        deserializationShouldCreateValidObject(document, bmi);
    }
}