# Test Data Validator

This validator is a Python application that validates test data against schemas.

## Requirements

You'll need [Python 3.7](https://www.python.org/downloads/) or later.
 
You'll also need the [requests](https://pypi.org/project/requests/) and [jsonschema](https://pypi.org/project/jsonschema/) Python packages. Note that `jsonschema` depends on [format checkers](https://python-jsonschema.readthedocs.io/en/stable/validate/#validating-formats). You can install the format checkers with `jsonschema` by using the following commands:

```sh
pip install -r requirements.txt
```

> If you have both Python 2 and Python 3, your Python 3 command may be called `python3` and your Python 3 package installer command may be called `pip3`. Please use the appropriate Python 3 commands for your system. 

## Running the validator
To run the validator from a Linux shell or in a macOS terminal, run this command **from the `test-data-validator` directory**:

```sh
python validator.py
```

The validator will load all the test data files in test data directory and check them against the corresponding schema files in the schema directory.

The validator looks for schema files in the `../schemas` base directory and test data files in the `../test-data` 
base directory. The validator expects the schema and test data directories to have a fixed structure.

The schema directory structure looks like this:

    schemas
    `-- omh                         # namespace
        +-- body-weight-1.0.json    # schema file (name-version.json)
        `-- body-weight-1.1.json    

The test data directory structure looks like this:
  
    test-data
    `-- omh                           # schema namespace
        `-- body-weight               # schema name
            `-- 1.0                   # schema version
                +-- shouldPass        # contains positive tests
                |   +-- first.json    # test data file (any name)
                |   `-- second.json      
                `-- shouldFail        # contains negative tests
                    +-- third.json      
                    `-- fourth.json

The test data directory structure groups test data files by the schema to validate it against.

A _schema ID_ is a tuple consisting of (namespace, name, version), e.g. omh:body-weight:1.0.

The validator checks test data files against corresponding schema files. In the above example, test data file `test-data/omh/body-weight/1.0/shouldPass/first.json` corresponds to schema ID `omh:body-weight:1.0`, which is located in `schemas/omh/body-weight-1.0.json`, so the validator tests that `first.json` passes validation against `schemas/omh/body-weight-1.0.json`. According to the rules of semantic versioning, data that conforms to a `1.0` schema should also conform to a `1.1` schema, so the validator also tests that `first.json` passes validation against `schemas/omh/body-weight-1.1.json`. In total, the validator will validate all 4 data files against both versions of the `omh:body-weight` schema, for a total of 8 checks.


## Creating schemas

The validator helps you test whether the new schemas you're creating validate data correctly. 

* To add a new schema with an existing namespace, drop the schema file under the existing namespace directory, e.g. `schemas/<schema_namespace>`. 
* To add a new namespace, create a new namespace directory under the `schemas/` base directory. You'll also need to configure the corresponding base URI prefix, as discussed below. 
* To add a test data file that should match an existing schema, drop it in the corresponding `test-data/<schema_namespace>/<schema_name>/<schema_version>/shouldPass` directory. 
* To add a test data file that should not  match an existing schema, drop it in the corresponding `test-data/<schema_namespace>/<schema_name>/<schema_version>/shouldFail` directory.

Validation is triggered by the presence of test data files. If a schema doesn't have any corresponding test data files, it won't be tested directly. The schema may however be tested indirectly if it's referenced by another schema that has its own test data. For example, if there are no test data files for the `omh:time-frame:1.0` schema, but the `omh:blood-pressure:3.0` schema references `omh:time-frame`, the relevant time frame properties in the `omh:blood-pressure` test data files would be checked against the `omh:time-frame` schema. 

If a test data file is loaded by the validator but no corresponding local or remote schema is found, a warning will be shown.
```
Warning: No schemas have been found that validate data file '../test-data/omh/new-schema/1.0/shouldPass/some-data.json'.
```


## Working with remote schemas

A JSON schema is identified using its _base URI_, as [defined in RCF3986](https://datatracker.ietf.org/doc/html/rfc3986#section-5). A schema's [base URI](https://json-schema.org/understanding-json-schema/structuring.html#base-uri) can be set explicitly using the `$id` keyword. Alternatively, a schema's base URI can derived implicitly, using either the URL used to retrieve the schema, or the base URI of a different schema that references it using a relative `$ref` keyword. These mechanisms are discussed in depth in the [_Structuring a complex schema_ section](https://json-schema.org/understanding-json-schema/structuring.html) of the JSON Schema manual.   

The validator is capable of resolving both local and remote schemas by their base URIs.

- You can use the absolute base URI of a remote schema in the `$ref` property of a local schema, and the validator will retrieve, cache, and use the remote schema.
- You can create a test data file for a schema that only exists remotely, and the validator will similarly fetch and use the schema. For this to work, the suffix of the base URI of the remote schema must follow the `name-version.json` convention.  

The validator constructs a base URI for a schema by mapping its schema namespace to a base URI prefix and appending its filename. The `omh` namespace is mapped to the `https://w3id.org/openmhealth/schemas/omh` prefix, so the `omh:blood-glucose:3.0` schema has base URI `https://w3id.org/openmhealth/schemas/omh/blood-glucose-3.0.json`.

The following mappings are configured:

| namespace | base URI prefix                              |
|-----------|----------------------------------------------|
| granola   | https://w3id.org/openmhealth/schemas/granola |
| ieee      | https://w3id.org/ieee/ieee-1752-schema       |
| omh       | https://w3id.org/openmhealth/schemas/omh     |


To create a new namespace, add a dictionary entry to `schema_namespace_uris` in `validator_types.py`.


## Questions
If you encounter any problems or have questions, [create an issue](https://github.com/openmhealth/schemas/issues). If
you have general questions, come by our [developer forum](https://groups.google.com/forum/#!forum/omh-developers) and
ask away. 
 
