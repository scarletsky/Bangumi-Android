package io.github.scarletsky.bangumi.api.models;

import io.github.scarletsky.bangumi.api.models.Subject;

/**
 * Created by scarlex on 15-7-8.
 */
public class UserCollection {
    private String name;
    private int ep_status;
    private long lasttouch;
    private Subject subject;

    public String getName() {
        return name;
    }

    public int getEpStatus() {
        return ep_status;
    }

    public long getLasttouch() {
        return lasttouch;
    }

    public Subject getSubject() {
        return subject;
    }
}
