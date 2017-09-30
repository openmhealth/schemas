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
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.common.collect.Maps;
import org.openmhealth.schema.serializer.SerializationConstructor;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * A measurement of blood oxygen saturation.
 *
 * @author Chris Schaefbauer
 * @version 1.0
 * @see
 * <a href="http://www.openmhealth.org/documentation/#/schema-docs/schema-library/schemas/omh_oxygen-saturation">oxygen-saturation</a>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(SnakeCaseStrategy.class)
public class OxygenSaturation extends Measure {

    public static final SchemaId SCHEMA_ID = new SchemaId(OMH_NAMESPACE, "oxygen-saturation", "1.0");


    /**
     * A route by which supplemental oxygen is being administered.
     */
    public enum SupplementalOxygenAdministrationMode implements SchemaEnumValue {

        NASAL_CANNULA;

        private String schemaValue;
        private static final Map<String, SupplementalOxygenAdministrationMode> constantsBySchemaValue =
                Maps.newHashMap();

        static {
            for (SupplementalOxygenAdministrationMode mode : values()) {
                constantsBySchemaValue.put(mode.getSchemaValue(), mode);
            }
        }

        SupplementalOxygenAdministrationMode() {
            this.schemaValue = name().toLowerCase().replace('_', ' ');
        }

        @JsonValue
        @Override
        public String getSchemaValue() {
            return schemaValue;
        }

        @Nullable
        @JsonCreator
        public static SupplementalOxygenAdministrationMode findBySchemaValue(String schemaValue) {

            return constantsBySchemaValue.get(schemaValue);
        }
    }


    /**
     * A body system used to measure oxygen saturation.
     */
    public enum MeasurementSystem implements SchemaEnumValue {

        PERIPHERAL_CAPILLARY;

        private String schemaValue;
        private static final Map<String, MeasurementSystem> constantsBySchemaValue = Maps.newHashMap();

        static {
            for (MeasurementSystem system : values()) {
                constantsBySchemaValue.put(system.getSchemaValue(), system);
            }
        }

        MeasurementSystem() {
            this.schemaValue = name().toLowerCase().replace('_', ' ');
        }

        @JsonValue
        @Override
        public String getSchemaValue() {
            return schemaValue;
        }

        @Nullable
        @JsonCreator
        public static MeasurementSystem findBySchemaValue(String schemaValue) {

            return constantsBySchemaValue.get(schemaValue);
        }
    }


    /**
     * A method used to measure the oxygen saturation value. Pulse oximetry is currently the only method used for
     * measuring oxygen saturation in a non-hospital setting.
     */
    public enum MeasurementMethod implements SchemaEnumValue {

        PULSE_OXIMETRY;

        private String schemaValue;

        private static final Map<String, MeasurementMethod> constantsBySchemaValue = new HashMap<>();

        static {
            for (MeasurementMethod constant : values()) {
                constantsBySchemaValue.put(constant.getSchemaValue(), constant);
            }
        }

        MeasurementMethod() {
            this.schemaValue = name().toLowerCase().replace('_', ' ');
        }

        @JsonValue
        @Override
        public String getSchemaValue() {
            return schemaValue;
        }

        @Nullable
        @JsonCreator
        public static MeasurementMethod findBySchemaValue(String schemaValue) {

            return constantsBySchemaValue.get(schemaValue);
        }
    }


    private TypedUnitValue<PercentUnit> oxygenSaturation;
    private MeasurementSystem measurementSystem;
    private MeasurementMethod measurementMethod;
    private TypedUnitValue<OxygenFlowRateUnit> supplementalOxygenFlowRate;
    private SupplementalOxygenAdministrationMode supplementalOxygenAdministrationMode;

    @SerializationConstructor
    protected OxygenSaturation() {
    }

    public static class Builder extends Measure.Builder<OxygenSaturation, Builder> {

        private final TypedUnitValue<PercentUnit> oxygenSaturation;
        private MeasurementSystem measurementSystem;
        private MeasurementMethod measurementMethod;
        private TypedUnitValue<OxygenFlowRateUnit> supplementalOxygenFlowRate;
        private SupplementalOxygenAdministrationMode supplementalOxygenAdministrationMode;

        public Builder(TypedUnitValue<PercentUnit> oxygenSaturation) {

            checkNotNull(oxygenSaturation, "An oxygen saturation value hasn't been specified.");

            this.oxygenSaturation = oxygenSaturation;
        }

        public Builder setMeasurementSystem(MeasurementSystem measurementSystem) {
            this.measurementSystem = measurementSystem;
            return this;
        }

        public Builder setMeasurementMethod(MeasurementMethod measurementMethod) {
            this.measurementMethod = measurementMethod;
            return this;
        }

        public Builder setSupplementalOxygenFlowRate(TypedUnitValue<OxygenFlowRateUnit> supplementalOxygenFlowRate) {
            this.supplementalOxygenFlowRate = supplementalOxygenFlowRate;
            return this;
        }

        public Builder setSupplementalOxygenAdministrationMode(
                SupplementalOxygenAdministrationMode administrationMode) {
            this.supplementalOxygenAdministrationMode = administrationMode;
            return this;
        }

        @Override
        public OxygenSaturation build() {
            return new OxygenSaturation(this);
        }
    }

    private OxygenSaturation(Builder builder) {

        super(builder);

        this.oxygenSaturation = builder.oxygenSaturation;
        this.measurementSystem = builder.measurementSystem;
        this.supplementalOxygenFlowRate = builder.supplementalOxygenFlowRate;
        this.supplementalOxygenAdministrationMode = builder.supplementalOxygenAdministrationMode;
        this.measurementMethod = builder.measurementMethod;
    }

    @Override
    public SchemaId getSchemaId() {
        return SCHEMA_ID;
    }

    public TypedUnitValue<PercentUnit> getOxygenSaturation() {
        return oxygenSaturation;
    }

    public TypedUnitValue<OxygenFlowRateUnit> getSupplementalOxygenFlowRate() {
        return supplementalOxygenFlowRate;
    }

    @JsonProperty("system")
    public MeasurementSystem getMeasurementSystem() {
        return measurementSystem;
    }

    @JsonProperty("oxygen_therapy_mode_of_administration")
    public SupplementalOxygenAdministrationMode getSupplementalOxygenAdministrationMode() {
        return supplementalOxygenAdministrationMode;
    }

    public MeasurementMethod getMeasurementMethod() {
        return measurementMethod;
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

        OxygenSaturation that = (OxygenSaturation) object;

        if (!oxygenSaturation.equals(that.oxygenSaturation)) {
            return false;
        }
        if (measurementSystem != null ? !measurementSystem.equals(that.measurementSystem)
                : that.measurementSystem != null) {
            return false;
        }
        if (supplementalOxygenFlowRate != null ? !supplementalOxygenFlowRate.equals(that.supplementalOxygenFlowRate)
                : that.supplementalOxygenFlowRate != null) {

            return false;
        }
        if (supplementalOxygenAdministrationMode != null
                ? !supplementalOxygenAdministrationMode.equals(that.supplementalOxygenAdministrationMode)
                : that.supplementalOxygenAdministrationMode != null) {

            return false;
        }

        return measurementMethod == that.measurementMethod;
    }

    @Override
    public int hashCode() {

        int result = super.hashCode();

        result = 31 * result + oxygenSaturation.hashCode();
        result = 31 * result + (measurementSystem != null ? measurementSystem.hashCode() : 0);
        result = 31 * result + (supplementalOxygenFlowRate != null ? supplementalOxygenFlowRate.hashCode() : 0);
        result = 31 * result +
                (supplementalOxygenAdministrationMode != null ? supplementalOxygenAdministrationMode.hashCode() : 0);
        result = 31 * result + (measurementMethod != null ? measurementMethod.hashCode() : 0);

        return result;
    }
}
