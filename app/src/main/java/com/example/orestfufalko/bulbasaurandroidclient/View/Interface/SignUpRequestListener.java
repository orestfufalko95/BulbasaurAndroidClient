package com.example.orestfufalko.bulbasaurandroidclient.View.Interface;

import android.support.annotation.Nullable;

import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.SignUpResponseDTO;

/**
 * Created by orestfufalko on 04.12.2016.
 */

public interface SignUpRequestListener {
    void onSignUpSuccess();
    void onSignUpError(SignUpResponseDTO signUpResponse);
    void onSignUpFailure(String message, @Nullable String stackTrace);
}
