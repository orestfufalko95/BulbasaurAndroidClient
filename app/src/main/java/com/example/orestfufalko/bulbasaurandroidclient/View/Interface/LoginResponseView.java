package com.example.orestfufalko.bulbasaurandroidclient.View.Interface;

import android.support.annotation.Nullable;

import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.UserProfileDTO;

public interface LoginResponseView {
    void showResponse(UserProfileDTO userProfileDTO);
    void showWrongData();
    void showLoginError(int code, String message, @Nullable String stackTrace);
}
