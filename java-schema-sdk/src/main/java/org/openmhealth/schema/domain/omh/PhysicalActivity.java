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
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.openmhealth.schema.serializer.SerializationConstructor;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;


/**
 * A single episode of physical activity.
 *
 * @author Emerson Farrugia
 * @author Chris Schaefbauer
 * @version 1.2
 * @see <a href="http://www.openmhealth.org/documentation/#/schema-docs/schema-library/schemas/omh_physical-activity">physical-activity</a>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(SnakeCaseStrategy.class)
public class PhysicalActivity extends Measure {

    public static final SchemaId SCHEMA_ID = new SchemaId(OMH_NAMESPACE, "physical-activity", "1.2");


    /**
     * A self-reported intensity of the activity performed.
     */
    public enum SelfReportedIntensity implements SchemaEnumValue {

        LIGHT,
        MODERATE,
        VIGOROUS;

        private String schemaValue;
        private static final Map<String, SelfReportedIntensity> constantsBySchemaValue = new HashMap<>();

        static {
            for (SelfReportedIntensity constant : values()) {
                constantsBySchemaValue.put(constant.getSchemaValue(), constant);
            }
        }

        SelfReportedIntensity() {
            this.schemaValue = name().toLowerCase().replace('_', ' ');
        }

        @Override
        @JsonValue
        public String getSchemaValue() {
            return this.schemaValue;
        }

        @Nullable
        @JsonCreator
        public static SelfReportedIntensity findBySchemaValue(String schemaValue) {
            return constantsBySchemaValue.get(schemaValue);
        }
    }


    private String activityName;
    private LengthUnitValue distance;
    private SelfReportedIntensity reportedActivityIntensity;
    private KcalUnitValue caloriesBurned;

    @SerializationConstructor
    protected PhysicalActivity() {
    }

    public static class Builder extends Measure.Builder<PhysicalActivity, Builder> {

        private String activityName;
        private LengthUnitValue distance;
        private SelfReportedIntensity reportedActivityIntensity;
        private KcalUnitValue caloriesBurned;

        public Builder(String activityName) {

            checkNotNull(activityName, "An activity name hasn't been specified.");
            checkArgument(!activityName.isEmpty(), "An activity name hasn't been specified.");

            this.activityName = activityName;
        }

        public Builder setDistance(LengthUnitValue distance) {
            this.distance = distance;
            return this;
        }

        public Builder setReportedActivityIntensity(SelfReportedIntensity reportedActivityIntensity) {
            this.reportedActivityIntensity = reportedActivityIntensity;
            return this;
        }

        public Builder setCaloriesBurned(KcalUnitValue caloriesBurned) {
            this.caloriesBurned = caloriesBurned;
            return this;
        }

        @Override
        public PhysicalActivity build() {
            return new PhysicalActivity(this);
        }
    }

    private PhysicalActivity(Builder builder) {
        super(builder);

        this.activityName = builder.activityName;
        this.distance = builder.distance;
        this.reportedActivityIntensity = builder.reportedActivityIntensity;
        this.caloriesBurned = builder.caloriesBurned;
    }

    public String getActivityName() {
        return activityName;
    }

    public LengthUnitValue getDistance() {
        return distance;
    }

    public SelfReportedIntensity getReportedActivityIntensity() {
        return reportedActivityIntensity;
    }

    @JsonProperty("kcal_burned")
    public KcalUnitValue getCaloriesBurned() {
        return caloriesBurned;
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

        PhysicalActivity that = (PhysicalActivity) object;

        if (!activityName.equals(that.activityName)) {
            return false;
        }
        if (distance != null ? !distance.equals(that.distance) : that.distance != null) {
            return false;
        }
        if (caloriesBurned != null ? !caloriesBurned.equals(that.caloriesBurned) : that.caloriesBurned != null) {
            return false;
        }
        return reportedActivityIntensity == that.reportedActivityIntensity;
    }

    @Override
    public int hashCode() {

        int result = super.hashCode();
        result = 31 * result + activityName.hashCode();
        result = 31 * result + (distance != null ? distance.hashCode() : 0);
        result = 31 * result + (reportedActivityIntensity != null ? reportedActivityIntensity.hashCode() : 0);
        result = 31 * result + (caloriesBurned != null ? caloriesBurned.hashCode() : 0);
        return result;
    }
}
