package com.example.orestfufalko.bulbasaurandroidclient.View.Interface;

import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.UserInfoForSearchDTO;

import java.util.ArrayList;

/**
 * Created by orestfufalko on 17.12.2016.
 */

public interface GamePresenterListener{
    void onAddGameSuccess();
    void onAddGameFailure();

    void onDeleteGameSuccess();
    void onDeleteGameFailure();

    void onGetUsersByGameSuccess(ArrayList<UserInfoForSearchDTO> users);
    void onGetUsersByNameFailure();
}
