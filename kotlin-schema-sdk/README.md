# Kotlin Schema SDK
 
This SDK is a collection of Kotlin classes that help you produce and consume Open mHealth-compliant data in your Kotlin, Java, Scala, and Groovy applications. It contains template Kotlin classes for a few Open mHealth and IEEE schemas.

> The SDK doesn't currently have classes for all schemas, but is meant as a template to be extended as needed. We are gathering feedback on whether a complete, pre-packaged SDK is useful. Please let us know in [this poll](https://github.com/openmhealth/schemas/discussions/19).
> 
> If you find the SDK useful and would like to contribute, feel free to open PRs to add support for more Open mHealth or IEEE schemas.

## Requirements

The SDK requires Java 8, since it makes heavy use of JSR-310 date and time constructs.

## Using it

The SDK is not deployed to a Maven repository for now, but should be built directly.

## Building it

Run the following command from the root of this repository.

```sh 
$ ./gradlew kotlin-schema-sdk:build
```
 
The resulting JAR files will available in `kotlin-schema-sdk/build/libs`.

