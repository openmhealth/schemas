package org.openmhealth.schema.domain.omh

import org.openmhealth.schema.domain.OMH_NAMESPACE
import org.openmhealth.schema.domain.SchemaId
import org.openmhealth.schema.domain.SchemaSupport
import org.openmhealth.schema.domain.ieee.DescriptiveStatistic
import org.openmhealth.schema.domain.ieee.TimeFrame

/**
 * A person's heart rate.
 *
 * @author Emerson Farrugia
 * @version 1.0
 * @see [heart-rate](http://www.openmhealth.org/documentation/#/schema-docs/schema-library/schemas/omh_heart-rate)
 */
data class HeartRate(
    val heartRate: HeartRateUnitValue,
    val effectiveTimeFrame: TimeFrame,
    val temporalRelationshipToPhysicalActivity: TemporalRelationshipToPhysicalActivity? = null,
    val temporalRelationshipToSleep: TemporalRelationshipToSleep? = null,
    val descriptiveStatistic: DescriptiveStatistic? = null
) : SchemaSupport {

    override val schemaId: SchemaId
        get() = SchemaId(OMH_NAMESPACE.value, "heart-rate", "2.0")
}
