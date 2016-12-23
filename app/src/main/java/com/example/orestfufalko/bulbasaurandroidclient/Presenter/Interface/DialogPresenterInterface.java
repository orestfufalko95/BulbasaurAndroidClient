package com.example.orestfufalko.bulbasaurandroidclient.Presenter.Interface;

import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.MessageInputDTO;


public interface DialogPresenterInterface {
    void getMessages();
    void sendMessage(MessageInputDTO message);
}
