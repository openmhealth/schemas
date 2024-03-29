import os

from jsonschema import validate, RefResolver, ValidationError, Draft202012Validator
from validator_types import SchemaFile, DataFile, SchemaId
from ieee_url_workaround import ieee_schema_directories, create_synthetic_ieee_schema
import sys
import traceback

schema_base_dir = '../schema'
test_data_base_dir = '../test-data'

# e.g. {
#        "https://w3id.org/openmhealth/schemas/omh/step-count-1.0.json" : <dict>,
#        "https://w3id.org/openmhealth/schemas/omh/step-count-2.0.json" : <dict>,
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
    total_loaded = 0
    for path, _, schema_filenames in os.walk(schema_base_dir):
        for schema_filename in schema_filenames:
            if schema_filename.startswith(".") or schema_filename.endswith("x.json"):
                continue
            schema_file = SchemaFile.from_path(os.path.join(path, schema_filename))
            record_schema_version(schema_file)
            ref_resolver_store[schema_file.schema_id.base_uri] = schema_file.data
            total_loaded += 1

    print("Loaded {} schemas.".format(total_loaded))


def load_synthetic_ieee_schemas():
    total_loaded = 0
    for schema_name in ieee_schema_directories.keys():
        schema_id = SchemaId("ieee", schema_name)
        schema_data = create_synthetic_ieee_schema(schema_id)
        schema_file = SchemaFile(schema_id, schema_data, "synthetic")
        record_schema_version(schema_file)
        ref_resolver_store[schema_file.schema_id.base_uri] = schema_file.data
        total_loaded += 1

    print("Loaded {} synthetic IEEE schemas.".format(total_loaded))


def validate_data_file_against_schema(data_file: DataFile, schema_file: SchemaFile) -> bool:
    """
    :return: true if validation passed, false otherwise
    """
    try:
        # see https://python-jsonschema.readthedocs.io/en/stable/references/
        resolver = RefResolver(schema_file.schema_id.base_uri, schema_file.data, ref_resolver_store)

        # see https://python-jsonschema.readthedocs.io/en/stable/validate/
        validate(data_file.data, schema_file.data, resolver=resolver, format_checker=Draft202012Validator.FORMAT_CHECKER)

        if not data_file.should_pass:
            print("Error: The data file '{}' should have failed validation against schema {}, but"
                  " passed.".format(data_file.name, schema_file.schema_id), end="\n\n", file=sys.stderr)
            print("- Schema path: {}".format(schema_file.path))
            print("- Data file path: {}".format(data_file.path), end="\n\n")
            return False

    except ValidationError as ve:
        if data_file.should_pass:
            print("Error: The data file '{}' should have passed validation against schema {}, but failed"
                  " with the following error:".format(data_file.name, schema_file.schema_id), file=sys.stderr)
            print(ve, end="\n\n")
            print("- Schema path: {}".format(schema_file.path))
            print("- Data file path: {}".format(data_file.path), end="\n\n")
            return False

    except Exception:
        print("Error: An exception occurred while validating {} against {}.".format(data_file, schema_file), file=sys.stderr)
        traceback.print_exc()
        sys.exit(1)

    return True


def validate_data_file(data_file: DataFile) -> int:
    """
    :return: the number of validation failures
    """
    schema_major_version_key = \
        "{0}:{1}:{2}".format(data_file.schema_id.namespace, data_file.schema_id.name, data_file.schema_id.version.major)

    validated = False
    failures = 0

    for schema_file in schema_major_version_map.get(schema_major_version_key, []):
        if data_file.schema_id.version.minor > schema_file.schema_id.version.minor:
            continue

        failures += 1 if not validate_data_file_against_schema(data_file, schema_file) else 0
        validated = True

    if not validated:
        remote_schema_file = SchemaFile.from_schema_id(data_file.schema_id)
        if remote_schema_file:
            failures += 1 if not validate_data_file_against_schema(data_file, remote_schema_file) else 0
        else:
            print("Warning: No local or remote schemas have been found that validate data file '{0}'.".format(data_file.path))

    return failures


def add_wildcard_version_to_ref_resolver_store(schema_file: SchemaFile):
    schema_base_uri = schema_file.schema_id.base_uri
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


def is_data_file(filename: str):
    return filename.find(".json") > -1


def count_data_files():
    total_files = 0

    for path, _, test_data_filenames in os.walk(test_data_base_dir):
        for filename in test_data_filenames:
            if is_data_file(filename):
                total_files += 1

    return total_files


def validate_data_files() -> int:
    """
    :return: the number of validation failures
    """
    total_files = count_data_files()
    if total_files == 0:
        print("No data files found.")
        return 0

    total_validated = 0
    total_failures = 0

    for path, _, test_data_filenames in os.walk(test_data_base_dir):

        for test_data_filename in test_data_filenames:
            if not is_data_file(test_data_filename):
                continue

            data_file = DataFile.from_path(os.path.join(path, test_data_filename), test_data_base_dir)
            total_failures += validate_data_file(data_file)
            total_validated += 1
            print("Validating test data files: %3u%% (%u/%u)"
                  % (
                      total_validated * 100 / total_files,
                      total_validated,
                      total_files
                  ),
                  end='\r')

    print("\nValidated {} test data files.".format(total_validated))
    return total_failures


load_schemas()
load_synthetic_ieee_schemas()
add_wildcard_versions_to_ref_resolver_store()

validation_failure_count = validate_data_files()
sys.exit(0 if validation_failure_count == 0 else 1)
