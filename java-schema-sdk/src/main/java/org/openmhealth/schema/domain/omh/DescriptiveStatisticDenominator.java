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
 * A denominator used by by a descriptive statistic when applied to a measure with an implicit duration.
 *
 * @author Emerson Farrugia
 * @version 1.0
 * @see
 * <a href="http://www.openmhealth.org/documentation/#/schema-docs/schema-library/schemas/omh_descriptive-statistic-denominator">descriptive-statistic-denominator</a>
 */
public enum DescriptiveStatisticDenominator implements SchemaEnumValue, SchemaSupport {

    DAY("d"),
    EPISODE,
    MEAL,
    SESSION;

    public static final SchemaId SCHEMA_ID = new SchemaId(OMH_NAMESPACE, "descriptive-statistic-denominator", "1.0");

    private String schemaValue;
    private static final Map<String, DescriptiveStatisticDenominator> constantsBySchemaValue = new HashMap<>();

    static {
        for (DescriptiveStatisticDenominator constant : values()) {
            constantsBySchemaValue.put(constant.getSchemaValue(), constant);
        }
    }

    DescriptiveStatisticDenominator() {
        this.schemaValue = name().toLowerCase().replace('_', ' ');
    }

    DescriptiveStatisticDenominator(String schemaValue) {

        this.schemaValue = schemaValue;
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
    public static DescriptiveStatisticDenominator findBySchemaValue(String schemaValue) {
        return constantsBySchemaValue.get(schemaValue);
    }
}
