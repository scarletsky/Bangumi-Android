package io.github.scarletsky.bangumi.events;

/**
 * Created by scarlex on 15-7-10.
 */
public class UpdatedEpEvent {

    private int watchStatusId;
    private int position;

    public UpdatedEpEvent(int watchStatusId, int position) {
        this.watchStatusId = watchStatusId;
        this.position = position;
    }

    public int getWatchStatusId() {
        return watchStatusId;
    }

    public int getPosition() {
        return position;
    }
}
