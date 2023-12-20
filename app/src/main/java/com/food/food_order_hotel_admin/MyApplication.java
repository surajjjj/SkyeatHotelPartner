//package com.food.food_order_hotel_admin;
//
//import android.app.Application;
//import android.content.Context;
//
//import com.google.firebase.FirebaseApp;
//import com.onesignal.OneSignal;
//
//
//public class MyApplication extends Application {
//    public static Context mContext;
//    private static final String ONESIGNAL_APP_ID = "f6317f50-c642-46f5-9a75-74bcfbcbac0b" +
//            "";
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        mContext=this;
//        FirebaseApp.initializeApp(this);
//
//
//        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);
//
//        // OneSignal Initialization
//        OneSignal.initWithContext(this);
//
//        OneSignal.unsubscribeWhenNotificationsAreDisabled(true);
//
//        OneSignal.setAppId(ONESIGNAL_APP_ID);
//
//        // promptForPushNotifications will show the native Android notification permission prompt.
//        // We recommend removing the following code and instead using an In-App Message to prompt for notification permission (See step 7)
//        OneSignal.promptForPushNotifications();
////         OneSignal Initialization
////        OneSignal.startInit(this)
////                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
////                .unsubscribeWhenNotificationsAreDisabled(true)
////                .init();
//
//
//    }
//    public static boolean isActivityVisible() {
//        return activityVisible;
//    }
//
//    public static void activityResumed() {
//        activityVisible = true;
//    }
//
//    public static void activityPaused() {
//        activityVisible = false;
//    }
//
//    private static boolean activityVisible;
//}