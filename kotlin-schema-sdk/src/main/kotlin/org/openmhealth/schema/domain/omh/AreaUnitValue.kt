package org.openmhealth.schema.domain.omh

import org.openmhealth.schema.domain.OMH_NAMESPACE
import org.openmhealth.schema.domain.SchemaId
import org.openmhealth.schema.domain.SchemaSupport
import java.math.BigDecimal

/**
 * An area.
 *
 * @author Emerson Farrugia
 * @version 1.0
 * @see [area-unit-value](http://www.openmhealth.org/documentation/#/schema-docs/schema-library/schemas/omh_area-unit-value)
 */
data class AreaUnitValue(
    val unit: AreaUnit,
    val value: BigDecimal
) : SchemaSupport {

    constructor(unit: AreaUnit, value: Double) : this(unit, BigDecimal.valueOf(value))

    override val schemaId: SchemaId
        get() = SchemaId(OMH_NAMESPACE.value, "area-unit-value", "1.0")
}
