package org.openmhealth.schema.domain.omh

import com.fasterxml.jackson.databind.JsonNode
import org.junit.jupiter.params.ParameterizedTest
import org.openmhealth.schema.domain.ieee.*
import org.openmhealth.schema.domain.ieee.DescriptiveStatistic.MINIMUM
import org.openmhealth.schema.domain.omh.HeartRateUnit.BEATS_PER_MINUTE
import org.openmhealth.schema.domain.omh.TemporalRelationshipToSleep.ON_WAKING
import org.openmhealth.schema.support.DataFileSource


class HeartRateTests : MappingTests() {

    @ParameterizedTest
    @DataFileSource(schemaId = "omh:heart-rate:2.0", filename = "with-temporal-relationship-to-sleep.json")
    fun `with-temporal-relationship-to-sleep`(json: JsonNode) {

        val value = HeartRate(
            heartRate = BEATS_PER_MINUTE.toUnitValue(67.5),
            effectiveTimeFrame = DateTimeTimeFrame.parse("2020-02-05T07:25:00-08:00"),
            temporalRelationshipToSleep = ON_WAKING
        )

        assertThatMappingWorks(value, json)
    }

    @ParameterizedTest
    @DataFileSource(schemaId = "omh:heart-rate:2.0", filename = "with-descriptive-statistic.json")
    fun `with-descriptive-statistic`(json: JsonNode) {

        val value = HeartRate(
            heartRate = BEATS_PER_MINUTE.toUnitValue(50.0),
            effectiveTimeFrame = TimeIntervalTimeFrame(
                StartEndTimeInterval(
                    startDateTime = DateTime.parse("2020-02-05T06:00:00+01:00"),
                    endDateTime = DateTime.parse("2020-02-06T06:00:00+01:00")
                )
            ),
            descriptiveStatistic = MINIMUM
        )

        assertThatMappingWorks(value, json)
    }
}
