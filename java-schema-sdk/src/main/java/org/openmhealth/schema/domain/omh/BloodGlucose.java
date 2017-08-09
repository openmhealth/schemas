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

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * A person's blood glucose level.
 *
 * @author Emerson Farrugia
 * @version 1.0
 * @see <a href="http://www.openmhealth.org/documentation/#/schema-docs/schema-library/schemas/omh_blood-glucose">blood-glucose</a>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(SnakeCaseStrategy.class)
public class BloodGlucose extends Measure {

    public static final SchemaId SCHEMA_ID = new SchemaId(OMH_NAMESPACE, "blood-glucose", "1.0");

    private TypedUnitValue<BloodGlucoseUnit> bloodGlucose;
    private BloodSpecimenType bloodSpecimenType;
    private TemporalRelationshipToMeal temporalRelationshipToMeal;
    private TemporalRelationshipToSleep temporalRelationshipToSleep;


    @SerializationConstructor
    protected BloodGlucose() {
    }

    public static class Builder extends Measure.Builder<BloodGlucose, Builder> {

        private TypedUnitValue<BloodGlucoseUnit> bloodGlucose;
        private BloodSpecimenType bloodSpecimenType;
        private TemporalRelationshipToMeal temporalRelationshipToMeal;
        private TemporalRelationshipToSleep temporalRelationshipToSleep;

        public Builder(TypedUnitValue<BloodGlucoseUnit> bloodGlucose) {

            checkNotNull(bloodGlucose, "A blood glucose level hasn't been specified.");
            this.bloodGlucose = bloodGlucose;
        }

        public Builder setBloodSpecimenType(BloodSpecimenType bloodSpecimenType) {
            this.bloodSpecimenType = bloodSpecimenType;
            return this;
        }

        public Builder setTemporalRelationshipToMeal(TemporalRelationshipToMeal temporalRelationshipToMeal) {
            this.temporalRelationshipToMeal = temporalRelationshipToMeal;
            return this;
        }

        public Builder setTemporalRelationshipToSleep(TemporalRelationshipToSleep temporalRelationshipToSleep) {
            this.temporalRelationshipToSleep = temporalRelationshipToSleep;
            return this;
        }

        @Override
        public BloodGlucose build() {
            return new BloodGlucose(this);
        }
    }

    private BloodGlucose(Builder builder) {
        super(builder);

        bloodGlucose = builder.bloodGlucose;
        bloodSpecimenType = builder.bloodSpecimenType;
        temporalRelationshipToMeal = builder.temporalRelationshipToMeal;
        temporalRelationshipToSleep = builder.temporalRelationshipToSleep;
    }

    public TypedUnitValue<BloodGlucoseUnit> getBloodGlucose() {
        return bloodGlucose;
    }

    public BloodSpecimenType getBloodSpecimenType() {
        return bloodSpecimenType;
    }

    public TemporalRelationshipToMeal getTemporalRelationshipToMeal() {
        return temporalRelationshipToMeal;
    }

    public TemporalRelationshipToSleep getTemporalRelationshipToSleep() {
        return temporalRelationshipToSleep;
    }

    @Override
    public SchemaId getSchemaId() {
        return SCHEMA_ID;
    }

    @SuppressWarnings("SimplifiableIfStatement")
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

        BloodGlucose that = (BloodGlucose) object;

        if (!bloodGlucose.equals(that.bloodGlucose)) {
            return false;
        }
        if (bloodSpecimenType != that.bloodSpecimenType) {
            return false;
        }
        if (temporalRelationshipToMeal != that.temporalRelationshipToMeal) {
            return false;
        }
        return temporalRelationshipToSleep == that.temporalRelationshipToSleep;
    }

    @Override
    public int hashCode() {

        int result = super.hashCode();
        result = 31 * result + bloodGlucose.hashCode();
        result = 31 * result + (bloodSpecimenType != null ? bloodSpecimenType.hashCode() : 0);
        result = 31 * result + (temporalRelationshipToMeal != null ? temporalRelationshipToMeal.hashCode() : 0);
        result = 31 * result + (temporalRelationshipToSleep != null ? temporalRelationshipToSleep.hashCode() : 0);
        return result;
    }
}
