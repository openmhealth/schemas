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


/**
 * A base class for measures that provides an extensible builder for common properties.
 *
 * @author Emerson Farrugia
 */
public abstract class Measure implements AdditionalPropertySupport {

    // these fields are not final to prevent errors caused by the presence of the default serialization constructor
    private TimeFrame effectiveTimeFrame;
    private DescriptiveStatistic descriptiveStatistic;
    private String userNotes;
    private Map<String, Object> additionalProperties = new HashMap<>();


    @SuppressWarnings("unchecked")
    public static abstract class Builder<T extends Builder<?>> {

        private TimeFrame effectiveTimeFrame;
        private DescriptiveStatistic descriptiveStatistic;
        private String userNotes;

        public T setEffectiveTimeFrame(TimeFrame effectiveTimeFrame) {
            this.effectiveTimeFrame = effectiveTimeFrame;
            return (T) this;
        }

        public T setEffectiveTimeFrame(TimeInterval timeInterval) {
            this.effectiveTimeFrame = new TimeFrame(timeInterval);
            return (T) this;
        }

        public T setEffectiveTimeFrame(OffsetDateTime dateTime) {
            this.effectiveTimeFrame = new TimeFrame(dateTime);
            return (T) this;
        }

        public T setDescriptiveStatistic(DescriptiveStatistic descriptiveStatistic) {
            this.descriptiveStatistic = descriptiveStatistic;
            return (T) this;
        }

        public T setUserNotes(String userNotes) {
            this.userNotes = userNotes;
            return (T) this;
        }

        public abstract Measure build();
    }

    @SerializationConstructor
    protected Measure() {

    }

    protected Measure(Builder builder) {

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
    @SuppressWarnings("SimplifiableIfStatement")
    public boolean equals(Object object) {

        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        Measure measure = (Measure) object;

        if (effectiveTimeFrame != null ? !effectiveTimeFrame.equals(measure.effectiveTimeFrame)
                : measure.effectiveTimeFrame != null) {
            return false;
        }
        if (descriptiveStatistic != measure.descriptiveStatistic) {
            return false;
        }
        return !(userNotes != null ? !userNotes.equals(measure.userNotes) : measure.userNotes != null);

    }

    @Override
    public int hashCode() {

        int result = effectiveTimeFrame != null ? effectiveTimeFrame.hashCode() : 0;
        result = 31 * result + (descriptiveStatistic != null ? descriptiveStatistic.hashCode() : 0);
        result = 31 * result + (userNotes != null ? userNotes.hashCode() : 0);
        return result;
    }
}
