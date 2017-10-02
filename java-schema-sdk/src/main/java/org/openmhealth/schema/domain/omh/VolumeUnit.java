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
 * A unit of volume.
 *
 * @author Emerson Farrugia
 * @version 1.0
 * @see <a href="http://www.openmhealth.org/documentation/#/schema-docs/schema-library/schemas/omh_volume-unit-value">volume-unit-value</a>
 */
public enum VolumeUnit implements Unit {

    FEMTOLITER("fL"),
    PICOLITER("pL"),
    NANOLITER("nL"),
    MICROLITER("uL"),
    MILLILITER("mL"),
    CENTILITER("cL"),
    DECILITER("dL"),
    LITER("L"),
    KILOLITER("kL"),
    FLUID_OUNCE("fl oz"),
    CUP("Cup"),
    CUBIC_INCH("in^3"),
    PINT("pt"),
    QUART("qt"),
    GALLON("gal"),
    TEASPOON("tsp"),
    TABLESPOON("tbsp");

    private String schemaValue;
    private static final Map<String, VolumeUnit> constantsBySchemaValue = new HashMap<>();

    static {
        for (VolumeUnit constant : values()) {
            constantsBySchemaValue.put(constant.getSchemaValue(), constant);
        }
    }

    VolumeUnit(String schemaValue) {
        this.schemaValue = schemaValue;
    }

    @Override
    @JsonValue
    public String getSchemaValue() {
        return this.schemaValue;
    }

    @Nullable
    @JsonCreator
    public static VolumeUnit findBySchemaValue(String schemaValue) {
        return constantsBySchemaValue.get(schemaValue);
    }

    public VolumeUnitValue newUnitValue(BigDecimal value) {
        return new VolumeUnitValue(this, value);
    }

    public VolumeUnitValue newUnitValue(double value) {
        return new VolumeUnitValue(this, value);
    }

    public VolumeUnitValue newUnitValue(long value) {
        return new VolumeUnitValue(this, value);
    }
}
