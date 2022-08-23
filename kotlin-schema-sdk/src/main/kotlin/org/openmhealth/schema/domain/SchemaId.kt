package org.openmhealth.schema.domain

import java.util.regex.Pattern

/**
 * A schema identifier. It consists of a namespace, a name, and a version. A schema identifier unambiguously identifies
 * a single, immutable schema. The namespace is used to avoid naming collisions in schemas written by different groups
 * or organisations.
 *
 * @author Emerson Farrugia
 * @version 1.0
 * @see [schema-id](https://www.openmhealth.org/documentation/#/schema-docs/schema-library/schemas/omh_schema-id)
 */
data class SchemaId(
    val namespace: SchemaNamespace = OMH_NAMESPACE,
    val name: SchemaName,
    val version: SchemaVersion
) : Comparable<SchemaId> {

    constructor(namespace: String, name: String, version: String) : this(
        namespace = SchemaNamespace(namespace),
        name = SchemaName(name),
        version = SchemaVersion.fromString(version)
    )

    companion object {
        val PATTERN: Pattern = Pattern.compile("([^:]+):([^:]+):([^:]+)")

        fun fromString(id: String): SchemaId {
            val matcher = PATTERN.matcher(id)
            require(matcher.matches()) { "A malformed schema ID has been specified." }

            return SchemaId(
                namespace = matcher.group(1),
                name = matcher.group(2),
                version = matcher.group(3)
            )
        }

        private val comparator = compareBy<SchemaId> { it.namespace }
            .thenBy { it.name }
            .thenBy { it.version }
    }

    override fun toString(): String = "$namespace:$name:$version"

    override fun compareTo(other: SchemaId): Int =
        comparator.compare(this, other)
}
