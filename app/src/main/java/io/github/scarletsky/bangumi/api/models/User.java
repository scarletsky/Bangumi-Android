package io.github.scarletsky.bangumi.api.models;

/**
 * Created by scarlex on 15-7-6.
 */
public class User {
    private int id;
    private String url;
    private String username;
    private String nickname;
    private Image avatar;
    private String sign;
    private String auth;
    private String auth_encode;

    public User() {

    }

    public User(int id, String nickname, String avatarLarge) {
        this.id = id;
        this.nickname = nickname;

        Image avatar = new Image();
        avatar.setLarge(avatarLarge);
        this.avatar = avatar;
    }

    public int getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getNickname() {
        return nickname;
    }

    public Image getAvatar() {
        return avatar;
    }

    public String getSign() {
        return sign;
    }

    public String getAuth() {
        return auth;
    }

    public String getAuthEncode() {
        return auth_encode;
    }
}
