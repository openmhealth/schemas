import os
from jsonschema import validate, RefResolver, ValidationError, draft202012_format_checker
from validator_types import SchemaFile, DataFile
import sys
import traceback

schema_base_dir = '../schema'
test_data_base_dir = '../test-data'

# e.g. {
#        "https://www.openmhealth.org/schema/omh/step-count-1.0.json" : <dict>,
#        "https://www.openmhealth.org/schema/omh/step-count-1.x.json" : <dict>,
#      }
ref_resolver_store = {}

# e.g. { "omh:blood-pressure:1" : { 1: <SchemaFile>, 2: <SchemaFile>] }
schema_major_version_map = {}


def record_schema_version(schema_file: SchemaFile):
    schema_id = schema_file.schema_id
    schema_major_version_key = schema_id.namespace + ":" + schema_id.name + ":" + schema_id.version.major.__str__()

    schema_minor_versions = schema_major_version_map.get(schema_major_version_key, [])
    schema_minor_versions.append(schema_file)
    schema_major_version_map[schema_major_version_key] = schema_minor_versions


def load_schemas():
    for path, _, schema_filenames in os.walk(schema_base_dir):
        if not schema_filenames:
            continue

        for schema_filename in schema_filenames:
            if schema_filename.endswith("x.json"):
                continue
            schema_file = SchemaFile.from_path(os.path.join(path, schema_filename))
            record_schema_version(schema_file)
            ref_resolver_store[schema_file.base_uri] = schema_file.data


def validate_data_file(data_file: DataFile):
    schema_major_version_key = \
        "{0}:{1}:{2}".format(data_file.schema_id.namespace, data_file.schema_id.name, data_file.schema_id.version.major)

    validated = False
    for schema_file in schema_major_version_map.get(schema_major_version_key, []):
        if data_file.schema_id.version.minor > schema_file.schema_id.version.minor:
            continue

        try:
            # see https://python-jsonschema.readthedocs.io/en/stable/references/
            resolver = RefResolver(schema_file.base_uri, schema_file.data, ref_resolver_store)

            # see https://python-jsonschema.readthedocs.io/en/stable/validate/
            validate(data_file.data, schema_file.data, resolver=resolver, format_checker=draft202012_format_checker)

            if not data_file.should_pass:
                print("{} should have failed but passed".format(data_file), file=sys.stderr) # TODO

        except ValidationError as ve:
            if data_file.should_pass:
                print("{} should have passed but failed".format(data_file), file=sys.stderr) # TODO
                print(ve.message, file=sys.stderr) # TODO

        except Exception as e:
            traceback.print_exc()
            sys.exit(1)

        validated = True

    if not validated:
        print("No schema found to validated data file {0}".format(data_file))

    return


def add_wildcard_version_to_ref_resolver_store(schema_file: SchemaFile):
    schema_base_uri = schema_file.base_uri
    schema_base_uri_without_extension = schema_base_uri[:-5]
    schema_base_uri_without_minor_version = schema_base_uri[:schema_base_uri_without_extension.rindex(".")]
    schema_wildcard_base_uri = schema_base_uri_without_minor_version + ".x.json"
    ref_resolver_store[schema_wildcard_base_uri] = schema_file.data


def add_wildcard_versions_to_ref_resolver_store():
    for (schema_major_version_key, schema_files) in schema_major_version_map.items():
        latest_schema_file = schema_files[0]
        for schema_file in schema_files:
            if schema_file.schema_id.version.minor > latest_schema_file.schema_id.version.minor:
                latest_schema_file = schema_file

        add_wildcard_version_to_ref_resolver_store(latest_schema_file)


def validate_data_files():
    for path, _, test_data_filenames in os.walk(test_data_base_dir):
        if not test_data_filenames:
            continue

        for test_data_filename in test_data_filenames:
            data_file = DataFile.from_path(os.path.join(path, test_data_filename), test_data_base_dir)
            validate_data_file(data_file)


load_schemas()
add_wildcard_versions_to_ref_resolver_store()
validate_data_files()
