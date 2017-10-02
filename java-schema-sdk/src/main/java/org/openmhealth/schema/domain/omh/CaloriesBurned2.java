/*
 * Copyright 2017 Open mHealth
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
 * The calories burned in a single episode of activity.
 *
 * @author Emerson Farrugia
 * @version 2.0
 * @see <a href="http://www.openmhealth.org/documentation/#/schema-docs/schema-library/schemas/omh_calories-burned">calories-burned</a>
 */
@JsonInclude(NON_NULL)
@JsonNaming(SnakeCaseStrategy.class)
public class CaloriesBurned2 extends Measure {

    public static final SchemaId SCHEMA_ID = new SchemaId(OMH_NAMESPACE, "calories-burned", "2.0");

    private KcalUnitValue kcalBurned;
    private String activityName;
    private DescriptiveStatisticDenominator descriptiveStatisticDenominator;


    @SerializationConstructor
    protected CaloriesBurned2() {
    }

    public static class Builder
            extends Measure.TimeIntervalEffectiveTimeFrameBuilder<CaloriesBurned2, CaloriesBurned2.Builder> {

        private KcalUnitValue kcalBurned;
        private String activityName;
        private DescriptiveStatisticDenominator descriptiveStatisticDenominator;

        public Builder(KcalUnitValue kcalBurned, TimeFrame effectiveTimeFrame) {

            super(effectiveTimeFrame);

            checkNotNull(kcalBurned, "A unit value of calories burned hasn't been specified.");
            this.kcalBurned = kcalBurned;
        }

        public Builder(KcalUnitValue kcalBurned, TimeInterval effectiveTimeInterval) {
            this(kcalBurned, new TimeFrame(effectiveTimeInterval));
        }

        public Builder setActivityName(String activityName) {
            this.activityName = activityName;
            return this;
        }

        public Builder setDescriptiveStatisticDenominator(DescriptiveStatisticDenominator denominator) {
            this.descriptiveStatisticDenominator = denominator;
            return this;
        }

        @Override
        public CaloriesBurned2 build() {
            return new CaloriesBurned2(this);
        }
    }

    private CaloriesBurned2(Builder builder) {
        super(builder);

        this.kcalBurned = builder.kcalBurned;
        this.activityName = builder.activityName;
        this.descriptiveStatisticDenominator = builder.descriptiveStatisticDenominator;
    }

    public KcalUnitValue getKcalBurned() {
        return kcalBurned;
    }

    public String getActivityName() {
        return activityName;
    }

    public DescriptiveStatisticDenominator getDescriptiveStatisticDenominator() {
        return descriptiveStatisticDenominator;
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

        CaloriesBurned2 that = (CaloriesBurned2) o;

        return Objects.equals(kcalBurned, that.kcalBurned) &&
                Objects.equals(activityName, that.activityName) &&
                descriptiveStatisticDenominator == that.descriptiveStatisticDenominator;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), kcalBurned, activityName, descriptiveStatisticDenominator);
    }
}
