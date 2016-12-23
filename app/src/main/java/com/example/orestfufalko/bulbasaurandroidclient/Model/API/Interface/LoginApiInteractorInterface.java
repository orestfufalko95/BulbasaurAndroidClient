package com.example.orestfufalko.bulbasaurandroidclient.Model.API.Interface;

import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.LoginInputDTO;
import com.example.orestfufalko.bulbasaurandroidclient.View.Interface.LoginRequestListener;

/**
 * Created by Виталий on 06.12.2016.
 */

public interface LoginApiInteractorInterface {
    void loginUser(LoginInputDTO loginInputDTO, LoginRequestListener loginRequestListener);
}
