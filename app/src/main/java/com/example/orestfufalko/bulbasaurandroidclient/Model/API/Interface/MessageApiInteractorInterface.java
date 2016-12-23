package com.example.orestfufalko.bulbasaurandroidclient.Model.API.Interface;

import com.example.orestfufalko.bulbasaurandroidclient.Presenter.Interface.GetMessageRequestListener;

/**
 * Created by Vitalii Khorobchuk on 17.12.2016.
 */

public interface MessageApiInteractorInterface {
    void getLastMessages(GetMessageRequestListener messageRequest);
}
