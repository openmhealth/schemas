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

import com.github.fge.jackson.JacksonUtils;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.LogLevel;
import com.github.fge.jsonschema.core.report.ProcessingMessage;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import org.openmhealth.schema.domain.DataFile;
import org.openmhealth.schema.domain.SchemaFile;
import org.openmhealth.schema.domain.SchemaVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;

import static org.openmhealth.schema.domain.DataFileValidationResult.PASS;


/**
 * A primitive validation service implementation. This implementation currently logs failures, but will eventually
 * return reports that can be rendered in a browser.
 *
 * @author Emerson Farrugia
 */
@Service
public class ValidationServiceImpl implements ValidationService {

    private static final Logger log = LoggerFactory.getLogger(ValidationServiceImpl.class);

    @Override
    public void validateDataFiles(Collection<SchemaFile> schemaFiles, Collection<DataFile> dataFiles) {

        try {
            for (SchemaFile schemaFile : schemaFiles) {

                log.info(schemaFile.getSchemaId().toString());

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

                    ProcessingReport report = schemaFile.getJsonSchema().validate(dataFile.getData());

                    if (dataFile.getExpectedValidationResult() == PASS) {
                        if (report.isSuccess()) {
                            log.info("> {} -- OK (passed as expected)", dataFile.getName());
                        }
                        else {
                            if (!log.isInfoEnabled()) {
                                log.warn(schemaFile.getSchemaId().toString());
                            }

                            log.error("> {} -- NOT OK (failed but was expected to pass)", dataFile.getName());
                            for (ProcessingMessage processingMessage : report) {
                                if (processingMessage.getLogLevel() != LogLevel.WARNING) {
                                    log.error(JacksonUtils.prettyPrint(processingMessage.asJson()));
                                }
                            }
                        }
                    }
                    else {
                        if (!report.isSuccess()) {
                            log.info("> {} -- OK (failed as expected)", dataFile.getName());
                        }
                        else {
                            if (!log.isInfoEnabled()) {
                                log.warn(schemaFile.getSchemaId().toString());
                            }

                            log.error("> {} -- NOT OK (passed but was expected to fail)", dataFile.getName());
                            for (ProcessingMessage processingMessage : report) {
                                if (processingMessage.getLogLevel() != LogLevel.WARNING) {
                                    log.error(JacksonUtils.prettyPrint(processingMessage.asJson()));
                                }
                            }
                        }
                    }
                }
            }
        }
        catch (ProcessingException e) {
            throw new RuntimeException("The specified data files and schemas can't be validated.", e);
        }
    }
}
