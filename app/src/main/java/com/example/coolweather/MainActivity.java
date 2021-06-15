package com.example.coolweather;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.gyf.immersionbar.ImmersionBar;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImmersionBar.with(this).statusBarColor("#100000")
                .fitsSystemWindows(true).init();
        SharedPreferences preferences=getSharedPreferences("WeatherActivity",MODE_PRIVATE);
        String responseNow =preferences.getString("responseNow",null);
        String responseForecast =preferences.getString("responseForecast",null);
        String responseAqi =preferences.getString("responseAqi",null);
        String responseSuggestion =preferences.getString("responseSuggestion",null);
        String CountyName = preferences.getString("countyName",null);
        String locationId =preferences.getString("locationId",null);
        if (responseNow!=null&&responseForecast!=null&&responseAqi!=null&&responseSuggestion!=null&&CountyName!=null&&locationId!=null) {
            Intent intent=new Intent(this,WeatherActivity.class);
            startActivity(intent);
            finish();
        }


    }
}
