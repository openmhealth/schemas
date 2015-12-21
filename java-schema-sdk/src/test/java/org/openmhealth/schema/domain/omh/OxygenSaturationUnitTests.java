package org.openmhealth.schema.domain.omh;

import org.testng.annotations.Test;

import java.time.OffsetDateTime;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.openmhealth.schema.domain.omh.DescriptiveStatistic.MAXIMUM;
import static org.openmhealth.schema.domain.omh.OxygenFlowRateUnit.LITERS_PER_MINUTE;
import static org.openmhealth.schema.domain.omh.OxygenSaturation.SupplementalOxygenAdministrationMode.NASAL_CANNULA;
import static org.openmhealth.schema.domain.omh.OxygenSaturation.MeasurementMethod.*;
import static org.openmhealth.schema.domain.omh.OxygenSaturation.MeasurementSystem.PERIPHERAL_CAPILLARY;
import static org.openmhealth.schema.domain.omh.PercentUnit.PERCENT;


/**
 * @author Chris Schaefbauer
 */
public class OxygenSaturationUnitTests extends SerializationUnitTests {

    public static final String SCHEMA_FILENAME = "schema/omh/oxygen-saturation-1.0.json";

    @Override
    public String getSchemaFilename() {
        return SCHEMA_FILENAME;
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void constructorShouldThrowExceptionOnNullOxygenSaturationValue() {

        new OxygenSaturation.Builder(null);
    }

    @Test
    public void buildShouldConstructMeasureUsingOnlyRequiredProperties() {

        OxygenSaturation oxygenSaturation = new OxygenSaturation.Builder(new TypedUnitValue<>(PERCENT, 92)).build();

        assertThat(oxygenSaturation, notNullValue());
        assertThat(oxygenSaturation.getOxygenSaturation(), equalTo(new TypedUnitValue<>(PERCENT, 92)));

        assertThat(oxygenSaturation.getEffectiveTimeFrame(), nullValue());
        assertThat(oxygenSaturation.getUserNotes(), nullValue());
        assertThat(oxygenSaturation.getDescriptiveStatistic(), nullValue());
        assertThat(oxygenSaturation.getSupplementalOxygenFlowRate(), nullValue());
        assertThat(oxygenSaturation.getSystem(), nullValue());
        assertThat(oxygenSaturation.getOxygenTherapyModeOfAdministration(), nullValue());
        assertThat(oxygenSaturation.getMeasurementMethod(), nullValue());
    }

    @Test
    public void buildShouldConstructMeasureUsingOptionalProperties() {

        OxygenSaturation oxygenSaturation = new OxygenSaturation.Builder(new TypedUnitValue<>(PERCENT, 93.5))
                .setEffectiveTimeFrame(OffsetDateTime.parse("2015-04-01T12:34:01+04:00"))
                .setUserNotes("a note about oxygen sat")
                .setDescriptiveStatistic(MAXIMUM)
                .setSystem(PERIPHERAL_CAPILLARY)
                .setSupplementalOxygenFlowRate(new TypedUnitValue<>(LITERS_PER_MINUTE, 3.2))
                .setOxygenTherapyModeOfAdministration(NASAL_CANNULA)
                .setMeasurementMethod(PULSE_OXIMETRY)
                .build();

        assertThat(oxygenSaturation, notNullValue());
        assertThat(oxygenSaturation.getOxygenSaturation(), equalTo(new TypedUnitValue<>(PERCENT, 93.5)));
        assertThat(oxygenSaturation.getUserNotes(), equalTo("a note about oxygen sat"));
        assertThat(oxygenSaturation.getEffectiveTimeFrame(),
                equalTo(new TimeFrame(OffsetDateTime.parse("2015-04-01T12:34:01+04:00"))));
        assertThat(oxygenSaturation.getDescriptiveStatistic(), equalTo(MAXIMUM));
        assertThat(oxygenSaturation.getSystem(), equalTo(PERIPHERAL_CAPILLARY));
        assertThat(oxygenSaturation.getSupplementalOxygenFlowRate(),
                equalTo(new TypedUnitValue<>(LITERS_PER_MINUTE, 3.2)));
        assertThat(oxygenSaturation.getOxygenTherapyModeOfAdministration(), equalTo(NASAL_CANNULA));
        assertThat(oxygenSaturation.getMeasurementMethod(), equalTo(PULSE_OXIMETRY));
    }

    @Test
    public void measureShouldSerializeCorrectly() throws Exception {

        OxygenSaturation oxygenSaturation = new OxygenSaturation.Builder(new TypedUnitValue<>(PERCENT, 96.5))
                .setEffectiveTimeFrame(OffsetDateTime.parse("2013-02-05T07:25:00Z"))
                .setOxygenTherapyModeOfAdministration(NASAL_CANNULA)
                .setSupplementalOxygenFlowRate(new TypedUnitValue<>(LITERS_PER_MINUTE, 2.5))
                .setMeasurementMethod(PULSE_OXIMETRY)
                .setSystem(PERIPHERAL_CAPILLARY)
                .build();

        String expectedDocument = "{\n" +
                "    \"oxygen_saturation\": {\n" +
                "        \"value\": 96.5,\n" +
                "        \"unit\": \"%\"\n" +
                "    },\n" +
                "    \"effective_time_frame\": {\n" +
                "        \"date_time\": \"2013-02-05T07:25:00Z\"\n" +
                "    },\n" +
                "    \"supplemental_oxygen_flow_rate\": {\n" +
                "        \"value\": 2.5,\n" +
                "        \"unit\": \"L/min\"\n" +
                "    },\n" +
                "    \"oxygen_therapy_mode_of_administration\": \"nasal cannula\",\n" +
                "    \"system\": \"peripheral capillary\",\n" +
                "    \"measurement_method\": \"pulse oximetry\"\n" +
                "}\n";

        serializationShouldCreateValidDocument(oxygenSaturation, expectedDocument);
        deserializationShouldCreateValidObject(expectedDocument, oxygenSaturation);
    }

    @Test
    public void equalsShouldReturnCorrectComparison() {

        OxygenSaturation oxygenSaturation = new OxygenSaturation.Builder(new TypedUnitValue<>(PERCENT, 96.5))
                .setEffectiveTimeFrame(OffsetDateTime.parse("2013-02-05T07:25:00Z"))
                .setOxygenTherapyModeOfAdministration(NASAL_CANNULA)
                .setSupplementalOxygenFlowRate(new TypedUnitValue<>(LITERS_PER_MINUTE, 2.5))
                .setMeasurementMethod(PULSE_OXIMETRY)
                .setSystem(PERIPHERAL_CAPILLARY)
                .build();

        OxygenSaturation sameOxygenSaturation = new OxygenSaturation.Builder(new TypedUnitValue<>(PERCENT, 96.5))
                .setEffectiveTimeFrame(OffsetDateTime.parse("2013-02-05T07:25:00Z"))
                .setOxygenTherapyModeOfAdministration(NASAL_CANNULA)
                .setSupplementalOxygenFlowRate(new TypedUnitValue<>(LITERS_PER_MINUTE, 2.5))
                .setMeasurementMethod(PULSE_OXIMETRY)
                .setSystem(PERIPHERAL_CAPILLARY)
                .build();

        assertThat(oxygenSaturation.equals(sameOxygenSaturation), is(true));

        OxygenSaturation withDifferentValue = new OxygenSaturation.Builder(new TypedUnitValue<>(PERCENT, 90))
                .setEffectiveTimeFrame(OffsetDateTime.parse("2013-02-05T07:25:00Z"))
                .setOxygenTherapyModeOfAdministration(NASAL_CANNULA)
                .setSupplementalOxygenFlowRate(new TypedUnitValue<>(LITERS_PER_MINUTE, 2.5))
                .setMeasurementMethod(PULSE_OXIMETRY)
                .setSystem(PERIPHERAL_CAPILLARY)
                .build();

        assertThat(oxygenSaturation.equals(withDifferentValue), is(false));

        OxygenSaturation withSameValueDifferentOptionalParameters =
                new OxygenSaturation.Builder(new TypedUnitValue<>(PERCENT, 96.5))
                        .setEffectiveTimeFrame(OffsetDateTime.parse("2013-02-05T07:25:00Z"))
                        .build();

        assertThat(oxygenSaturation.equals(withSameValueDifferentOptionalParameters), is(false));

    }

}
