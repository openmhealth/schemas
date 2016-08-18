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

package org.openmhealth.schema.domain.omh;

import com.google.common.base.Joiner;
import org.openmhealth.schema.serializer.SerializationConstructor;

import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.regex.Pattern.compile;


/**
 * A schema identifier. It consists of a namespace, a name, and a version. A schema identifier unambiguously identifies
 * a single, immutable schema. The namespace is used to avoid naming collisions in schemas written by different groups
 * or organisations.
 *
 * @author Emerson Farrugia
 * @version 1.0
 * @see <a href="http://www.openmhealth.org/documentation/#/schema-docs/schema-library/schemas/omh_schema-id">schema-id</a>
 */
public class SchemaId implements Comparable<SchemaId>, SchemaSupport {

    public static final String NAMESPACE_PATTERN_STRING = "[a-zA-Z0-9.-]+";
    public static final Pattern NAMESPACE_PATTERN = compile(NAMESPACE_PATTERN_STRING);
    public static final String NAME_PATTERN_STRING = "[a-zA-Z0-9-]+";
    public static final Pattern NAME_PATTERN = compile(NAME_PATTERN_STRING);

    public static final SchemaId SCHEMA_ID = new SchemaId(OMH_NAMESPACE, "schema-id", "1.0");

    private String namespace;
    private String name;
    private SchemaVersion version;


    @SerializationConstructor
    protected SchemaId() {
    }

    public SchemaId(String namespace, String name, String version) {

        this(namespace, name, new SchemaVersion(version));
    }

    public SchemaId(String namespace, String name, SchemaVersion version) {

        checkNotNull(namespace, "A namespace hasn't been specified.");
        checkArgument(isValidNamespace(namespace), "An invalid namespace has been specified.");
        checkNotNull(name, "A name hasn't been specified.");
        checkArgument(isValidName(name), "An invalid name has been specified.");
        checkNotNull(version, "A version hasn't been specified.");

        this.namespace = namespace;
        this.name = name;
        this.version = version;
    }

    public String getNamespace() {
        return namespace;
    }

    public static boolean isValidNamespace(String namespace) {
        return namespace == null || NAMESPACE_PATTERN.matcher(namespace).matches();
    }

    public String getName() {
        return name;
    }

    public static boolean isValidName(String name) {
        return name == null || NAME_PATTERN.matcher(name).matches();
    }

    public SchemaVersion getVersion() {
        return version;
    }

    @Override
    public String toString() {

        return Joiner.on(":").join(namespace, name, version);
    }

    @Override
    public SchemaId getSchemaId() {
        return SCHEMA_ID;
    }

    @Override
    public boolean equals(Object object) {

        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        SchemaId schemaId = (SchemaId) object;

        return name.equals(schemaId.name) && namespace.equals(schemaId.namespace) && version.equals(schemaId.version);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public int compareTo(SchemaId that) {

        if (getNamespace().compareTo(that.getNamespace()) != 0) {
            return getNamespace().compareTo(that.getNamespace());
        }

        if (getName().compareTo(that.getName()) != 0) {
            return getName().compareTo(that.getName());
        }

        return getVersion().compareTo(that.getVersion());
    }
}
