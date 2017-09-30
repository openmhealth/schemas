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

package org.openmhealth.schema;


import com.github.fge.jackson.JacksonUtils;
import com.github.fge.jsonschema.core.report.ProcessingMessage;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import org.junit.runner.RunWith;
import org.openmhealth.schema.configuration.Application;
import org.openmhealth.schema.domain.DataFile;
import org.openmhealth.schema.domain.DataFileValidationResult;
import org.openmhealth.schema.domain.SchemaFile;
import org.openmhealth.schema.domain.omh.SchemaVersion;
import org.openmhealth.schema.service.DataFileService;
import org.openmhealth.schema.service.SchemaFileService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.openmhealth.schema.domain.DataFileValidationResult.FAIL;
import static org.openmhealth.schema.domain.DataFileValidationResult.PASS;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;


/**
 * A test suite that dynamically creates a test for each combination of schema and applicable test data file.
 *
 * @author Emerson Farrugia
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = NONE)
public class TestDataValidationIntegrationTests extends AbstractTestNGSpringContextTests {

    private static final Logger logger = getLogger(TestDataValidationIntegrationTests.class);

    @Autowired
    private SchemaFileService schemaFileService;

    @Autowired
    private DataFileService dataFileService;

    @Value("${schemaFiles.baseDirectory}")
    private String schemaFileBaseDirectory;

    @Value("${testDataFiles.baseDirectory}")
    private String testDataFileBaseDirectory;


    @Test(dataProvider = "validationPairProvider")
    public void validateTestData(SchemaFile schemaFile, DataFile dataFile) throws Exception {

        ProcessingReport report = schemaFile.getJsonSchema().validate(dataFile.getData());
        DataFileValidationResult validationResult = report.isSuccess() ? PASS : FAIL;

        if (validationResult != dataFile.getExpectedValidationResult()) {
            for (ProcessingMessage processingMessage : report) {
                logger.error(JacksonUtils.prettyPrint(processingMessage.asJson()));
            }
        }

        assertThat(dataFile.getExpectedValidationResult(), equalTo(validationResult));
    }

    /**
     * @return an iterator over all pairs of schema files and applicable test data files
     */
    @DataProvider(name = "validationPairProvider")
    public Iterator<Object[]> newValidationPairProvider() {

        List<SchemaFile> schemaFiles = schemaFileService.getSchemaFiles(asUri(schemaFileBaseDirectory));

        if (schemaFiles.size() == 0) {
            return Collections.emptyIterator();
        }

        List<DataFile> dataFiles = dataFileService.getDataFiles(asUri(testDataFileBaseDirectory));

        if (dataFiles.size() == 0) {
            return Collections.emptyIterator();
        }

        List<Object[]> validationPairs = new ArrayList<>();

        for (SchemaFile schemaFile : schemaFiles) {

            for (DataFile dataFile : dataFiles) {

                if (!dataFile.getSchemaId().getName().equals(schemaFile.getSchemaId().getName())) {
                    continue;
                }

                SchemaVersion dataFileVersion = dataFile.getSchemaId().getVersion();
                SchemaVersion schemaFileVersion = schemaFile.getSchemaId().getVersion();

                if (dataFileVersion.getMajor() != schemaFileVersion.getMajor()) {
                    continue;
                }

                // a data point conforms to the schema listed in its header and newer minor versions of that schema
                if (dataFileVersion.getMinor() > schemaFileVersion.getMinor()) {
                    continue;
                }

                validationPairs.add(new Object[] {schemaFile, dataFile});
            }
        }

        return validationPairs.iterator();
    }

    private URI asUri(String filename) {

        return new File(filename).toURI();
    }
}
