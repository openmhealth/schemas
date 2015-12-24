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
import java.time.OffsetDateTime;

import static java.time.ZoneOffset.UTC;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.openmhealth.schema.domain.omh.DescriptiveStatistic.AVERAGE;
import static org.openmhealth.schema.domain.omh.DescriptiveStatistic.MINIMUM;
import static org.openmhealth.schema.domain.omh.MassUnit.KILOGRAM;
import static org.openmhealth.schema.domain.omh.TimeFrameFactory.FIXED_POINT_IN_TIME;
import static org.openmhealth.schema.domain.omh.TimeFrameFactory.OCTOBER;


/**
 * @author Emerson Farrugia
 */
public class BodyWeightUnitTests extends SerializationUnitTests {

    public static final String SCHEMA_FILENAME = "schema/omh/body-weight-1.0.json";

    @Test(expectedExceptions = NullPointerException.class)
    public void constructorShouldThrowExceptionOnUndefinedBodyWeight() {

        new BodyWeight.Builder(null);
    }

    @Test
    public void buildShouldConstructMeasureUsingOnlyRequiredProperties() {

        MassUnitValue massUnitValue = new MassUnitValue(KILOGRAM, BigDecimal.valueOf(65));

        BodyWeight bodyWeight = new BodyWeight.Builder(massUnitValue).build();

        assertThat(bodyWeight, notNullValue());
        assertThat(bodyWeight.getBodyWeight(), equalTo(massUnitValue));
        assertThat(bodyWeight.getEffectiveTimeFrame(), nullValue());
        assertThat(bodyWeight.getDescriptiveStatistic(), nullValue());
        assertThat(bodyWeight.getUserNotes(), nullValue());
    }

    @Test
    public void buildShouldConstructMeasureUsingTimeIntervalTimeFrame() {

        MassUnitValue massUnitValue = new MassUnitValue(KILOGRAM, BigDecimal.valueOf(65));

        BodyWeight bodyWeight = new BodyWeight.Builder(massUnitValue)
                .setEffectiveTimeFrame(OCTOBER)
                .setDescriptiveStatistic(AVERAGE)
                .setUserNotes("feeling fine")
                .build();

        assertThat(bodyWeight, notNullValue());
        assertThat(bodyWeight.getBodyWeight(), equalTo(massUnitValue));
        assertThat(bodyWeight.getEffectiveTimeFrame(), equalTo(OCTOBER));
        assertThat(bodyWeight.getDescriptiveStatistic(), equalTo(AVERAGE));
        assertThat(bodyWeight.getUserNotes(), equalTo("feeling fine"));
    }

    @Test
    public void buildShouldConstructMeasureUsingDateTimeTimeFrame() {

        MassUnitValue massUnitValue = new MassUnitValue(KILOGRAM, BigDecimal.valueOf(65));

        BodyWeight bodyWeight = new BodyWeight.Builder(massUnitValue)
                .setEffectiveTimeFrame(FIXED_POINT_IN_TIME)
                .build();

        assertThat(bodyWeight, notNullValue());
        assertThat(bodyWeight.getBodyWeight(), equalTo(massUnitValue));
        assertThat(bodyWeight.getEffectiveTimeFrame(), equalTo(FIXED_POINT_IN_TIME));
        assertThat(bodyWeight.getDescriptiveStatistic(), nullValue());
    }

    @Override
    protected String getSchemaFilename() {
        return SCHEMA_FILENAME;
    }

    @Test
    public void measureShouldSerializeCorrectly() throws Exception {

        MassUnitValue massUnitValue = new MassUnitValue(KILOGRAM, BigDecimal.valueOf(50));

        TimeInterval effectiveTimeInterval = TimeInterval.ofStartDateTimeAndEndDateTime(
                OffsetDateTime.of(2013, 2, 5, 7, 25, 0, 0, UTC),
                OffsetDateTime.of(2013, 6, 5, 7, 25, 0, 0, UTC)
        );

        BodyWeight bodyWeight = new BodyWeight.Builder(massUnitValue)
                .setEffectiveTimeFrame(effectiveTimeInterval)
                .setDescriptiveStatistic(MINIMUM)
                .build();

        String document = "{\n" +
                "    \"body_weight\": {\n" +
                "        \"value\": 50,\n" +
                "        \"unit\": \"kg\"\n" +
                "    },\n" +
                "    \"effective_time_frame\": {\n" +
                "        \"time_interval\": {\n" +
                "            \"start_date_time\": \"2013-02-05T07:25:00Z\",\n" +
                "            \"end_date_time\": \"2013-06-05T07:25:00Z\"\n" +
                "        }\n" +
                "    },\n" +
                "    \"descriptive_statistic\": \"minimum\"\n" +
                "}";

        serializationShouldCreateValidDocument(bodyWeight, document);
        deserializationShouldCreateValidObject(document, bodyWeight);
    }
}