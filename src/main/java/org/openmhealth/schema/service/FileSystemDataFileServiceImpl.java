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

package org.openmhealth.schema.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openmhealth.schema.domain.DataFile;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.FileVisitor;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;


/**
 * A data file service implementation that loads data files from the file system. This simple implementation is very
 * brittle and can be replaced with a {@link FileVisitor} to make it more robust.
 *
 * @author Emerson Farrugia
 */
@Service
public class FileSystemDataFileServiceImpl implements DataFileService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<DataFile> getDataFiles(URI baseDirectory) {

        List<DataFile> dataFiles = new ArrayList<>();

        try {
            // e.g. omh
            File[] namespaceDirectories = new File(baseDirectory).listFiles();

            if (namespaceDirectories == null) {
                return dataFiles;
            }

            for (File namespaceDirectory : namespaceDirectories) {

                // e.g. omh/blood-pressure
                File[] schemaNameDirectories = namespaceDirectory.listFiles();

                if (schemaNameDirectories == null) {
                    continue;
                }

                for (File schemaNameDirectory : schemaNameDirectories) {

                    // e.g. omh/blood-pressure/1.0
                    File[] versionDirectories = schemaNameDirectory.listFiles();

                    if (versionDirectories == null) {
                        continue;
                    }

                    for (File versionDirectory : versionDirectories) {

                        // e.g. omh/blood-pressure/1.0/shouldPass
                        File[] testDataDirectories = versionDirectory.listFiles();

                        if (testDataDirectories == null) {
                            continue;
                        }

                        for (File testDataDirectory : testDataDirectories) {

                            // e.g. generic/blood-pressure/1.0/shouldPass/test-data.json
                            File[] testDataFiles = testDataDirectory.listFiles();

                            if (testDataFiles == null) {
                                continue;
                            }

                            for (File testDataFile : testDataFiles) {

                                JsonNode testData = objectMapper.readTree(testDataFile);
                                dataFiles.add(new DataFile(testDataFile.toURI(), testData));
                            }
                        }
                    }
                }
            }
        }
        catch (IOException e) {
            throw new RuntimeException(format("The data files in directory '%s' can't be loaded.", baseDirectory), e);
        }

        return dataFiles;
    }
}
