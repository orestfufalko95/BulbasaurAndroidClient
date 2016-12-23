package com.example.orestfufalko.bulbasaurandroidclient.Presenter.Contract;

import com.example.orestfufalko.bulbasaurandroidclient.Model.API.Interface.LoginApiInteractorInterface;
import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.LoginInputDTO;
import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.UserProfileDTO;
import com.example.orestfufalko.bulbasaurandroidclient.Presenter.Interface.LoginPresenterInterface;
import com.example.orestfufalko.bulbasaurandroidclient.View.Interface.LoginRequestListener;
import com.example.orestfufalko.bulbasaurandroidclient.View.Interface.LoginResponseView;

/**
 * Created by Vitalii Khorobchuk on 06.12.2016.
 */

public class LoginPresenter implements LoginRequestListener, LoginPresenterInterface {

    private LoginApiInteractorInterface loginApiInteractor;
    private LoginResponseView loginResponseView;
    private final String TAG = "LoginPresenterTAG";

    public LoginPresenter(LoginApiInteractorInterface loginApiInteractor, LoginResponseView loginResponseView) {
        this.loginApiInteractor = loginApiInteractor;
        this.loginResponseView = loginResponseView;
    }

    @Override
    public void onLoginSuccess(UserProfileDTO userProfileDTO) {
        loginResponseView.showResponse(userProfileDTO);
    }

    @Override
    public void onLoginError(int requestCode) {
        if (requestCode == 400){
            loginResponseView.showWrongData();
        }
        else {
            loginResponseView.showLoginError(requestCode, "", null);
        }

    }


    @Override
    public void onLoginFailure(String message, String stackTrace) {
        loginResponseView.showLoginError(1, message, stackTrace);
    }

    @Override
    public void login(String email, String password ) {

            LoginInputDTO loginInputDTO = new LoginInputDTO();
            loginInputDTO.setEmail(email);
            loginInputDTO.setPassword(password);
            loginApiInteractor.loginUser(loginInputDTO, this);
    }


}
