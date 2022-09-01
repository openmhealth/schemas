package org.openmhealth.schema.domain.ieee

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id

/**
 * A time frame, which is either a point in time or a time interval.
 *
 * @author Emerson Farrugia
 * @version 1.0
 * @see [time-frame][https://w3id.org/ieee/ieee-1752-schema/time-frame.json]
 */
@JsonTypeInfo(use = Id.DEDUCTION)
sealed class TimeFrame

data class DateTimeTimeFrame(
    val dateTime: DateTime
) : TimeFrame() {
    companion object {

        fun parse(text: String) =
            DateTimeTimeFrame(DateTime.parse(text))
    }
}

data class TimeIntervalTimeFrame(
    val timeInterval: TimeInterval
) : TimeFrame()


