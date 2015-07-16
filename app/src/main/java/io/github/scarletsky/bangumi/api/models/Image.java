package io.github.scarletsky.bangumi.api.models;

/**
 * Created by scarlex on 15-7-6.
 */
public class Image {
    private String large;
    private String common;
    private String medium;
    private String small;
    private String grid;

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public String getCommon() {
        return common;
    }

    public String getMedium() {
        return medium;
    }

    public String getSmall() {
        return small;
    }

    public String getGrid() {
        return grid;
    }
}
