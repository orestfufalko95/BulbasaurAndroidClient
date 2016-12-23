package com.example.orestfufalko.bulbasaurandroidclient.Presenter.Contract;

import android.util.Log;

import com.example.orestfufalko.bulbasaurandroidclient.Model.API.Interface.DialogApiInteractorInterface;
import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.AllMessageResponseDTO;
import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.Message;
import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.MessageInputDTO;
import com.example.orestfufalko.bulbasaurandroidclient.Presenter.Interface.DialogMessagesRequestListener;
import com.example.orestfufalko.bulbasaurandroidclient.Presenter.Interface.DialogPresenterInterface;
import com.example.orestfufalko.bulbasaurandroidclient.View.Interface.DialogViewInterface;
import com.example.orestfufalko.bulbasaurandroidclient.utils.TimeParseUtil;

/**
 * Created by Vitalii Khorobchuk on 17.12.2016.
 */

public class DialogPresenter implements DialogPresenterInterface, DialogMessagesRequestListener {

    private final String TAG = "DialogPresenterTAG";

    private DialogViewInterface dialogView;
    private DialogApiInteractorInterface dialogApi;
    private int friendId;
    private int messagesCount;

    public DialogPresenter(DialogViewInterface dialogView, DialogApiInteractorInterface dialogApi, int friendId) {
        this.dialogView = dialogView;
        this.dialogApi = dialogApi;
        this.friendId = friendId;
        this.messagesCount = -1;
    }

    @Override
    public void onGetMessagesSuccess(AllMessageResponseDTO allMessages) {
        messagesCount = allMessages.getMessages().size();
        Log.i(TAG, "onGetMessagesSuccess: " + messagesCount);
        dialogView.showMessages(allMessages);
    }

    @Override
    public void onSendMessageSuccess() {
        dialogApi.getAllMessages(friendId,this);
    }

    @Override
    public void getMessages() {
        dialogApi.getAllMessages(friendId, this);
    }


    @Override
    public void sendMessage(MessageInputDTO message)
    {
        if (messagesCount == 0){
            dialogApi.sendFirstMessage(message, this);
        }
        else{
            dialogApi.sendMessage(message, this);
        }

    }
}
