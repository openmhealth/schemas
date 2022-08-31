package org.openmhealth.schema.domain.ieee

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id.DEDUCTION
import com.fasterxml.jackson.databind.jsontype.impl.TypeIdResolverBase
import org.openmhealth.schema.domain.IEEE_NAMESPACE
import org.openmhealth.schema.domain.SchemaId
import org.openmhealth.schema.domain.SchemaSupport
import java.time.OffsetDateTime

/**
 * A time interval.
 *
 * @author Emerson Farrugia
 * @version 1.0
 */
@JsonTypeInfo(use = DEDUCTION)
sealed class TimeInterval: SchemaSupport {
    override val schemaId: SchemaId
        get() = SchemaId(IEEE_NAMESPACE.value, "time-interval", "1.0")
}

data class StartEndTimeInterval(
    val startDateTime: OffsetDateTime,
    val endDateTime: OffsetDateTime,
) : TimeInterval() {
    init {
        require(!startDateTime.isAfter(endDateTime)) { "The specified start and end date times are reversed." }
    }
}

data class StartDurationTimeInterval(
    val startDateTime: OffsetDateTime,
    val duration: DurationUnitValue
) : TimeInterval()

data class EndDurationTimeInterval(
    val endDateTime: OffsetDateTime,
    val duration: DurationUnitValue
) : TimeInterval()
