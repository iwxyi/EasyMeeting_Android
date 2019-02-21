package com.iwxyi.easymeeting.Globals;

import android.app.Application;
import android.content.Context;

public class MyApp extends Application {
    private static Context context;

    public void onCreate(){
        super.onCreate();
        MyApp.context = getApplicationContext();
    }

    public static Context getContext() {
        return MyApp.context;
    }
}
