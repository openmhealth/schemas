package org.openmhealth.schema.domain.omh

import com.fasterxml.jackson.databind.JsonNode
import org.junit.jupiter.params.ParameterizedTest
import org.openmhealth.schema.domain.omh.AreaUnit.SQUARE_INCH
import org.openmhealth.schema.domain.omh.AreaUnit.SQUARE_METER
import org.openmhealth.schema.support.DataFileSource


class AreaUnitValueTests : MappingTests() {

    @ParameterizedTest
    @DataFileSource(schemaId = "omh:area-unit-value:1.0", filename = "positive-value.json")
    fun `positive-value`(json: JsonNode) {
        val value = AreaUnitValue(SQUARE_METER, 7.6)

        assertThatMappingWorks(value, json)
    }

    @ParameterizedTest
    @DataFileSource(schemaId = "omh:area-unit-value:1.0", filename = "zero-value.json")
    fun `zero-value`(json: JsonNode) {
        val value = AreaUnitValue(SQUARE_INCH, 0.0)

        assertThatMappingWorks(value, json)
    }
}
