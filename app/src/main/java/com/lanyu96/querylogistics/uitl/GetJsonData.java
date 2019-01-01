package com.lanyu96.querylogistics.uitl;


import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 通过okHttp3 请求网络数据,返回String类型数据
 */

public class GetJsonData {
    private static String TAG = "TESTJSON";
    private static String data = "";
    public String getJsonData(String urlStr) {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(urlStr)
                .build();
        Call call = client.newCall(request);
        Log.i(TAG,"请求的数据为:"+urlStr);
        try {
            Response response = call.execute();
            data = response.body().string();
            Log.i(TAG, "获取数据成功");
            Log.i(TAG, "response code =" + response.code());
            Log.i(TAG, "response body = " + data);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }
}
