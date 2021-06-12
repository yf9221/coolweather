package com.example.coolweather;

import com.example.coolweather.util.HttpUtil;
import com.example.coolweather.util.Utility;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test(){
        String url="https://devapi.qweather.com/v7/indices/1d?type=1,2,8&location=101010100&key=c4a4936cbb7b4ebc8875652a13257fa7";
        Utility.handleSuggestionResponse(url);
    }
}