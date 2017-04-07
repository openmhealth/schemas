/*
 * Copyright 2016 Open mHealth
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
import org.openmhealth.schema.domain.omh.SchemaId;
import org.openmhealth.schema.domain.omh.SchemaVersion;

import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.regex.Pattern.compile;
import static org.openmhealth.schema.domain.DataFileValidationResult.FAIL;
import static org.openmhealth.schema.domain.DataFileValidationResult.PASS;


/**
 * A representation of a data file containing a JSON document.
 *
 * @author Emerson Farrugia
 */
public class DataFile {

    public static final Pattern TEST_DATA_URI_PATTERN =
            compile("/([a-z0-9-]+)/([a-z0-9-]+)/([^/]+)/(shouldPass|shouldFail)/([a-z0-9-]+)\\.json$");

    private String name;
    private URI location;
    private String path;
    private SchemaId schemaId;
    private DataFileValidationResult expectedValidationResult;
    private JsonNode data;

    // TODO move this into a builder specific to our structure
    public DataFile(URI location, JsonNode data) {

        Matcher matcher = TEST_DATA_URI_PATTERN.matcher(location.toString());

        checkArgument(matcher.find(), "The URI '%s' doesn't identify a document containing test data.", location);

        this.name = matcher.group(5);
        this.location = location;
        this.path = matcher.group(0);
        this.schemaId = new SchemaId(matcher.group(1), matcher.group(2), new SchemaVersion(matcher.group(3)));
        this.expectedValidationResult = matcher.group(4).equals("shouldPass") ? PASS : FAIL;
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public URI getLocation() {
        return location;
    }

    public String getPath() {
        return path;
    }

    public SchemaId getSchemaId() {
        return schemaId;
    }

    public DataFileValidationResult getExpectedValidationResult() {
        return expectedValidationResult;
    }

    public JsonNode getData() {
        return data;
    }

    @Override
    public String toString() {
        return getPath();
    }
}
