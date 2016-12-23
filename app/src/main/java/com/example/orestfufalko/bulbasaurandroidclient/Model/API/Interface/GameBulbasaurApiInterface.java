package com.example.orestfufalko.bulbasaurandroidclient.Model.API.Interface;

import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.UserInfoForSearchDTO;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Path;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by orestfufalko on 17.12.2016.
 */
public interface GameBulbasaurApiInterface {

    @POST("game/{gameId}")
    Call<Void> addGameToUser(@Path("gameId") long gameId);

    @DELETE("game/{gameId}")
//    @HTTP(method = "DELETE", path = "/game/{gameId}", hasBody = true)
    Call<Void> deleteGameFromUser(@Path("gameId") long gameId);


    @GET("game/{id}/users")
    Call<List<UserInfoForSearchDTO>> getUsersByGame(@Path("id") String id);
}
