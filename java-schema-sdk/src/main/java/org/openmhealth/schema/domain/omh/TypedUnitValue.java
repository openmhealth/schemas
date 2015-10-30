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


/**
 * A unit value implementation that uses a Java enum to represent units.
 *
 * @author Emerson Farrugia
 */
public class TypedUnitValue<T extends Unit> extends UnitValue {

    private T typedUnit;


    @SerializationConstructor
    protected TypedUnitValue() {
    }

    @JsonCreator
    public TypedUnitValue(@JsonProperty("unit") T typedUnit, @JsonProperty("value") BigDecimal value) {
        super(typedUnit.getSchemaValue(), value);

        this.typedUnit = typedUnit;
    }

    public TypedUnitValue(T typedUnit, double value) {
        this(typedUnit, BigDecimal.valueOf(value));
    }

    public TypedUnitValue(T typedUnit, long value) {
        this(typedUnit, BigDecimal.valueOf(value));
    }

    @JsonIgnore
    public T getTypedUnit() {
        return typedUnit;
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

        TypedUnitValue<?> that = (TypedUnitValue<?>) object;

        return typedUnit.equals(that.typedUnit);

    }

    @Override
    public int hashCode() {

        int result = super.hashCode();
        result = 31 * result + typedUnit.hashCode();
        return result;
    }
}
