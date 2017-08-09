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
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.openmhealth.schema.serializer.SerializationConstructor;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * A measurement of the ambient temperature.
 *
 * @author Chris Schaefbauer
 * @version 1.0
 * @see
 * <a href="http://www.openmhealth.org/documentation/#/schema-docs/schema-library/schemas/omh_ambient-temperature">ambient-temperature</a>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(SnakeCaseStrategy.class)
public class AmbientTemperature extends Measure {

    private static final SchemaId SCHEMA_ID = new SchemaId(OMH_NAMESPACE, "ambient-temperature", "1.0");

    private TemperatureUnitValue ambientTemperature;

    @SerializationConstructor
    protected AmbientTemperature() {
    }

    public static class Builder extends Measure.Builder<AmbientTemperature, Builder> {

        private final TemperatureUnitValue ambientTemperature;

        public Builder(TemperatureUnitValue ambientTemperature) {

            checkNotNull(ambientTemperature, "An ambient temperature value was not specified.");

            this.ambientTemperature = ambientTemperature;
        }

        @Override
        public AmbientTemperature build() {
            return new AmbientTemperature(this);
        }
    }

    private AmbientTemperature(Builder builder) {

        super(builder);

        this.ambientTemperature = builder.ambientTemperature;
    }

    @Override
    public SchemaId getSchemaId() {
        return SCHEMA_ID;
    }

    public TemperatureUnitValue getAmbientTemperature() {
        return ambientTemperature;
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

        AmbientTemperature that = (AmbientTemperature) object;

        return ambientTemperature.equals(that.ambientTemperature);
    }

    @Override
    public int hashCode() {

        int result = super.hashCode();

        result = 31 * result + ambientTemperature.hashCode();

        return result;
    }
}
