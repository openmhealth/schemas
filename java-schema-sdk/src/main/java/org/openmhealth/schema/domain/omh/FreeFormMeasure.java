/*
 * Copyright 2016 Open mHealth
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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.openmhealth.schema.serializer.SerializationConstructor;


/**
 * A measure whose only declared property is a schema identifier, requiring JSON Schema additional properties for any
 * remaining properties.
 *
 * @author Emerson Farrugia
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(SnakeCaseStrategy.class)
public class FreeFormMeasure extends Measure {

    private SchemaId schemaId;

    @SerializationConstructor
    protected FreeFormMeasure() {
    }

    public static class Builder extends Measure.Builder<FreeFormMeasure, FreeFormMeasure.Builder> {

        private SchemaId schemaId;

        public Builder(String schemaNamespace, String schemaName, String schemaVersion) {
            this.schemaId = new SchemaId(schemaNamespace, schemaName, schemaVersion);
        }

        @Override
        public FreeFormMeasure build() {
            return new FreeFormMeasure(this);
        }
    }

    private FreeFormMeasure(FreeFormMeasure.Builder builder) {
        super(builder);

        schemaId = builder.schemaId;
    }

    @Override
    public SchemaId getSchemaId() {
        return this.schemaId;
    }
}
