package io.github.scarletsky.bangumi.events;

import io.github.scarletsky.bangumi.api.models.Collection;

/**
 * Created by scarlex on 15-7-15.
 */
public class UpdateSubjectGradeEvent {
    private Collection collection;

    public UpdateSubjectGradeEvent(Collection collection) {
        this.collection = collection;
    }

    public Collection getCollection() {
        return collection;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }
}
