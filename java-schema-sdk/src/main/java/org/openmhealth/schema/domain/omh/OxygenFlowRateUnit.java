package org.openmhealth.schema.domain.omh;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;


/**
 * @author Chris Schaefbauer
 */
public enum OxygenFlowRateUnit implements Unit {

    LITERS_PER_MINUTE("L/min");

    private final String schemaValue;

    private static final Map<String, OxygenFlowRateUnit> constantsBySchemaValue = new HashMap<>();

    static {
        for (OxygenFlowRateUnit constant : values()) {
            constantsBySchemaValue.put(constant.getSchemaValue(), constant);
        }
    }

    OxygenFlowRateUnit(String schemaValue) {
        this.schemaValue = schemaValue;
    }

    @Override
    @JsonValue
    public String getSchemaValue() {
        return schemaValue;
    }

    @Nullable
    @JsonCreator
    public static OxygenFlowRateUnit findBySchemaValue(String schemaValue) {
        return constantsBySchemaValue.get(schemaValue);
    }
}
