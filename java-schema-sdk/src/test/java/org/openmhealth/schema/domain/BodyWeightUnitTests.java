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

package org.openmhealth.schema.domain;

import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static java.math.BigDecimal.TEN;
import static java.time.ZoneOffset.UTC;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.openmhealth.schema.domain.DescriptiveStatistic.AVERAGE;
import static org.openmhealth.schema.domain.DescriptiveStatistic.MINIMUM;
import static org.openmhealth.schema.domain.DurationUnit.DAY;
import static org.openmhealth.schema.domain.MassUnit.KILOGRAM;


/**
 * @author Emerson Farrugia
 */
public class BodyWeightUnitTests extends SerializationUnitTests {

    public static final String SCHEMA_FILENAME = "schema/omh/body-weight-1.0.json";

    @Test(expectedExceptions = NullPointerException.class)
    public void builderShouldThrowExceptionOnUndefinedBodyWeight() {

        new BodyWeight.Builder(null);
    }

    @Test
    public void buildShouldConstructBodyWeightUsingOnlyRequiredProperties() {

        MassUnitValue massUnitValue = new MassUnitValue(KILOGRAM, BigDecimal.valueOf(65));

        BodyWeight bodyWeight = new BodyWeight.Builder(massUnitValue).build();

        assertThat(bodyWeight, notNullValue());
        assertThat(bodyWeight.getBodyWeight(), equalTo(massUnitValue));
        assertThat(bodyWeight.getEffectiveTimeFrame(), nullValue());
        assertThat(bodyWeight.getDescriptiveStatistic(), nullValue());
    }

    @Test
    public void buildShouldConstructBodyWeightUsingTimeIntervalTimeFrame() {

        MassUnitValue massUnitValue = new MassUnitValue(KILOGRAM, BigDecimal.valueOf(65));

        TimeInterval effectiveTimeInterval = TimeInterval.ofEndDateTimeAndDuration(
                OffsetDateTime.now(),
                new DurationUnitValue(DAY, TEN));

        BodyWeight bodyWeight = new BodyWeight.Builder(massUnitValue)
                .setEffectiveTimeFrame(effectiveTimeInterval)
                .setDescriptiveStatistic(AVERAGE)
                .setUserNotes("feeling fine")
                .build();

        assertThat(bodyWeight, notNullValue());
        assertThat(bodyWeight.getBodyWeight(), equalTo(massUnitValue));
        assertThat(bodyWeight.getEffectiveTimeFrame(), equalTo(new TimeFrame(effectiveTimeInterval)));
        assertThat(bodyWeight.getDescriptiveStatistic(), equalTo(AVERAGE));
        assertThat(bodyWeight.getUserNotes(), equalTo("feeling fine"));
    }

    @Test
    public void buildShouldConstructBodyWeightUsingDateTimeTimeFrame() {

        MassUnitValue massUnitValue = new MassUnitValue(KILOGRAM, BigDecimal.valueOf(65));
        OffsetDateTime effectiveDateTime = OffsetDateTime.now();

        BodyWeight bodyWeight = new BodyWeight.Builder(massUnitValue)
                .setEffectiveTimeFrame(effectiveDateTime)
                .build();

        assertThat(bodyWeight, notNullValue());
        assertThat(bodyWeight.getBodyWeight(), equalTo(massUnitValue));
        assertThat(bodyWeight.getEffectiveTimeFrame(), equalTo(new TimeFrame(effectiveDateTime)));
        assertThat(bodyWeight.getDescriptiveStatistic(), nullValue());
    }

    @Override
    protected String getSchemaFilename() {
        return SCHEMA_FILENAME;
    }

    @Test
    public void bodyWeightShouldSerializeCorrectly() throws Exception {

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