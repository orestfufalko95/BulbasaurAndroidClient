package com.example.orestfufalko.bulbasaurandroidclient.Presenter.Interface;

import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.UserInfoForSearchDTO;

import java.util.ArrayList;

/**
 * Created by orestfufalko on 17.12.2016.
 */

public interface GameApiInteractorListener {

    void onAddGameSuccess();
    void onAddGameFailure(int responseStatusCode);

    void onDeleteGameSuccess();
    void onDeleteGameFailure(int responseStatusCode);

//    void onRequestAllGamesSuccess(ArrayList<GameDTO> games);
//    void onRequestAllGamesFailure(int responseStatusCode);

    void onGetUsersByGameSuccess(ArrayList<UserInfoForSearchDTO> users);
    void onGetUsersByNameFailure(int responseStatusCode);
}
