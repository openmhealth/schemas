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

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.openmhealth.schema.domain.omh.BodyTemperature.MeasurementLocation.ORAL;
import static org.openmhealth.schema.domain.omh.BodyTemperature.MeasurementLocation.RECTAL;
import static org.openmhealth.schema.domain.omh.DescriptiveStatistic.MAXIMUM;
import static org.openmhealth.schema.domain.omh.TemperatureUnit.*;
import static org.openmhealth.schema.domain.omh.TimeFrameFactory.FIXED_POINT_IN_TIME;


/**
 * @author Chris Schaefbauer
 */
public class BodyTemperatureUnitTests extends SerializationUnitTests {

    public static final String SCHEMA_FILENAME = "schema/omh/body-temperature-1.0.json";

    @Override
    public String getSchemaFilename() {
        return SCHEMA_FILENAME;
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void constructorShouldThrowExceptionWhenBodyTemperatureIsNull() {

        new BodyTemperature.Builder(null).build();
    }

    @Test
    public void buildShouldConstructCorrectMeasureUsingOnlyRequiredProperties() {

        BodyTemperature bodyTemperature = new BodyTemperature.Builder(new TemperatureUnitValue(CELSIUS, 37.3))
                .build();

        assertThat(bodyTemperature.getBodyTemperature(), notNullValue());
        assertThat(bodyTemperature.getBodyTemperature(), equalTo(new TemperatureUnitValue(CELSIUS, 37.3)));

        assertThat(bodyTemperature.getMeasurementLocation(), nullValue());
        assertThat(bodyTemperature.getEffectiveTimeFrame(), nullValue());
        assertThat(bodyTemperature.getDescriptiveStatistic(), nullValue());
        assertThat(bodyTemperature.getUserNotes(), nullValue());
    }

    @Test
    public void buildShouldConstructCorrectMeasureUsingOptionalProperties() {

        BodyTemperature bodyTemperature = new BodyTemperature.Builder(new TemperatureUnitValue(FAHRENHEIT, 99.8))
                .setDescriptiveStatistic(MAXIMUM)
                .setEffectiveTimeFrame(FIXED_POINT_IN_TIME)
                .setUserNotes("Body temperature high")
                .setMeasurementLocation(ORAL)
                .build();

        assertThat(bodyTemperature.getBodyTemperature(), notNullValue());
        assertThat(bodyTemperature.getBodyTemperature(), equalTo(new TemperatureUnitValue(FAHRENHEIT, 99.8)));
        assertThat(bodyTemperature.getDescriptiveStatistic(), equalTo(MAXIMUM));
        assertThat(bodyTemperature.getEffectiveTimeFrame(), equalTo(FIXED_POINT_IN_TIME));
        assertThat(bodyTemperature.getUserNotes(), equalTo("Body temperature high"));
        assertThat(bodyTemperature.getMeasurementLocation(), equalTo(ORAL));
    }

    @Test
    public void measureShouldSerializeCorrectly() throws Exception {

        BodyTemperature bodyTemperature = new BodyTemperature.Builder(new TemperatureUnitValue(KELVIN, 310L))
                .setDescriptiveStatistic(MAXIMUM)
                .setEffectiveTimeFrame(FIXED_POINT_IN_TIME)
                .setMeasurementLocation(RECTAL)
                .build();

        String expectedDocument = "{\n" +
                "    \"body_temperature\": {\n" +
                "        \"value\": 310,\n" +
                "        \"unit\": \"K\"\n" +
                "    },\n" +
                "    \"effective_time_frame\": {\n" +
                "        \"date_time\": \"2015-10-21T16:29:00-07:00\"\n" +
                "    },\n" +
                "    \"measurement_location\": \"rectal\",\n" +
                "    \"descriptive_statistic\": \"maximum\"\n" +
                "}";

        serializationShouldCreateValidDocument(bodyTemperature, expectedDocument);
        deserializationShouldCreateValidObject(expectedDocument, bodyTemperature);
    }

    @Test
    public void equalsShouldReturnTrueWhenSameValuesForAllValues() {

        BodyTemperature bodyTemperature = new BodyTemperature.Builder(new TemperatureUnitValue(KELVIN, 310L))
                .setEffectiveTimeFrame(OffsetDateTime.parse("2015-11-10T14:16:01Z"))
                .setMeasurementLocation(RECTAL)
                .build();

        BodyTemperature sameBodyTemperature = new BodyTemperature.Builder(new TemperatureUnitValue(KELVIN, 310L))
                .setEffectiveTimeFrame(OffsetDateTime.parse("2015-11-10T14:16:01Z"))
                .setMeasurementLocation(RECTAL)
                .build();

        assertThat(bodyTemperature.equals(sameBodyTemperature), is(true));
    }

    @Test
    public void equalsShouldReturnFalseWhenRequiredValuesAreDifferent() {

        BodyTemperature bodyTemperature = new BodyTemperature.Builder(new TemperatureUnitValue(KELVIN, 310L))
                .setEffectiveTimeFrame(OffsetDateTime.parse("2015-11-10T14:16:01Z"))
                .setMeasurementLocation(RECTAL)
                .build();

        BodyTemperature withDifferentRequiredValues =
                new BodyTemperature.Builder(new TemperatureUnitValue(FAHRENHEIT, 98.33))
                        .setEffectiveTimeFrame(OffsetDateTime.parse("2015-11-10T14:16:01Z"))
                        .setMeasurementLocation(RECTAL)
                        .build();

        assertThat(bodyTemperature.equals(withDifferentRequiredValues), is(false));
    }

    @Test
    public void equalsShouldReturnTrueWhenOptionalValuesAreDifferent() {

        BodyTemperature bodyTemperature = new BodyTemperature.Builder(new TemperatureUnitValue(KELVIN, 310L))
                .setEffectiveTimeFrame(OffsetDateTime.parse("2015-11-10T14:16:01Z"))
                .setMeasurementLocation(RECTAL)
                .build();

        BodyTemperature withDifferentOptionalValues =
                new BodyTemperature.Builder(new TemperatureUnitValue(KELVIN, 310L))
                        .setEffectiveTimeFrame(OffsetDateTime.parse("2015-11-10T14:16:01Z"))
                        .setMeasurementLocation(ORAL)
                        .build();

        assertThat(bodyTemperature.equals(withDifferentOptionalValues), is(false));
    }
}
