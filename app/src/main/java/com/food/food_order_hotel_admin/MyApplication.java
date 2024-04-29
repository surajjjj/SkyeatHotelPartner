package com.food.food_order_hotel_admin;

import android.app.Application;
import android.content.Context;


public class MyApplication extends Application {
    public static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext=this;
//        OneSignal.startInit(this)
//                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
//                .unsubscribeWhenNotificationsAreDisabled(true)
//                .init();
    }
}