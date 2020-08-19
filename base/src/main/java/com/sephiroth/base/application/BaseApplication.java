package com.sephiroth.base.application;

import android.app.Application;

public class BaseApplication extends Application {

    public static  Application applicationContext;
    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this;
    }
}
