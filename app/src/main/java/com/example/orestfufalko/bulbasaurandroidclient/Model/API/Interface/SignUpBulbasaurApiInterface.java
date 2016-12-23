package com.example.orestfufalko.bulbasaurandroidclient.Model.API.Interface;

import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.SignUpInputDTO;
import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.SignUpResponseDTO;
import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.UserProfileDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface SignUpBulbasaurApiInterface {
    @POST("login/signup")
    Call<SignUpResponseDTO> getUserProfileDTOById(@Body SignUpInputDTO signUpInputDTO);
}
