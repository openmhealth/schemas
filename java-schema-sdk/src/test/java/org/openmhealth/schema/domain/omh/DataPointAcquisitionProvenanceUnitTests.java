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

import org.testng.annotations.Test;

import java.time.OffsetDateTime;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.openmhealth.schema.domain.omh.DataPointModality.SENSED;


/**
 * @author Emerson Farrugia
 */
public class DataPointAcquisitionProvenanceUnitTests {

    @Test(expectedExceptions = NullPointerException.class)
    public void constructorShouldThrowExceptionOnUndefinedSourceName() {

        new DataPointAcquisitionProvenance.Builder(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void constructorShouldThrowExceptionOnEmptySourceName() {

        new DataPointAcquisitionProvenance.Builder("");
    }

    @Test
    public void buildShouldConstructObjectUsingOnlyRequiredProperties() {

        DataPointAcquisitionProvenance provenance = new DataPointAcquisitionProvenance.Builder("chest strap").build();

        assertThat(provenance, notNullValue());
        assertThat(provenance.getSourceName(), equalTo("chest strap"));
        assertThat(provenance.getSourceCreationDateTime(), nullValue());
        assertThat(provenance.getModality(), nullValue());
    }

    @Test
    public void buildShouldConstructObjectUsingOptionalProperties() {

        OffsetDateTime sourceCreationDateTime = OffsetDateTime.now();

        DataPointAcquisitionProvenance provenance = new DataPointAcquisitionProvenance.Builder("chest strap")
                .setSourceCreationDateTime(sourceCreationDateTime)
                .setModality(SENSED)
                .build();

        assertThat(provenance, notNullValue());
        assertThat(provenance.getSourceName(), equalTo("chest strap"));
        assertThat(provenance.getSourceCreationDateTime(), equalTo(sourceCreationDateTime));
        assertThat(provenance.getModality(), equalTo(SENSED));
    }
}