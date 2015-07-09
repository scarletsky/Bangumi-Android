package io.github.scarletsky.bangumi.events;

import java.util.List;

import io.github.scarletsky.bangumi.api.models.Ep;

/**
 * Created by scarlex on 15-7-9.
 */
public class GetSubjectEpsEvent {

    private List<Ep> eps;

    public GetSubjectEpsEvent(List<Ep> eps) {
        this.eps = eps;
    }

    public List<Ep> getEps() {
        return eps;
    }
}
