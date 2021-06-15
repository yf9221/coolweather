package com.example.coolweather.util;

import android.content.Context;

public  class WeatherIcon {

    public static int getWeatherIcon(String weather, Context context){
       int id;
       switch (weather){
           case "晴" :id=context.getResources().getIdentifier("p100","mipmap",context.getPackageName());break;
           case "多云" :id=context.getResources().getIdentifier("p101","mipmap",context.getPackageName());break;
           case "少云" :id=context.getResources().getIdentifier("p102","mipmap",context.getPackageName());break;
           case "晴间多云" :id=context.getResources().getIdentifier("p103","mipmap",context.getPackageName());break;
           case "阴" :id=context.getResources().getIdentifier("p104","mipmap",context.getPackageName());break;
           case "阵雨" :id=context.getResources().getIdentifier("p300","mipmap",context.getPackageName());break;
           case "强阵雨" :id=context.getResources().getIdentifier("p301","mipmap",context.getPackageName());break;
           case "雷阵雨" :id=context.getResources().getIdentifier("p302","mipmap",context.getPackageName());break;
           case "强雷阵雨" :id=context.getResources().getIdentifier("p303","mipmap",context.getPackageName());break;
           case "雷阵雨伴有冰雹" :id=context.getResources().getIdentifier("p304","mipmap",context.getPackageName());break;
           case "小雨" :id=context.getResources().getIdentifier("p305","mipmap",context.getPackageName());break;
           case "中雨" :id=context.getResources().getIdentifier("p306","mipmap",context.getPackageName());break;
           case "大雨" :id=context.getResources().getIdentifier("p307","mipmap",context.getPackageName());break;
           case "极端降雨" :id=context.getResources().getIdentifier("p308","mipmap",context.getPackageName());break;
           case "毛毛雨/细雨" :id=context.getResources().getIdentifier("p309","mipmap",context.getPackageName());break;
           case "暴雨" :id=context.getResources().getIdentifier("p310","mipmap",context.getPackageName());break;
           case "大暴雨" :id=context.getResources().getIdentifier("p311","mipmap",context.getPackageName());break;
           case "特大暴雨" :id=context.getResources().getIdentifier("p312","mipmap",context.getPackageName());break;
           case "冻雨" :id=context.getResources().getIdentifier("p313","mipmap",context.getPackageName());break;
           case "小到中雨" :id=context.getResources().getIdentifier("p314","mipmap",context.getPackageName());break;
           case "中到大雨" :id=context.getResources().getIdentifier("p315","mipmap",context.getPackageName());break;
           case "大到暴雨" :id=context.getResources().getIdentifier("p316","mipmap",context.getPackageName());break;
           case "暴雨到大暴雨" :id=context.getResources().getIdentifier("p317","mipmap",context.getPackageName());break;
           case "大暴雨到特大暴雨" :id=context.getResources().getIdentifier("p318","mipmap",context.getPackageName());break;
           case "雨" :id=context.getResources().getIdentifier("p399","mipmap",context.getPackageName());break;
           case "小雪" :id=context.getResources().getIdentifier("p400","mipmap",context.getPackageName());break;
           case "中雪" :id=context.getResources().getIdentifier("p401","mipmap",context.getPackageName());break;
           case "大雪" :id=context.getResources().getIdentifier("p402","mipmap",context.getPackageName());break;
           case "暴雪" :id=context.getResources().getIdentifier("p403","mipmap",context.getPackageName());break;
           case "雨夹雪" :id=context.getResources().getIdentifier("p404","mipmap",context.getPackageName());break;
           case "雨雪天气" :id=context.getResources().getIdentifier("p405","mipmap",context.getPackageName());break;
           case "阵雨夹雪" :id=context.getResources().getIdentifier("p406","mipmap",context.getPackageName());break;
           case "阵雪" :id=context.getResources().getIdentifier("p407","mipmap",context.getPackageName());break;
           case "小到中雪" :id=context.getResources().getIdentifier("p408","mipmap",context.getPackageName());break;
           case "中到大雪" :id=context.getResources().getIdentifier("p409","mipmap",context.getPackageName());break;
           case "大到暴雪" :id=context.getResources().getIdentifier("p410","mipmap",context.getPackageName());break;
           case "雪" :id=context.getResources().getIdentifier("p499","mipmap",context.getPackageName());break;
           case "薄雾	" :id=context.getResources().getIdentifier("p500","mipmap",context.getPackageName());break;
           case "雾" :id=context.getResources().getIdentifier("p501","mipmap",context.getPackageName());break;
           case "霾" :id=context.getResources().getIdentifier("p502","mipmap",context.getPackageName());break;
           case "扬沙" :id=context.getResources().getIdentifier("p503","mipmap",context.getPackageName());break;
           case "浮尘" :id=context.getResources().getIdentifier("p504","mipmap",context.getPackageName());break;
           case "沙尘暴	" :id=context.getResources().getIdentifier("p507","mipmap",context.getPackageName());break;
           case "强沙尘暴" :id=context.getResources().getIdentifier("p508","mipmap",context.getPackageName());break;
           case "浓雾" :id=context.getResources().getIdentifier("p509","mipmap",context.getPackageName());break;
           case "强浓雾" :id=context.getResources().getIdentifier("p510","mipmap",context.getPackageName());break;
           case "中度霾" :id=context.getResources().getIdentifier("p511","mipmap",context.getPackageName());break;
           case "重度霾" :id=context.getResources().getIdentifier("p512","mipmap",context.getPackageName());break;
           case "严重霾" :id=context.getResources().getIdentifier("p513","mipmap",context.getPackageName());break;
           case "大雾" :id=context.getResources().getIdentifier("p514","mipmap",context.getPackageName());break;
           case "特强浓雾" :id=context.getResources().getIdentifier("p515","mipmap",context.getPackageName());break;
           case "热" :id=context.getResources().getIdentifier("p900","mipmap",context.getPackageName());break;
           case "冷" :id=context.getResources().getIdentifier("p901","mipmap",context.getPackageName());break;
           case "未知" :id=context.getResources().getIdentifier("p999","mipmap",context.getPackageName());break;
           default:
               throw new IllegalStateException("Unexpected value: " + weather);
       }
       return id;

    }


}

