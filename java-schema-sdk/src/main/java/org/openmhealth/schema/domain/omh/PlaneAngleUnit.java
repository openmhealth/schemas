/*
 * Copyright 2017 Open mHealth
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
 * A unit of an angle on a plane.
 *
 * @author Emerson Farrugia
 * @version 1.0
 * @see <a href="http://www.openmhealth.org/documentation/#/schema-docs/schema-library/schemas/omh_plane-angle-unit-value">plane-angle-unit-value</a>
 */
public enum PlaneAngleUnit implements Unit {

    DEGREE_OF_ARC("deg");

    private String schemaValue;
    private static final Map<String, PlaneAngleUnit> constantsBySchemaValue = new HashMap<>();

    static {
        for (PlaneAngleUnit constant : values()) {
            constantsBySchemaValue.put(constant.getSchemaValue(), constant);
        }
    }

    PlaneAngleUnit(String schemaValue) {
        this.schemaValue = schemaValue;
    }

    @Override
    @JsonValue
    public String getSchemaValue() {
        return this.schemaValue;
    }

    @Nullable
    @JsonCreator
    public static PlaneAngleUnit findBySchemaValue(String schemaValue) {
        return constantsBySchemaValue.get(schemaValue);
    }

    public PlaneAngleUnitValue newUnitValue(BigDecimal value) {
        return new PlaneAngleUnitValue(this, value);
    }

    public PlaneAngleUnitValue newUnitValue(double value) {
        return new PlaneAngleUnitValue(this, value);
    }

    public PlaneAngleUnitValue newUnitValue(long value) {
        return new PlaneAngleUnitValue(this, value);
    }
}
