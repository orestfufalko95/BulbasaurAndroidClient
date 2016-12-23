package com.example.orestfufalko.bulbasaurandroidclient.Model.API.Interface;

import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.UserInfoForSearchDTO;
import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.UserProfileDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by orestfufalko on 17.12.2016.
 */

public interface FriendBulbasaurApiInterface {

    @GET("person/friends/{id}")
    Call<List<UserInfoForSearchDTO>> getFriendsById(
            @Path("id") String userId
    );
}
