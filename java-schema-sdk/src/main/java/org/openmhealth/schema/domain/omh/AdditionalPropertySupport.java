/*
 * Copyright 2015 Open mHealth
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
     * Sets an additional property.
     *
     * @param name the name of the property to set
     * @param value the value of the property to set
     */
    @JsonAnySetter
    default void setAdditionalProperty(String name, Object value) {

        checkNotNull(name, "A name hasn't been specified.");
        checkArgument(!name.isEmpty(), "An empty name has been specified.");

        getAdditionalProperties().put(name, value);
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
