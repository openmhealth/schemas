/*
 * Copyright 2017 Open mHealth
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

import java.time.OffsetDateTime;
import java.util.Objects;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.google.common.base.Preconditions.checkNotNull;


/**
 * A speed measurement.
 *
 * @author Emerson Farrugia
 * @version 1.0
 * @see <a href="http://www.openmhealth.org/documentation/#/schema-docs/schema-library/schemas/omh_speed">speed</a>
 */
@JsonInclude(NON_NULL)
@JsonNaming(SnakeCaseStrategy.class)
public class Speed extends Measure {

    public static final SchemaId SCHEMA_ID = new SchemaId(OMH_NAMESPACE, "speed", "1.0");

    private SpeedUnitValue speed;


    @SerializationConstructor
    protected Speed() {
    }

    public static class Builder
            extends Measure.EffectiveTimeFrameBuilder<Speed, Builder> {

        private SpeedUnitValue speed;

        public Builder(SpeedUnitValue speed, TimeFrame effectiveTimeFrame) {

            super(effectiveTimeFrame);

            checkNotNull(speed, "A speed hasn't been specified.");
            this.speed = speed;
        }

        public Builder(SpeedUnitValue speed, TimeInterval effectiveTimeInterval) {

            super(effectiveTimeInterval);

            checkNotNull(speed, "A speed hasn't been specified.");
            this.speed = speed;
        }

        public Builder(SpeedUnitValue speed, OffsetDateTime effectiveDateTime) {

            super(effectiveDateTime);

            checkNotNull(speed, "A speed hasn't been specified.");
            this.speed = speed;
        }

        @Override
        public Speed build() {
            return new Speed(this);
        }
    }

    private Speed(Builder builder) {
        super(builder);

        this.speed = builder.speed;
    }

    public SpeedUnitValue getSpeed() {
        return speed;
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

        Speed speed1 = (Speed) o;

        return Objects.equals(speed, speed1.speed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), speed);
    }
}
