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

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * A person’s blood pressure as a combination of a systolic and a diastolic blood pressure.
 *
 * @author Emerson Farrugia
 * @version 1.0
 * @see <a href="http://www.openmhealth.org/documentation/#/schema-docs/schema-library/schemas/omh_blood-pressure">blood-pressure</a>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(SnakeCaseStrategy.class)
public class BloodPressure extends Measure {

    public static final SchemaId SCHEMA_ID = new SchemaId(OMH_NAMESPACE, "blood-pressure", "1.0");

    private SystolicBloodPressure systolicBloodPressure;
    private DiastolicBloodPressure diastolicBloodPressure;
    private PositionDuringMeasurement positionDuringMeasurement;


    @SerializationConstructor
    protected BloodPressure() {
    }

    public static class Builder extends Measure.Builder<BloodPressure, Builder> {

        private SystolicBloodPressure systolicBloodPressure;
        private DiastolicBloodPressure diastolicBloodPressure;
        private PositionDuringMeasurement positionDuringMeasurement;

        public Builder(SystolicBloodPressure systolicBloodPressure, DiastolicBloodPressure diastolicBloodPressure) {

            checkNotNull(systolicBloodPressure, "A systolic blood pressure hasn't been specified.");
            checkNotNull(diastolicBloodPressure, "A diastolic blood pressure hasn't been specified.");

            this.systolicBloodPressure = systolicBloodPressure;
            this.diastolicBloodPressure = diastolicBloodPressure;
        }

        public Builder setPositionDuringMeasurement(PositionDuringMeasurement positionDuringMeasurement) {
            this.positionDuringMeasurement = positionDuringMeasurement;
            return this;
        }

        @Override
        public BloodPressure build() {
            return new BloodPressure(this);
        }
    }

    private BloodPressure(Builder builder) {
        super(builder);

        this.systolicBloodPressure = builder.systolicBloodPressure;
        this.diastolicBloodPressure = builder.diastolicBloodPressure;
        this.positionDuringMeasurement = builder.positionDuringMeasurement;
    }

    public SystolicBloodPressure getSystolicBloodPressure() {
        return systolicBloodPressure;
    }

    public DiastolicBloodPressure getDiastolicBloodPressure() {
        return diastolicBloodPressure;
    }

    public PositionDuringMeasurement getPositionDuringMeasurement() {
        return positionDuringMeasurement;
    }

    @Override
    public SchemaId getSchemaId() {
        return SCHEMA_ID;
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
        if (!super.equals(object)) {
            return false;
        }

        BloodPressure that = (BloodPressure) object;

        if (!systolicBloodPressure.equals(that.systolicBloodPressure)) {
            return false;
        }
        if (!diastolicBloodPressure.equals(that.diastolicBloodPressure)) {
            return false;
        }
        return positionDuringMeasurement == that.positionDuringMeasurement;
    }

    @Override
    public int hashCode() {

        int result = super.hashCode();
        result = 31 * result + systolicBloodPressure.hashCode();
        result = 31 * result + diastolicBloodPressure.hashCode();
        result = 31 * result + (positionDuringMeasurement != null ? positionDuringMeasurement.hashCode() : 0);
        return result;
    }
}
