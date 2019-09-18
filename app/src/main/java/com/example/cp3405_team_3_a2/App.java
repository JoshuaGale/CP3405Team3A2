package com.example.cp3405_team_3_a2;

import android.app.Application;
import android.content.Context;

public class App extends Application {
    private static App instance;

    public static Context getContext(){
        return instance;
    }

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
    }
}
