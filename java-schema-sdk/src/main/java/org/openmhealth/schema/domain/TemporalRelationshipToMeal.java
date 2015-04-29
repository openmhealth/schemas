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

package org.openmhealth.schema.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;


/**
 * The temporal relationship of a clinical measure or assessment to meals.
 *
 * @author Emerson Farrugia
 * @version 1.0
 * @see <a href="http://www.openmhealth.org/developers/schemas/#temporal-relationship-to-meal">temporal-relationship
 * -to-meal</a>
 */
public enum TemporalRelationshipToMeal {

    FASTING,
    NOT_FASTING,
    BEFORE_MEAL,
    AFTER_MEAL,
    BEFORE_BREAKFAST,
    AFTER_BREAKFAST,
    BEFORE_LUNCH,
    AFTER_LUNCH,
    BEFORE_DINNER,
    AFTER_DINNER,
    TWO_HOURS_POSTPRANDIAL("2 hours postprandial");

    private String schemaValue;
    private static final Map<String, TemporalRelationshipToMeal> constantsBySchemaValue = new HashMap<>();

    static {
        for (TemporalRelationshipToMeal constant : values()) {
            constantsBySchemaValue.put(constant.getSchemaValue(), constant);
        }
    }

    TemporalRelationshipToMeal() {
        this.schemaValue = name().toLowerCase().replace('_', ' ');
    }

    TemporalRelationshipToMeal(String schemaValue) {
        this.schemaValue = schemaValue;
    }

    @JsonValue
    public String getSchemaValue() {
        return this.schemaValue;
    }

    @Nullable
    @JsonCreator
    public static TemporalRelationshipToMeal findBySchemaValue(String schemaValue) {
        return constantsBySchemaValue.get(schemaValue);
    }
}
