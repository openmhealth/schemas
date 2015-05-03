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

package org.openmhealth.schema.domain;

import org.testng.annotations.Test;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.openmhealth.schema.domain.MassUnit.KILOGRAM;


/**
 * @author Emerson Farrugia
 */
public class BodyWeightUnitTests {

    @Test(expectedExceptions = NullPointerException.class)
    public void builderShouldThrowExceptionOnUndefinedBodyWeight() {

        new BodyWeight.Builder(null);
    }

    @Test
    public void buildShouldConstructBodyWeightUsingOnlyRequiredProperties() {

        MassUnitValue massUnitValue = new MassUnitValue(KILOGRAM, BigDecimal.valueOf(65));

        BodyWeight bodyWeight = new BodyWeight.Builder(massUnitValue).build();

        assertThat(bodyWeight, notNullValue());
        assertThat(bodyWeight.getBodyWeight(), equalTo(massUnitValue));
        assertThat(bodyWeight.getEffectiveTimeFrame(), nullValue());
        assertThat(bodyWeight.getDescriptiveStatistic(), nullValue());
    }

    // TODO add construction and serialization tests
}