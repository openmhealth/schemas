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

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * A time frame, which is either a point in time or a time interval.
 *
 * @author Emerson Farrugia
 * @version 1.0
 * @see <a href="http://www.openmhealth.org/documentation/#/schema-docs/schema-library/schemas/omh_time-frame">time-frame</a>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(SnakeCaseStrategy.class)
public class TimeFrame implements SchemaSupport, AdditionalPropertySupport {

    public static final SchemaId SCHEMA_ID = new SchemaId(OMH_NAMESPACE, "time-frame", "1.0");

    private TimeInterval timeInterval;
    private OffsetDateTime dateTime;
    private Map<String, Object> additionalProperties = new HashMap<>();


    @SerializationConstructor
    protected TimeFrame() {

    }

    public TimeFrame(TimeInterval timeInterval) {

        checkNotNull(timeInterval, "A time interval hasn't been specified.");

        this.timeInterval = timeInterval;
    }

    public TimeFrame(OffsetDateTime dateTime) {

        checkNotNull(dateTime, "A point in time hasn't been specified.");

        this.dateTime = dateTime;
    }

    public TimeInterval getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(TimeInterval timeInterval) {
        this.timeInterval = timeInterval;
    }

    public OffsetDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(OffsetDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public SchemaId getSchemaId() {
        return SCHEMA_ID;
    }

    @Override
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
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

        TimeFrame timeFrame = (TimeFrame) object;

        if (timeInterval != null ? !timeInterval.equals(timeFrame.timeInterval) : timeFrame.timeInterval != null) {
            return false;
        }
        return !(dateTime != null ? !dateTime.equals(timeFrame.dateTime) : timeFrame.dateTime != null);

    }

    @Override
    public int hashCode() {

        int result = timeInterval != null ? timeInterval.hashCode() : 0;
        result = 31 * result + (dateTime != null ? dateTime.hashCode() : 0);
        return result;
    }
}
