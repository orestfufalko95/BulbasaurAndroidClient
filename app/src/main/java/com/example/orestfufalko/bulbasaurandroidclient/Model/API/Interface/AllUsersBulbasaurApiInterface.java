package com.example.orestfufalko.bulbasaurandroidclient.Model.API.Interface;

import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.UserInfoForSearchDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by orestfufalko on 19.12.2016.
 */

public interface AllUsersBulbasaurApiInterface {
    @GET("person/all")
    Call<List<UserInfoForSearchDTO>> getAllUsers();
}
