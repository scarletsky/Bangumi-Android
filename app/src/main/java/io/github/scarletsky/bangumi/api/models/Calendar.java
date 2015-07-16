package io.github.scarletsky.bangumi.api.models;

import java.util.List;

/**
 * Created by scarlex on 15-7-3.
 */
public class Calendar {
    private Weekday weekday;
    private List<Subject> items;

    public class Weekday {
        private String en;
        private String cn;
        private String ja;
        private int id;

        public String getEn() {
            return en;
        }

        public String getCn() {
            return cn;
        }

        public String getJa() {
            return ja;
        }

        public int getId() {
            return id;
        }
    }

    public Weekday getWeekday() {
        return weekday;
    }

    public List<Subject> getItems() {
        return items;
    }
}
