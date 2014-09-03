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

import com.fasterxml.jackson.databind.JsonNode;

import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.regex.Pattern.compile;
import static org.openmhealth.schema.domain.DataFileValidationResult.FAIL;
import static org.openmhealth.schema.domain.DataFileValidationResult.PASS;
import static org.openmhealth.schema.domain.SchemaVersion.VERSION_PATTERN_STRING;

/**
 * A representation of a data file containing a JSON document.
 *
 * @author Emerson Farrugia
 */
public class DataFile {

    public static final Pattern SAMPLE_DATA_URI_PATTERN =
            compile("/(clinical|generic)/([a-z-]+)/([^/]+)/(shouldPass|shouldFail)/([a-z-]+)\\.json$");

    private String name;
    private URI location;
    private SchemaId schemaId;
    private SchemaCategory schemaCategory;
    private DataFileValidationResult expectedValidationResult;
    private JsonNode data;

    // TODO move this into a builder specific to our structure
    public DataFile(URI location, String schemaNamespace, JsonNode data) {

        Matcher matcher = SAMPLE_DATA_URI_PATTERN.matcher(location.toString());

        checkArgument(matcher.find(), "The URI '%s' doesn't identify a document containing sample data.", location);

        this.name = matcher.group(5);
        this.location = location;
        this.schemaId = new SchemaId(schemaNamespace, matcher.group(2), new SchemaVersion(matcher.group(3)));
        this.schemaCategory = SchemaCategory.valueOf(matcher.group(1).toUpperCase());
        this.expectedValidationResult = matcher.group(4).equals("shouldPass") ? PASS : FAIL;
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public URI getLocation() {
        return location;
    }

    public SchemaId getSchemaId() {
        return schemaId;
    }

    public SchemaCategory getSchemaCategory() {
        return schemaCategory;
    }

    public DataFileValidationResult getExpectedValidationResult() {
        return expectedValidationResult;
    }

    public JsonNode getData() {
        return data;
    }
}
