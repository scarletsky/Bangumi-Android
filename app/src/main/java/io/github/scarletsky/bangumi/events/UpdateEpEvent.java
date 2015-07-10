package io.github.scarletsky.bangumi.events;

import io.github.scarletsky.bangumi.api.models.Ep;

/**
 * Created by scarlex on 15-7-10.
 */
public class UpdateEpEvent {
    private int epId;
    private Ep.WatchStatus watchStatus;
    private int position;

    public UpdateEpEvent(int epId, Ep.WatchStatus watchStatus, int position) {
        this.epId = epId;
        this.watchStatus = watchStatus;
        this.position = position;
    }

    public int getEpId() {
        return epId;
    }

    public Ep.WatchStatus getStatus() {
        return watchStatus;
    }

    public int getPosition() {
        return position;
    }
}
