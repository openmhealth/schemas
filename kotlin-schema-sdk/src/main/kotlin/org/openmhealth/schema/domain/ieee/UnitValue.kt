package org.openmhealth.schema.domain.ieee

import org.openmhealth.schema.domain.IEEE_NAMESPACE
import org.openmhealth.schema.domain.SchemaId
import org.openmhealth.schema.domain.SchemaSupport
import java.math.BigDecimal


/**
 * A numerical value with a unit of measure.
 *
 * @author Emerson Farrugia
 * @version 1.0
 * @see [unit-value][https://w3id.org/ieee/ieee-1752-schema/unit-value.json]
 */
data class UnitValue(
    val unit: String,
    val value: BigDecimal,
) : SchemaSupport {

    constructor(unit: String, value: Double) : this(unit, BigDecimal.valueOf(value))
    constructor(unit: String, value: Long) : this(unit, BigDecimal.valueOf(value))

    init {
        require(unit.isNotEmpty()) { "An empty unit has been specified." }
    }

    override val schemaId: SchemaId
        get() = SchemaId(IEEE_NAMESPACE.value, "unit-value", "1.0")
}
