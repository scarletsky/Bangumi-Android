package io.github.scarletsky.bangumi.utils;

import com.squareup.otto.Bus;

/**
 * Created by scarlex on 15-7-3.
 */
public class BusProvider {
    private static Bus mBus;

    public static Bus getInstance() {
        if (mBus == null) {
            mBus = new Bus();
        }

        return mBus;
    }
}
