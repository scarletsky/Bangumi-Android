package io.github.scarletsky.bangumi.api.models;

/**
 * Created by scarlex on 15-7-3.
 */
public class Subject {
    private int id;
    private String url;
    private int type;
    private String name;
    private String name_cn;
    private String summary;
    private int eps;
    private String air_date;
    private int air_weekday;
    private Image images;

    public int getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public int getType() {
        return type;
    }

    public String getTypeDetail() {
        String type = "";

        switch (this.type) {
            case 1:
                type = "漫画/小说";
                break;
            case 2:
                type = "动画/二次元番";
                break;
            case 3:
                type = "音乐";
                break;
            case 4:
                type = "游戏";
                break;
            case 6:
                type = "三次元番";
                break;
        }

        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getNameCn() {
        return name_cn;
    }

    public String getSummary() {
        return summary.equals("") ? "无" : summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getEps() {
        return eps;
    }

    public void setEps(int eps) {
        this.eps = eps;
    }

    public String getAirDate() {
        return air_date;
    }

    public String getAirWeekday() {
        String weekday = "";
        switch (this.air_weekday) {
            case 1:
                weekday = "星期一";
                break;
            case 2:
                weekday = "星期二";
                break;
            case 3:
                weekday = "星期三";
                break;
            case 4:
                weekday = "星期四";
                break;
            case 5:
                weekday = "星期五";
                break;
            case 6:
                weekday = "星期六";
                break;
            case 7:
                weekday = "星期日";
                break;
        }

        return weekday;
    }

    public Image getImages() {
        return images;
    }

}
