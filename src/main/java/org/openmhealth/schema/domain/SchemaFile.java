/*
 * Copyright 2014 Open mHealth
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openmhealth.schema.domain;

import com.github.fge.jsonschema.main.JsonSchema;

import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.regex.Pattern.compile;

/**
 * A representation of a schema file.
 *
 * @author Emerson Farrugia
 */
public class SchemaFile {

    /*
     * This pattern corresponds to the current layout in the /schema directory. The metadata extracted from the URI
     * should probably live in the schema itself.
     */
    public static final Pattern SCHEMA_URI_PATTERN = compile("/([a-z-]+)/([a-z-]+)-(.+)\\.json$");

    private SchemaId schemaId;
    private URI location;
    private JsonSchema jsonSchema;

    // TODO move this into a builder specific to our structure
    public SchemaFile(URI location, JsonSchema jsonSchema) {

        checkNotNull(location);
        checkNotNull(jsonSchema);

        Matcher matcher = SCHEMA_URI_PATTERN.matcher(location.toString());

        checkArgument(matcher.find(), "The URI '%s' doesn't identify a schema.", location);

        this.schemaId = new SchemaId(matcher.group(1), matcher.group(2), new SchemaVersion(matcher.group(3)));
        this.location = location;
        this.jsonSchema = jsonSchema;
    }

    public SchemaId getSchemaId() {
        return schemaId;
    }

    public URI getLocation() {
        return location;
    }

    public JsonSchema getJsonSchema() {
        return jsonSchema;
    }
}
