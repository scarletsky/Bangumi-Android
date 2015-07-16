package io.github.scarletsky.bangumi.api.models;

/**
 * Created by scarlex on 15-7-6.
 */
public class Collection {
    private Status status = new Status();
    private long lasttouch = 0;
    private int rating = 0;
    private String comment = "";
    private int ep_status = 0;

    public enum WatchStatus {
        WISH(1, "wish", "想看"),
        WATCHED(2, "collect", "看过"),
        WATCHING(3, "do", "在看"),
        ON_HOLD(4, "on_hold", "搁置"),
        DROP(5, "drop", "弃番");

        private int id;
        private String type;
        private String name;

        WatchStatus(int id, String type, String name) {
            this.id = id;
            this.type = type;
            this.name = name;
        }

        public static WatchStatus getById(int id) {
            return WatchStatus.values()[id - 1];
        }

        public int getId() {
            return id;
        }

        public String getType() {
            return type;
        }

        public String getName() {
            return name;
        }
    }

    public class Status {
        private int id = 0;
        private String type;
        private String name;

        public int getId() {
            return id;
        }

        public String getType() {
            return type;
        }

        public String getName() {
            return name;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public Status getStatus() {
        return status;
    }

    public long getLasttouch() {
        return lasttouch;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public String getCommentDetail() {
        if (comment.equals("")) {
            return "暂无吐槽";
        } else {
            return comment;
        }
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getEpStatus() {
        return ep_status;
    }
}
