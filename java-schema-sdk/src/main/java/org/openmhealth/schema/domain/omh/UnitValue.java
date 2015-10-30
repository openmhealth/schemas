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
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.openmhealth.schema.serializer.SerializationConstructor;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;


/**
 * A base class for unit value tuples.
 *
 * @author Emerson Farrugia
 * @version 1.0
 * @see <a href="http://www.openmhealth.org/documentation/#/schema-docs/schema-library/schemas/omh_unit-value">unit-value</a>
 */
public class UnitValue implements SchemaSupport, AdditionalPropertySupport {

    public static final SchemaId SCHEMA_ID = new SchemaId(OMH_NAMESPACE, "unit-value", "1.0");

    private String unit;
    private BigDecimal value;
    private Map<String, Object> additionalProperties = new HashMap<>();


    @SerializationConstructor
    protected UnitValue() {
    }

    @JsonCreator
    public UnitValue(@JsonProperty("unit") String unit, @JsonProperty("value") BigDecimal value) {

        checkNotNull(unit, "A unit hasn't been specified.");
        checkArgument(!unit.isEmpty(), "An empty unit has been specified.");
        checkNotNull(value, "A value hasn't been specified.");

        this.unit = unit;
        this.value = value;
    }

    public UnitValue(String unit, double value) {
        this(unit, BigDecimal.valueOf(value));
    }

    public UnitValue(String unit, long value) {
        this(unit, BigDecimal.valueOf(value));
    }

    public String getUnit() {
        return unit;
    }

    public BigDecimal getValue() {
        return value;
    }

    @Override
    @JsonIgnore
    public SchemaId getSchemaId() {
        return SCHEMA_ID;
    }

    @Override
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @Override
    public boolean equals(Object object) {

        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        UnitValue unitValue = (UnitValue) object;

        return unit.equals(unitValue.unit) && value.compareTo(unitValue.value) == 0;
    }

    @Override
    public int hashCode() {

        int result = unit.hashCode();
        result = 31 * result + value.hashCode();
        return result;
    }
}
