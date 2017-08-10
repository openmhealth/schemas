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

import java.math.BigDecimal;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static org.openmhealth.schema.domain.omh.DurationUnit.MINUTE;


/**
 * A measurement of minutes of moderate-intensity activity performed.
 *
 * @author Emerson Farrugia
 * @version 1.0
 * @see <a href="http://www.openmhealth.org/documentation/#/schema-docs/schema-library/schemas/omh_minutes-moderate-activity">minutes-moderate-activity</a>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(SnakeCaseStrategy.class)
public class MinutesModerateActivity extends Measure {

    public static final SchemaId SCHEMA_ID = new SchemaId(OMH_NAMESPACE, "minutes-moderate-activity", "1.0");

    private DurationUnitValue minutesModerateActivity;


    @SerializationConstructor
    protected MinutesModerateActivity() {
    }

    public static class Builder extends Measure.Builder<MinutesModerateActivity, Builder> {

        private DurationUnitValue minutesModerateActivity;


        public Builder(DurationUnitValue minutesModerateActivity) {

            checkNotNull(minutesModerateActivity, "An activity duration hasn't been specified.");
            checkArgument(minutesModerateActivity.getTypedUnit() == MINUTE,
                    "The unit '{}' is not a valid activity duration unit.", minutesModerateActivity.getUnit());

            this.minutesModerateActivity = minutesModerateActivity;
        }

        /**
         * @param minutesModerateActivityValue the number of minutes of moderate activity
         */
        public Builder(BigDecimal minutesModerateActivityValue) {

            checkNotNull(minutesModerateActivityValue, "An activity duration hasn't been specified.");
            this.minutesModerateActivity = new DurationUnitValue(MINUTE, minutesModerateActivityValue);
        }

        /**
         * @param minutesModerateActivityValue the number of minutes of moderate activity
         */
        public Builder(double minutesModerateActivityValue) {
            this(BigDecimal.valueOf(minutesModerateActivityValue));
        }

        /**
         * @param minutesModerateActivityValue the number of minutes of moderate activity
         */
        public Builder(long minutesModerateActivityValue) {
            this(BigDecimal.valueOf(minutesModerateActivityValue));
        }

        @Override
        public MinutesModerateActivity build() {
            return new MinutesModerateActivity(this);
        }
    }

    private MinutesModerateActivity(Builder builder) {
        super(builder);

        this.minutesModerateActivity = builder.minutesModerateActivity;
    }

    public DurationUnitValue getMinutesModerateActivity() {
        return minutesModerateActivity;
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

        MinutesModerateActivity that = (MinutesModerateActivity) object;

        return minutesModerateActivity.equals(that.minutesModerateActivity);
    }

    @Override
    public int hashCode() {

        int result = super.hashCode();
        result = 31 * result + minutesModerateActivity.hashCode();
        return result;
    }
}
