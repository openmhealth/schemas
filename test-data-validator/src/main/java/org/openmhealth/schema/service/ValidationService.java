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

import org.openmhealth.schema.domain.DataFile;
import org.openmhealth.schema.domain.SchemaFile;
import org.openmhealth.schema.domain.ValidationSummary;

import java.util.Collection;

/**
 * A service that validates schema files and data files.
 *
 * @author Emerson Farrugia
 */
public interface ValidationService {

    /**
     * @param schemaFiles a collection of schema files
     * @param dataFiles the data files to validate against the schema files
     */
    ValidationSummary validateDataFiles(Collection<SchemaFile> schemaFiles, Collection<DataFile> dataFiles);
}
