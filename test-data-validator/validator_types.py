import json
import os
import requests
import sys
import traceback


schema_namespace_uris = {
    "granola": "https://w3id.org/openmhealth/schemas/granola",
    "ieee": "https://w3id.org/ieee/ieee-1752-schema",
    "omh": "https://w3id.org/openmhealth/schemas/omh",
}


class SchemaVersion:
    def __init__(self, major: int = 1, minor: int = 0):
        self.major = major
        self.minor = minor

    def __str__(self):
        return "{0}.{1}".format(self.major, self.minor)

    @classmethod
    def from_string(cls, string):
        segments = string.split('.')
        return cls(int(segments[0]), int(segments[1]))


class SchemaId:
    def __init__(self, namespace: str, name: str, version: SchemaVersion = SchemaVersion()):
        self.namespace = namespace
        self.name = name
        self.version = version
        self.base_uri = schema_namespace_uris[namespace] + "/{0}-{1}.json".format(name, version)

    def __str__(self):
        return "{0}:{1}:{2}".format(self.namespace, self.name, self.version)


class SchemaFile:
    def __init__(self, schema_id: SchemaId, data: dict, path: str):
        self.schema_id = schema_id
        self.data = data
        self.path = path

    @classmethod
    def from_path(cls, path: str):
        path_segments = path.split(os.sep)  # e.g. [..., 'omh', 'blood-pressure-2.0.json']

        schema_namespace = path_segments[-2]
        schema_filename = path_segments[-1]
        schema_name = schema_filename[0:schema_filename.rindex("-")]
        schema_version = SchemaVersion.from_string(schema_filename[len(schema_name)+1:-5])
        schema_id = SchemaId(schema_namespace, schema_name, schema_version)

        file = open(path, "r")
        data = json.load(file)
        file.close()

        return cls(schema_id, data, path)

    @classmethod
    def from_schema_id(cls, schema_id: SchemaId):
        response = requests.get(schema_id.base_uri)
        if response.status_code == 200:
            data = json.loads(response.text)
            return cls(schema_id, data, "remote")
        else:
            return None

    def __str__(self):
        return self.schema_id.__str__()


class DataFile:
    def __init__(self, name: str, schema_id: SchemaId, data: dict, should_pass: bool, path: str):
        self.name = name
        self.schema_id = schema_id
        self.data = data
        self.should_pass = should_pass
        self.path = path

    # e.g. path: ../test-data/omh/medication-prescription/1.0/shouldPass/valid-med-coumadin.json, base_dir: ../test-data
    @classmethod
    def from_path(cls, path: str, base_dir: str):
        name = path[path.rindex(os.sep)+1:]
        coordinates = path[len(base_dir) + 1:].split(os.sep)  # e.g. ['omh', 'blood-pressure', '2.0', 'shouldPass']

        try:
            schema_version = SchemaVersion.from_string(coordinates[2])
        except Exception:
            print("Error: An exception occurred while extracting a schema version from path '{}'.".format(path), file=sys.stderr)
            traceback.print_exc()
            sys.exit(1)

        schema_id = SchemaId(coordinates[0], coordinates[1], schema_version)
        should_pass = coordinates[3] == "shouldPass"

        file = open(path, "r")
        data = json.load(file)
        file.close()

        return cls(name, schema_id, data, should_pass, path)

    def __str__(self):
        return "DataFile[name={0}, schema_id={1}, should_pass={2}]".format(self.name, self.schema_id, self.should_pass)
