package io.github.scarletsky.bangumi.api.responses;

import java.util.List;

import io.github.scarletsky.bangumi.api.models.Subject;

/**
 * Created by scarlex on 15-7-12.
 */
public class SearchResponse {
    private int results;
    private List<Subject> list;

    public List<Subject> getList() {
        return list;
    }

    public int getResults() {
        return results;
    }
}
