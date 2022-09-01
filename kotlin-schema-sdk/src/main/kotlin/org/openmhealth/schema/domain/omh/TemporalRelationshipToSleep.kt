package org.openmhealth.schema.domain.omh

import org.openmhealth.schema.domain.OMH_NAMESPACE
import org.openmhealth.schema.domain.SchemaEnumValue
import org.openmhealth.schema.domain.SchemaId
import org.openmhealth.schema.domain.SchemaSupport

/**
 * The temporal relationship of a clinical measure or assessment to sleep.
 *
 * @author Emerson Farrugia
 * @version 1.0
 * @see  [temporal-relationship
 * -to-sleep](http://www.openmhealth.org/documentation/./schema-docs/schema-library/schemas/omh_temporal-relationship-to-sleep)
 */
enum class TemporalRelationshipToSleep : SchemaEnumValue, SchemaSupport {

    BEFORE_SLEEPING,
    DURING_SLEEP,
    ON_WAKING;

    override val schemaValue: String
        get() = name.lowercase().replace("_", " ")

    override val schemaId: SchemaId
        get() = SchemaId(OMH_NAMESPACE.value, "temporal-relationship-to-sleep", "1.0")
}
