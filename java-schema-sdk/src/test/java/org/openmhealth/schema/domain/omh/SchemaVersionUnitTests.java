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
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.openmhealth.schema.domain.omh.SchemaVersion.isValidVersion;


/**
 * @author Emerson Farrugia
 */
public class SchemaVersionUnitTests {

    @Test(expectedExceptions = NullPointerException.class)
    public void stringConstructorShouldThrowExceptionOnUndefinedVersion() {

        new SchemaVersion(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void stringConstructorShouldThrowExceptionOnMalformedVersion() {

        new SchemaVersion("a");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void stringConstructorShouldThrowExceptionOnNegativeMajorVersion() {

        new SchemaVersion("-2.1");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void stringConstructorShouldThrowExceptionOnMalformedMajorVersion() {

        new SchemaVersion("a.1");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void stringConstructorShouldThrowExceptionOnNegativeMinorVersion() {

        new SchemaVersion("2.-1");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void stringConstructorShouldThrowExceptionOnMalformedMinorVersion() {

        new SchemaVersion("2.a");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void stringConstructorShouldThrowExceptionOnMalformedQualifier() {

        new SchemaVersion("2.1.%");
    }

    @Test
    public void stringConstructorShouldSupportZeroMajorVersion() {

        SchemaVersion schemaVersion = new SchemaVersion("0.1");

        assertThat(schemaVersion, notNullValue());
        assertThat(schemaVersion.getMajor(), equalTo(0));
        assertThat(schemaVersion.getMinor(), equalTo(1));
        assertThat(schemaVersion.getQualifier().isPresent(), equalTo(false));
    }

    @Test
    public void stringConstructorShouldSupportZeroMinorVersion() {

        SchemaVersion schemaVersion = new SchemaVersion("3.0");

        assertThat(schemaVersion, notNullValue());
        assertThat(schemaVersion.getMajor(), equalTo(3));
        assertThat(schemaVersion.getMinor(), equalTo(0));
        assertThat(schemaVersion.getQualifier().isPresent(), equalTo(false));
    }

    @Test
    public void stringConstructorShouldSupportQualifier() {

        SchemaVersion schemaVersion = new SchemaVersion("2.1.RELEASE");

        assertThat(schemaVersion, notNullValue());
        assertThat(schemaVersion.getMajor(), equalTo(2));
        assertThat(schemaVersion.getMinor(), equalTo(1));
        assertThat(schemaVersion.getQualifier().isPresent(), equalTo(true));
        assertThat(schemaVersion.getQualifier().get(), equalTo("RELEASE"));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void numericConstructorShouldThrowExceptionOnNegativeMajorVersion() {

        new SchemaVersion(-2, 1, null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void numericConstructorShouldThrowExceptionOnNegativeMinorVersion() {

        new SchemaVersion(2, -1, null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void numericConstructorShouldThrowExceptionOnMalformedQualifier() {

        new SchemaVersion(-2, 1, "%");
    }

    @Test
    public void numericConstructorShouldSupportZeroMajorVersion() {

        SchemaVersion schemaVersion = new SchemaVersion(0, 1);

        assertThat(schemaVersion, notNullValue());
        assertThat(schemaVersion.getMajor(), equalTo(0));
        assertThat(schemaVersion.getMinor(), equalTo(1));
        assertThat(schemaVersion.getQualifier().isPresent(), equalTo(false));
    }

    @Test
    public void numericConstructorShouldSupportZeroMinorVersion() {

        SchemaVersion schemaVersion = new SchemaVersion(3, 0);

        assertThat(schemaVersion, notNullValue());
        assertThat(schemaVersion.getMajor(), equalTo(3));
        assertThat(schemaVersion.getMinor(), equalTo(0));
        assertThat(schemaVersion.getQualifier().isPresent(), equalTo(false));
    }

    @Test
    public void numericConstructorShouldSupportQualifier() {

        SchemaVersion schemaVersion = new SchemaVersion(2, 1, "RELEASE");

        assertThat(schemaVersion, notNullValue());
        assertThat(schemaVersion.getMajor(), equalTo(2));
        assertThat(schemaVersion.getMinor(), equalTo(1));
        assertThat(schemaVersion.getQualifier().isPresent(), equalTo(true));
        assertThat(schemaVersion.getQualifier().get(), equalTo("RELEASE"));
    }

    @Test
    public void isValidVersionShouldReturnTrueOnUndefinedVersion() {

        assertThat(isValidVersion(null), equalTo(true));
    }

    @Test
    public void isValidVersionShouldReturnFalseOnMalformedVersion() {

        assertThat(isValidVersion("a"), equalTo(false));
    }

    @Test
    public void isValidVersionShouldReturnFalseOnNegativeMajorVersion() {

        assertThat(isValidVersion("-2.1"), equalTo(false));
    }

    @Test
    public void isValidVersionShouldReturnFalseOnMalformedMajorVersion() {

        assertThat(isValidVersion("a.1"), equalTo(false));
    }

    @Test
    public void isValidVersionShouldReturnFalseOnNegativeMinorVersion() {

        assertThat(isValidVersion("2.-1"), equalTo(false));
    }

    @Test
    public void isValidVersionShouldReturnFalseOnMalformedMinorVersion() {

        assertThat(isValidVersion("2.a"), equalTo(false));
    }

    @Test
    public void isValidVersionShouldReturnFalseOnMalformedQualifier() {

        assertThat(isValidVersion("2.1.%"), equalTo(false));
    }

    @Test
    public void isValidVersionShouldReturnTrueOnZeroMajorVersion() {

        assertThat(isValidVersion("0.1"), equalTo(true));
    }

    @Test
    public void isValidVersionShouldReturnTrueOnZeroMinorVersion() {

        assertThat(isValidVersion("3.0"), equalTo(true));
    }

    @Test
    public void isValidVersionShouldReturnTrueOnQualifier() {

        assertThat(isValidVersion("2.1.RELEASE"), equalTo(true));
    }

    @Test
    public void compareToShouldOrderByMajorVersionFirst() {

        assertThat(new SchemaVersion("0.1").compareTo(new SchemaVersion("1.0")), equalTo(-1));
        assertThat(new SchemaVersion("0.1").compareTo(new SchemaVersion("1.0.RC1")), equalTo(-1));
        assertThat(new SchemaVersion("0.1.RC1").compareTo(new SchemaVersion("1.0")), equalTo(-1));
        assertThat(new SchemaVersion("0.1.RC2").compareTo(new SchemaVersion("1.0.RC1")), equalTo(-1));

        assertThat(new SchemaVersion("2.1").compareTo(new SchemaVersion("1.0")), equalTo(1));
        assertThat(new SchemaVersion("2.1").compareTo(new SchemaVersion("1.0.RC1")), equalTo(1));
        assertThat(new SchemaVersion("2.1.RC1").compareTo(new SchemaVersion("1.0")), equalTo(1));
        assertThat(new SchemaVersion("2.1.RC2").compareTo(new SchemaVersion("1.0.RC3")), equalTo(1));
    }

    @Test
    public void compareToShouldOrderByMinorVersionSecond() {

        assertThat(new SchemaVersion("1.1").compareTo(new SchemaVersion("1.2")), equalTo(-1));
        assertThat(new SchemaVersion("1.1.RC1").compareTo(new SchemaVersion("1.2")), equalTo(-1));
        assertThat(new SchemaVersion("1.1").compareTo(new SchemaVersion("1.2.RC1")), equalTo(-1));
        assertThat(new SchemaVersion("1.1.RC1").compareTo(new SchemaVersion("1.2.RC1")), equalTo(-1));

        assertThat(new SchemaVersion("1.3").compareTo(new SchemaVersion("1.2")), equalTo(1));
        assertThat(new SchemaVersion("1.3.RC1").compareTo(new SchemaVersion("1.2")), equalTo(1));
        assertThat(new SchemaVersion("1.3").compareTo(new SchemaVersion("1.2.RC1")), equalTo(1));
        assertThat(new SchemaVersion("1.3.RC1").compareTo(new SchemaVersion("1.2.RC1")), equalTo(1));

        assertThat(new SchemaVersion("1.1").compareTo(new SchemaVersion("1.1")), equalTo(0));
    }

    @Test
    public void compareToShouldOrderByQualifierPresenceThird() {

        assertThat(new SchemaVersion("1.1.RC1").compareTo(new SchemaVersion("1.1")), equalTo(-1));

        assertThat(new SchemaVersion("1.1").compareTo(new SchemaVersion("1.1.RC2")), equalTo(1));
    }

    @Test
    public void compareToShouldOrderByQualifierFourth() {

        assertThat(new SchemaVersion("1.1.RC1").compareTo(new SchemaVersion("1.1.RC2")), equalTo(-1));

        assertThat(new SchemaVersion("3.0.RC3").compareTo(new SchemaVersion("3.0.RC2")), equalTo(1));

        assertThat(new SchemaVersion("3.0.RC3").compareTo(new SchemaVersion("3.0.RC3")), equalTo(0));
    }
}