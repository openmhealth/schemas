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
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.openmhealth.schema.serializer.SerializationConstructor;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * A measurement of body fat percentage.
 *
 * @author Chris Schaefbauer
 * @version 1.0
 * @see
 * <a href="http://www.openmhealth.org/documentation/#/schema-docs/schema-library/schemas/omh_body-fat-percentage">body-fat-percentage</a>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(SnakeCaseStrategy.class)
public class BodyFatPercentage extends Measure {

    private static final SchemaId SCHEMA_ID = new SchemaId(OMH_NAMESPACE, "body-fat-percentage", "1.0");

    private TypedUnitValue<PercentUnit> bodyFatPercentage;

    @SerializationConstructor
    protected BodyFatPercentage() {
    }

    public static class Builder extends Measure.Builder<BodyFatPercentage, Builder> {

        private final TypedUnitValue<PercentUnit> bodyFatPercentage;

        public Builder(TypedUnitValue<PercentUnit> bodyFatPercentage) {

            checkNotNull(bodyFatPercentage, "No body fat percentage value was specified.");

            this.bodyFatPercentage = bodyFatPercentage;
        }

        @Override
        public BodyFatPercentage build() {
            return new BodyFatPercentage(this);
        }
    }

    private BodyFatPercentage(Builder builder) {

        super(builder);

        this.bodyFatPercentage = builder.bodyFatPercentage;
    }

    @Override
    public SchemaId getSchemaId() {
        return SCHEMA_ID;
    }

    public TypedUnitValue<PercentUnit> getBodyFatPercentage() {
        return bodyFatPercentage;
    }


    @Override
    public boolean equals(Object object) {

        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        if (!super.equals(object)) {
            return false;
        }

        BodyFatPercentage that = (BodyFatPercentage) object;

        return bodyFatPercentage.equals(that.bodyFatPercentage);
    }

    @Override
    public int hashCode() {

        int result = super.hashCode();

        result = 31 * result + bodyFatPercentage.hashCode();

        return result;
    }
}
