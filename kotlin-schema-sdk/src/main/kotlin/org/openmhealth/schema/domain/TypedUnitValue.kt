package org.openmhealth.schema.domain

import java.math.BigDecimal

data class TypedUnitValue<T : Unit>(
    val unit: T,
    val value: BigDecimal
) {
    constructor(unit: T, value: Double) : this(unit, BigDecimal.valueOf(value))
    constructor(unit: T, value: Long) : this(unit, BigDecimal.valueOf(value))
}
