package com.example.orestfufalko.bulbasaurandroidclient.Presenter.Contract;

import com.example.orestfufalko.bulbasaurandroidclient.Model.API.Interface.MessageApiInteractorInterface;
import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.LastMessageDTO;
import com.example.orestfufalko.bulbasaurandroidclient.Presenter.Interface.GetMessageRequestListener;
import com.example.orestfufalko.bulbasaurandroidclient.Presenter.Interface.MessagesPresenterInterface;
import com.example.orestfufalko.bulbasaurandroidclient.View.Interface.MessagesViewInterface;

import java.util.ArrayList;

/**
 * Created by Vitalii Khorobchuk on 17.12.2016.
 */

public class MessagesPresenter implements MessagesPresenterInterface, GetMessageRequestListener {

    private MessageApiInteractorInterface messageApiInteractorInterface;
    private MessagesViewInterface messagesView;

    public MessagesPresenter(MessageApiInteractorInterface messageApiInteractorInterface, MessagesViewInterface messagesView) {
        this.messageApiInteractorInterface = messageApiInteractorInterface;
        this.messagesView = messagesView;
    }

    @Override
    public void GetLastMessages() {
        messageApiInteractorInterface.getLastMessages(this);
    }

    @Override
    public void onSuccess(ArrayList<LastMessageDTO> lastMessages) {
        messagesView.showMessages(lastMessages);
    }
}
