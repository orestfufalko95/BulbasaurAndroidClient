package com.example.orestfufalko.bulbasaurandroidclient.Presenter.Interface;

import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.LastMessageDTO;

import java.util.ArrayList;

/**
 * Created by Vitalii Khorobchuk on 17.12.2016.
 */

public interface GetMessageRequestListener {
    void onSuccess(ArrayList<LastMessageDTO> lastMessages);
}
