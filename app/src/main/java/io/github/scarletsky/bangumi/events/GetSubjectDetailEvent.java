package io.github.scarletsky.bangumi.events;

import io.github.scarletsky.bangumi.api.models.Subject;

/**
 * Created by scarlex on 15-7-6.
 */
public class GetSubjectDetailEvent {
    private Subject subject;

    public GetSubjectDetailEvent(Subject subject) {
        this.subject = subject;
    }

    public Subject getSubject() {
        return subject;
    }
}
