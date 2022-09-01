package org.openmhealth.schema.domain

import java.util.regex.Pattern

val IEEE_NAMESPACE = SchemaNamespace("ieee")
val OMH_NAMESPACE = SchemaNamespace("omh")

/**
 * A schema namespace, denoting a group or organisation that creates schemas.
 *
 * @author Emerson Farrugia
 */
@JvmInline
value class SchemaNamespace(
    val value: String
) : Comparable<SchemaNamespace> {

    init {
        require(isValid(value)) { "A malformed namespace has been specified." }
    }

    companion object {
        private const val PATTERN_STRING = "[a-zA-Z0-9.-]+"
        private val PATTERN: Pattern = Pattern.compile(PATTERN_STRING)

        fun isValid(string: String?): Boolean =
            string
                ?.let { PATTERN.matcher(it).matches() }
                ?: true
    }

    override fun toString(): String = value

    override fun compareTo(other: SchemaNamespace): Int =
        value.compareTo(other.value)
}
