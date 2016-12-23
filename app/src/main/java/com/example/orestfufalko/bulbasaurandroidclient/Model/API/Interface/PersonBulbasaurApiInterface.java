package com.example.orestfufalko.bulbasaurandroidclient.Model.API.Interface;

import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.UserProfileDTO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by orestfufalko on 03.12.2016.
 */

public interface PersonBulbasaurApiInterface {
    @GET("person/{id}")
    Call<UserProfileDTO> getUserProfileDTOById(
            @Path("id") String userId
    );

}
