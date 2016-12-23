package com.example.orestfufalko.bulbasaurandroidclient.Presenter.Interface;

import android.content.Context;

import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.SignUpInputDTO;

/**
 * Created by orestfufalko on 04.12.2016.
 */

public interface SignUpPresenterInterface {
    void signUp(Context context, String email, String password, String name, String surName );
}
