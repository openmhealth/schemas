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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.openmhealth.schema.serializer.SerializationConstructor;

import java.util.EnumSet;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static org.openmhealth.schema.domain.omh.DurationUnit.*;


/**
 * A measurement of sleep duration.
 *
 * @author Emerson Farrugia
 * @version 2.0
 * @see <a href="http://www.openmhealth.org/documentation/#/schema-docs/schema-library/schemas/omh_sleep-duration">sleep-duration</a>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(SnakeCaseStrategy.class)
public class SleepDuration2 extends Measure {

    public static final SchemaId SCHEMA_ID = new SchemaId(OMH_NAMESPACE, "sleep-duration", "2.0");

    private DurationUnitValue sleepDuration;
    private DescriptiveStatisticDenominator descriptiveStatisticDenominator;


    @SerializationConstructor
    protected SleepDuration2() {
    }

    public static class Builder extends TimeIntervalEffectiveTimeFrameBuilder<SleepDuration2, Builder> {

        private DurationUnitValue sleepDuration;
        private DescriptiveStatisticDenominator descriptiveStatisticDenominator;

        public Builder(DurationUnitValue sleepDuration, TimeFrame effectiveTimeFrame) {

            super(effectiveTimeFrame);

            checkNotNull(sleepDuration, "A sleep duration hasn't been specified.");
            checkArgument(EnumSet.of(SECOND, MINUTE, HOUR).contains(sleepDuration.getTypedUnit()),
                    "The unit '{}' is not a valid sleep duration unit.", sleepDuration.getUnit());

            this.sleepDuration = sleepDuration;
        }

        public Builder(DurationUnitValue sleepDuration, TimeInterval effectiveTimeInterval) {
            this(sleepDuration, new TimeFrame(effectiveTimeInterval));
        }

        public Builder setDescriptiveStatisticDenominator(DescriptiveStatisticDenominator denominator) {
            this.descriptiveStatisticDenominator = denominator;
            return this;
        }

        @Override
        public SleepDuration2 build() {
            return new SleepDuration2(this);
        }
    }

    private SleepDuration2(Builder builder) {
        super(builder);

        this.sleepDuration = builder.sleepDuration;
        this.descriptiveStatisticDenominator = builder.descriptiveStatisticDenominator;
    }

    public DurationUnitValue getSleepDuration() {
        return sleepDuration;
    }

    public DescriptiveStatisticDenominator getDescriptiveStatisticDenominator() {
        return descriptiveStatisticDenominator;
    }

    @Override
    public SchemaId getSchemaId() {
        return SCHEMA_ID;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        if (!super.equals(o)) {
            return false;
        }

        SleepDuration2 that = (SleepDuration2) o;

        return Objects.equals(sleepDuration, that.sleepDuration) &&
                descriptiveStatisticDenominator == that.descriptiveStatisticDenominator;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), sleepDuration, descriptiveStatisticDenominator);
    }
}
