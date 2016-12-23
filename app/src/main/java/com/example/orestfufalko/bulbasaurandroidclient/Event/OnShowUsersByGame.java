package com.example.orestfufalko.bulbasaurandroidclient.Event;

import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.UserInfoForSearchDTO;

import java.util.List;

/**
 * Created by orestfufalko on 18.12.2016.
 */

public class OnShowUsersByGame {
    public List<UserInfoForSearchDTO> users;

    public OnShowUsersByGame(List<UserInfoForSearchDTO> users) {
        this.users = users;
    }
}
