package com.example.orestfufalko.bulbasaurandroidclient.Presenter.Interface;

import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.AllMessageResponseDTO;

/**
 * Created by Vitalii Khorobchuk on 17.12.2016.
 */

public interface DialogMessagesRequestListener {
    void onGetMessagesSuccess(AllMessageResponseDTO allMessages);
    void onSendMessageSuccess();
}
