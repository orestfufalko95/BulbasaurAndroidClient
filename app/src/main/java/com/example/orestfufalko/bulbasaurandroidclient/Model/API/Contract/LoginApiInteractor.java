package com.example.orestfufalko.bulbasaurandroidclient.Model.API.Contract;

import android.util.Log;

import com.example.orestfufalko.bulbasaurandroidclient.Model.API.Interface.LoginApiInteractorInterface;
import com.example.orestfufalko.bulbasaurandroidclient.Model.API.Interface.LoginBulbasaurApiInterface;
import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.LoginInputDTO;
import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.UserProfileDTO;
import com.example.orestfufalko.bulbasaurandroidclient.View.Interface.LoginRequestListener;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginApiInteractor implements LoginApiInteractorInterface {

    private final String TAG = getClass().getSimpleName();
    private LoginBulbasaurApiInterface loginBulbasaurApiInterface;

    public LoginApiInteractor(LoginBulbasaurApiInterface loginBulbasaurApiInterface) {
        this.loginBulbasaurApiInterface = loginBulbasaurApiInterface;
    }

    @Override
    public void loginUser(LoginInputDTO loginInputDTO, final LoginRequestListener loginRequestListener) {
        Call<UserProfileDTO> callUserProfile = loginBulbasaurApiInterface.getUserProfileDTOById(loginInputDTO);
        callUserProfile.enqueue(new Callback<UserProfileDTO>() {
            @Override
            public void onResponse(Call<UserProfileDTO> call, Response<UserProfileDTO> response) {

                UserProfileDTO body = null;
                if (response.code() == 200){
                    try {
                        body = response.body();
                    } catch (Exception e) {
                        Log.e(TAG, "onResponse: ", e );
                        loginRequestListener.onLoginError(response.code());
                    }
                    loginRequestListener.onLoginSuccess(body);
                }
                else {
                    loginRequestListener.onLoginError(response.code());
                }
            }

            @Override
            public void onFailure(Call<UserProfileDTO> call, Throwable t) {

                loginRequestListener.onLoginFailure(t.getMessage(), Arrays.toString(t.getStackTrace()));
                Log.e(TAG, "FAILED" + t.getMessage() );
            }
        });
    }
}
