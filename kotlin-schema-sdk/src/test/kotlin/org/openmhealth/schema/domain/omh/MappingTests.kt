package org.openmhealth.schema.domain.omh

import com.fasterxml.jackson.databind.JsonNode
import org.assertj.core.api.Assertions.assertThat
import org.openmhealth.schema.configuration.JacksonConfiguration.objectMapper
import com.fasterxml.jackson.module.kotlin.readValue

open class MappingTests {

    inline fun <reified T> assertThatMappingWorks(value: T, json: JsonNode) {
        assertThatSerializationWorks(actualValue = value, expectedJson = json)
        assertThatDeserializationWorks(actualJson = json, expectedValue = value)
    }

    fun <T> assertThatSerializationWorks(actualValue: T, expectedJson: JsonNode) {
        val actualValueAsJsonNode: JsonNode = objectMapper.valueToTree(actualValue)

        assertThat(actualValueAsJsonNode).isEqualTo(expectedJson)
    }

    inline fun <reified T> assertThatDeserializationWorks(actualJson: JsonNode, expectedValue: T) {
        val actualValue: T = objectMapper.treeToValue(actualJson, T::class.java)

        assertThat(actualValue).isEqualTo(expectedValue)
    }
}
