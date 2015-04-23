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

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import org.openmhealth.schema.domain.SchemaFile;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;


/**
 * A schema file service implementation that loads schema files from the file system.
 *
 * @author Emerson Farrugia
 */
@Service
public class FileSystemSchemaFileServiceImpl implements SchemaFileService {

    private final JsonSchemaFactory jsonSchemaFactory = JsonSchemaFactory.byDefault();

    @Override
    public List<SchemaFile> getSchemaFiles(URI baseDirectory) {

        List<SchemaFile> schemaFiles = new ArrayList<>();

        try {
            File[] namespaceDirectories = new File(baseDirectory).listFiles();

            if (namespaceDirectories == null) {
                return schemaFiles;
            }

            for (File namespaceDirectory : namespaceDirectories) {

                File[] files = namespaceDirectory.listFiles();

                if (files == null) {
                    continue;
                }

                for (File file : files) {

                    JsonSchema jsonSchema = jsonSchemaFactory.getJsonSchema(file.toURI().toString());
                    schemaFiles.add(new SchemaFile(file.toURI(), jsonSchema));
                }
            }
        }
        catch (ProcessingException e) {
            throw new RuntimeException(format("The schema files in directory '%s' can't be loaded.", baseDirectory),
                    e);
        }

        return schemaFiles;
    }
}
