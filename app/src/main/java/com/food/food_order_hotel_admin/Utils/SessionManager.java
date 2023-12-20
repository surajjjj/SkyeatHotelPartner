package com.food.food_order_hotel_admin.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

//import com.food.food_order_poweradmin.model.Store;
import com.google.gson.Gson;

public class SessionManager {
    private final SharedPreferences mPrefs;
    SharedPreferences.Editor mEditor;
    public static String userdata = "riderdata";
    public static String curruncy = "currncy";

    public SessionManager(Context context) {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        mEditor = mPrefs.edit();
    }

    public void setStringData(String key, String val) {
        mEditor.putString(key, val);
        mEditor.commit();
    }

    public String getStringData(String key) {
        return mPrefs.getString(key, "");
    }

    public void setBooleanData(String key, Boolean val) {
        mEditor.putBoolean(key, val);
        mEditor.commit();
    }

    public boolean getBooleanData(String key) {
        return mPrefs.getBoolean(key, false);
    }

    public void setIntData(String key, int val) {
        mEditor.putInt(key, val);
        mEditor.commit();
    }

    public int getIntData(String key) {
        return mPrefs.getInt(key, 0);
    }

//    public void setUserDetails(String key, Store val) {
//        mEditor.putString(userdata, new Gson().toJson(val));
//        mEditor.commit();
//    }

//    public Store getUserDetails(String key) {
//        return new Gson().fromJson(mPrefs.getString(userdata, ""), Store.class);
//    }

    public void logoutUser() {
        mEditor.clear();
        mEditor.commit();
    }
}
