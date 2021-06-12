package com.example.coolweather.util;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.coolweather.db.City;
import com.example.coolweather.db.County;
import com.example.coolweather.db.Province;
import com.example.coolweather.gson.AQI;
import com.example.coolweather.gson.Forecast;
import com.example.coolweather.gson.Now;
import com.example.coolweather.gson.Suggestion;
import com.example.coolweather.gson.Weather;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Utility {
    private static final String TAG = "Utility";
    private final static String ENCODE = "GBK";
    private static String sCountyLocationId;
    private static int sI;


    /**
     * 解析和处理服务器返回的省级数据
     *
     * @param response
     * @return
     */
    public static boolean handleProvinceResponse(String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONObject provinceAll = new JSONObject(response);
                JSONArray provinceArray = provinceAll.getJSONArray("result");
                for (int i = 0; i < provinceArray.length(); i++) {
                    JSONObject provinceObject = provinceArray.getJSONObject(i);
                    Province province = new Province();
                    province.setProvinceName(provinceObject.getString("name"));
                    province.setProvinceCode(provinceObject.getInt("id")+"");
                    province.save();
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 解析和处理服务器返回的市级数据
     *
     * @param response
     * @param provinceName
     * @return
     */
    public static boolean handleCityResponse(String response, String provinceName) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONObject cityAll = new JSONObject(response);
                JSONArray cityArray = cityAll.getJSONArray("result");
                for (int i = 0; i < cityArray.length(); i++) {
                    JSONObject cityObject = cityArray.getJSONObject(i);
                    Log.d(TAG, "handleCityResponse: " + cityObject);
                    City city = new City();
                    city.setCityName(cityObject.getString("name"));
                    city.setCityCode(cityObject.getString("id"));
                    city.setProvinceName(cityObject.getString("parentname"));
                    city.setProvinceCode(cityObject.getString("parentid"));
                    city.save();
                }
                return true;
            } catch (Exception e) {
                Log.d(TAG, "handleCityResponse: " + e);
            }

        }
        return false;
    }

    public static boolean handeCountyResponse(String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONObject countyAll = new JSONObject(response);
                JSONArray countyArray = countyAll.getJSONArray("result");
                for (int i = 0; i < countyArray.length(); i++) {
                    JSONObject countyObject = countyArray.getJSONObject(i);
                    boolean isLocationId = getCountyLocationID(countyObject.getString("name"));
                    if (isLocationId) {
                        County county = new County();
                        county.setCountyName(countyObject.getString("name"));
                        county.setLocationId(sCountyLocationId);
                        county.setCityCode(countyObject.getString("parentid"));
                        county.setCityName(countyObject.getString("parentname"));
                        county.save();
                    }
                }

                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return false;
    }
    public static boolean getCountyLocationID(final String CountyName) {
        Log.d(TAG,"名字为"+CountyName);
        Log.d(TAG, "https://geoapi.qweather.com/v2/city/lookup?location=" + urlEncodeChinese(CountyName) + "&key=c4a4936cbb7b4ebc8875652a13257fa7");
        HttpUtil.sendOkHttpReques("https://geoapi.qweather.com/v2/city/lookup?location=" + urlEncodeChinese(CountyName) + "&key=c4a4936cbb7b4ebc8875652a13257fa7", new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(TAG, "onFailure: 错误+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseText = response.body().string();
                sCountyLocationId = handleCountyLocationResponse(responseText);

            }
        });
        if (sCountyLocationId != null) {
            return  true;
        }
        return false;


    }




    public static String  handleCountyLocationResponse(String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONObject countyAll = new JSONObject(response);
                JSONArray countyArray = countyAll.getJSONArray("location");
                JSONObject countyObject = countyArray.getJSONObject(0);
                String countyLocationId=countyObject.getString("id");
                Log.d(TAG, "handleCountyLocationResponse: 得到的locationID为"+countyLocationId);
                return countyLocationId;
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return null;
    }


    public static Now handleNowResponse(String response){
        try {
            JSONObject jsonObject=new JSONObject(response);
            JSONObject jsonObject1=jsonObject.getJSONObject("now");
            String nowJspm=jsonObject1.toString();
            Gson gson=new Gson();
            Now now=gson.fromJson(nowJspm,Now.class);
            return now;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static AQI handleAqiResponse(String response){
        try {
            JSONObject jsonObject=new JSONObject(response);
            JSONObject jsonObject1=jsonObject.getJSONObject("now");
            String nowJspm=jsonObject1.toString();
            Gson gson=new Gson();
            AQI aqi=gson.fromJson(nowJspm,AQI.class);
            return aqi;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }



    public static Suggestion handleSuggestionResponse(String response){
        try {
            JSONObject jsonObject=new JSONObject(response);
            Gson gson=new Gson();
            Suggestion suggestion=gson.fromJson(jsonObject.toString(),Suggestion.class);
            List<Suggestion.DailyBean> dailyBeans=suggestion.getDaily();
            Log.d(TAG, "handleSuggestionResponsejtyjytjtyjtyjtyj: "+dailyBeans.toString());
            return suggestion;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static Forecast handleForecastResponse(String response){
        try {
            JSONObject jsonObject=new JSONObject(response);
             Gson gson=new Gson();
            Forecast forecasts=gson.fromJson(jsonObject.toString(), Forecast.class);
            List<Forecast.DailyBean> dailyBeans=forecasts.getDaily();
            Log.d(TAG, "handleForecastResponse: "+  dailyBeans.toString());
            return forecasts;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static String urlEncodeChinese(String url) {
        try {
            Matcher matcher = Pattern.compile("[\\u4e00-\\u9fa5]").matcher(url);
            String tmp = "";
            while (matcher.find()) {
                tmp = matcher.group();
                url = url.replaceAll(tmp, URLEncoder.encode(tmp, "UTF-8"));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return url.replace(" ","%20");
    }

}
