package org.openmhealth.schema.configuration

import com.fasterxml.jackson.annotation.JsonInclude.Include
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.node.JsonNodeFactory
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

object JacksonConfiguration {

    val objectMapper: ObjectMapper by lazy {
        val objectMapper = jacksonObjectMapper()

        // use snake case for names
        objectMapper.propertyNamingStrategy = PropertyNamingStrategies.SNAKE_CASE

        // serialize dates, date times, and times as strings, not numbers
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)

        // don't serialize nulls
        objectMapper.setSerializationInclusion(Include.NON_NULL)

        // deserialize JSON numbers to Java BigDecimals
        objectMapper.nodeFactory = JsonNodeFactory.withExactBigDecimals(true);
        objectMapper.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)

        // preserve time zone offsets when deserializing
        objectMapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)

        // we default to the ISO8601 format for JSR-310
        objectMapper.registerModule(JavaTimeModule())

        objectMapper
    }
}
