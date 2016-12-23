package com.example.orestfufalko.bulbasaurandroidclient.View.Interface;

import android.support.annotation.Nullable;



public interface SignUpResponseView {
    void showSignUpOk();
    void showSignUpError(int etId, String message);
    void showError(String message, @Nullable String stackTrace);
}
