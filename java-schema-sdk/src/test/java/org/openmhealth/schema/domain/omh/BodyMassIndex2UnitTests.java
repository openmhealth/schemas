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
import static org.openmhealth.schema.domain.omh.BodyMassIndexUnit2.KILOGRAMS_PER_SQUARE_METER;
import static org.openmhealth.schema.domain.omh.DescriptiveStatistic.AVERAGE;
import static org.openmhealth.schema.domain.omh.DescriptiveStatistic.MAXIMUM;
import static org.openmhealth.schema.domain.omh.TimeFrameFactory.FIXED_MONTH;


/**
 * @author Emerson Farrugia
 */
public class BodyMassIndex2UnitTests extends SerializationUnitTests {

    public static final String SCHEMA_FILENAME = "schema/omh/body-mass-index-2.0.json";

    @Test
    public void buildShouldConstructMeasureUsingOnlyRequiredProperties() {

        TypedUnitValue<BodyMassIndexUnit2> bmiValue = new TypedUnitValue<>(KILOGRAMS_PER_SQUARE_METER, 20);

        BodyMassIndex2 bmi = new BodyMassIndex2.Builder(bmiValue, FIXED_MONTH).build();

        assertThat(bmi, notNullValue());
        assertThat(bmi.getEffectiveTimeFrame(), equalTo(FIXED_MONTH));
        assertThat(bmi.getBodyMassIndex(), equalTo(bmiValue));
        assertThat(bmi.getDescriptiveStatistic(), nullValue());
        assertThat(bmi.getUserNotes(), nullValue());
    }

    @Test
    public void buildShouldConstructMeasureUsingOptionalProperties() {

        TypedUnitValue<BodyMassIndexUnit2> bmiValue = new TypedUnitValue<>(KILOGRAMS_PER_SQUARE_METER, 20);

        BodyMassIndex2 bmi = new BodyMassIndex2.Builder(bmiValue, FIXED_MONTH)
                .setDescriptiveStatistic(AVERAGE)
                .setUserNotes("feeling fine")
                .build();

        assertThat(bmi, notNullValue());
        assertThat(bmi.getBodyMassIndex(), equalTo(bmiValue));
        assertThat(bmi.getEffectiveTimeFrame(), equalTo(FIXED_MONTH));
        assertThat(bmi.getDescriptiveStatistic(), equalTo(AVERAGE));
        assertThat(bmi.getUserNotes(), equalTo("feeling fine"));
    }

    @Override
    protected String getSchemaFilename() {
        return SCHEMA_FILENAME;
    }

    @Test
    public void measureShouldSerializeCorrectly() throws Exception {

        BodyMassIndex2 bmi =
                new BodyMassIndex2.Builder(new TypedUnitValue<>(KILOGRAMS_PER_SQUARE_METER, 16), FIXED_MONTH)
                        .setDescriptiveStatistic(MAXIMUM)
                        .setUserNotes("I felt fine")
                        .build();

        String document = "{\n" +
                "    \"body_mass_index\": {\n" +
                "        \"value\": 16,\n" +
                "        \"unit\": \"kg/m^2\"\n" +
                "    },\n" +
                "    \"effective_time_frame\": {\n" +
                "        \"time_interval\": {\n" +
                "            \"start_date_time\": \"2015-10-01T00:00:00-07:00\",\n" +
                "            \"end_date_time\": \"2015-11-01T00:00:00-07:00\"\n" +
                "        }\n" +
                "    },\n" +
                "    \"descriptive_statistic\": \"maximum\",\n" +
                "    \"user_notes\": \"I felt fine\"\n" +
                "}";

        serializationShouldCreateValidDocument(bmi, document);
        deserializationShouldCreateValidObject(document, bmi);
    }
}