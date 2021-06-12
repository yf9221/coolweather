package com.example.coolweather.util;

public  class CnToPy {

    public static String topy(String cn)
    {
        String name = null;
        switch (cn){
            case "云南":
                name="yunnan";
                break;
            case "青海":
                name="qinghai";
                break;
            case "新疆":
                name="xinjiang";
                break;
            case "四川":
                name="sichuan";
                break;
            case "西藏":
                name="xizang";
                break;
            case "陕西":
                name="Shaanxi";
                break;
            case "宁夏":
                name="ningxia";
                break;
            case "甘肃":
                name="gansu";
                break;
            case "安徽":
                name="anhui";
                break;
            case "湖北":
                name="hubei";
                break;
            case "湖南":
                name="hunan";
                break;
            case "广东":
                name="guangdong";
                break;
            case "广西":
                name="guangxi";
                break;
            case "海南":
                name="hainan";
                break;
            case "贵州":
                name="guizhou";
                break;
            case "河南":
                name="henan";
                break;
            case "山西":
                name="shanxi";
                break;
            case "福建":
                name="fujian";
                break;
            case "山东":
                name="shandong";
                break;
            case "江苏":
                name="Jiangsu";
                break;
            case "浙江":
                name="zhejiang";
                break;
            case "江西":
                name="jiangxi";
                break;
            case "北京":
                name="beij";
                break;
            case "上海":
                name="shanghai";
                break;
            case "天津":
                name="tianjin";
                break;
            case "重庆":
                name="chongqin";
                break;
            case "香港":
                name="xianggan";
                break;
            case "澳门":
                name="aomen";
                break;
            case "台湾":
                name="taiwan";
                break;
            case "黑龙江":
                name="heilongjiang";
                break;
             case "吉林":
                name="jilin";
                break;
            case "辽宁":
                name="liaoning";
                break;
            case "内蒙古":
                name="neimenggu";
                break;
            case "河北":
                name="heibei";
                break;

        }
        return name;
    }

}
