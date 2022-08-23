package org.openmhealth.schema.domain

import com.fasterxml.jackson.annotation.JsonValue

/**
 * An interface implemented by Kotlin enumerations that represent schema enumerations.
 *
 * @author Emerson Farrugia
 */
interface SchemaEnumValue {
    /**
     * The schema enumeration value.
     */
    @get:JsonValue
    val schemaValue: String
}

inline fun <reified E> findBySchemaValue(schemaValue: String): E?
        where E : SchemaEnumValue,
              E : Enum<E> =
    enumValues<E>()
        .firstOrNull { it.schemaValue == schemaValue }
