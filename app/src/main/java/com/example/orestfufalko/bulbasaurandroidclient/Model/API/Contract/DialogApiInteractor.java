package com.example.orestfufalko.bulbasaurandroidclient.Model.API.Contract;

import android.util.Log;

import com.example.orestfufalko.bulbasaurandroidclient.Model.API.Interface.DialogApiInteractorInterface;
import com.example.orestfufalko.bulbasaurandroidclient.Model.API.Interface.DialogBulbasaurApiInterface;
import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.AllMessageResponseDTO;
import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.MessageInputDTO;
import com.example.orestfufalko.bulbasaurandroidclient.Presenter.Interface.DialogMessagesRequestListener;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Vitalii Khorobchuk on 17.12.2016.
 */

public class DialogApiInteractor implements DialogApiInteractorInterface {

    private final String TAG = "DialogApiInteractorTAG";

    private DialogBulbasaurApiInterface dialogBulbasaurApiInterface;

    public DialogApiInteractor(DialogBulbasaurApiInterface dialogBulbasaurApiInterface) {
        this.dialogBulbasaurApiInterface = dialogBulbasaurApiInterface;
    }

    @Override
    public void getAllMessages(int friendId, final DialogMessagesRequestListener messageRequest) {
        Log.i(TAG, "start: " );
        Call<AllMessageResponseDTO> callAllMessages = dialogBulbasaurApiInterface.getAllMessages(String.valueOf(friendId));
        callAllMessages.enqueue(new Callback<AllMessageResponseDTO>() {
            @Override
            public void onResponse(Call<AllMessageResponseDTO> call, Response<AllMessageResponseDTO> response) {
                if (response.code() == 200){
                    Log.i(TAG, "resonse OK " );
                    messageRequest.onGetMessagesSuccess(response.body());
                }
                else {
                    Log.i(TAG, "onResponse: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<AllMessageResponseDTO> call, Throwable t) {
                Log.i(TAG, "onFailure: " + t.getMessage());
                Log.i(TAG, "onFailure: " + Arrays.toString(t.getStackTrace()));
            }
        });
    }

    @Override
    public void sendMessage(MessageInputDTO messageInput, final DialogMessagesRequestListener messagesRequest) {
        Call<Void> callSendMessage = dialogBulbasaurApiInterface.sendMessage(messageInput.receiverId, messageInput.message);
        callSendMessage.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.i(TAG, "onResponse: ");
                if (response.code() == 200){
                    messagesRequest.onSendMessageSuccess();
                }
                else {
                    Log.i(TAG, "onResponse: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.i(TAG, "onFailure: "  + t.getMessage());
                Log.i(TAG, "onFailure: " + Arrays.toString(t.getStackTrace()));
                Log.i(TAG, "onFailure: ");
            }
        });
    }

    @Override
    public void sendFirstMessage(MessageInputDTO messageInput, final DialogMessagesRequestListener messagesRequest) {
        Call<Void> callSenFirstMessage = dialogBulbasaurApiInterface.sendFirstMessage(messageInput.receiverId, messageInput.message);
        callSenFirstMessage.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.i(TAG, "onResponse: ");
                if (response.code() == 200){
                    messagesRequest.onSendMessageSuccess();
                }
                else {
                    Log.i(TAG, "onResponse: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.i(TAG, "onFailure: "  + t.getMessage());
                Log.i(TAG, "onFailure: " + Arrays.toString(t.getStackTrace()));
                Log.i(TAG, "onFailure: ");
            }
        });
    }
}


