package com.example.coolweather;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.coolweather.gson.AQI;
import com.example.coolweather.gson.Basic;
import com.example.coolweather.gson.Forecast;
import com.example.coolweather.gson.Now;
import com.example.coolweather.gson.Suggestion;
import com.example.coolweather.gson.Weather;
import com.example.coolweather.service.AutoUpdateService;
import com.example.coolweather.util.HttpUtil;
import com.example.coolweather.util.Utility;
import com.example.coolweather.util.WeatherIcon;
import com.gyf.immersionbar.ImmersionBar;
import org.jetbrains.annotations.NotNull;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity {
    private View weatherLayout;
    private TextView titleCity;
    private TextView titleUpdateTime;
    private TextView degreeText;
    private TextView weatherInfoText;
    private LinearLayout forcecastLayout;
    private TextView aqiText;
    private TextView pm25Text;
    private TextView comfortText;
    private TextView carWashText;
    private TextView sportText;
    private Weather mWeather;
    private String mNowUrl;
    private String mFcUrl;
    private String mAqiUrl;
    private String mSuggestionUrl;
    private String mCountyName;
    private ImageView mPingPicImg;
    public SwipeRefreshLayout mSwipeRefreshLayout;
    private String mLocationId;
    public DrawerLayout mDrawerLayout;
    private Button mNavButton;
    private ImageView mWeatherIcon;
    private static Handler mHandler=BaseApplication.getHandler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        ImmersionBar.with(this).statusBarColor(R.color.colorTouMing)
                .fitsSystemWindows(true).init();
        initView();


    }

    private void initView() {
        weatherLayout=this.findViewById(R.id.weather_layout);
        titleCity=this.findViewById(R.id.title_city);
        titleUpdateTime=this.findViewById(R.id.title_update_time);
        degreeText=this.findViewById(R.id.degree_text);
        weatherInfoText=this.findViewById(R.id.weather_info_text);
        forcecastLayout=this.findViewById(R.id.forecast_layout);
        mPingPicImg = findViewById(R.id.bing_pic_img);
        aqiText=this.findViewById(R.id.aqi_text);
        pm25Text=this.findViewById(R.id.pm25_text);
        comfortText=this.findViewById(R.id.comfort_text);
        carWashText=this.findViewById(R.id.car_wash_text);
        sportText=this.findViewById(R.id.sport_text);
        mSwipeRefreshLayout = this.findViewById(R.id.swipe_refresh);
        mDrawerLayout = this.findViewById(R.id.drawer_layout);
        mDrawerLayout.setScrimColor(getResources().getColor(R.color.colorWhite));
        mNavButton = this.findViewById(R.id.nav_button);
        mWeatherIcon = this.findViewById(R.id.weather_icon);
        SharedPreferences preferences=getPreferences(this.MODE_PRIVATE);
      /*  String bingPic=preferences.getString("bing_pic",null);
        if (bingPic != null) {
            Glide.with(this).load(bingPic).into(mPingPicImg);
        }else {
          loadBingPic();
        }*/
        String responseNow =preferences.getString("responseNow",null);
        String responseForecast =preferences.getString("responseForecast",null);
        String responseAqi =preferences.getString("responseAqi",null);
        String responseSuggestion =preferences.getString("responseSuggestion",null);
        mLocationId = preferences.getString("locationId",null);
        mCountyName = preferences.getString("countyName",null);
        if (responseNow!=null&&responseForecast!=null&&responseAqi!=null&&responseSuggestion!=null&&mLocationId!=null&&mCountyName!=null){
            mWeather=new Weather();
            //有缓存时直接解析天气数据
            Now now = Utility.handleNowResponse(responseNow);
            Forecast forecast = Utility.handleForecastResponse(responseForecast);
            AQI aqi = Utility.handleAqiResponse(responseAqi);
            Suggestion suggestion = Utility.handleSuggestionResponse(responseSuggestion);
            Basic basic=new Basic();
            basic.setCityName(mCountyName);
            basic.setLocationId(mLocationId);
            mWeather.setNow(now);
            mWeather.setForecastList(forecast);
            mWeather.setAqi(aqi);
            mWeather.setSuggestion(suggestion);
            mWeather.setBasic(basic);
            showWeatherInfo(mWeather);
        }else {
            //无缓存时去服务器查询天气
            mCountyName = getIntent().getStringExtra("countyName");
            mLocationId=getIntent().getStringExtra("locationId");
            weatherLayout.setVisibility(View.INVISIBLE);
            requestWeather(mLocationId);
        }
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestWeather(mLocationId);

            }
        });
        mNavButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void loadBingPic() {
        String requestBingpic="http://guolin.tech/api/bing_pic";
        HttpUtil.sendOkHttpReques(requestBingpic, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final String responseText = response.body().string();
                SharedPreferences sharedPreferences=getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("bing_pic",responseText);
                editor.apply();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (responseText != null) {
                            Glide.with(WeatherActivity.this).load(responseText).into(mPingPicImg);
                        }
                    }
                });


            }
        });
    }

    public void requestWeather(final String locationId) {
        mNowUrl = "https://devapi.qweather.com/v7/weather/now?location="+locationId+"&key=c4a4936cbb7b4ebc8875652a13257fa7";
        mFcUrl = "https://devapi.qweather.com/v7/weather/3d?location="+locationId+"&key=c4a4936cbb7b4ebc8875652a13257fa7";
        mAqiUrl = "https://devapi.qweather.com/v7/air/now?location="+locationId+"&key=c4a4936cbb7b4ebc8875652a13257fa7";
        mSuggestionUrl = "https://devapi.qweather.com/v7/indices/1d?type=1,2,8&location="+locationId+"&key=c4a4936cbb7b4ebc8875652a13257fa7";
        HttpUtil.sendOkHttpReques(mNowUrl, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Toast.makeText(WeatherActivity.this,"获取天气信息失败",Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }


            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final String responseNow=response.body().string();
                Now now = Utility.handleNowResponse(responseNow);
                mWeather=new Weather();
                mWeather.setNow(now);
                SharedPreferences.Editor editor=getPreferences(MODE_PRIVATE).edit();
                editor.putString("responseNow",responseNow);
                editor.apply();
                requestForecast(locationId);
                //final Weather weather = new Weather();

            }
        });
    }
    private void requestForecast(final String locationId) {
        HttpUtil.sendOkHttpReques(mFcUrl, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseForecast = response.body().string();
                Forecast forecast = Utility.handleForecastResponse(responseForecast);
                mWeather.setForecastList(forecast);
                SharedPreferences.Editor editor=getPreferences(MODE_PRIVATE).edit();
                editor.putString("responseForecast",responseForecast);
                editor.apply();
                requestAqi(locationId);
            }
        });

    }
    private void requestAqi(final String locationId) {
        HttpUtil.sendOkHttpReques(mAqiUrl, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
               e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseAqi = response.body().string();
                AQI aqi = Utility.handleAqiResponse(responseAqi);
                mWeather.setAqi(aqi);
                SharedPreferences.Editor editor=getPreferences(MODE_PRIVATE).edit();
                editor.putString("responseAqi",responseAqi);
                editor.apply();
                requestSueestion(locationId);
            }
        });

    }

    private void requestSueestion(final String locationId) {
        HttpUtil.sendOkHttpReques(mSuggestionUrl, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final String responseSuggestion = response.body().string();
                Suggestion suggestion = Utility.handleSuggestionResponse(responseSuggestion);
                mWeather.setSuggestion(suggestion);
                mWeather.setStatus("ok");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if ( "ok".equals(mWeather.status)) {
                             SharedPreferences.Editor editor=getPreferences(MODE_PRIVATE).edit();
                            editor.putString("responseSuggestion",responseSuggestion);
                            editor.putString("countyName", mCountyName);
                            editor.putString("locationId",locationId);
                            editor.apply();
                            Basic basic=new Basic();
                            basic.setCityName(mCountyName);
                            basic.setLocationId(locationId);
                            mWeather.setBasic(basic);
                            showWeatherInfo(mWeather);
                        }
                        mSwipeRefreshLayout.setRefreshing(false);

                    }
                });

            }
        });

    }




    private void showWeatherInfo(Weather weather) {
              mLocationId=weather.basic.locationId;
              String cityName=weather.basic.cityName;
              //String updateTime=weather.now.updateTime;
              String degree=weather.now.temperature+"°C";
              String weatherInfo=weather.now.info;
              titleCity.setText(cityName);
              SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");// HH:mm:ss
               Date date = new Date(System.currentTimeMillis());
              titleUpdateTime.setText(simpleDateFormat.format(date));
              degreeText.setText(degree);
              weatherInfoText.setText(weatherInfo);
              mWeatherIcon.setImageResource(WeatherIcon.getWeatherIcon(weatherInfo,this));
              forcecastLayout.removeAllViews();
        for (Forecast.DailyBean dailyBean : weather.ForecastList.daily) {
            View view= LayoutInflater.from(this).inflate(R.layout.forcast_item,forcecastLayout,false);
            TextView dateText=view.findViewById(R.id.data_text);
            TextView infoText=view.findViewById(R.id.info_text);
            TextView maxText=view.findViewById(R.id.max_text);
            TextView minText=view.findViewById(R.id.min_text);
            ImageView weatherItemIcon=view.findViewById(R.id.weather_item_icon);
            ImageView tempMaxIcon=view.findViewById(R.id.temp_max);
            ImageView tempMinIcon=view.findViewById(R.id.temp_min);
            dateText.setText(dailyBean.date);
            infoText.setText(dailyBean.info);
            maxText.setText(dailyBean.max);
            minText.setText(dailyBean.min);
            weatherItemIcon.setImageResource(WeatherIcon.getWeatherIcon(dailyBean.getInfo(),this));
            tempMaxIcon.setImageResource(WeatherIcon.getWeatherIcon("热",this));
            tempMinIcon.setImageResource(WeatherIcon.getWeatherIcon("冷",this));
            forcecastLayout.addView(view);
        }
        if (weather.aqi != null) {
            aqiText.setText(weather.aqi.aqi);
            pm25Text.setText(weather.aqi.pm25);
        }

            String comfort="舒适度："+weather.suggestion.daily.get(2).text;
            String carWash="洗车指数："+weather.suggestion.daily.get(0).text;
            String sport="运动建议："+weather.suggestion.daily.get(1).text;
            comfortText.setText(comfort);
            carWashText.setText(carWash);
            sportText.setText(sport);
            weatherLayout.setVisibility(View.VISIBLE);
            Random random=new Random();
            int f=random.nextInt(6)+1;
            mPingPicImg.setImageResource(getResources().getIdentifier("bg"+f,"mipmap",getPackageName()));
       /* Intent intent=new Intent(this, AutoUpdateService.class);
        startService(intent);*/


    }

    public String getCountyName() {
        return mCountyName;
    }

    public void setCountyName(String countyName) {
        mCountyName = countyName;
    }



}
