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

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static java.time.Month.NOVEMBER;
import static java.time.Month.OCTOBER;
import static org.openmhealth.schema.domain.omh.TimeInterval.ofStartDateTimeAndEndDateTime;


/**
 * A factory of time frames used to simplify tests and avoid repetition.
 *
 * @author Emerson Farrugia
 */
public class TimeFrameFactory {

    public static final ZoneId PT = ZoneId.of("America/Los_Angeles");

    public static final TimeFrame FIXED_MONTH =
            new TimeFrame(ofStartDateTimeAndEndDateTime(
                    LocalDate.of(2015, OCTOBER, 1).atStartOfDay(PT).toOffsetDateTime(),
                    LocalDate.of(2015, NOVEMBER, 1).atStartOfDay(PT).toOffsetDateTime()
            ));

    public static final TimeFrame FIXED_DAY =
            new TimeFrame(ofStartDateTimeAndEndDateTime(
                    LocalDate.of(2015, OCTOBER, 21).atStartOfDay(PT).toOffsetDateTime(),
                    LocalDate.of(2015, OCTOBER, 22).atStartOfDay(PT).toOffsetDateTime()
            ));

    public static final TimeFrame FIXED_NIGHT_TIME_HOURS =
            new TimeFrame(ofStartDateTimeAndEndDateTime(
                    LocalDate.of(2015, OCTOBER, 21).atStartOfDay(PT).minusHours(1).toOffsetDateTime(),
                    LocalDate.of(2015, OCTOBER, 21).atStartOfDay(PT).plusHours(7).toOffsetDateTime()
            ));

    // which isn't arbitrary at all, of course, but we'll limit the Back to the Future references to this comment
    public static final TimeFrame FIXED_POINT_IN_TIME =
            new TimeFrame(ZonedDateTime.of(2015, OCTOBER.getValue(), 21, 16, 29, 0, 0, PT).toOffsetDateTime());
}