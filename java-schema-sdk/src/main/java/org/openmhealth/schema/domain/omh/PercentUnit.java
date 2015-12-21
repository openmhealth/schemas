package org.openmhealth.schema.domain.omh;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;


/**
 * @author Chris Schaefbauer
 */
public enum PercentUnit implements Unit {

    PERCENT("%");

    private final String schemaValue;
    private static final Map<String, PercentUnit> constantsBySchemaValue = new HashMap<>();

    static {
        for (PercentUnit constant : values()) {
            constantsBySchemaValue.put(constant.getSchemaValue(), constant);
        }
    }

    PercentUnit(String schemaValue) {
        this.schemaValue = schemaValue;
    }

    @Override
    @JsonValue
    public String getSchemaValue() {
        return schemaValue;
    }

    @Nullable
    @JsonCreator
    public static PercentUnit findBySchemaValue(String schemaValue) {
        return constantsBySchemaValue.get(schemaValue);
    }
}
