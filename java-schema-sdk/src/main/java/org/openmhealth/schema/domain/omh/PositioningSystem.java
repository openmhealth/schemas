package org.openmhealth.schema.domain.omh;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;


/**
 * A system used to acquire positions.
 *
 * @author Emerson Farrugia
 * @version 1.0
 * @see <a href="http://www.openmhealth.org/documentation/#/schema-docs/schema-library/schemas/omh_geoposition">geoposition</a>
 */
enum PositioningSystem implements SchemaEnumValue {

    GPS,
    GLONASS,
    GALILEO("Galileo"),
    BEIDOU("Beidou"),
    COMPASS,
    IRNSS,
    QZSS;

    private String schemaValue;
    private static final Map<String, PositioningSystem> constantsBySchemaValue = new HashMap<>();

    static {
        for (PositioningSystem constant : values()) {
            constantsBySchemaValue.put(constant.getSchemaValue(), constant);
        }
    }

    PositioningSystem() {
        this.schemaValue = this.name();
    }

    PositioningSystem(String schemaValue) {
        this.schemaValue = schemaValue;
    }

    @Override
    @JsonValue
    public String getSchemaValue() {
        return this.schemaValue;
    }

    @Nullable
    @JsonCreator
    public static PositioningSystem findBySchemaValue(String schemaValue) {
        return constantsBySchemaValue.get(schemaValue);
    }
}
