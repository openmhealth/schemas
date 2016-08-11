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
import com.fasterxml.jackson.annotation.JsonProperty;
import org.openmhealth.schema.serializer.SerializationConstructor;

import java.math.BigDecimal;


/**
 * @author Chris Schaefbauer
 * @version 1.0
 * @see
 * <a href="http://www.openmhealth.org/documentation/#/schema-docs/schema-library/schemas/omh_temperature-unit-value">temperature-unit-value</a>
 */
public class TemperatureUnitValue extends TypedUnitValue<TemperatureUnit> {

    public static final SchemaId SCHEMA_ID = new SchemaId(OMH_NAMESPACE, "temperature-unit-value", "1.0");

    @SerializationConstructor
    protected TemperatureUnitValue() {
    }

    @JsonCreator
    public TemperatureUnitValue(@JsonProperty("unit") TemperatureUnit unit, @JsonProperty("value") BigDecimal value) {
        super(unit, value);
    }

    @Override
    public SchemaId getSchemaId() {
        return SCHEMA_ID;
    }

    public TemperatureUnitValue(TemperatureUnit unit, Long value) {
        super(unit, value);
    }

    public TemperatureUnitValue(TemperatureUnit unit, Double value) {
        super(unit, value);
    }
}
