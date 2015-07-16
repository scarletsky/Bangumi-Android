package io.github.scarletsky.bangumi.api.models;


import java.util.List;

/**
 * Created by scarlex on 15-7-9.
 */
public class SubjectEp {

    private int id;
    private String url;
    private int type;
    private String name;
    private String name_cn;
    private String summary;
    private List<Ep> eps;
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

    public String getName() {
        return name;
    }

    public String getNameCn() {
        return name_cn;
    }

    public String getSummary() {
        return summary;
    }

    public List<Ep> getEps() {
        return eps;
    }

    public String getAirDate() {
        return air_date;
    }

    public int getAirWeekday() {
        return air_weekday;
    }

    public Image getImages() {
        return images;
    }
}
