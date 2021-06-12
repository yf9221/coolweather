package com.example.coolweather.util;

import android.net.Uri;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static org.junit.Assert.*;

public class UtilityTest {
    @Test
    public void test(){
        String URL="https://devapi.qweather.com/v7/indices/1d?type=1,2,8&location=101010100&key=c4a4936cbb7b4ebc8875652a13257fa7";
        HttpUtil.sendOkHttpReques(URL, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String string = response.body().string();
                Utility.handleSuggestionResponse(string);
            }
        });

    }

}