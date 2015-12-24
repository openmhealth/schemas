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

import static java.math.BigDecimal.TEN;
import static java.time.ZoneOffset.UTC;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.openmhealth.schema.domain.omh.DescriptiveStatistic.MEDIAN;
import static org.openmhealth.schema.domain.omh.TimeFrameFactory.OCTOBER;


/**
 * @author Emerson Farrugia
 */
public class StepCountUnitTests extends SerializationUnitTests {

    public static final String SCHEMA_FILENAME = "schema/omh/step-count-1.0.json";

    @Test(expectedExceptions = NullPointerException.class)
    public void constructorShouldThrowExceptionOnUndefinedStepCount() {

        new StepCount.Builder(null);
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void setDescriptiveStatisticShouldThrowException() {

        new StepCount.Builder(TEN).setDescriptiveStatistic(MEDIAN);
    }

    @Test
    public void buildShouldConstructMeasureUsingOnlyRequiredProperties() {

        StepCount stepCount = new StepCount.Builder(TEN).build();

        assertThat(stepCount, notNullValue());
        assertThat(stepCount.getStepCount(), equalTo(TEN));
        assertThat(stepCount.getEffectiveTimeFrame(), nullValue());
        assertThat(stepCount.getDescriptiveStatistic(), nullValue());
        assertThat(stepCount.getUserNotes(), nullValue());
    }

    @Test
    public void buildShouldConstructMeasureUsingOptionalProperties() {

        StepCount stepCount = new StepCount.Builder(TEN)
                .setEffectiveTimeFrame(OCTOBER)
                .setUserNotes("feeling fine")
                .build();

        assertThat(stepCount, notNullValue());
        assertThat(stepCount.getStepCount(), equalTo(TEN));
        assertThat(stepCount.getEffectiveTimeFrame(), equalTo(OCTOBER));
        assertThat(stepCount.getDescriptiveStatistic(), nullValue());
        assertThat(stepCount.getUserNotes(), equalTo("feeling fine"));
    }

    @Override
    protected String getSchemaFilename() {
        return SCHEMA_FILENAME;
    }

    @Test
    public void measureShouldSerializeCorrectly() throws Exception {

        StepCount stepCount = new StepCount.Builder(BigDecimal.valueOf(6000))
                .setEffectiveTimeFrame(TimeInterval.ofStartDateTimeAndEndDateTime(
                        OffsetDateTime.of(2013, 2, 5, 6, 25, 0, 0, UTC),
                        OffsetDateTime.of(2013, 2, 5, 7, 25, 0, 0, UTC)
                ))
                .build();

        String document = "{\n" +
                "    \"step_count\": 6000,\n" +
                "    \"effective_time_frame\": {\n" +
                "        \"time_interval\": {\n" +
                "            \"start_date_time\": \"2013-02-05T06:25:00Z\",\n" +
                "            \"end_date_time\": \"2013-02-05T07:25:00Z\"\n" +
                "        }\n" +
                "    }\n" +
                "}";

        serializationShouldCreateValidDocument(stepCount, document);
        deserializationShouldCreateValidObject(document, stepCount);
    }
}