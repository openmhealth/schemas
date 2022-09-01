package org.openmhealth.schema.domain

import com.fasterxml.jackson.annotation.JsonIgnore

/**
 * An interface for schema classes.
 *
 * @author Emerson Farrugia
 */
interface SchemaSupport {

    /**
     * @return the schema this class corresponds to
     */
    @get:JsonIgnore
    val schemaId: SchemaId
}
