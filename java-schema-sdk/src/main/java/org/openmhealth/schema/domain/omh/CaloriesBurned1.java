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

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.google.common.base.Preconditions.checkNotNull;


/**
 * The calories burned in a single episode of activity.
 *
 * @author Emerson Farrugia
 * @version 1.0
 * @see <a href="http://www.openmhealth.org/documentation/#/schema-docs/schema-library/schemas/omh_calories-burned">calories-burned</a>
 */
@JsonInclude(NON_NULL)
@JsonNaming(SnakeCaseStrategy.class)
public class CaloriesBurned1 extends Measure {

    public static final SchemaId SCHEMA_ID = new SchemaId(OMH_NAMESPACE, "calories-burned", "1.0");

    private KcalUnitValue kcalBurned;
    private String activityName;


    @SerializationConstructor
    protected CaloriesBurned1() {
    }

    public static class Builder extends Measure.Builder<CaloriesBurned1, Builder> {

        private KcalUnitValue kcalBurned;
        private String activityName;

        public Builder(KcalUnitValue kcalBurned) {

            checkNotNull(kcalBurned, "A unit value of calories burned hasn't been specified.");
            this.kcalBurned = kcalBurned;
        }

        public Builder setActivityName(String activityName) {
            this.activityName = activityName;
            return this;
        }

        @Override
        public CaloriesBurned1 build() {
            return new CaloriesBurned1(this);
        }
    }

    private CaloriesBurned1(Builder builder) {
        super(builder);

        this.kcalBurned = builder.kcalBurned;
        this.activityName = builder.activityName;
    }

    public KcalUnitValue getKcalBurned() {
        return kcalBurned;
    }

    public String getActivityName() {
        return activityName;
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

        CaloriesBurned1 that = (CaloriesBurned1) object;

        if (!kcalBurned.equals(that.kcalBurned)) {
            return false;
        }
        return !(activityName != null ? !activityName.equals(that.activityName) : that.activityName != null);
    }

    @Override
    public int hashCode() {

        int result = super.hashCode();
        result = 31 * result + kcalBurned.hashCode();
        result = 31 * result + (activityName != null ? activityName.hashCode() : 0);
        return result;
    }
}
