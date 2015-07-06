package io.github.scarletsky.bangumi.events;

import io.github.scarletsky.bangumi.api.models.Subject;

/**
 * Created by scarlex on 15-7-4.
 */
public class GetSubjectEvent {

    private Subject subject;

    public GetSubjectEvent(Subject subject) {
        this.subject = subject;
    }

    public Subject getSubject() {
        return subject;
    }
}
