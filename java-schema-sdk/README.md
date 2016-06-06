# Java Schema SDK

This SDK is a collection of Java classes that help you produce and consume Open mHealth compliant data in your Java,
Groovy, and Scala applications. It contains a Java class for each schema, along with all necessary builders, enums,
  serialization annotations, and unit tests.

## Requirements

The SDK requires Java 8, since it makes heavy use of JSR-310 date and time constructs. It also depends on Guava for
utility methods and Jackson for serialization.

## Using it

The SDK has been deployed to JCenter and Maven Central as a Maven dependency, along with corresponding source and
Javadoc artifacts.

If you use Gradle, add the following dependency to your build script to use it

```groovy
compile 'org.openmhealth.schema:omh-schema-sdk:1.0.6'
```

If you use Maven, add the following dependency to your POM file to use it

```xml
<dependency>
    <groupId>org.openmhealth.schema</groupId>
    <artifactId>omh-schema-sdk</artifactId>
    <version>[1.0,2.0)</version>
</dependency>
```

## Building it

If you'd like to build the JAR file yourself, run the following command from the root of this repository.

```sh
$ ./gradlew java-schema-sdk:build
```

The resulting JAR files will available in `java-schema-sdk/build/libs`.
