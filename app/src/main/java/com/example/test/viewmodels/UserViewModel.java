package com.example.test.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.test.model.User;

public class UserViewModel extends ViewModel {
    private MutableLiveData<User> userLiveData;

    public MutableLiveData<User> getUserLiveData() {
        if (userLiveData == null) {
            this.userLiveData = new MutableLiveData<>();
        }
        return userLiveData;
    }

    public void setUserLiveData(User user) {
        if (userLiveData != null) {
            userLiveData.setValue(user);
        }
    }
}
