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

import org.testng.annotations.DataProvider;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * A specialized base class for serialization and deserialization unit tests that maintains a list of
 * serialization tuples to test, and feeds them into parent test cases using TestNG data providers.
 *
 * @author Emerson Farrugia
 */
public abstract class DataProvidingSerializationUnitTests extends SerializationUnitTests {

    private List<SerializationTuple> serializationTuples = new ArrayList<>();

    /**
     * @return a list of serialization tuples to test
     */
    protected List<SerializationTuple> getSerializationTuples() {
        return this.serializationTuples;
    }

    protected void addSerializationTest(SerializationTuple tuple) {
        this.serializationTuples.add(tuple);
    }

    /**
     * @param document a JSON document
     * @param object a Java object corresponding to the document
     */
    protected void addSerializationTest(String document, Object object) {

        addSerializationTest(new SerializationTuple(document, object));
    }

    @Override
    @DataProvider(name = "expectedDocumentProvider")
    public Iterator<Object[]> newExpectedDocumentProvider() {

        return getSerializationTuples()
                .stream()
                .map((t) -> new Object[]{t.getObject(), t.getDocument()})
                .iterator();
    }

    @Override
    @DataProvider(name = "expectedObjectProvider")
    public Iterator<Object[]> newExpectedObjectProvider() {

        return getSerializationTuples()
                .stream()
                .map((t) -> new Object[]{t.getDocument(), t.getObject()})
                .iterator();
    }
}
