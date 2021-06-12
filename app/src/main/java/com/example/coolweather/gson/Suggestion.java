package com.example.coolweather.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 生活指数建议
 */
public class Suggestion {
    @SerializedName("daily")
    public List<DailyBean> daily;

    public List<DailyBean> getDaily() {
        return daily;
    }

    public void setDaily(List<DailyBean> daily) {
        this.daily = daily;
    }


    public static class DailyBean {
        @SerializedName("text")
        public String text;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}
