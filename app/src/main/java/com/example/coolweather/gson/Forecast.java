package com.example.coolweather.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;


/**
 * 未来天气
 */
public class Forecast {
    @SerializedName("daily")
    public List<DailyBean> daily;

    public List<DailyBean> getDaily() {
        return daily;
    }

    public void setDaily(List<DailyBean> daily) {
        this.daily = daily;
    }

    public static class DailyBean{
        @SerializedName("fxDate")
        public String date;
        @SerializedName("tempMax")
        public String max;
        @SerializedName("tempMin")
        public String min;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getMax() {
            return max;
        }

        public void setMax(String max) {
            this.max = max;
        }

        public String getMin() {
            return min;
        }

        public void setMin(String min) {
            this.min = min;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        @SerializedName("textDay")
        public String info;
        @Override
        public String toString() {
            return "DailyBean{" +
                    "date='" + date + '\'' +
                    ", max='" + max + '\'' +
                    ", min='" + min + '\'' +
                    ", info='" + info + '\'' +
                    '}';
        }
    }


}
