package com.example.orestfufalko.bulbasaurandroidclient.View.Interface;

import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.UserInfoForSearchDTO;

import java.util.ArrayList;

/**
 * Created by orestfufalko on 19.12.2016.
 */

public interface AllUsersPresenterListener {
    void onRequestAllUsersSuccess(ArrayList<UserInfoForSearchDTO> users);
    void onAllUsersRequestFailure();
}
