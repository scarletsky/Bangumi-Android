package io.github.scarletsky.bangumi.utils;

import android.content.Context;
import android.content.SharedPreferences;

import io.github.scarletsky.bangumi.api.models.User;

/**
 * Created by scarlex on 15-7-7.
 */
public class SessionManager {

    private static final String TAG = SessionManager.class.getSimpleName();
    private static final String PREF_NAME = "BangumiPref";
    private static final String KEY_IS_LOGIN = "isLogin";
    private static final String KEY_AUTH = "auth";
    private static final String KEY_AUTH_ENCODE = "authEncode";

    private SharedPreferences pref;

    public SessionManager(Context ctx) {
        this.pref = ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public boolean getIsLogin() {
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

    public void logout() {
        pref.edit()
                .putBoolean(KEY_IS_LOGIN, false)
                .remove(KEY_AUTH)
                .remove(KEY_AUTH_ENCODE)
                .apply();
    }

}
