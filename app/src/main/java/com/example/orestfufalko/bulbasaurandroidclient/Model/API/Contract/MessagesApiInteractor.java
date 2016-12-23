package com.example.orestfufalko.bulbasaurandroidclient.Model.API.Contract;

import android.util.Log;

import com.example.orestfufalko.bulbasaurandroidclient.Model.API.Interface.MessageApiInteractorInterface;
import com.example.orestfufalko.bulbasaurandroidclient.Model.API.Interface.MessagesBulbasaurApiInterface;
import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.LastMessageDTO;
import com.example.orestfufalko.bulbasaurandroidclient.Presenter.Interface.GetMessageRequestListener;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Vitalii Khorobchuk on 17.12.2016.
 */

public class MessagesApiInteractor implements MessageApiInteractorInterface {

    private MessagesBulbasaurApiInterface messagesBulbasaurApiInterface;
    private final String TAG = "MessagesTAG";

    public MessagesApiInteractor(MessagesBulbasaurApiInterface messagesBulbasaurApiInterface) {
        this.messagesBulbasaurApiInterface = messagesBulbasaurApiInterface;
    }

    @Override
    public void getLastMessages(final GetMessageRequestListener messageRequest) {
        Log.i(TAG, "Start - getting");
        Call<ArrayList<LastMessageDTO>> callUserProfile = messagesBulbasaurApiInterface.getLastMessages();
        Log.i(TAG, "sended");
        callUserProfile.enqueue(new Callback<ArrayList<LastMessageDTO>>() {
            @Override
            public void onResponse(Call<ArrayList<LastMessageDTO>> call, Response<ArrayList<LastMessageDTO>> response) {
                Log.i(TAG, "CAME -" + response.code());
                if (response.code() == 200){
                    ArrayList<LastMessageDTO> lastMessages = response.body();
                    messageRequest.onSuccess(lastMessages);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<LastMessageDTO>> call, Throwable t) {
                Log.i(TAG, "onFailure: failed");
                Log.i(TAG, t.getMessage());
                Log.i(TAG, Arrays.toString(t.getStackTrace()));
            }
        });
    }
}
