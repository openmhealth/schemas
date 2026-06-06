"""
pytest configuration for schema validation

Any .json file in the test-data directory produces a test

Paths must be in the form:

namespace / schema / version / shoud[Pass|Fail] / document.json

e.g.

test-data/omh/medication/1.0/shouldPass/valid-medication.json
"""

from __future__ import annotations

import json
from contextlib import nullcontext
from functools import lru_cache
from pathlib import Path
from subprocess import check_call
from typing import Any, Generator

import jsonschema
import pytest
from packaging.version import Version
from referencing import Registry, Resource
from referencing.exceptions import Unresolvable
from referencing.jsonschema import DRAFT202012

root = Path(__file__).parent.parent.resolve()
schema_base = root / "schema"
ieee_dir = root / "ieee"
ieee_schemas = ieee_dir / "schemas"

schema_namespace_uris = {
    "granola": "https://w3id.org/openmhealth/schemas/granola",
    "ieee": "https://w3id.org/ieee/ieee-1752-schema",
    "omh": "https://w3id.org/openmhealth/schemas/omh",
}


def load_ieee_schemas() -> None:
    """
    Download ieee schemas with git

    Cannot download with requests or resolve by URL
    because IEEE GitLab blocks requests.
    """
    ieee_url = "https://opensource.ieee.org/omh/1752"
    print(f"Downloading IEEE schemas from {ieee_url}")
    if ieee_dir.exists():
        check_call(["git", "pull"], cwd=ieee_dir)
    else:
        check_call(["git", "clone", ieee_url, ieee_dir.name], cwd=ieee_dir.parent)


@lru_cache
def load_registry() -> Registry:
    """Construct a Registry containing all schemas"""
    registry = Registry()
    load_ieee_schemas()

    ieee_ns_uri = schema_namespace_uris["ieee"]
    for schema_path in ieee_schemas.rglob("**/*.json"):
        schema = json.loads(schema_path.read_text())

        resource = Resource.from_contents(schema)
        registry = resource @ registry
        # also load at file-path (internal $id does not always include version, but omh references with version)
        # both version and no-version appear to resolve on w3id.org
        schema_id = f"{ieee_ns_uri}/{schema_path.name}"
        registry = registry.with_resource(schema_id, resource)

    ieee_records = len(registry)
    assert ieee_records > 0
    print(f"Loaded {ieee_records} IEEE records")
    # load our schemas
    for name in ["omh", "granola"]:
        before = len(registry)
        ns_uri = schema_namespace_uris[name]
        for schema_path in (schema_base / name).rglob("**/*.json"):
            schema = json.loads(schema_path.read_text())
            # omh schemas don't always have $id,
            # load at filename so e.g. symlink specs resolve
            spec = DRAFT202012.detect(schema)
            schema_id = f"{ns_uri}/{schema_path.name}"
            # reveal_type(Resource)
            resource = spec.create_resource(schema)
            registry = registry.with_resource(schema_id, resource)
        new_records = len(registry) - before
        print(f"Loaded {new_records} {name} records")

    # crawl loads all the references
    registry.crawl()
    return registry


@pytest.fixture(scope="session")
def registry() -> Registry:
    return load_registry()


def compatible_schemas(schema_id: str, registry: Registry) -> list[str]:
    """Locate all compatible schemas that we have

    Semantic version matches, so 1.0 yields 1.1, 1.2, 1.x, etc.
    as long as they all exist.
    """
    schema_ids = [schema_id]
    base, _, version_ext = schema_id.rpartition("-")
    version, _, ext = version_ext.rpartition(".")
    v = Version(version)
    major, minor = v.major, v.minor
    resolver = registry.resolver()
    # keep incrementing minor version as long as we have a schema
    # that matches
    while True:
        minor += 1
        schema_id = f"{base}-{major}.{minor}.{ext}"
        try:
            resolver.lookup(schema_id)
        except Unresolvable:
            # no match
            break
        else:
            schema_ids.append(schema_id)
    return schema_ids


class SchemaTest(pytest.Function):
    """
    pytest Function representing a single test from a JSON test file in test-data
    """

    def __init__(
        self,
        name: str,
        parent: Any,
        schema_id: str,
        document_path: Path,
        should_pass: bool,
    ) -> None:
        self.schema_id = schema_id
        self.document_path = document_path
        self.should_pass = should_pass
        super().__init__(name, parent, callobj=self.test_schema)

    def test_schema(self, registry: Registry) -> None:
        # read the document to test
        document = json.loads(self.document_path.read_text())
        context = (
            nullcontext()
            if self.should_pass
            else pytest.raises(jsonschema.ValidationError)
        )
        with context:
            jsonschema.validate(
                document,
                {"$ref": self.schema_id},
                registry=registry,
                format_checker=jsonschema.FormatChecker(),
            )


class SchemaTestFile(pytest.File):
    """
    Represents a file containing a test

    Generates a test for each existing semver-compatible schema,
    i.e. 1.1 tests 1.1, 1.2, etc.
    """

    def collect(self) -> Generator[SchemaTest]:
        # path: test-data/omh/blood-pressure/1.0/shouldPass/blood-pressure-only.json
        assert self.path.parent.name in {"shouldPass", "shouldFail"}
        should_pass = self.path.parent.name == "shouldPass"
        version = self.path.parents[1].name
        name = self.path.parents[2].name
        ns = self.path.parents[3].name
        ns_uri = schema_namespace_uris[ns]
        schema_id = f"{ns_uri}/{name}-{version}.json"
        registry = load_registry()
        if should_pass:
            # test all semver-compatible schemas
            schema_ids = compatible_schemas(schema_id, registry)
        else:
            # only test specified schema if it should fail
            schema_ids = [schema_id]

        for schema_id in schema_ids:
            yield SchemaTest.from_parent(
                self,
                name=schema_id.rpartition("/")[-1],
                document_path=self.path,
                schema_id=schema_id,
                should_pass=should_pass,
            )


def pytest_collect_file(parent: Any, file_path: Path) -> SchemaTestFile | None:
    """
    Find any .json file in the test-data directory and construct a test for it.
    """
    if file_path.suffix == ".json":
        return SchemaTestFile.from_parent(parent, path=file_path)
    return None
