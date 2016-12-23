package com.example.orestfufalko.bulbasaurandroidclient.Model.API.Interface;

import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.LoginInputDTO;

import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.SignUpResponseDTO;
import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.UserProfileDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Виталий on 05.12.2016.
 */

public interface LoginBulbasaurApiInterface {
    @POST("login")
    Call<UserProfileDTO> getUserProfileDTOById(@Body LoginInputDTO loginInputDTO);
}
