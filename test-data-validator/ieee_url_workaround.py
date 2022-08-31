# This workaround is necessary because IEEE schemas have $refs to schemas which don't resolve, because
# they're missing version numbers. Once those w3id.org rewrite rules are fixed, this can be removed.
from validator_types import SchemaId

ieee_schema_directories = {
    # environment
    "ambient-light": "environment",
    "ambient-sound": "environment",
    "ambient-temperature": "environment",

    # metadata
    "data-point": "metadata",
    "data-series": "metadata",
    "header": "metadata",
    "schema-id": "metadata",

    # physical activity
    "physical-activity": "physical_activity",

    # sleep
    "apnea-hypopnea-index": "sleep",
    "arousal-index": "sleep",
    "deep-sleep": "sleep",
    "light-sleep": "sleep",
    "sleep-episode": "sleep",
    "sleep-onset-latency": "sleep",
    "sleep-stage-summary": "sleep",
    "snore-index": "sleep",
    "time-in-bed": "sleep",
    "total-sleep-time": "sleep",
    "wake-after-sleep-onset": "sleep",

    # survey
    "survey-answer": "survey",
    "survey-categorical-answer": "survey",
    "survey-date-answer": "survey",
    "survey-item": "survey",
    "survey-question": "survey",
    "survey-time-answer": "survey",
    "survey-unit-value-answer": "survey",
    "survey": "survey",

    # utility
    "body-posture": "utility",
    "date-time": "utility",
    "descriptive-statistic-denominator": "utility",
    "descriptive-statistic": "utility",
    "duration-unit-value-range": "utility",
    "duration-unit-value": "utility",
    "frequency-unit-value": "utility",
    "illuminance-unit-value": "utility",
    "kcal-unit-value": "utility",
    "length-unit-value": "utility",
    "percent-unit-value": "utility",
    "sound-unit-value": "utility",
    "speed-unit-value": "utility",
    "temperature-unit-value": "utility",
    "time-frame": "utility",
    "time-interval": "utility",
    "unit-value-range": "utility",
    "unit-value": "utility",
}

ieee_schema_string_types = [
    "body-posture",
    "date-time",
    "descriptive-statistic",
    "descriptive-statistic-denominator"
]


def create_synthetic_ieee_schema(schema_id: SchemaId):
    return \
        {
            "$schema": "http://json-schema.org/draft-07/schema#",
            "$id": schema_id.base_uri,
            "type": "string" if schema_id.name in ieee_schema_string_types else "object",
            "allOf": [
                {
                    "$ref": "https://opensource.ieee.org/omh/1752/-/raw/main/schemas/{}/{}-1.0.json".format(ieee_schema_directories[schema_id.name], schema_id.name)
                }
            ]
        }
