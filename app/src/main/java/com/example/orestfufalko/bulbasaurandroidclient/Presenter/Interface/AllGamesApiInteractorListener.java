package com.example.orestfufalko.bulbasaurandroidclient.Presenter.Interface;

import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.GameDTO;

import java.util.List;

/**
 * Created by orestfufalko on 18.12.2016.
 */

public interface AllGamesApiInteractorListener {
    void onRequestAllGamesSuccess(List<GameDTO> games);
    void onRequestAllGamesFailure(int responseCode);
}
