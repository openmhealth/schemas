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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.openmhealth.schema.serializer.SerializationConstructor;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;


/**
 * The header of a data point.
 *
 * @author Emerson Farrugia
 * @version 1.0
 * @see <a href="http://www.openmhealth.org/developers/schemas/#header">header</a>
 */
@JsonInclude(NON_NULL)
@JsonNaming(LowerCaseWithUnderscoresStrategy.class)
public class DataPointHeader implements SchemaSupport, AdditionalPropertySupport {

    public static final SchemaId SCHEMA_ID = new SchemaId(OMH_NAMESPACE, "header", "1.0");

    private String id;
    private OffsetDateTime creationDateTime;
    private SchemaId bodySchemaId;
    private DataPointAcquisitionProvenance acquisitionProvenance;
    private Map<String, Object> additionalProperties = new HashMap<>();



    @SerializationConstructor
    private DataPointHeader() {
    }

    public static class Builder {

        private String id;
        private OffsetDateTime creationDateTime;
        private SchemaId bodySchemaId;
        private DataPointAcquisitionProvenance acquisitionProvenance;

        /**
         * @param id the identifier of the data point
         * @param schemaId the identifier of the schema the data point conforms to
         */
        public Builder(String id, SchemaId schemaId) {

            this(id, schemaId, OffsetDateTime.now());
        }

        /**
         * @param id the identifier of the data point
         * @param bodySchemaId the identifier of the schema the data point body conforms to
         * @param creationDateTime the creation date time of this data point
         */
        public Builder(String id, SchemaId bodySchemaId, OffsetDateTime creationDateTime) {

            checkNotNull(id, "An identifier hasn't been specified.");
            checkArgument(!id.isEmpty(), "An empty identifier has been specified.");
            checkNotNull(bodySchemaId, "A schema identifier hasn't been specified.");
            checkNotNull(creationDateTime, "A creation date time hasn't been specified.");

            this.id = id;
            this.bodySchemaId = bodySchemaId;
            this.creationDateTime = creationDateTime;
        }

        /**
         * @param acquisitionProvenance the acquisition provenance of this data point
         * @return this builder
         */
        public Builder setAcquisitionProvenance(DataPointAcquisitionProvenance acquisitionProvenance) {
            this.acquisitionProvenance = acquisitionProvenance;
            return this;
        }

        public DataPointHeader build() {
            return new DataPointHeader(this);
        }
    }

    private DataPointHeader(Builder builder) {

        this.id = builder.id;
        this.creationDateTime = builder.creationDateTime;
        this.bodySchemaId = builder.bodySchemaId;
        this.acquisitionProvenance = builder.acquisitionProvenance;
    }

    public String getId() {
        return id;
    }

    // TODO discuss changing the property name to body_schema_id
    @JsonProperty("schema_id")
    public SchemaId getBodySchemaId() {
        return bodySchemaId;
    }

    public OffsetDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public Optional<DataPointAcquisitionProvenance> getAcquisitionProvenance() {
        return Optional.ofNullable(acquisitionProvenance);
    }

    public void setAcquisitionProvenance(DataPointAcquisitionProvenance acquisitionProvenance) {
        this.acquisitionProvenance = acquisitionProvenance;
    }

    @Override
    public SchemaId getSchemaId() {
        return SCHEMA_ID;
    }

    @Override
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @Override
    public boolean equals(Object object) {

        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        DataPointHeader that = (DataPointHeader) object;

        if (!id.equals(that.id)) {
            return false;
        }
        if (!creationDateTime.equals(that.creationDateTime)) {
            return false;
        }
        if (!bodySchemaId.equals(that.bodySchemaId)) {
            return false;
        }
        return !(acquisitionProvenance != null ? !acquisitionProvenance.equals(that.acquisitionProvenance)
                : that.acquisitionProvenance != null);

    }

    @Override
    public int hashCode() {

        int result = id.hashCode();
        result = 31 * result + creationDateTime.hashCode();
        result = 31 * result + bodySchemaId.hashCode();
        result = 31 * result + (acquisitionProvenance != null ? acquisitionProvenance.hashCode() : 0);
        return result;
    }
}
