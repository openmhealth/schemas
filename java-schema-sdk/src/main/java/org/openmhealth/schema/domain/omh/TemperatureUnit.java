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
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.Maps;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Map;


/**
 * A unit of temperature.
 *
 * @author Chris Schaefbauer
 * @version 1.0
 * @see <a href="http://www.openmhealth.org/documentation/#/schema-docs/schema-library/schemas/omh_temperature-unit-value">temperature-unit-value</a>
 */
public enum TemperatureUnit implements Unit {

    KELVIN("K"),
    CELSIUS("C"),
    FAHRENHEIT("F");

    private static Map<String, TemperatureUnit> constantsBySchemaValue = Maps.newHashMap();
    private final String schemaValue;

    static {
        for (TemperatureUnit unit : values()) {
            constantsBySchemaValue.put(unit.getSchemaValue(), unit);
        }
    }

    TemperatureUnit(String schemaValue) {
        this.schemaValue = schemaValue;
    }

    @Override
    @JsonValue
    public String getSchemaValue() {
        return schemaValue;
    }

    @Nullable
    @JsonCreator
    public static TemperatureUnit findBySchemaValue(String schemaValue) {
        return constantsBySchemaValue.get(schemaValue);
    }

    public TemperatureUnitValue newUnitValue(BigDecimal value) {
        return new TemperatureUnitValue(this, value);
    }

    public TemperatureUnitValue newUnitValue(double value) {
        return new TemperatureUnitValue(this, value);
    }

    public TemperatureUnitValue newUnitValue(long value) {
        return new TemperatureUnitValue(this, value);
    }
}
