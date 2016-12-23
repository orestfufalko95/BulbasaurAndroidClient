package com.example.orestfufalko.bulbasaurandroidclient.View.Interface;

import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.GameDTO;

import java.util.ArrayList;

/**
 * Created by orestfufalko on 18.12.2016.
 */

public interface AllGamesPresenterListener {
    void onAllGamesPresenterRequestSuccess(ArrayList<GameDTO> games);
    void onAllGamesPresenterRequestFailure();
}
