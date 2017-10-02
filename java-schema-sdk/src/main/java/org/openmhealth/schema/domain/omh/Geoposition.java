/*
 * Copyright 2015 Open mHealth
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openmhealth.schema.domain.omh;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.openmhealth.schema.serializer.SerializationConstructor;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static org.openmhealth.schema.domain.omh.LengthUnit.FOOT;
import static org.openmhealth.schema.domain.omh.LengthUnit.METER;
import static org.openmhealth.schema.domain.omh.SignalToNoiseRatioUnit.DECIBEL;


/**
 * A geographic position measurement.
 *
 * @author Emerson Farrugia
 * @version 1.0
 * @see <a href="http://www.openmhealth.org/documentation/#/schema-docs/schema-library/schemas/omh_geoposition">geoposition</a>
 */
@JsonInclude(NON_NULL)
@JsonNaming(SnakeCaseStrategy.class)
public class Geoposition extends Measure {

    public static final SchemaId SCHEMA_ID = new SchemaId(OMH_NAMESPACE, "geoposition", "1.0");

    private PlaneAngleUnitValue latitude;
    private PlaneAngleUnitValue longitude;
    // TODO add accuracy
    private LengthUnitValue elevation;
    private Integer numberOfSatellitesInView;
    private Integer numberOfSatellitesInFix;
    private List<TypedUnitValue<SignalToNoiseRatioUnit>> satelliteSignalStrengths;
    private PositioningSystem positioningSystem;


    @SerializationConstructor
    protected Geoposition() {
    }

    public static class Builder extends Measure.DateTimeEffectiveTimeFrameBuilder<Geoposition, Builder> {

        private PlaneAngleUnitValue latitude;
        private PlaneAngleUnitValue longitude;
        private LengthUnitValue elevation;
        private Integer numberOfSatellitesInView;
        private Integer numberOfSatellitesInFix;
        private List<TypedUnitValue<SignalToNoiseRatioUnit>> satelliteSignalStrengths = new ArrayList<>();
        private PositioningSystem positioningSystem;

        public Builder(PlaneAngleUnitValue latitude, PlaneAngleUnitValue longitude, TimeFrame effectiveTimeFrame) {

            super(effectiveTimeFrame);

            checkNotNull(latitude, "A latitude hasn't been specified.");
            checkNotNull(longitude, "A longitude hasn't been specified.");

            this.latitude = latitude;
            this.longitude = longitude;
        }

        public Builder(PlaneAngleUnitValue latitude, PlaneAngleUnitValue longitude, OffsetDateTime effectiveDateTime) {
            this(latitude, longitude, new TimeFrame(effectiveDateTime));
        }

        public Builder setElevation(LengthUnitValue elevation) {

            checkArgument(
                    elevation.getTypedUnit() == METER || elevation.getTypedUnit() == FOOT,
                    "The specified elevation has the unsupported unit {}.", elevation.getTypedUnit());

            this.elevation = elevation;
            return this;
        }

        public Builder setNumberOfSatellitesInView(Integer numberOfSatellitesInView) {

            checkArgument(numberOfSatellitesInView >= 0, "An invalid number of satellites in view has been specified.");

            this.numberOfSatellitesInView = numberOfSatellitesInView;
            return this;
        }

        public Builder setNumberOfSatellitesInFix(Integer numberOfSatellitesInFix) {

            checkArgument(numberOfSatellitesInFix >= 0, "An invalid number of satellites in fix has been specified.");

            this.numberOfSatellitesInFix = numberOfSatellitesInFix;
            return this;
        }

        public Builder setSatelliteSignalStrengths(
                List<TypedUnitValue<SignalToNoiseRatioUnit>> satelliteSignalStrengths) {

            this.satelliteSignalStrengths = satelliteSignalStrengths;
            return this;
        }

        public Builder addSatelliteSignalStrengthInDb(Integer value) {

            if (value != null) {
                this.satelliteSignalStrengths.add(new TypedUnitValue<>(DECIBEL, value));
            }

            return this;
        }

        public Builder setPositioningSystem(PositioningSystem positioningSystem) {
            this.positioningSystem = positioningSystem;
            return this;
        }

        @Override
        public Geoposition build() {
            return new Geoposition(this);
        }
    }

    private Geoposition(Builder builder) {

        super(builder);

        this.latitude = builder.latitude;
        this.longitude = builder.longitude;
        this.elevation = builder.elevation;
        this.numberOfSatellitesInView = builder.numberOfSatellitesInView;
        this.numberOfSatellitesInFix = builder.numberOfSatellitesInFix;
        this.satelliteSignalStrengths = builder.satelliteSignalStrengths;
        this.positioningSystem = builder.positioningSystem;
    }

    public PlaneAngleUnitValue getLatitude() {
        return latitude;
    }

    public PlaneAngleUnitValue getLongitude() {
        return longitude;
    }

    public LengthUnitValue getElevation() {
        return elevation;
    }

    public Integer getNumberOfSatellitesInView() {
        return numberOfSatellitesInView;
    }

    public Integer getNumberOfSatellitesInFix() {
        return numberOfSatellitesInFix;
    }

    public List<TypedUnitValue<SignalToNoiseRatioUnit>> getSatelliteSignalStrengths() {
        return satelliteSignalStrengths;
    }

    public PositioningSystem getPositioningSystem() {
        return positioningSystem;
    }

    @Override
    public SchemaId getSchemaId() {
        return SCHEMA_ID;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        if (!super.equals(o)) {
            return false;
        }

        Geoposition that = (Geoposition) o;

        return Objects.equals(latitude, that.latitude) &&
                Objects.equals(longitude, that.longitude) &&
                Objects.equals(elevation, that.elevation) &&
                Objects.equals(numberOfSatellitesInView, that.numberOfSatellitesInView) &&
                Objects.equals(numberOfSatellitesInFix, that.numberOfSatellitesInFix) &&
                Objects.equals(satelliteSignalStrengths, that.satelliteSignalStrengths) &&
                positioningSystem == that.positioningSystem;
    }

    @Override
    public int hashCode() {
        return Objects
                .hash(super.hashCode(), latitude, longitude, elevation, numberOfSatellitesInView,
                        numberOfSatellitesInFix, satelliteSignalStrengths, positioningSystem);
    }
}
