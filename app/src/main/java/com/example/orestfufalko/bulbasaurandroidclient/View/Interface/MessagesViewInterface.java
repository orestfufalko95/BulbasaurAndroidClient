package com.example.orestfufalko.bulbasaurandroidclient.View.Interface;

import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.LastMessageDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vitalii Khorobchuk on 17.12.2016.
 */

public interface MessagesViewInterface {
    void showMessages(ArrayList<LastMessageDTO> lastMessages);
}
