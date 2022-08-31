package org.openmhealth.schema.domain.ieee

import org.openmhealth.schema.domain.IEEE_NAMESPACE
import org.openmhealth.schema.domain.SchemaEnumValue
import org.openmhealth.schema.domain.SchemaId
import org.openmhealth.schema.domain.SchemaSupport

/**
 * The descriptive statistic of a set of measurements. A measurement value can be the result of combining
 * various measurements and calculating descriptive statistics like average, maximum, minimum, etc. Additional
 * descriptive statistics will be added as the need arises. A measurement value without a descriptive statistic
 * is interpreted as being the result of an individual measurement.
 *
 * @author Emerson Farrugia
 * @version 1.0
 * @see [descriptive-statistic][https://w3id.org/ieee/ieee-1752-schema/descriptive-statistic.json]
 */
enum class DescriptiveStatistic(
    private val customSchemaValue: String? = null
) : SchemaEnumValue, SchemaSupport {

    AVERAGE,
    COUNT,
    MAXIMUM,
    MEDIAN,
    MINIMUM,
    STANDARD_DEVIATION,
    SUM,
    VARIANCE,
    TWENTIETH_PERCENTILE("20th percentile"),
    EIGHTIETH_PERCENTILE("80th percentile"),
    LOWER_QUARTILE,
    UPPER_QUARTILE,
    QUARTILE_DEVIATION,
    FIRST_QUINTILE("1st quintile"),
    SECOND_QUINTILE("2nd quintile"),
    THIRD_QUINTILE("3rd quintile"),
    FOURTH_QUINTILE("4th quintile");

    override val schemaValue: String
        get() = customSchemaValue ?: name.lowercase().replace("_", " ")

    override val schemaId: SchemaId
        get() = SchemaId(IEEE_NAMESPACE.value, "descriptive-statistic", "1.0")
}
