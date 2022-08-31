package org.openmhealth.schema.domain.ieee

import org.openmhealth.schema.domain.TypedUnitValue
import org.openmhealth.schema.domain.Unit

/**
 * A unit of duration or length of time.
 *
 * @author Emerson Farrugia
 * @version 1.0
 * @see [duration-unit][https://w3id.org/ieee/ieee-1752-schema/duration-unit-value.json]
 */
enum class DurationUnit(
    override val schemaValue: String
) : Unit {
    PICOSECOND("ps"),
    NANOSECOND("ns"),
    MICROSECOND("us"),
    MILLISECOND("ms"),
    SECOND("sec"),
    MINUTE("min"),
    HOUR("h"),
    DAY("d"),
    WEEK("wk"),
    MONTH("Mo"),
    YEAR("yr");
}

typealias DurationUnitValue = TypedUnitValue<DurationUnit>
