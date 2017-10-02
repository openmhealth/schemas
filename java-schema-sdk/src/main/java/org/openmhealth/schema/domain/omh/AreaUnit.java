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

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


/**
 * A unit of area, or a measure of a surface.
 *
 * @author Emerson Farrugia
 * @version 1.0
 * @see <a href="http://www.openmhealth.org/documentation/#/schema-docs/schema-library/schemas/omh_area-unit-value">area-unit-value</a>
 */
public enum AreaUnit implements Unit {

    SQUARE_MILLIMETER("mm^2"),
    SQUARE_CENTIMETER("cm^2"),
    SQUARE_METER("m^2"),
    SQUARE_KILOMETER("km^2"),
    SQUARE_INCH("in^2"),
    SQUARE_FOOT("ft^2"),
    SQUARE_YARD("yd^2"),
    SQUARE_MILE("mi^2");

    private String schemaValue;
    private static final Map<String, AreaUnit> constantsBySchemaValue = new HashMap<>();

    static {
        for (AreaUnit constant : values()) {
            constantsBySchemaValue.put(constant.getSchemaValue(), constant);
        }
    }

    AreaUnit(String schemaValue) {
        this.schemaValue = schemaValue;
    }

    @Override
    @JsonValue
    public String getSchemaValue() {
        return this.schemaValue;
    }

    @Nullable
    @JsonCreator
    public static AreaUnit findBySchemaValue(String schemaValue) {
        return constantsBySchemaValue.get(schemaValue);
    }

    public AreaUnitValue newUnitValue(BigDecimal value) {
        return new AreaUnitValue(this, value);
    }

    public AreaUnitValue newUnitValue(double value) {
        return new AreaUnitValue(this, value);
    }

    public AreaUnitValue newUnitValue(long value) {
        return new AreaUnitValue(this, value);
    }
}
