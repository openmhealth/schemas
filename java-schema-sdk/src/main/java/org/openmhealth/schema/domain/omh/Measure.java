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

import org.openmhealth.schema.serializer.SerializationConstructor;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;


/**
 * A base class for measures that provides an extensible builder for common properties.
 *
 * @author Emerson Farrugia
 */
public abstract class Measure implements SchemaSupport, AdditionalPropertySupport {

    // these fields are not final to prevent errors caused by the presence of the default serialization constructor
    private TimeFrame effectiveTimeFrame;
    private DescriptiveStatistic descriptiveStatistic;
    private String userNotes;
    private Map<String, Object> additionalProperties = new HashMap<>();


    @SuppressWarnings("unchecked")
    public static abstract class Builder<M extends Measure, B extends Builder<M, B>> {

        private TimeFrame effectiveTimeFrame;
        private DescriptiveStatistic descriptiveStatistic;
        private String userNotes;

        public B setEffectiveTimeFrame(TimeFrame timeFrame) {
            this.effectiveTimeFrame = timeFrame;
            return (B) this;
        }

        public B setEffectiveTimeFrame(TimeInterval timeInterval) {
            this.effectiveTimeFrame = new TimeFrame(timeInterval);
            return (B) this;
        }

        public B setEffectiveTimeFrame(OffsetDateTime dateTime) {
            this.effectiveTimeFrame = new TimeFrame(dateTime);
            return (B) this;
        }

        public B setDescriptiveStatistic(DescriptiveStatistic descriptiveStatistic) {
            this.descriptiveStatistic = descriptiveStatistic;
            return (B) this;
        }

        public B setUserNotes(String userNotes) {
            this.userNotes = userNotes;
            return (B) this;
        }

        public abstract M build();
    }


    /**
     * A builder that requires an effective time frame.
     */
    @SuppressWarnings("unchecked")
    public static abstract class EffectiveTimeFrameBuilder<M extends Measure, B extends EffectiveTimeFrameBuilder<M, B>> {

        private TimeFrame effectiveTimeFrame;
        private DescriptiveStatistic descriptiveStatistic;
        private String userNotes;

        public EffectiveTimeFrameBuilder(TimeFrame timeFrame) {

            checkNotNull(timeFrame, "A time frame hasn't been specified.");
            this.effectiveTimeFrame = timeFrame;
        }

        public EffectiveTimeFrameBuilder(TimeInterval timeInterval) {

            checkNotNull(timeInterval, "A time interval hasn't been specified.");
            this.effectiveTimeFrame = new TimeFrame(timeInterval);
        }

        public EffectiveTimeFrameBuilder(OffsetDateTime dateTime) {

            checkNotNull(dateTime, "A date time hasn't been specified.");
            this.effectiveTimeFrame = new TimeFrame(dateTime);
        }

        public B setDescriptiveStatistic(DescriptiveStatistic descriptiveStatistic) {
            this.descriptiveStatistic = descriptiveStatistic;
            return (B) this;
        }

        public B setUserNotes(String userNotes) {
            this.userNotes = userNotes;
            return (B) this;
        }

        public abstract M build();
    }


    /**
     * A builder that requires a time interval effective time frame.
     */
    @SuppressWarnings("unchecked")
    public static abstract class DateTimeEffectiveTimeFrameBuilder<M extends Measure, B extends DateTimeEffectiveTimeFrameBuilder<M, B>> {

        private TimeFrame effectiveTimeFrame;
        private DescriptiveStatistic descriptiveStatistic;
        private String userNotes;

        public DateTimeEffectiveTimeFrameBuilder(TimeFrame timeFrame) {

            checkNotNull(timeFrame, "A time frame hasn't been specified.");
            checkArgument(timeFrame.getDateTime() != null,
                    "A time frame without a date time has been specified.");

            this.effectiveTimeFrame = timeFrame;
        }

        public DateTimeEffectiveTimeFrameBuilder(OffsetDateTime dateTime) {

            checkNotNull(dateTime, "A date time hasn't been specified.");
            this.effectiveTimeFrame = new TimeFrame(dateTime);
        }

        public B setDescriptiveStatistic(DescriptiveStatistic descriptiveStatistic) {
            this.descriptiveStatistic = descriptiveStatistic;
            return (B) this;
        }

        public B setUserNotes(String userNotes) {
            this.userNotes = userNotes;
            return (B) this;
        }

        public abstract M build();
    }


    /**
     * A builder that requires a time interval effective time frame.
     */
    @SuppressWarnings("unchecked")
    public static abstract class TimeIntervalEffectiveTimeFrameBuilder<M extends Measure, B extends TimeIntervalEffectiveTimeFrameBuilder<M, B>> {

        private TimeFrame effectiveTimeFrame;
        private DescriptiveStatistic descriptiveStatistic;
        private String userNotes;

        public TimeIntervalEffectiveTimeFrameBuilder(TimeFrame timeFrame) {

            checkNotNull(timeFrame, "A time frame hasn't been specified.");
            checkArgument(timeFrame.getTimeInterval() != null,
                    "A time frame without a time interval has been specified.");

            this.effectiveTimeFrame = timeFrame;
        }

        public TimeIntervalEffectiveTimeFrameBuilder(TimeInterval timeInterval) {

            checkNotNull(timeInterval, "A time interval hasn't been specified.");
            this.effectiveTimeFrame = new TimeFrame(timeInterval);
        }

        public B setDescriptiveStatistic(DescriptiveStatistic descriptiveStatistic) {
            this.descriptiveStatistic = descriptiveStatistic;
            return (B) this;
        }

        public B setUserNotes(String userNotes) {
            this.userNotes = userNotes;
            return (B) this;
        }

        public abstract M build();
    }

    @SerializationConstructor
    protected Measure() {

    }

    protected Measure(Builder builder) {

        this.effectiveTimeFrame = builder.effectiveTimeFrame;
        this.descriptiveStatistic = builder.descriptiveStatistic;
        this.userNotes = builder.userNotes;
    }

    protected Measure(EffectiveTimeFrameBuilder builder) {

        this.effectiveTimeFrame = builder.effectiveTimeFrame;
        this.descriptiveStatistic = builder.descriptiveStatistic;
        this.userNotes = builder.userNotes;
    }

    protected Measure(TimeIntervalEffectiveTimeFrameBuilder builder) {

        this.effectiveTimeFrame = builder.effectiveTimeFrame;
        this.descriptiveStatistic = builder.descriptiveStatistic;
        this.userNotes = builder.userNotes;
    }

    protected Measure(DateTimeEffectiveTimeFrameBuilder builder) {

        this.effectiveTimeFrame = builder.effectiveTimeFrame;
        this.descriptiveStatistic = builder.descriptiveStatistic;
        this.userNotes = builder.userNotes;
    }

    public TimeFrame getEffectiveTimeFrame() {
        return effectiveTimeFrame;
    }

    public DescriptiveStatistic getDescriptiveStatistic() {
        return descriptiveStatistic;
    }

    public String getUserNotes() {
        return userNotes;
    }

    @Override
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Measure measure = (Measure) o;

        return Objects.equals(effectiveTimeFrame, measure.effectiveTimeFrame) &&
                descriptiveStatistic == measure.descriptiveStatistic &&
                Objects.equals(userNotes, measure.userNotes);
    }

    @Override
    public int hashCode() {

        return Objects.hash(effectiveTimeFrame, descriptiveStatistic, userNotes);
    }
}
