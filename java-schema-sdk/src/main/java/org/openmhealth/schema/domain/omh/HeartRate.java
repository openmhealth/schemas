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

import java.math.BigDecimal;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * A person's heart rate.
 *
 * @author Emerson Farrugia
 * @version 1.0
 * @see <a href="http://www.openmhealth.org/documentation/#/schema-docs/schema-library/schemas/omh_heart-rate">heart-rate</a>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(SnakeCaseStrategy.class)
public class HeartRate extends Measure {

    public static final SchemaId SCHEMA_ID = new SchemaId(OMH_NAMESPACE, "heart-rate", "1.1");

    private TypedUnitValue<HeartRateUnit> heartRate;
    private TemporalRelationshipToPhysicalActivity temporalRelationshipToPhysicalActivity;
    private TemporalRelationshipToSleep temporalRelationshipToSleep;


    @SerializationConstructor
    protected HeartRate() {
    }

    public static class Builder extends Measure.Builder<HeartRate, Builder> {

        private TypedUnitValue<HeartRateUnit> heartRate;
        private TemporalRelationshipToPhysicalActivity temporalRelationshipToPhysicalActivity;
        private TemporalRelationshipToSleep temporalRelationshipToSleep;

        public Builder(TypedUnitValue<HeartRateUnit> heartRate) {

            checkNotNull(heartRate, "A heart rate hasn't been specified.");
            this.heartRate = heartRate;
        }

        /**
         * @param heartRateValue the heart rate in beats per minute
         */
        public Builder(BigDecimal heartRateValue) {

            checkNotNull(heartRateValue, "A heart rate hasn't been specified.");
            this.heartRate = new TypedUnitValue<>(HeartRateUnit.BEATS_PER_MINUTE, heartRateValue);
        }

        /**
         * @param heartRateValue the heart rate in beats per minute
         */
        public Builder(double heartRateValue) {
            this(BigDecimal.valueOf(heartRateValue));
        }

        /**
         * @param heartRateValue the heart rate in beats per minute
         */
        public Builder(long heartRateValue) {
            this(BigDecimal.valueOf(heartRateValue));
        }

        public Builder setTemporalRelationshipToPhysicalActivity(TemporalRelationshipToPhysicalActivity relationship) {
            this.temporalRelationshipToPhysicalActivity = relationship;
            return this;
        }

        public Builder setTemporalRelationshipToSleep(TemporalRelationshipToSleep relationship) {
            this.temporalRelationshipToSleep = relationship;
            return this;
        }

        @Override
        public HeartRate build() {
            return new HeartRate(this);
        }
    }

    private HeartRate(Builder builder) {
        super(builder);

        this.heartRate = builder.heartRate;
        this.temporalRelationshipToPhysicalActivity = builder.temporalRelationshipToPhysicalActivity;
        this.temporalRelationshipToSleep = builder.temporalRelationshipToSleep;
    }

    public TypedUnitValue<HeartRateUnit> getHeartRate() {
        return heartRate;
    }

    public TemporalRelationshipToPhysicalActivity getTemporalRelationshipToPhysicalActivity() {
        return temporalRelationshipToPhysicalActivity;
    }

    public TemporalRelationshipToSleep getTemporalRelationshipToSleep() {
        return temporalRelationshipToSleep;
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

        HeartRate heartRate1 = (HeartRate) o;

        return Objects.equals(heartRate, heartRate1.heartRate) &&
                temporalRelationshipToPhysicalActivity == heartRate1.temporalRelationshipToPhysicalActivity &&
                temporalRelationshipToSleep == heartRate1.temporalRelationshipToSleep;
    }

    @Override
    public int hashCode() {
        return Objects
                .hash(super.hashCode(), heartRate, temporalRelationshipToPhysicalActivity, temporalRelationshipToSleep);
    }
}
