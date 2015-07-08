package io.github.scarletsky.bangumi.events;

/**
 * Created by scarlex on 15-7-7.
 */
public class SessionChangeEvent {

    private boolean isLogin;

    public SessionChangeEvent(boolean isLogin) {
        this.isLogin = isLogin;
    }

    public boolean isLogin() {
        return isLogin;
    }
}
