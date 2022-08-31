package org.openmhealth.schema.domain.omh

import com.fasterxml.jackson.databind.JsonNode
import org.junit.jupiter.params.ParameterizedTest
import org.openmhealth.schema.domain.ieee.*
import org.openmhealth.schema.domain.ieee.DurationUnit.DAY
import org.openmhealth.schema.support.DataFileSource
import java.time.OffsetDateTime


class TimeIntervalTests : MappingTests() {

    @ParameterizedTest
    @DataFileSource(schemaId = "ieee:time-interval:1.0", filename = "start-time-end-time.json")
    fun `start-time-end-time`(json: JsonNode) {

        val value = StartEndTimeInterval(
            startDateTime = DateTime.parse("2013-02-05T07:25:00Z"),
            endDateTime = DateTime.parse("2013-02-05T07:35:00Z")
        )

        assertThatMappingWorks(value, json)
    }

    @ParameterizedTest
    @DataFileSource(schemaId = "ieee:time-interval:1.0", filename = "start-time-duration.json")
    fun `start-time-duration`(json: JsonNode) {

        val value = StartDurationTimeInterval(
            startDateTime = DateTime.parse("2013-02-05T07:25:00Z"),
            duration = DurationUnitValue(DAY, 10.2)
        )

        assertThatMappingWorks(value, json)
    }

    @ParameterizedTest
    @DataFileSource(schemaId = "ieee:time-interval:1.0", filename = "end-time-duration.json")
    fun `end-time-duration`(json: JsonNode) {

        val value = EndDurationTimeInterval(
            endDateTime = DateTime.parse("2013-02-05T07:35:00Z"),
            duration = DurationUnitValue(DAY, 10.9)
        )

        assertThatMappingWorks(value, json)
    }
}
