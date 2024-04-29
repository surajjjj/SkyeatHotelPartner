package com.food.food_order_hotel_admin.Activity;

import android.app.Activity;
import android.os.Bundle;

public class MyLifecycleHandler {

    private int resumed;
    private int paused;
    private int started;
    private int stopped;


    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
    }


    public void onActivityDestroyed(Activity activity) {
    }

    public void onActivityResumed(Activity activity) {
        ++resumed;
    }


    public void onActivityPaused(Activity activity) {
        ++paused;
        android.util.Log.w("test", "application is in foreground: " + (resumed > paused));
    }


    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }

    public void onActivityStarted(Activity activity) {
        ++started;
    }

    public void onActivityStopped(Activity activity) {
        ++stopped;
        android.util.Log.w("test", "application is visible: " + (started > stopped));
    }

    // If you want a static function you can use to check if your application is
    // foreground/background, you can use the following:
    /*
    // Replace the four variables above with these four
    private static int resumed;
    private static int paused;
    private static int started;
    private static int stopped;

    // And these two public static functions
    public static boolean isApplicationVisible() {
        return started > stopped;
    }

    public static boolean isApplicationInForeground() {
        return resumed > paused;
    }
    */
}
