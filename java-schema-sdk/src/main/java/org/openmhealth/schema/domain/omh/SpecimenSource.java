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
import java.util.HashMap;
import java.util.Map;


/**
 * The set of allowable values describing the source of a specimen analyzed.
 *
 * @author Emerson Farrugia
 * @version 1.0
 * @see
 * <a href="http://www.openmhealth.org/documentation/#/schema-docs/schema-library/schemas/omh_specimen-source">specimen-source</a>
 */
public enum SpecimenSource implements SchemaEnumValue, SchemaSupport {

    INTERSTITIAL_FLUID,
    CAPILLARY_BLOOD,
    PLASMA,
    SERUM,
    TEARS,
    WHOLE_BLOOD;

    public static final SchemaId SCHEMA_ID = new SchemaId(OMH_NAMESPACE, "specimen-source", "1.0");

    private String schemaValue;
    private static final Map<String, SpecimenSource> constantsBySchemaValue = new HashMap<>();

    static {
        for (SpecimenSource constant : values()) {
            constantsBySchemaValue.put(constant.getSchemaValue(), constant);
        }
    }

    SpecimenSource() {
        this.schemaValue = name().toLowerCase().replace('_', ' ');
    }

    @Override
    public SchemaId getSchemaId() {
        return SCHEMA_ID;
    }

    @Override
    @JsonValue
    public String getSchemaValue() {
        return this.schemaValue;
    }

    @Nullable
    @JsonCreator
    public static SpecimenSource findBySchemaValue(String schemaValue) {
        return constantsBySchemaValue.get(schemaValue);
    }
}
