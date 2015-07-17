package io.github.scarletsky.bangumi.api.models;

/**
 * Created by scarlex on 15-7-9.
 */
public class Ep {

    private int id;
    private String url;
    private int type;
    private double sort;
    private String name;
    private String name_cn;
    private String duration;
    private String airdate;
    private int comment;
    private String desc;
    private String status;
    private int watch_status = 0;

    public enum Status {
        AIR,
        NA,
        TODAY
    }

    public enum WatchStatus {
        INIT(0, "init"),
        WISH(1, "queue"),
        WATCHED(2, "watched"),
        DROP(3, "drop"),
        UNDO(9, "remove");

        private int watchStatusId;
        private String str;

        WatchStatus(int watchStatusId, String str) {
            this.watchStatusId = watchStatusId;
            this.str = str;
        }

        public int getWatchStatusId() {
            return watchStatusId;
        }

        public String getStr() {
            return str;
        }
    }

    public int getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public int getType() {
        return type;
    }

    public double getSort() {
        return sort;
    }

    public String getName() {
        return name;
    }

    public String getNameCn() {
        return name_cn;
    }

    public String getTitle() {
        return name_cn.equals("") ? name : name_cn;
    }

    public String getDuration() {
        return duration;
    }

    public String getAirdate() {
        return airdate;
    }

    public int getComment() {
        return comment;
    }

    public String getDesc() {
        return desc;
    }

    public Status getStatus() {

        switch (status) {
            case "NA":
                return Status.NA;
            case "Today":
                return Status.TODAY;
            default:
                return Status.AIR;
        }
    }

    public WatchStatus getWatchStatus() {

        switch (watch_status) {
            case 1:
                return WatchStatus.WISH;
            case 2:
                return WatchStatus.WATCHED;
            case 3:
                return WatchStatus.DROP;
            default:
                return WatchStatus.INIT;

        }

    }

    public void setWatchStatus(int watch_status) {
        this.watch_status = watch_status;
    }
}
