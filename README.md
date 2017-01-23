# Open mHealth Schema Repository

A repository of Open mHealth [schemas](http://www.openmhealth.org/documentation/#/schema-docs/overview). This repository also includes sample test data, a validator for that data, and a Java schema SDK.

[![Build Status](https://travis-ci.org/openmhealth/schemas.svg?branch=master)](https://travis-ci.org/openmhealth/schemas)

## Schemas
The schemas are located in the [schema](schema) directory.

## Validator and test data
The [validator](test-data-validator) is a simple application that validates test data against schemas. The test data 
that it uses is located in the [test-data](test-data) directory.  

## Java SDK 
The [Java SDK](java-schema-sdk) helps you produce and consume Open mHealth compliant data in your Java, Groovy, and Scala applications. 

## Tooling
There are a few tools which are well-suited to working with the schemas and source code in this repository.

* [Git](http://git-scm.com/downloads) to be able to pull the code
* some text editor, preferably one that can do syntax highlighting for JSON documents
    * [Atom](https://atom.io/) if you're comfortable with command line Git
    * [Atom](https://atom.io/) and [SourceTree](http://www.sourcetreeapp.com) if you're *not* comfortable with command-line Git
    * [IntelliJ IDEA Community Edition](http://www.jetbrains.com/idea/download/) for a full IDE with Git integration
