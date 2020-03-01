package com.comsats.ars.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class AppSettings {

    private final String PREF_NAME = "MainPref";

    private final String PREF_IS_LOGIN = "IsLogin";
    private final String PREF_LOGIN_USERNAME = "LOGIN_USERNAME";

    private Context mContext;
    private SharedPreferences mPreferences;

    public AppSettings(Context context) {
        mContext = context;
        mPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void setIsLogin(boolean isLogin) {
        mPreferences.edit().putBoolean(PREF_IS_LOGIN, isLogin).apply();
    }

    public boolean isLogin() {
        return mPreferences.getBoolean(PREF_IS_LOGIN, false);
    }

    public void setLoginUsername(String username) {
        mPreferences.edit().putString(PREF_LOGIN_USERNAME, username).apply();
    }

    public String getLoginUsername() {
        return mPreferences.getString(PREF_LOGIN_USERNAME, null);
    }
}
