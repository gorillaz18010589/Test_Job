package com.example.test.api;


import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MyOkHttpApi {
    private static OkHttpClient client = null;
    private static MyOkHttpApi instance;

    public static MyOkHttpApi instance() {
        if (instance == null) instance = new MyOkHttpApi();
        return instance;
    }

    //1.設定OkHttpClient.Builder連線時間設定
    public MyOkHttpApi() {
        if (client == null) {
            client = new OkHttpClient
                    .Builder()
                    .readTimeout(120, TimeUnit.SECONDS)
                    .connectTimeout(120, TimeUnit.SECONDS)
                    .writeTimeout(120, TimeUnit.SECONDS)
                    .build();
        }
    }


    public interface OkHttpCallBack {
        void onFailure(IOException e);
        void onSuccess(Response response);
    }


    private class MyCallBack implements Callback {
        private OkHttpCallBack okHttpCallBack;

        public MyCallBack(OkHttpCallBack okHttpCallBack) {
            this.okHttpCallBack = okHttpCallBack;
        }

        @Override
        public void onFailure(Call call, IOException e) {
            okHttpCallBack.onFailure(e);
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            okHttpCallBack.onSuccess(response);
        }
    }

    public void doGet(String url, OkHttpCallBack callBack) {
        MyCallBack myCallBack = new MyCallBack(callBack);
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(myCallBack);
    }

}