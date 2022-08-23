package org.openmhealth.schema.domain

import java.util.regex.Pattern

/**
 * A semantic schema version, consisting of a major number, minor number, and an optional qualifier.
 *
 * @author Emerson Farrugia
 */
data class SchemaVersion(
    val major: Int = 1,
    val minor: Int = 0,
    val qualifier: String? = null
) : Comparable<SchemaVersion> {

    init {
        require(major >= 0) { "A negative major version has been specified." }
        require(minor >= 0) { "A negative minor version has been specified." }
        require(qualifier == null || QUALIFIER_PATTERN.matcher(qualifier).matches()) {
            "A malformed qualifier has been specified."
        }
    }

    companion object {
        private const val QUALIFIER_PATTERN_STRING = "[a-zA-Z0-9]+"
        private const val PATTERN_STRING = "(\\d+)\\.(\\d+)(?:\\.($QUALIFIER_PATTERN_STRING))?"

        val QUALIFIER_PATTERN: Pattern = Pattern.compile(QUALIFIER_PATTERN_STRING)
        private val PATTERN: Pattern = Pattern.compile(PATTERN_STRING)

        fun fromString(version: String): SchemaVersion {
            val matcher = PATTERN.matcher(version)
            require(matcher.matches()) { "A malformed version has been specified." }

            return SchemaVersion(
                major = Integer.valueOf(matcher.group(1)),
                minor = Integer.valueOf(matcher.group(2)),
                qualifier = matcher.group(3)
            )
        }

        val comparator = compareBy<SchemaVersion> { it.major }
            .thenBy { it.minor }
            .thenBy { it.qualifier }
    }

    override fun toString(): String =
        "$major.$minor" +
                (qualifier
                    ?.let { ".$it" }
                    ?: "")

    override fun compareTo(other: SchemaVersion): Int =
        comparator.compare(this, other)
}
