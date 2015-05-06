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

package org.openmhealth.schema.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.openmhealth.schema.serializer.SerializationConstructor;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * A data point.
 *
 * @author Emerson Farrugia
 * @version 1.0
 * @see <a href="http://www.openmhealth.org/developers/schemas/#data-point">data-point</a>
 */
public class DataPoint<T> {

    private String id; // this is duplicated from the header to make Spring Data happy
    private DataPointHeader header;
    private T body;


    @SerializationConstructor
    private DataPoint() {
    }

    /**
     * @param header the header of this data point
     * @param body the body of this data point
     */
    @JsonCreator
    public DataPoint(@JsonProperty("header") DataPointHeader header, @JsonProperty("body") T body) {

        checkNotNull(header, "A header hasn't been specified.");
        checkNotNull(body, "A body hasn't been specified.");

        this.id = header.getId();
        this.header = header;
        this.body = body;
    }

    public DataPointHeader getHeader() {
        return header;
    }

    public T getBody() {
        return body;
    }

    @SuppressWarnings("SimplifiableIfStatement")
    @Override
    public boolean equals(Object object) {

        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        DataPoint<?> that = (DataPoint<?>) object;

        if (!id.equals(that.id)) {
            return false;
        }
        if (!header.equals(that.header)) {
            return false;
        }
        return body.equals(that.body);
    }

    @Override
    public int hashCode() {

        int result = id.hashCode();
        result = 31 * result + header.hashCode();
        result = 31 * result + body.hashCode();
        return result;
    }
}
