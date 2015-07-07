package io.github.scarletsky.bangumi;

import android.app.Application;

import io.github.scarletsky.bangumi.utils.SessionManager;

/**
 * Created by scarlex on 15-7-7.
 */
public class BangumiApplication extends Application {

    private static BangumiApplication mInstance;
    private SessionManager session;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized BangumiApplication getInstance() {
        return mInstance;
    }

    public SessionManager getSession() {
        if (session == null) {
            session = new SessionManager(getApplicationContext());
        }

        return session;
    }

}
