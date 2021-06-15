package com.example.coolweather;

import android.content.Context;
import android.os.Handler;

import org.litepal.LitePalApplication;



public class BaseApplication extends LitePalApplication {



    @Override
    public void onCreate() {
        super.onCreate();



    }

    public static Handler getHandler(){
        return sHandler;
    }

}
