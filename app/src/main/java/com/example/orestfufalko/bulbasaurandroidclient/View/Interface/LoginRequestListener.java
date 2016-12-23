package com.example.orestfufalko.bulbasaurandroidclient.View.Interface;

import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.UserProfileDTO;

/**
 * Created by Виталий on 06.12.2016.
 */

public interface LoginRequestListener {
    void onLoginSuccess(UserProfileDTO userProfileDTO);
    void onLoginError(int requestCode);
    void onLoginFailure(String message, String stackTrace);
}
