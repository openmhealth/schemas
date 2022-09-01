package org.openmhealth.schema.domain.omh

import com.fasterxml.jackson.databind.JsonNode
import org.junit.jupiter.params.ParameterizedTest
import org.openmhealth.schema.domain.ieee.*
import org.openmhealth.schema.domain.ieee.DurationUnit.DAY
import org.openmhealth.schema.support.DataFileSource
import java.time.OffsetDateTime


class TimeFrameTests : MappingTests() {

    @ParameterizedTest
    @DataFileSource(schemaId = "ieee:time-frame:1.0", filename = "date-time.json")
    fun `date-time`(json: JsonNode) {

        val value = DateTimeTimeFrame(
            dateTime = DateTime.parse("2013-02-05T07:25:00.123Z"),
        )

        assertThatMappingWorks(value, json)
    }

    @ParameterizedTest
    @DataFileSource(schemaId = "ieee:time-frame:1.0", filename = "time-interval.json")
    fun `time-interval`(json: JsonNode) {

        val value = TimeIntervalTimeFrame(
            timeInterval = EndDurationTimeInterval(
                endDateTime = OffsetDateTime.parse("2013-02-05T07:35:00Z"),
                duration = DurationUnitValue(DAY, 7.6)
            )
        )

        assertThatMappingWorks(value, json)
    }
}
