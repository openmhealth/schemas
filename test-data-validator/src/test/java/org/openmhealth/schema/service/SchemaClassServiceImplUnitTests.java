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

package org.openmhealth.schema.service;

import org.openmhealth.schema.domain.omh.BloodPressure;
import org.openmhealth.schema.domain.omh.SchemaId;
import org.testng.annotations.Test;

import java.util.Set;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


/**
 * @author Emerson Farrugia
 */
public class SchemaClassServiceImplUnitTests {

    private SchemaClassServiceImpl schemaClassService = new SchemaClassServiceImpl();

    @Test
    public void initializeSchemaIdsShouldFindSchemaClasses() throws Exception {

        schemaClassService.initializeSchemaIds();

        Set<SchemaId> schemaIds = schemaClassService.getSchemaIds();

        assertThat(schemaIds, not(empty()));
        assertThat(schemaIds.size(), greaterThan(1));
        assertThat(schemaIds, hasItem(BloodPressure.SCHEMA_ID));
    }
}