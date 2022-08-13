# Test Data Validator

This validator is a Python application that validates test data against schemas.

### Requirements

You'll need [Python 3.7](https://www.python.org/downloads/) or later.
 
You'll also need the [jsonschema](https://pypi.org/project/jsonschema/) Python package. `jsonschema` depends on format checkers, [listed at the bottom of the `jsonschema` validation documentation](https://python-jsonschema.readthedocs.io/en/stable/validate/#validating-formats). To install the format checking Python packages along with `jsonschema`, run: 

```sh
pip install "jsonschema[format]"
```

> If you have both Python 2 and Python 3, your Python 3 command may be called `python3` and your Python 3 package installer command may be called `pip3`. Please use the appropriate Python 3 commands for your system. 

### Building and running
To build and run the validator from a Linux shell or in a macOS terminal, run this command **from the `test-data-validator` directory**:

```sh
python validator.py
```

The validator will run through the test data files and display a validation summary and any validation errors.

### Testing new schemas 
The validator looks for schema files in the `../schemas` base directory and test data files in the `../test-data` 
base directory. 

The validator expects these schema and test data directories to have a fixed structure. The schema directory structure is 
simple:

    schemas
    `-- omh                         # namespace
        +-- body-weight-1.0.json    # schema file
        `-- body-weight-1.1.json    # schema file

* To add a new schema with an existing namespace, drop the schema file under the existing namespace directory. 
* To add a new schema with a new namespace, create a new namespace directory as a sibling to the `omh` namespace directory 
and drop the schema file in there.
  
The test data directory structure looks like this:
  
    test-data
    `-- omh                           # namespace
        `-- body-weight               # schema name
            `-- 1.0                   # version
                +-- shouldPass        # contains positive tests
                |   +-- first.json    # test data file
                |   `-- second.json      
                `-- shouldFail        # contains negative tests
                    +-- third.json      
                    `-- fourth.json

The validator checks test data files against any matching schema files found in the schema directory. In the above 
example, the validator checks that test data file `test-data/omh/body-weight/1.0/shouldPass/first.json` matches schema `omh:body-weight:1.0` located in `schemas/omh/body-weight-1.0.json`. According to the rules of semantic
 versioning, `1.0` data should conform to `1.1` schemas, so the validator also checks that `first.json` matches `schemas/omh/body-weight-1.1.json`.

To add a test data file that should match an existing schema, drop it in the `shouldPass` directory of the corresponding schema version. 

To add a test data file that should not match an existing schema, drop it in the `shouldFail` directory of the corresponding schema version.

### Questions
If you encounter any problems or have questions, [create an issue](https://github.com/openmhealth/schemas/issues). If
you have general questions, come by our [developer forum](https://groups.google.com/forum/#!forum/omh-developers) and
ask away. 
 
