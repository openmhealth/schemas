# Test Data Validator

This validator is a simple application that validates test data against schemas. It makes good use of Francis Galiegue's
 JSON Schema validator, which you can find [here](https://github.com/fge/json-schema-validator). Thank you, Francis.

### Requirements
You'll need

* [Gradle](http://www.gradle.org/downloads) to build the source code, but this will get downloaded automatically
* [Java SE 8](http://www.oracle.com/technetwork/java/javase/downloads/index-jsp-138363.html) to run it

### Building and running
To build and run the validator from a Linux shell or on OS X Terminal, either run this command from the `test-data-validator` directory

```sh
$ ../gradlew bootRun
```

or this command from the root of the repository

```sh
$ ./gradlew test-data-validator:bootRun
```

The validator will run through the test data files and display a validation summary and any validation errors. If
you want to see more verbose information, you can adjust the logging level in the `application.properties` file.

### Testing new schemas 
By default, the validator looks for schema files in the `../schemas` base directory and test data files in the `../test-data` 
base directory. To change these defaults, modify the corresponding lines in the `application.properties` file.

The validator expects these schema and test data directories to have a fixed structure. The schema directory structure is 
simple

    schemas
    `-- omh                         # namespace
        +-- body-weight-1.0.json    # schema file
        `-- body-weight-1.1.json    # schema file

* To add a new schema with an existing namespace, drop the schema file under the existing namespace directory. 
* To add a new schema with a new namespace, create a new namespace directory as a sibling to the `omh` namespace directory 
and drop the schema file in there.
  
The test data directory structure looks like this
  
    test-data
    `-- omh                         # namespace
        `-- body-weight             # schema name
            `-- 1.0                 # version
                +-- shouldPass      # contains positive tests
                |   +-- a.json      # test data file
                |   `-- b.json      
                `-- shouldFail      # contains negative tests
                    +-- c.json      
                    `-- d.json

The validator checks test data files against any matching schema files found in the schema directory. In the above 
example, it will check that `a.json` matches `body-weight-1.0.json` because `a.json` is stored under
the `shouldPass` directory of namespace `omh`, schema `body-weight`, and version `1.0`. According to the rules of semantic
 versioning, `1.0` data should conform to `1.1` schemas, so the validator also checks that `a.json` matches `body-weight-1.1.json`.

* To add a test data file that should match an existing schema, drop it in the `shouldPass` directory of the corresponding schema version.
* To add a test data file that should not match an existing schema, drop it in the `shouldFail` directory of the corresponding schema version.

### Questions
If you encounter any problems or have questions, [create an issue](https://github.com/openmhealth/schemas/issues). If
you have general questions, come by our [developer forum](https://groups.google.com/forum/#!forum/omh-developers) and
ask away. 
 
