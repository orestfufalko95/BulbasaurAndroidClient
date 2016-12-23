package com.example.orestfufalko.bulbasaurandroidclient.Model.API.Interface;

import android.content.Context;

import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.SignUpInputDTO;
import com.example.orestfufalko.bulbasaurandroidclient.View.Interface.SignUpRequestListener;


public interface SignUpApiInteractorInterface {
    void SignUp(Context context, SignUpInputDTO signUpInputDTO, SignUpRequestListener listener);
}
