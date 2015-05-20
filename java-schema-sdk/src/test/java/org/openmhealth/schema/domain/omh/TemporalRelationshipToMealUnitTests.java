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
import static org.openmhealth.schema.domain.omh.TemporalRelationshipToMeal.*;


/**
 * @author Emerson Farrugia
 */
public class TemporalRelationshipToMealUnitTests {

    @Test
    public void getSchemaValueShouldPreferSpecifiedValue() {

        assertThat(TWO_HOURS_POSTPRANDIAL.getSchemaValue(), equalTo("2 hours postprandial"));
    }

    @Test
    public void getSchemaValueShouldDefaultToConvertedConstantValue() {

        assertThat(AFTER_LUNCH.getSchemaValue(), equalTo("after lunch"));
    }

    @Test
    public void findBySchemaValueShouldReturnNullOnUnrecognizedValue() throws Exception {

        assertThat(findBySchemaValue("unknown"), nullValue());
    }

    @Test
    public void findBySchemaValueShouldReturnConstantHavingSpecifiedValue() throws Exception {

        assertThat(findBySchemaValue("before dinner"), equalTo(BEFORE_DINNER));
    }

    @Test
    public void findBySchemaValueShouldReturnConstantHavingConvertedValue() throws Exception {

        assertThat(findBySchemaValue("2 hours postprandial"), equalTo(TWO_HOURS_POSTPRANDIAL));
    }
}