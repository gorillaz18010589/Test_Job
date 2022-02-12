package com.example.test.viewmodels;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.test.R;
import com.example.test.model.PostResult;
import com.example.test.repository.PostRepository;
import com.google.gson.Gson;

import java.util.ArrayList;

public class PostViewModel extends AndroidViewModel {
    private PostRepository postRepository;
    public MutableLiveData<ArrayList<PostResult>> postListLiveData;
    public MutableLiveData<PostResult> postLiveData;
    private String key = getApplication().getResources().getString(R.string.data_key);
    private String spName = getApplication().getResources().getString(R.string.sp_name);

    public PostViewModel(@NonNull Application application) {
        super(application);
        this.postRepository = new PostRepository(application);
    }

    public MutableLiveData<ArrayList<PostResult>> getPostList() {
        postListLiveData = postRepository.getPostLiveData();
        return postListLiveData;
    }

    public MutableLiveData<PostResult> getPostLiveData() {
        if (postLiveData == null) {
            postLiveData = new MutableLiveData<>();
        }
        return postLiveData;
    }

    public void setPost(PostResult post) {
        if (postLiveData != null) {
            postLiveData.postValue(post);
        }
    }

    public void save() {
        try {
            if (postListLiveData != null) {
                SharedPreferences sharedPreferences = getApplication().getSharedPreferences(spName, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(key, new Gson().toJson(getPostList().getValue()));
                editor.apply();
                String data = sharedPreferences.getString(key, "null");
            }
        } catch (Exception e) {

        }
    }

    public void load() {
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences(spName, Context.MODE_PRIVATE);
        String data = sharedPreferences.getString(key, "null");
    }

}
