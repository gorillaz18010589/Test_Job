package com.example.test.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.test.api.MyOkHttpApi;
import com.example.test.model.PostResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Response;

public class PostRepository {
    private MutableLiveData<ArrayList<PostResult>> postListLiveData;
    private Application application;
    private String URL = "https://jsonplaceholder.typicode.com/posts";


    public PostRepository(Application application) {
        this.application = application;
    }

    public MutableLiveData<ArrayList<PostResult>> getPostLiveData() {
        if (postListLiveData == null) {
            postListLiveData = new MutableLiveData<>();
        }

         MyOkHttpApi.instance().doGet(URL, new MyOkHttpApi.OkHttpCallBack() {
            @Override
            public void onFailure(IOException e) {

            }

            @Override
            public void onSuccess(Response response) {
                if (response.body() != null) {
                    ArrayList<PostResult> postResultList = null;
                    try {
                        postResultList = new Gson().fromJson(response.body().string(), new TypeToken<ArrayList<PostResult>>() {
                        }.getType());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    postListLiveData.postValue(postResultList);
                }
            }
        });
        return postListLiveData;
    }
}
