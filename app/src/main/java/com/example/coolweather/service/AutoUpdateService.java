package com.example.coolweather.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import com.example.coolweather.util.HttpUtil;

import java.util.prefs.Preferences;

public class AutoUpdateService extends Service {
    private static final String TAG = "AutoUpdateService";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        updateWeather();
        updateBingpc();
        AlarmManager manager= (AlarmManager) getSystemService(ALARM_SERVICE);
        int anHour=8*60*60*1000;
        long triggerAtTime= SystemClock.elapsedRealtime()+anHour;
        Intent i=new Intent(this,AutoUpdateService.class);
        PendingIntent pi=PendingIntent.getService(this,0,i,0);
        manager.cancel(pi);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime,pi);
        Log.d(TAG, "onStartCommand: 已经启动+++++++++++");
        return super.onStartCommand(intent, flags, startId);
    }

    private void updateBingpc() {

    }

    private void updateWeather() {
     /*   SharedPreferences preferences=getSharedPreferences("WeatherActivity",MODE_PRIVATE);
        String locationId=preferences.getString("locationId",null);
        String mNowUrl = "https://devapi.qweather.com/v7/weather/now?location=" + locationId + "&key=c4a4936cbb7b4ebc8875652a13257fa7";
        String mFcUrl = "https://devapi.qweather.com/v7/weather/3d?location="+locationId+"&key=c4a4936cbb7b4ebc8875652a13257fa7";
        String mAqiUrl = "https://devapi.qweather.com/v7/air/now?location="+locationId+"&key=c4a4936cbb7b4ebc8875652a13257fa7";
        String mSuggestionUrl = "https://devapi.qweather.com/v7/indices/1d?type=1,2,8&location="+locationId+"&key=c4a4936cbb7b4ebc8875652a13257fa7";
        HttpUtil.sendOkHttpReques(,);*/
    }


}
