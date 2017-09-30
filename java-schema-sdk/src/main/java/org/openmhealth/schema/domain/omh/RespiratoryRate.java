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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.common.collect.Maps;
import org.openmhealth.schema.serializer.SerializationConstructor;

import javax.annotation.Nullable;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * A measurement of respiratory rate.
 *
 * @author Chris Schaefbauer
 * @version 1.0
 * @see
 * <a href="http://www.openmhealth.org/documentation/#/schema-docs/schema-library/schemas/omh_respiratory-rate">respiratory-rate</a>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(SnakeCaseStrategy.class)
public class RespiratoryRate extends Measure {

    private static final SchemaId SCHEMA_ID = new SchemaId(OMH_NAMESPACE, "respiratory-rate", "1.0");


    /**
     * A unit for the rate at which respiration occurs.
     */
    public enum RespirationUnit implements Unit {

        BREATHS_PER_MINUTE("breaths/min");

        private final String schemaValue;
        private static final Map<String, RespirationUnit> constantsBySchemaValue = Maps.newHashMap();

        static {
            for (RespirationUnit unit : values()) {
                constantsBySchemaValue.put(unit.getSchemaValue(), unit);
            }
        }

        RespirationUnit(String schemaValue) {
            this.schemaValue = schemaValue;
        }

        @Override
        @JsonValue
        public String getSchemaValue() {
            return schemaValue;
        }

        @JsonCreator
        @Nullable
        public RespirationUnit findBySchemaValue(String schemaValue) {
            return constantsBySchemaValue.get(schemaValue);
        }
    }


    private TypedUnitValue<RespirationUnit> respiratoryRate;
    private TemporalRelationshipToPhysicalActivity temporalRelationshipToPhysicalActivity;

    @SerializationConstructor
    protected RespiratoryRate() {

    }

    public static class Builder extends Measure.Builder<org.openmhealth.schema.domain.omh.RespiratoryRate, Builder> {

        private final TypedUnitValue<RespirationUnit> respiratoryRate;
        private TemporalRelationshipToPhysicalActivity temporalRelationshipToPhysicalActivity;

        public Builder(TypedUnitValue<RespirationUnit> respiratoryRate) {

            checkNotNull(respiratoryRate, "The respiratory rate value hasn't been specified.");

            this.respiratoryRate = respiratoryRate;
        }

        public Builder setTemporalRelationshipToPhysicalActivity(
                TemporalRelationshipToPhysicalActivity temporalRelationshipToPhysicalActivity) {

            this.temporalRelationshipToPhysicalActivity = temporalRelationshipToPhysicalActivity;
            return this;
        }

        @Override
        public RespiratoryRate build() {
            return new RespiratoryRate(this);
        }
    }

    private RespiratoryRate(Builder builder) {

        super(builder);

        this.respiratoryRate = builder.respiratoryRate;
        this.temporalRelationshipToPhysicalActivity = builder.temporalRelationshipToPhysicalActivity;
    }

    @Override
    public SchemaId getSchemaId() {
        return SCHEMA_ID;
    }

    public TypedUnitValue<RespirationUnit> getRespiratoryRate() {
        return respiratoryRate;
    }

    public TemporalRelationshipToPhysicalActivity getTemporalRelationshipToPhysicalActivity() {
        return temporalRelationshipToPhysicalActivity;
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

        RespiratoryRate that = (RespiratoryRate) object;

        if (!this.respiratoryRate.equals(that.respiratoryRate)) {
            return false;
        }

        return temporalRelationshipToPhysicalActivity == that.temporalRelationshipToPhysicalActivity;
    }

    @Override
    public int hashCode() {

        int result = super.hashCode();

        result = 31 * result + respiratoryRate.hashCode();
        result = 31 * result + (temporalRelationshipToPhysicalActivity != null
                ? temporalRelationshipToPhysicalActivity.hashCode() : 0);

        return result;
    }
}
