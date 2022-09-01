package org.openmhealth.schema.domain

import java.util.regex.Pattern

/**
 * A schema name, which uniquely [identifies][SchemaId] a schema when combined with a [SchemaNamespace] and [SchemaVersion].
 *
 * @author Emerson Farrugia
 */
@JvmInline
value class SchemaName(
    val value: String
) : Comparable<SchemaName> {

    init {
        require(isValid(value)) { "A malformed name has been specified." }
    }

    companion object {
        private const val PATTERN_STRING = "[a-zA-Z0-9-]+"
        private val PATTERN: Pattern = Pattern.compile(PATTERN_STRING)

        fun isValid(string: String?): Boolean =
            string
                ?.let { PATTERN.matcher(it).matches() }
                ?: true
    }

    override fun toString(): String = value

    override fun compareTo(other: SchemaName): Int =
        value.compareTo(other.value)
}
