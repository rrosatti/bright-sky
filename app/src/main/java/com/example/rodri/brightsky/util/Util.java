package com.example.rodri.brightsky.util;

import android.app.Activity;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by rodri on 4/30/2016.
 */
public class Util {

    public static void setFullScreen(Activity activity) {
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

}
