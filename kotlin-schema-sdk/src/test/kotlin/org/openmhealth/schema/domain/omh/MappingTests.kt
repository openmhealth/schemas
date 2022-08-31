package org.openmhealth.schema.domain.omh

import org.assertj.core.api.Assertions.assertThat
import org.openmhealth.schema.configuration.JacksonConfiguration.objectMapper
import com.fasterxml.jackson.module.kotlin.readValue

open class MappingTests {

    inline fun <reified T> assertThatMappingWorks(value: T, json: String) {
        assertThatSerializationWorks(actualValue = value, expectedJson = json)
        assertThatDeserializationWorks(actualJson = json, expectedValue = value)
    }

    fun <T> assertThatSerializationWorks(actualValue: T, expectedJson: String) {
        val actualValueAsString: String = objectMapper.writeValueAsString(actualValue)

        assertThat(actualValueAsString).isEqualTo(expectedJson)
    }

    inline fun <reified T> assertThatDeserializationWorks(actualJson: String, expectedValue: T) {
        val actualValue: T = objectMapper.readValue(actualJson)

        assertThat(actualValue).isEqualTo(expectedValue)
    }
}
