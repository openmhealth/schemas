package org.openmhealth.schema.domain.omh

import org.openmhealth.schema.domain.Unit


/**
 * A unit of area, or a measure of a surface.
 *
 * @author Emerson Farrugia
 * @version 1.0
 * @see [area-unit-value](http://www.openmhealth.org/documentation/#/schema-docs/schema-library/schemas/omh_area-unit-value)
 */
enum class AreaUnit(
    override val schemaValue: String
) : Unit {
    SQUARE_MILLIMETER("mm^2"),
    SQUARE_CENTIMETER("cm^2"),
    SQUARE_METER("m^2"),
    SQUARE_KILOMETER("km^2"),
    SQUARE_INCH("in^2"),
    SQUARE_FOOT("ft^2"),
    SQUARE_YARD("yd^2"),
    SQUARE_MILE("mi^2");
}
