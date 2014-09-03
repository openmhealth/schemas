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

package org.openmhealth.schema.configuration;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import org.openmhealth.schema.domain.DataFile;
import org.openmhealth.schema.domain.SchemaFile;
import org.openmhealth.schema.service.DataFileService;
import org.openmhealth.schema.service.SchemaFileService;
import org.openmhealth.schema.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.List;

/**
 * A standalone application that validates sample data files against their corresponding schemas.
 *
 * @author Emerson Farrugia
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan("org.openmhealth.schema")
public class Application {

    @Value("${schemaFiles.baseDirectory}")
    private String schemaFileBaseDirectory;

    @Value("${sampleDataFiles.baseDirectory}")
    private String sampleDataFileBaseDirectory;

    @Autowired
    private SchemaFileService schemaFileService;

    @Autowired
    private DataFileService dataFileService;

    @Autowired
    private ValidationService validationService;

    public static void main(String[] args) throws IOException, ProcessingException {

        ConfigurableApplicationContext applicationContext = SpringApplication.run(Application.class, args);

        Application application = applicationContext.getBean(Application.class);
        application.checkSampleDataFiles();
    }

    public void checkSampleDataFiles() {

        List<SchemaFile> schemaFiles = schemaFileService.getSchemaFiles(asUri(schemaFileBaseDirectory));
        List<DataFile> dataFiles = dataFileService.getDataFiles(asUri(sampleDataFileBaseDirectory));

        validationService.validateDataFiles(schemaFiles, dataFiles);
    }

    private URI asUri(String filename) {
        return new File(filename).toURI();
    }
}
