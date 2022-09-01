package org.openmhealth.schema.domain.ieee

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id
import org.openmhealth.schema.domain.IEEE_NAMESPACE
import org.openmhealth.schema.domain.SchemaId
import org.openmhealth.schema.domain.SchemaSupport

/**
 * A time interval. No commitments are made whether the start or end time point itself is included in the
 * interval (i.e., whether the defined interval includes the boundary point(s) or not).
 *
 * @author Emerson Farrugia
 * @version 1.0
 * @see [time-interval][https://w3id.org/ieee/ieee-1752-schema/time-interval.json]
 */
@JsonTypeInfo(use = Id.DEDUCTION)
sealed class TimeInterval : SchemaSupport {
    override val schemaId: SchemaId
        get() = SchemaId(IEEE_NAMESPACE.value, "time-interval", "1.0")
}

data class StartEndTimeInterval(
    val startDateTime: DateTime,
    val endDateTime: DateTime,
) : TimeInterval() {
    init {
        require(!startDateTime.isAfter(endDateTime)) { "The specified start and end date times are reversed." }
    }
}

data class StartDurationTimeInterval(
    val startDateTime: DateTime,
    val duration: DurationUnitValue
) : TimeInterval()

data class EndDurationTimeInterval(
    val endDateTime: DateTime,
    val duration: DurationUnitValue
) : TimeInterval()
