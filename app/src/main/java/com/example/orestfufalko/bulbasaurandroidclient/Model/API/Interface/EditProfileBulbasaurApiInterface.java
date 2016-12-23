package com.example.orestfufalko.bulbasaurandroidclient.Model.API.Interface;

import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.SignUpResponseDTO;

import java.io.File;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by orestfufalko on 18.12.2016.
 */

public interface EditProfileBulbasaurApiInterface {

    @FormUrlEncoded
    @POST("person/edit")
    Call<SignUpResponseDTO> editProfile(
            @Field("Email") String Email,
            @Field("Name") String Name,
            @Field("Surname") String Surname,
            @Field("Photo") File Photo,
            @Field("Address") String Address,
            @Field("About") String About,
            @Field("Sex") byte Sex
    );
}
