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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.openmhealth.schema.domain.omh.LengthUnit.FOOT;
import static org.openmhealth.schema.domain.omh.LengthUnit.findBySchemaValue;


/**
 * @author Emerson Farrugia
 */
public class LengthUnitUnitTests {

    @Test
    public void findBySchemaValueShouldReturnNullOnUnrecognizedValue() throws Exception {

        assertThat(findBySchemaValue("unknown"), nullValue());
    }

    @Test
    public void findBySchemaValueShouldReturnMatchingConstant() throws Exception {

        assertThat(findBySchemaValue("ft"), equalTo(FOOT));
    }
}