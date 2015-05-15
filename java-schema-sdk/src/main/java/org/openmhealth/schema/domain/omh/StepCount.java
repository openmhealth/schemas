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
import com.fasterxml.jackson.databind.PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.openmhealth.schema.serializer.SerializationConstructor;

import java.math.BigDecimal;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * A number of steps.
 *
 * @author Emerson Farrugia
 * @version 1.0
 * @see <a href="http://www.openmhealth.org/developers/schemas/#step-count">step-count</a>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(LowerCaseWithUnderscoresStrategy.class)
public class StepCount extends Measure {

    public static final SchemaId SCHEMA_ID = new SchemaId(OMH_NAMESPACE, "step-count", "1.0");

    private BigDecimal stepCount;


    @SerializationConstructor
    private StepCount() {
    }

    public static class Builder extends Measure.Builder<Builder> {

        private BigDecimal stepCount;

        public Builder(BigDecimal stepCount) {

            checkNotNull(stepCount, "A step count hasn't been specified.");
            this.stepCount = stepCount;
        }

        /**
         * We are working on supporting descriptive statistics for counts, since the semantics of the effective time
         * frame become unclear without additional temporal information.
         */
        @Override
        public Builder setDescriptiveStatistic(DescriptiveStatistic descriptiveStatistic) {
            throw new UnsupportedOperationException();
        }

        @Override
        public StepCount build() {
            return new StepCount(this);
        }
    }

    private StepCount(Builder builder) {
        super(builder);

        this.stepCount = builder.stepCount;
    }

    public BigDecimal getStepCount() {
        return stepCount;
    }

    @Override
    public SchemaId getSchemaId() {
        return SCHEMA_ID;
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

        StepCount stepCount1 = (StepCount) object;

        return stepCount.equals(stepCount1.stepCount);
    }

    @Override
    public int hashCode() {

        int result = super.hashCode();
        result = 31 * result + stepCount.hashCode();
        return result;
    }
}
