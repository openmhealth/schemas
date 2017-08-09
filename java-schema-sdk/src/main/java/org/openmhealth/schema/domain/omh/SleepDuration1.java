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

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static org.openmhealth.schema.domain.omh.DurationUnit.*;


/**
 * A measurement of sleep duration.
 *
 * @author Emerson Farrugia
 * @version 1.0
 * @see <a href="http://www.openmhealth.org/documentation/#/schema-docs/schema-library/schemas/omh_sleep-duration">sleep-duration</a>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(SnakeCaseStrategy.class)
public class SleepDuration1 extends Measure {

    public static final SchemaId SCHEMA_ID = new SchemaId(OMH_NAMESPACE, "sleep-duration", "1.0");

    private DurationUnitValue sleepDuration;


    @SerializationConstructor
    protected SleepDuration1() {
    }

    public static class Builder extends Measure.Builder<SleepDuration1, Builder> {

        private DurationUnitValue sleepDuration;

        public Builder(DurationUnitValue sleepDuration) {

            checkNotNull(sleepDuration, "A sleep duration hasn't been specified.");
            checkArgument(EnumSet.of(SECOND, MINUTE, HOUR).contains(sleepDuration.getTypedUnit()),
                    "The unit '{}' is not a valid sleep duration unit.", sleepDuration.getUnit());

            this.sleepDuration = sleepDuration;
        }

        @Override
        public SleepDuration1 build() {
            return new SleepDuration1(this);
        }
    }

    private SleepDuration1(Builder builder) {
        super(builder);

        this.sleepDuration = builder.sleepDuration;
    }

    public DurationUnitValue getSleepDuration() {
        return sleepDuration;
    }

    @Override
    public SchemaId getSchemaId() {
        return SCHEMA_ID;
    }

    @Override
    public boolean equals(Object object) {

        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        if (!super.equals(object)) {
            return false;
        }

        SleepDuration1 that = (SleepDuration1) object;

        return sleepDuration.equals(that.sleepDuration);
    }

    @Override
    public int hashCode() {

        int result = super.hashCode();
        result = 31 * result + sleepDuration.hashCode();
        return result;
    }
}
