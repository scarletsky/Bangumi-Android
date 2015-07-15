package io.github.scarletsky.bangumi.events;

/**
 * Created by scarlex on 15-7-15.
 */
public class EditSubjectGradeEvent {
    private boolean isFinish = false;

    public boolean isFinish() {
        return isFinish;
    }

    public void setIsFinish(boolean isFinish) {
        this.isFinish = isFinish;
    }
}
