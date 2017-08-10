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
 * A measurement of body temperature.
 *
 * @author Chris Schaefbauer
 * @version 1.0
 * @see
 * <a href="http://www.openmhealth.org/documentation/#/schema-docs/schema-library/schemas/omh_body-temperature">body-temperature</a>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(SnakeCaseStrategy.class)
public class BodyTemperature extends Measure {

    private static final SchemaId SCHEMA_ID = new SchemaId(OMH_NAMESPACE, "body-temperature", "1.0");


    /**
     * A location where a body temperature measurement is taken.
     */
    public enum MeasurementLocation implements SchemaEnumValue {

        ORAL,
        RECTAL,
        VAGINAL,
        AXILLARY,
        OTIC,
        WRIST,
        CHEST,
        TEMPORAL;

        private String schemaValue;
        private static final Map<String, MeasurementLocation> constantsBySchemaValue = Maps.newHashMap();

        static {
            for (MeasurementLocation location : values()) {
                constantsBySchemaValue.put(location.getSchemaValue(), location);
            }
        }

        MeasurementLocation() {
            schemaValue = name().toLowerCase();
        }

        @Override
        @JsonValue
        public String getSchemaValue() {
            return schemaValue;
        }

        @JsonCreator
        @Nullable
        public MeasurementLocation findBySchemaValue(String schemaValue) {
            return constantsBySchemaValue.get(schemaValue);
        }
    }


    private TemperatureUnitValue bodyTemperature;
    private MeasurementLocation measurementLocation;

    @SerializationConstructor
    protected BodyTemperature() {
    }

    public static class Builder extends Measure.Builder<BodyTemperature, Builder> {

        private final TemperatureUnitValue bodyTemperature;
        private MeasurementLocation measurementLocation;

        public Builder(TemperatureUnitValue bodyTemperature) {

            checkNotNull(bodyTemperature, "An body temperature value hasn't been specified.");

            this.bodyTemperature = bodyTemperature;
        }

        public Builder setMeasurementLocation(MeasurementLocation measurementLocation) {
            this.measurementLocation = measurementLocation;
            return this;
        }

        @Override
        public BodyTemperature build() {
            return new BodyTemperature(this);
        }
    }

    private BodyTemperature(Builder builder) {

        super(builder);
        this.bodyTemperature = builder.bodyTemperature;
        this.measurementLocation = builder.measurementLocation;
    }

    @Override
    public SchemaId getSchemaId() {
        return SCHEMA_ID;
    }

    public TemperatureUnitValue getBodyTemperature() {
        return bodyTemperature;
    }

    public MeasurementLocation getMeasurementLocation() {
        return measurementLocation;
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

        BodyTemperature that = (BodyTemperature) object;

        if (!bodyTemperature.equals(that.bodyTemperature)) {
            return false;
        }

        return measurementLocation == that.measurementLocation;
    }

    @Override
    public int hashCode() {

        int result = super.hashCode();
        result = 31 * result + bodyTemperature.hashCode();
        result = 31 * result + (measurementLocation != null ? measurementLocation.hashCode() : 0);
        return result;
    }
}
