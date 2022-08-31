package org.openmhealth.schema.domain.omh

import org.openmhealth.schema.domain.OMH_NAMESPACE
import org.openmhealth.schema.domain.SchemaEnumValue
import org.openmhealth.schema.domain.SchemaId
import org.openmhealth.schema.domain.SchemaSupport

/**
 * The temporal relationship of a clinical measure or assessment to physical activity.
 *
 * @author Emerson Farrugia
 * @version 1.0
 * @see  [temporal
 * -relationship-to-physical-activity](https://www.openmhealth.org/documentation/#/schema-docs/schema-library/schemas/omh_temporal-relationship-to-physical-activity)
 */
enum class TemporalRelationshipToPhysicalActivity : SchemaEnumValue, SchemaSupport {

    AT_REST,
    ACTIVE,
    BEFORE_EXERCISE,
    AFTER_EXERCISE,
    DURING_EXERCISE;

    override val schemaValue: String
        get() = name.lowercase().replace("_", " ")

    override val schemaId: SchemaId
        get() = SchemaId(OMH_NAMESPACE.value, "temporal-relationship-to-physical-activity", "1.0")
}


