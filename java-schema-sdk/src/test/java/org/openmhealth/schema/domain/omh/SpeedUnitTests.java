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
import static org.openmhealth.schema.domain.omh.DescriptiveStatistic.AVERAGE;
import static org.openmhealth.schema.domain.omh.DescriptiveStatistic.MINIMUM;
import static org.openmhealth.schema.domain.omh.SpeedUnit.KILOMETERS_PER_HOUR;
import static org.openmhealth.schema.domain.omh.SpeedUnit.METERS_PER_SECOND;
import static org.openmhealth.schema.domain.omh.TimeFrameFactory.FIXED_MONTH;
import static org.openmhealth.schema.domain.omh.TimeFrameFactory.FIXED_POINT_IN_TIME;


/**
 * @author Emerson Farrugia
 */
public class SpeedUnitTests extends SerializationUnitTests {

    public static final String SCHEMA_FILENAME = "schema/omh/speed-1.0.json";

    @Test(expectedExceptions = NullPointerException.class)
    public void constructorShouldThrowExceptionOnUndefinedSpeed() {

        new Speed.Builder(null, FIXED_MONTH);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void constructorShouldThrowExceptionOnUndefinedEffectiveTimeFrame() {

        new Speed.Builder(METERS_PER_SECOND.newUnitValue(15), (OffsetDateTime) null);
    }

    @Test
    public void buildShouldConstructMeasureUsingOnlyRequiredProperties() {

        SpeedUnitValue speedUnitValue = KILOMETERS_PER_HOUR.newUnitValue(10);

        Speed speed = new Speed.Builder(speedUnitValue, FIXED_POINT_IN_TIME).build();

        assertThat(speed, notNullValue());
        assertThat(speed.getSpeed(), equalTo(speedUnitValue));
        assertThat(speed.getEffectiveTimeFrame(), equalTo(FIXED_POINT_IN_TIME));
        assertThat(speed.getDescriptiveStatistic(), nullValue());
        assertThat(speed.getUserNotes(), nullValue());
    }

    @Test
    public void buildShouldConstructMeasureUsingOptionalProperties() {

        SpeedUnitValue speedUnitValue = KILOMETERS_PER_HOUR.newUnitValue(10);

        Speed speed = new Speed.Builder(speedUnitValue, FIXED_POINT_IN_TIME)
                .setDescriptiveStatistic(AVERAGE)
                .setUserNotes("feeling fine")
                .build();

        assertThat(speed, notNullValue());
        assertThat(speed.getSpeed(), equalTo(speedUnitValue));
        assertThat(speed.getEffectiveTimeFrame(), equalTo(FIXED_POINT_IN_TIME));
        assertThat(speed.getDescriptiveStatistic(), equalTo(AVERAGE));
        assertThat(speed.getUserNotes(), equalTo("feeling fine"));
    }

    @Override
    protected String getSchemaFilename() {
        return SCHEMA_FILENAME;
    }

    @Test
    public void measureShouldSerializeCorrectly() throws Exception {

        Speed speed = new Speed.Builder(KILOMETERS_PER_HOUR.newUnitValue(10), FIXED_POINT_IN_TIME)
                .setDescriptiveStatistic(MINIMUM)
                .setUserNotes("feeling fine")
                .build();

        String document = "{\n" +
                "    \"speed\": {\n" +
                "        \"value\": 10,\n" +
                "        \"unit\": \"km/h\"\n" +
                "    },\n" +
                "    \"effective_time_frame\": {\n" +
                "        \"date_time\": \"2015-10-21T16:29:00-07:00\"\n" +
                "    },\n" +
                "    \"descriptive_statistic\": \"minimum\",\n" +
                "    \"user_notes\": \"feeling fine\"\n" +
                "}";

        serializationShouldCreateValidDocument(speed, document);
        deserializationShouldCreateValidObject(document, speed);
    }
}