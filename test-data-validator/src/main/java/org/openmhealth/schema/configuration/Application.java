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

package org.openmhealth.schema.configuration;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import org.openmhealth.schema.domain.DataFile;
import org.openmhealth.schema.domain.SchemaFile;
import org.openmhealth.schema.domain.ValidationSummary;
import org.openmhealth.schema.service.DataFileService;
import org.openmhealth.schema.service.SchemaFileService;
import org.openmhealth.schema.service.ValidationService;
import org.slf4j.Logger;
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

import static org.slf4j.LoggerFactory.getLogger;


/**
 * A standalone application that validates test data files against their corresponding schemas.
 *
 * @author Emerson Farrugia
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan("org.openmhealth.schema")
public class Application {

    private static final Logger logger = getLogger(Application.class);

    @Value("${schemaFiles.baseDirectory}")
    private String schemaFileBaseDirectory;

    @Value("${testDataFiles.baseDirectory}")
    private String testDataFileBaseDirectory;

    @Autowired
    private SchemaFileService schemaFileService;

    @Autowired
    private DataFileService dataFileService;

    @Autowired
    private ValidationService validationService;


    public static void main(String[] args) throws IOException, ProcessingException {

        ConfigurableApplicationContext applicationContext = SpringApplication.run(Application.class, args);

        Application application = applicationContext.getBean(Application.class);
        application.validateTestDataFiles();
    }

    public void validateTestDataFiles() {

        List<SchemaFile> schemaFiles = schemaFileService.getSchemaFiles(asUri(schemaFileBaseDirectory));
        if (schemaFiles.size() == 0) {
            logger.warn("No schema files have been loaded.");
            return;
        }

        logger.info("{} schema files have been loaded.", schemaFiles.size());

        List<DataFile> dataFiles = dataFileService.getDataFiles(asUri(testDataFileBaseDirectory));
        if (dataFiles.size() == 0) {
            logger.warn("No data files have been loaded.");
            return;
        }

        logger.info("{} data files have been loaded.", dataFiles.size());

        ValidationSummary validationSummary = validationService.validateDataFiles(schemaFiles, dataFiles);
        if (validationSummary.getAttempted() == 0) {
            logger.warn("No validations have been attempted.");
            return;
        }

        logger.info("{} validations have been attempted.", validationSummary.getAttempted());
        logger.info("{} validations have succeeded.", validationSummary.getSucceeded());
        logger.info("{} validations have failed.", validationSummary.getFailed());
    }

    private URI asUri(String filename) {
        return new File(filename).toURI();
    }
}
