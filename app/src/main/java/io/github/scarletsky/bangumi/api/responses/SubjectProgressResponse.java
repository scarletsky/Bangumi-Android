package io.github.scarletsky.bangumi.api.responses;

import java.util.List;

/**
 * Created by scarlex on 15-7-10.
 */
public class SubjectProgressResponse {
    private int subject_id;
    private List<Ep> eps;

    public class Ep {
        private int id;
        private Status status;

        public class Status {
            private int id;

            public int getId() {
                return id;
            }
        }

        public int getEpId() {
            return id;
        }

        public Status getStatus() {
            return status;
        }
    }

    public int getSubjectId() {
        return subject_id;
    }

    public List<Ep> getEps() {
        return eps;
    }
}
