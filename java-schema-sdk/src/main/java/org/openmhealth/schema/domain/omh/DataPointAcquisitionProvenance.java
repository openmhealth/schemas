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
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.openmhealth.schema.serializer.SerializationConstructor;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;


/**
 * The acquisition provenance of a data point, representing how and when the data point was acquired.
 *
 * @author Emerson Farrugia
 * @version 1.0
 * @see <a href="http://www.openmhealth.org/documentation/#/schema-docs/schema-library/schemas/omh_header">header</a>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(SnakeCaseStrategy.class)
public class DataPointAcquisitionProvenance implements AdditionalPropertySupport {

    private String sourceName;
    private OffsetDateTime sourceCreationDateTime;
    private DataPointModality modality;
    private Map<String, Object> additionalProperties = new HashMap<>();


    @SerializationConstructor
    protected DataPointAcquisitionProvenance() {
    }

    public static class Builder {

        private String sourceName;
        private OffsetDateTime sourceCreationDateTime;
        private DataPointModality modality;

        /**
         * @param sourceName the name of the source of the data point
         */
        public Builder(String sourceName) {

            checkNotNull(sourceName, "A source name hasn't been specified.");
            checkArgument(!sourceName.isEmpty(), "An empty source name has been specified.");

            this.sourceName = sourceName;
        }

        /**
         * @param sourceCreationDateTime the timestamp of data point creation at the source
         * @return this builder
         */
        public Builder setSourceCreationDateTime(OffsetDateTime sourceCreationDateTime) {
            this.sourceCreationDateTime = sourceCreationDateTime;
            return this;
        }

        /**
         * @param modality the modality whereby the measure is obtained
         * @return this builder
         */
        public Builder setModality(DataPointModality modality) {
            this.modality = modality;
            return this;
        }

        public DataPointAcquisitionProvenance build() {
            return new DataPointAcquisitionProvenance(this);
        }
    }

    private DataPointAcquisitionProvenance(Builder builder) {

        this.sourceName = builder.sourceName;
        this.sourceCreationDateTime = builder.sourceCreationDateTime;
        this.modality = builder.modality;
    }

    public String getSourceName() {
        return sourceName;
    }

    public OffsetDateTime getSourceCreationDateTime() {
        return sourceCreationDateTime;
    }

    public DataPointModality getModality() {
        return modality;
    }

    @Override
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @SuppressWarnings("SimplifiableIfStatement")
    @Override
    public boolean equals(Object object) {

        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        DataPointAcquisitionProvenance that = (DataPointAcquisitionProvenance) object;

        if (!sourceName.equals(that.sourceName)) {
            return false;
        }
        if (sourceCreationDateTime != null ? !sourceCreationDateTime.equals(that.sourceCreationDateTime)
                : that.sourceCreationDateTime != null) {
            return false;
        }
        return modality == that.modality;

    }

    @Override
    public int hashCode() {

        int result = sourceName.hashCode();
        result = 31 * result + (sourceCreationDateTime != null ? sourceCreationDateTime.hashCode() : 0);
        result = 31 * result + (modality != null ? modality.hashCode() : 0);
        return result;
    }
}
