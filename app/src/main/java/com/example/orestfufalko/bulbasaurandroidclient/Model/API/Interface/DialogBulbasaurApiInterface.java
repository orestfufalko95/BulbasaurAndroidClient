package com.example.orestfufalko.bulbasaurandroidclient.Model.API.Interface;

import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.AllMessageResponseDTO;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Vitalii Khorobchuk on 17.12.2016.
 */

public interface DialogBulbasaurApiInterface {
    @GET("message/all/{id}")
    Call<AllMessageResponseDTO> getAllMessages(
            @Path("id") String id
    );

    @FormUrlEncoded
    @POST("message")
    Call<Void> sendMessage(@Field("receiverId") String receiverId, @Field("message") String message);

    @FormUrlEncoded
    @POST("message/first_message")
    Call<Void> sendFirstMessage(@Field("receiverId") String receiverId, @Field("message") String message);
}
