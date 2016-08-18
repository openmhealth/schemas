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

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.google.common.base.Splitter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;


/**
 * An interface for schema classes that support additional properties.
 *
 * @author Emerson Farrugia
 */
public interface AdditionalPropertySupport {

    /**
     * Sets an additional property. This method supports dot-separated paths by creating nested maps when necessary.
     *
     * @param path  the path of the property to set
     * @param value the value of the property to set
     */
    @SuppressWarnings("unchecked")
    @JsonAnySetter
    default void setAdditionalProperty(String path, Object value) {

        checkNotNull(path, "A path hasn't been specified.");
        checkArgument(!path.isEmpty(), "An empty path has been specified.");

        Iterator<String> names = Splitter.on(".").omitEmptyStrings().split(path).iterator();
        Map<String, Object> currentCollection = getAdditionalProperties();

        while (names.hasNext()) {
            String currentName = names.next();

            // set the value, potentially overriding an existing value
            if (!names.hasNext()) {
                currentCollection.put(currentName, value);
                break;
            }

            // traverse into a collection if one exists
            if (currentCollection.get(currentName) instanceof Map) {
                currentCollection = (Map<String, Object>) currentCollection.get(currentName);
                continue;
            }

            // create a new collection, potentially overriding an existing value
            Map<String, Object> map = new HashMap<>();
            currentCollection.put(currentName, map);
            currentCollection = map;
        }
    }

    /**
     * Gets an additional property.
     *
     * @param name the name of the additional property to retrieve
     * @return the property value, if present
     */
    default Optional<Object> getAdditionalProperty(String name) {

        checkNotNull(name, "A name hasn't been specified.");
        checkArgument(!name.isEmpty(), "An empty name has been specified.");

        return Optional.ofNullable(getAdditionalProperties().get(name));
    }

    /**
     * Gets the additional properties.
     *
     * @return the additional properties as a map
     */
    @JsonAnyGetter
    Map<String, Object> getAdditionalProperties();
}
