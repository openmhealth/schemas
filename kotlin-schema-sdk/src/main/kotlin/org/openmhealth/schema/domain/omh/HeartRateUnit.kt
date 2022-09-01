package org.openmhealth.schema.domain.omh

import org.openmhealth.schema.domain.TypedUnitValue
import org.openmhealth.schema.domain.Unit


/**
 * A unit of heart rate.
 *
 * @author Emerson Farrugia
 * @version 1.0
 * @see [heart-rate](http://www.openmhealth.org/documentation/#/schema-docs/schema-library/schemas/omh_heart-rate)
 */
enum class HeartRateUnit(
    override val schemaValue: String
) : Unit {
    BEATS_PER_MINUTE("beats/min");
}

typealias HeartRateUnitValue = TypedUnitValue<HeartRateUnit>

fun HeartRateUnit.toUnitValue(value: Double) = HeartRateUnitValue(this, value)
