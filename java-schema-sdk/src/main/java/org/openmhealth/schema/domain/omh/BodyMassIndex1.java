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

import java.util.Objects;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.google.common.base.Preconditions.checkNotNull;


/**
 * A person's body mass index.
 *
 * @author Emerson Farrugia
 * @version 1.0
 * @see <a href="http://www.openmhealth.org/documentation/#/schema-docs/schema-library/schemas/omh_body-mass-index">body-mass-index</a>
 */
@JsonInclude(NON_NULL)
@JsonNaming(SnakeCaseStrategy.class)
public class BodyMassIndex1 extends Measure {

    public static final SchemaId SCHEMA_ID = new SchemaId(OMH_NAMESPACE, "body-mass-index", "1.0");

    private TypedUnitValue<BodyMassIndexUnit1> bodyMassIndex;


    @SerializationConstructor
    protected BodyMassIndex1() {
    }

    public static class Builder extends Measure.Builder<BodyMassIndex1, Builder> {

        private TypedUnitValue<BodyMassIndexUnit1> bodyMassIndex;


        public Builder(TypedUnitValue<BodyMassIndexUnit1> bodyMassIndex) {

            checkNotNull(bodyMassIndex, "A body mass index hasn't been specified.");
            this.bodyMassIndex = bodyMassIndex;
        }

        @Override
        public BodyMassIndex1 build() {
            return new BodyMassIndex1(this);
        }
    }

    private BodyMassIndex1(Builder builder) {
        super(builder);

        bodyMassIndex = builder.bodyMassIndex;
    }

    public TypedUnitValue<BodyMassIndexUnit1> getBodyMassIndex() {
        return bodyMassIndex;
    }

    @Override
    public SchemaId getSchemaId() {
        return SCHEMA_ID;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        if (!super.equals(o)) {
            return false;
        }

        BodyMassIndex1 that = (BodyMassIndex1) o;

        return Objects.equals(bodyMassIndex, that.bodyMassIndex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), bodyMassIndex);
    }
}
