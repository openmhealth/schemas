package org.openmhealth.schema.domain.ieee

/**
 * A time frame, which is either a point in time or a time interval.
 *
 * @author Emerson Farrugia
 * @version 1.0
 * @see [time-frame][https://w3id.org/ieee/ieee-1752-schema/time-frame.json]
 */
sealed class TimeFrame

data class DateTimeTimeFrame(
    val dateTime: DateTime
): TimeFrame()

data class TimeIntervalTimeFrame(
    val timeInterval: TimeInterval
): TimeFrame()


