package com.example.orestfufalko.bulbasaurandroidclient.Model.API.Interface;

import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.GameDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by orestfufalko on 17.12.2016.
 */

public interface AllGamesBulbasaurApiInterface {
    @GET("game/all")
    Call<List<GameDTO>> getAllGames();
}
