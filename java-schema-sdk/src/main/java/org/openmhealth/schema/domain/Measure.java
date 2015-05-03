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

package org.openmhealth.schema.domain;

import java.time.OffsetDateTime;


/**
 * A base class for measures that provides an extensible builder for common properties.
 *
 * @author Emerson Farrugia
 */
public abstract class Measure {

    private final TimeFrame effectiveTimeFrame;
    private final DescriptiveStatistic descriptiveStatistic;


    public static abstract class Builder<T extends Builder> {

        private TimeFrame effectiveTimeFrame;
        private DescriptiveStatistic descriptiveStatistic;

        public Builder setEffectiveTimeFrame(TimeFrame effectiveTimeFrame) {
            this.effectiveTimeFrame = effectiveTimeFrame;
            return this;
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

        public abstract Measure build();
    }

    protected Measure(Builder builder) {

        this.effectiveTimeFrame = builder.effectiveTimeFrame;
        this.descriptiveStatistic = builder.descriptiveStatistic;
    }

    public TimeFrame getEffectiveTimeFrame() {
        return effectiveTimeFrame;
    }

    public DescriptiveStatistic getDescriptiveStatistic() {
        return descriptiveStatistic;
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

        Measure measure = (Measure) object;

        if (effectiveTimeFrame != null
                ? !effectiveTimeFrame.equals(measure.effectiveTimeFrame)
                : measure.effectiveTimeFrame != null) {
            return false;
        }
        return descriptiveStatistic == measure.descriptiveStatistic;

    }

    @Override
    public int hashCode() {

        int result = effectiveTimeFrame != null ? effectiveTimeFrame.hashCode() : 0;
        result = 31 * result + (descriptiveStatistic != null ? descriptiveStatistic.hashCode() : 0);
        return result;
    }
}
