package io.github.scarletsky.bangumi.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by scarlex on 15-7-6.
 */
public class ToastManager {

    private static Toast mToast;

    public static void show(Context ctx, String text) {

        if (mToast != null) {
            mToast.cancel();
        }

        mToast = Toast.makeText(ctx, text, Toast.LENGTH_SHORT);
        mToast.show();
    }

}
