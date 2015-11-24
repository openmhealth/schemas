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
 * The period of time in which a day is commonly divided.
 *
 * @author Emerson Farrugia
 * @version 1.0
 * @see
 * <a href="http://www.openmhealth.org/documentation/#/schema-docs/schema-library/schemas/omh_part-of-day">part-of-day</a>
 */
public enum PartOfDay implements SchemaEnumValue, SchemaSupport {

    MORNING,
    AFTERNOON,
    EVENING,
    NIGHT;

    public static final SchemaId SCHEMA_ID = new SchemaId(OMH_NAMESPACE, "part-of-day", "1.0");

    private String schemaValue;
    private static final Map<String, PartOfDay> constantsBySchemaValue = new HashMap<>();

    static {
        for (PartOfDay constant : values()) {
            constantsBySchemaValue.put(constant.getSchemaValue(), constant);
        }
    }

    PartOfDay() {
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
    public static PartOfDay findBySchemaValue(String schemaValue) {
        return constantsBySchemaValue.get(schemaValue);
    }
}