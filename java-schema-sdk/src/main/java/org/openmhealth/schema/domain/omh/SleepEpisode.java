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
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.openmhealth.schema.serializer.SerializationConstructor;

import java.util.Objects;


/**
 * An episode of sleep.
 *
 * @author Emerson Farrugia
 * @version 1.0
 * @see <a href="http://www.openmhealth.org/documentation/#/schema-docs/schema-library/schemas/omh_sleep-episode">sleep-episode</a>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(SnakeCaseStrategy.class)
public class SleepEpisode extends Measure {

    public static final SchemaId SCHEMA_ID = new SchemaId(OMH_NAMESPACE, "sleep-episode", "1.0");

    private DurationUnitValue latencyToSleepOnset;
    private DurationUnitValue latencyToArising;
    private DurationUnitValue totalSleepTime;
    private Integer numberOfAwakenings;
    private Boolean mainSleep;
    private TypedUnitValue<PercentUnit> sleepMaintenanceEfficiencyPercentage;


    @SerializationConstructor
    protected SleepEpisode() {
    }

    public static class Builder extends TimeIntervalEffectiveTimeFrameBuilder<SleepEpisode, Builder> {

        private DurationUnitValue latencyToSleepOnset;
        private DurationUnitValue latencyToArising;
        private DurationUnitValue totalSleepTime;
        private Integer numberOfAwakenings;
        private Boolean mainSleep;
        private TypedUnitValue<PercentUnit> sleepMaintenanceEfficiencyPercentage;

        public Builder(TimeFrame effectiveTimeFrame) {
            super(effectiveTimeFrame);
        }

        public Builder(TimeInterval effectiveTimeInterval) {
            super(effectiveTimeInterval);
        }

        public Builder setLatencyToSleepOnset(DurationUnitValue latencyToSleepOnset) {
            this.latencyToSleepOnset = latencyToSleepOnset;
            return this;
        }

        public Builder setLatencyToArising(DurationUnitValue latencyToArising) {
            this.latencyToArising = latencyToArising;
            return this;
        }

        public Builder setTotalSleepTime(DurationUnitValue totalSleepTime) {
            this.totalSleepTime = totalSleepTime;
            return this;
        }

        public Builder setNumberOfAwakenings(Integer numberOfAwakenings) {
            this.numberOfAwakenings = numberOfAwakenings;
            return this;
        }

        public Builder setMainSleep(Boolean mainSleep) {
            this.mainSleep = mainSleep;
            return this;
        }

        public Builder setSleepMaintenanceEfficiencyPercentage(
                TypedUnitValue<PercentUnit> sleepMaintenanceEfficiencyPercentage) {
            this.sleepMaintenanceEfficiencyPercentage = sleepMaintenanceEfficiencyPercentage;
            return this;
        }

        /**
         * We are working on supporting descriptive statistics for episodes, since the semantics of the effective time
         * frame become unclear without additional temporal information.
         */
        @Override
        public Builder setDescriptiveStatistic(DescriptiveStatistic descriptiveStatistic) {
            throw new UnsupportedOperationException();
        }

        @Override
        public SleepEpisode build() {
            return new SleepEpisode(this);
        }
    }

    private SleepEpisode(Builder builder) {
        super(builder);

        this.latencyToSleepOnset = builder.latencyToSleepOnset;
        this.latencyToArising = builder.latencyToArising;
        this.totalSleepTime = builder.totalSleepTime;
        this.numberOfAwakenings = builder.numberOfAwakenings;
        this.mainSleep = builder.mainSleep;
        this.sleepMaintenanceEfficiencyPercentage = builder.sleepMaintenanceEfficiencyPercentage;
    }

    public DurationUnitValue getLatencyToSleepOnset() {
        return latencyToSleepOnset;
    }

    public DurationUnitValue getLatencyToArising() {
        return latencyToArising;
    }

    public DurationUnitValue getTotalSleepTime() {
        return totalSleepTime;
    }

    public Integer getNumberOfAwakenings() {
        return numberOfAwakenings;
    }

    @JsonProperty("is_main_sleep")
    public Boolean getMainSleep() {
        return mainSleep;
    }

    public TypedUnitValue<PercentUnit> getSleepMaintenanceEfficiencyPercentage() {
        return sleepMaintenanceEfficiencyPercentage;
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

        SleepEpisode that = (SleepEpisode) o;

        return Objects.equals(latencyToSleepOnset, that.latencyToSleepOnset) &&
                Objects.equals(latencyToArising, that.latencyToArising) &&
                Objects.equals(totalSleepTime, that.totalSleepTime) &&
                Objects.equals(numberOfAwakenings, that.numberOfAwakenings) &&
                Objects.equals(mainSleep, that.mainSleep) &&
                Objects.equals(sleepMaintenanceEfficiencyPercentage, that.sleepMaintenanceEfficiencyPercentage);
    }

    @Override
    public int hashCode() {
        return Objects
                .hash(super.hashCode(), latencyToSleepOnset, latencyToArising, totalSleepTime, numberOfAwakenings,
                        mainSleep, sleepMaintenanceEfficiencyPercentage);
    }
}
