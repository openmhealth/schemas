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

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;


/**
 * A time interval.
 *
 * @author Emerson Farrugia
 * @version 1.0
 * @see <a href="http://www.openmhealth.org/documentation/#/schema-docs/schema-library/schemas/omh_time-interval">time-interval</a>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(SnakeCaseStrategy.class)
public class TimeInterval implements SchemaSupport, AdditionalPropertySupport {

    public static final SchemaId SCHEMA_ID = new SchemaId(OMH_NAMESPACE, "time-interval", "1.0");

    private OffsetDateTime startDateTime;
    private OffsetDateTime endDateTime;
    private DurationUnitValue duration;
    private LocalDate date;
    private PartOfDay partOfDay;
    private Map<String, Object> additionalProperties = new HashMap<>();


    @SerializationConstructor
    protected TimeInterval() {

    }

    public static TimeInterval ofStartDateTimeAndEndDateTime(OffsetDateTime startDateTime, OffsetDateTime endDateTime) {

        checkNotNull(startDateTime, "A start date time hasn't been specified.");
        checkNotNull(endDateTime, "An end date time hasn't been specified.");
        checkArgument(!endDateTime.isBefore(startDateTime), "The specified start and end date times are reversed.");

        TimeInterval timeInterval = new TimeInterval();

        timeInterval.startDateTime = startDateTime;
        timeInterval.endDateTime = endDateTime;

        return timeInterval;
    }

    public static TimeInterval ofStartDateTimeAndDuration(OffsetDateTime startDateTime, DurationUnitValue duration) {

        checkNotNull(startDateTime, "A start date time hasn't been specified.");
        checkNotNull(duration, "A duration hasn't been specified.");

        TimeInterval timeInterval = new TimeInterval();

        timeInterval.startDateTime = startDateTime;
        timeInterval.duration = duration;

        return timeInterval;
    }

    public static TimeInterval ofEndDateTimeAndDuration(OffsetDateTime endDateTime, DurationUnitValue duration) {

        checkNotNull(endDateTime, "An end date time hasn't been specified.");
        checkNotNull(duration, "A duration hasn't been specified.");

        TimeInterval timeInterval = new TimeInterval();

        timeInterval.endDateTime = endDateTime;
        timeInterval.duration = duration;

        return timeInterval;
    }

    public static TimeInterval ofDateAndPartOfDay(LocalDate date, PartOfDay partOfDay) {

        checkNotNull(date, "A date hasn't been specified.");
        checkNotNull(partOfDay, "A part of day hasn't been specified.");

        TimeInterval timeInterval = new TimeInterval();

        timeInterval.date = date;
        timeInterval.partOfDay = partOfDay;

        return timeInterval;
    }

    public OffsetDateTime getStartDateTime() {
        return startDateTime;
    }

    public OffsetDateTime getEndDateTime() {
        return endDateTime;
    }

    public DurationUnitValue getDuration() {
        return duration;
    }

    public LocalDate getDate() {
        return date;
    }

    public PartOfDay getPartOfDay() {
        return partOfDay;
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

        TimeInterval that = (TimeInterval) object;

        if (startDateTime != null ? !startDateTime.equals(that.startDateTime) : that.startDateTime != null) {
            return false;
        }
        if (endDateTime != null ? !endDateTime.equals(that.endDateTime) : that.endDateTime != null) {
            return false;
        }
        if (duration != null ? !duration.equals(that.duration) : that.duration != null) {
            return false;
        }
        if (date != null ? !date.equals(that.date) : that.date != null) {
            return false;
        }
        return partOfDay == that.partOfDay;

    }

    @Override
    public int hashCode() {

        int result = startDateTime != null ? startDateTime.hashCode() : 0;
        result = 31 * result + (endDateTime != null ? endDateTime.hashCode() : 0);
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (partOfDay != null ? partOfDay.hashCode() : 0);
        return result;
    }
}
