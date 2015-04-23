# Open mHealth Schema Repository
A repository of Open mHealth [schemas](http://www.openmhealth.org/developers/schemas/). This repository also includes sample test data and a validator for that data.

## Tooling
There are a few tools which are well suited to working with the schemas and source code in this repository.

* [Git](http://git-scm.com/downloads) to be able to pull the code
* some text editor, preferably one that can do syntax highlighting for JSON documents
    * [Atom](https://atom.io/) if you're comfortable with command line Git
    * [Atom](https://atom.io/) and [SourceTree](http://www.sourcetreeapp.com) if you're *not* comfortable with command-line Git
    * [IntelliJ IDEA Community Edition](http://www.jetbrains.com/idea/download/) for a full IDE with Git integration

To run the validator, you'll also need

* [Gradle](http://www.gradle.org/downloads) to build the source code (but this should get downloaded automatically)
* [Java SE 8](http://www.oracle.com/technetwork/java/javase/downloads/index-jsp-138363.html) to run it

## Validator
The validator is a simple application that validates test data against schemas. It makes good use of Francis Galiegue's JSON Schema validator, which you can find [here](https://github.com/fge/json-schema-validator). Thank you, Francis.

### Configuration
By default, the validator looks for schemas in the `schemas` directory and test data files in the `test-data` file directory. 
If you want to test new schemas or new test data, just drop them into these directories and the validator will pick them up. 

To change these defaults, modify the corresponding lines in the `application.properties` file. You can also set the logging level in there to `WARN` if you find the output too verbose.

### Running
If you're using IntelliJ, simply create a run configuration that points at `org.openmhealth.schema.configuration.Application`.

If you're running from the command line on Linux or from Terminal on OS X, run the following commands from the root of the project directory.

1. `gradlew build`
1. `java -jar build/libs/validator-1.0.0-SNAPSHOT.jar`
