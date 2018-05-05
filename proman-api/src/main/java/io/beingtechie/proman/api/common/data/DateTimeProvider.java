/*
 * Copyright 2018-2019, https://beingtechie.io.
 *
 * File: DateTimeProvider.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
 */
package io.beingtechie.proman.api.common.data;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;

/**
 * Provider for date time objects.
 */
public final class DateTimeProvider {

    private static final DateTimeProvider INSTANCE = new DateTimeProvider();

    private DateTimeProvider() {
        // prohibit instantiation
    }

    public static DateTimeProvider getInstance() {
        return INSTANCE;
    }

    public ZonedDateTime currentSystemTime() {
        return ZonedDateTime.now();
    }

    public ZonedDateTime currentProgramTime() {
        return ZonedDateTime.now();
    }

    public ZonedDateTime fromTime(final long timeInMilliSecs) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMilliSecs);
        return ZonedDateTime.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), //
                calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND), 0, ZoneId.of("America/Indiana/Indianapolis"));
    }

}