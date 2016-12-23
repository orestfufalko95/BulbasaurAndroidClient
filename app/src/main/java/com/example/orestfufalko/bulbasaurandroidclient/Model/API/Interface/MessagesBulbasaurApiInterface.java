package com.example.orestfufalko.bulbasaurandroidclient.Model.API.Interface;

import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.LastMessageDTO;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Vitalii Khorobchuk on 17.12.2016.
 */

public interface MessagesBulbasaurApiInterface {
    @GET("message/last")
    Call<ArrayList<LastMessageDTO>> getLastMessages();
}
