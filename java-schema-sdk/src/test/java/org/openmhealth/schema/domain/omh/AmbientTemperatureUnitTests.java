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
import static org.openmhealth.schema.domain.omh.DescriptiveStatistic.MEDIAN;
import static org.openmhealth.schema.domain.omh.TemperatureUnit.CELSIUS;
import static org.openmhealth.schema.domain.omh.TemperatureUnit.FAHRENHEIT;
import static org.openmhealth.schema.domain.omh.TimeFrameFactory.FIXED_MONTH;
import static org.openmhealth.schema.domain.omh.TimeFrameFactory.FIXED_POINT_IN_TIME;


/**
 * @author Chris Schaefbauer
 */
public class AmbientTemperatureUnitTests extends SerializationUnitTests {

    public static final String SCHEMA_FILENAME = "schema/omh/ambient-temperature-1.0.json";

    @Override
    public String getSchemaFilename() {
        return SCHEMA_FILENAME;
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void constructorShouldThrowExceptionWhenBodyTemperatureIsNull() {

        new AmbientTemperature.Builder(null);
    }

    @Test
    public void buildShouldConstructCorrectMeasureUsingOnlyRequiredProperties() {

        AmbientTemperature ambientTemperature = new AmbientTemperature.Builder(new TemperatureUnitValue(CELSIUS, 50.3))
                .build();

        assertThat(ambientTemperature, notNullValue());
        assertThat(ambientTemperature.getAmbientTemperature().getTypedUnit(), equalTo(CELSIUS));
        assertThat(ambientTemperature.getAmbientTemperature().getValue().doubleValue(), equalTo(50.3));

        assertThat(ambientTemperature.getUserNotes(), nullValue());
        assertThat(ambientTemperature.getEffectiveTimeFrame(), nullValue());
        assertThat(ambientTemperature.getDescriptiveStatistic(), nullValue());
    }

    @Test
    public void buildShouldConstructCorrectMeasureIncludingOptionalProperties() {

        AmbientTemperature ambientTemperature =
                new AmbientTemperature.Builder(new TemperatureUnitValue(FAHRENHEIT, 87L))
                        .setDescriptiveStatistic(MEDIAN)
                        .setEffectiveTimeFrame(FIXED_MONTH)
                        .setUserNotes("Temp is fine")
                        .build();

        assertThat(ambientTemperature.getAmbientTemperature().getTypedUnit(), equalTo(FAHRENHEIT));
        assertThat(ambientTemperature.getAmbientTemperature().getValue().intValue(), equalTo(87));
        assertThat(ambientTemperature.getUserNotes(), equalTo("Temp is fine"));
        assertThat(ambientTemperature.getEffectiveTimeFrame(), equalTo(FIXED_MONTH));
        assertThat(ambientTemperature.getDescriptiveStatistic(), equalTo(MEDIAN));
    }

    @Test
    public void measureShouldSerializeCorrectly() throws Exception {

        AmbientTemperature ambientTemperature = new AmbientTemperature.Builder(new TemperatureUnitValue(CELSIUS, 34L))
                .setEffectiveTimeFrame(FIXED_POINT_IN_TIME)
                .build();

        String expectedDocument = "{\n" +
                "    \"ambient_temperature\": {\n" +
                "        \"value\": 34,\n" +
                "        \"unit\": \"C\"\n" +
                "    },\n" +
                "    \"effective_time_frame\": {\n" +
                "        \"date_time\": \"2015-10-21T16:29:00-07:00\"\n" +
                "    }\n" +
                "}";

        serializationShouldCreateValidDocument(ambientTemperature, expectedDocument);
        deserializationShouldCreateValidObject(expectedDocument, ambientTemperature);
    }

    @Test
    public void equalsShouldReturnTrueWhenSameValuesForAllValues() {

        AmbientTemperature ambientTemperature = new AmbientTemperature.Builder(new TemperatureUnitValue(CELSIUS, 34L))
                .setEffectiveTimeFrame(OffsetDateTime.parse("2013-02-05T07:25:00Z"))
                .build();

        AmbientTemperature sameAmbientTemperature =
                new AmbientTemperature.Builder(new TemperatureUnitValue(CELSIUS, 34L))
                        .setEffectiveTimeFrame(OffsetDateTime.parse("2013-02-05T07:25:00Z"))
                        .build();

        assertThat(ambientTemperature.equals(sameAmbientTemperature), is(true));
    }

    @Test
    public void equalsShouldReturnFalseWhenRequiredValuesAreDifferent() {

        AmbientTemperature ambientTemperature = new AmbientTemperature.Builder(new TemperatureUnitValue(CELSIUS, 34L))
                .setEffectiveTimeFrame(OffsetDateTime.parse("2013-02-05T07:25:00Z"))
                .build();

        AmbientTemperature withDifferentRequiredValue =
                new AmbientTemperature.Builder(new TemperatureUnitValue(FAHRENHEIT, 34L))
                        .setEffectiveTimeFrame(OffsetDateTime.parse("2013-02-05T07:25:00Z"))
                        .build();

        assertThat(ambientTemperature.equals(withDifferentRequiredValue), is(false));
    }
}
