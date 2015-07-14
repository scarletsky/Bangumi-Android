package io.github.scarletsky.bangumi.events;

import io.github.scarletsky.bangumi.api.models.Collection;

/**
 * Created by scarlex on 15-7-14.
 */
public class GetCollectionEvent {
    private Collection collection;

    public GetCollectionEvent(Collection collection) {
        this.collection = collection;
    }

    public Collection getCollection() {
        return collection;
    }
}
