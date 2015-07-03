package io.github.scarletsky.bangumi.events;

import java.util.List;

import io.github.scarletsky.bangumi.api.models.Calendar;

/**
 * Created by scarlex on 15-7-3.
 */
public class LoadCalendarEvent {

    private List<Calendar> calendars;

    public LoadCalendarEvent(List<Calendar> calendars) {
        this.calendars = calendars;
    }

    public List<Calendar> getCalendars() {
        return calendars;
    }
}
