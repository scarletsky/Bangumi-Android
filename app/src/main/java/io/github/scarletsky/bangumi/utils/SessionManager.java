package io.github.scarletsky.bangumi.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.squareup.otto.Bus;

import io.github.scarletsky.bangumi.api.models.User;
import io.github.scarletsky.bangumi.events.SessionChangeEvent;

/**
 * Created by scarlex on 15-7-7.
 */
public class SessionManager {

    private static final String TAG = SessionManager.class.getSimpleName();
    private static final String PREF_NAME = "BangumiPref";
    private static final String KEY_IS_LOGIN = "isLogin";
    private static final String KEY_AUTH = "auth";
    private static final String KEY_AUTH_ENCODE = "authEncode";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_USER_NICKNAME = "userNickname";
    private static final String KEY_USER_AVATAR = "userAvatar";

    private SharedPreferences pref;

    public SessionManager(Context ctx) {
        this.pref = ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public boolean isLogin() {
        return pref.getBoolean(KEY_IS_LOGIN, false);
    }

    public void setIsLogin(boolean isLogin) {
        pref.edit().putBoolean(KEY_IS_LOGIN, isLogin).apply();
    }

    public String getAuth() {
        return pref.getString(KEY_AUTH, "");
    }

    public void setAuth(String auth) {
        pref.edit().putString(KEY_AUTH, auth).apply();
    }

    public String getAuthEncode() {
        return pref.getString(KEY_AUTH_ENCODE, "");
    }

    public void setAuthEncode(String authEncode) {
        pref.edit().putString(KEY_AUTH_ENCODE, authEncode).apply();
    }

    public int getUserId() {
        return pref.getInt(KEY_USER_ID, 0);
    }

    public void setUserId(int id) {
        pref.edit().putInt(KEY_USER_ID, id).apply();
    }

    public String getUserNickname() {
        return pref.getString(KEY_USER_NICKNAME, "");
    }

    public void setUserNickname(String nickname) {
        pref.edit().putString(KEY_USER_NICKNAME, nickname).apply();
    }

    public String getUserAvatar() {
        return pref.getString(KEY_USER_AVATAR, "");
    }

    public void setUserAvatar(String avatar) {
        pref.edit().putString(KEY_USER_AVATAR, avatar).apply();
    }

    public User getUser() {
        return isLogin() ? new User(getUserId(), getUserNickname(), getUserAvatar()) : null;
    }

    public void logout() {
        pref.edit()
                .putBoolean(KEY_IS_LOGIN, false)
                .remove(KEY_AUTH)
                .remove(KEY_AUTH_ENCODE)
                .remove(KEY_USER_ID)
                .remove(KEY_USER_NICKNAME)
                .remove(KEY_USER_AVATAR)
                .apply();
        BusProvider.getInstance().post(new SessionChangeEvent(false));
    }

}
