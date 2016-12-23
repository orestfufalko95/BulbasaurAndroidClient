package com.example.orestfufalko.bulbasaurandroidclient.Model.API.Interface;

import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.MessageInputDTO;
import com.example.orestfufalko.bulbasaurandroidclient.Presenter.Interface.DialogMessagesRequestListener;

/**
 * Created by Vitalii Khorobchuk on 17.12.2016.
 */

public interface DialogApiInteractorInterface {
    void getAllMessages(int friendId, DialogMessagesRequestListener messageRequest);
    void sendMessage(MessageInputDTO messageInput, DialogMessagesRequestListener messagesRequest);
    void sendFirstMessage(MessageInputDTO messageInput, DialogMessagesRequestListener messagesRequest);
}
