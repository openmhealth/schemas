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

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.openmhealth.schema.domain.omh.DescriptiveStatistic.AVERAGE;
import static org.openmhealth.schema.domain.omh.DescriptiveStatistic.MEDIAN;
import static org.openmhealth.schema.domain.omh.LengthUnit.CENTIMETER;
import static org.openmhealth.schema.domain.omh.TimeFrameFactory.FIXED_MONTH;


/**
 * @author Emerson Farrugia
 */
public class BodyHeightUnitTests extends SerializationUnitTests {

    public static final String SCHEMA_FILENAME = "schema/omh/body-height-1.0.json";

    @Test(expectedExceptions = NullPointerException.class)
    public void constructorShouldThrowExceptionOnUndefinedBodyHeight() {

        new BodyHeight.Builder(null);
    }

    @Test
    public void buildShouldConstructMeasureUsingOnlyRequiredProperties() {

        LengthUnitValue lengthUnitValue = new LengthUnitValue(CENTIMETER, BigDecimal.valueOf(180));

        BodyHeight bodyHeight = new BodyHeight.Builder(lengthUnitValue).build();

        assertThat(bodyHeight, notNullValue());
        assertThat(bodyHeight.getBodyHeight(), equalTo(lengthUnitValue));
        assertThat(bodyHeight.getEffectiveTimeFrame(), nullValue());
        assertThat(bodyHeight.getDescriptiveStatistic(), nullValue());
        assertThat(bodyHeight.getUserNotes(), nullValue());
    }

    @Test
    public void buildShouldConstructMeasureUsingOptionalProperties() {

        LengthUnitValue lengthUnitValue = new LengthUnitValue(CENTIMETER, BigDecimal.valueOf(180));

        BodyHeight bodyHeight = new BodyHeight.Builder(lengthUnitValue)
                .setEffectiveTimeFrame(FIXED_MONTH)
                .setDescriptiveStatistic(AVERAGE)
                .setUserNotes("feeling fine")
                .build();

        assertThat(bodyHeight, notNullValue());
        assertThat(bodyHeight.getBodyHeight(), equalTo(lengthUnitValue));
        assertThat(bodyHeight.getEffectiveTimeFrame(), equalTo(FIXED_MONTH));
        assertThat(bodyHeight.getDescriptiveStatistic(), equalTo(AVERAGE));
        assertThat(bodyHeight.getUserNotes(), equalTo("feeling fine"));
    }

    @Override
    protected String getSchemaFilename() {
        return SCHEMA_FILENAME;
    }

    @Test
    public void measureShouldSerializeCorrectly() throws Exception {

        BodyHeight bodyHeight = new BodyHeight
                .Builder(new LengthUnitValue(CENTIMETER, BigDecimal.valueOf(180)))
                .setEffectiveTimeFrame(FIXED_MONTH)
                .setDescriptiveStatistic(MEDIAN)
                .build();

        String document = "{\n" +
                "    \"body_height\": {\n" +
                "        \"value\": 180,\n" +
                "        \"unit\": \"cm\"\n" +
                "    },\n" +
                "    \"effective_time_frame\": {\n" +
                "        \"time_interval\": {\n" +
                "            \"start_date_time\": \"2015-10-01T00:00:00-07:00\",\n" +
                "            \"end_date_time\": \"2015-11-01T00:00:00-07:00\"\n" +
                "        }\n" +
                "    },\n" +
                "    \"descriptive_statistic\": \"median\"\n" +
                "}";

        serializationShouldCreateValidDocument(bodyHeight, document);
        deserializationShouldCreateValidObject(document, bodyHeight);
    }
}