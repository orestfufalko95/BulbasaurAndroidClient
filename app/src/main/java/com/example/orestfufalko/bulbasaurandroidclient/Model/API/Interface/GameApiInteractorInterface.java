package com.example.orestfufalko.bulbasaurandroidclient.Model.API.Interface;

import com.example.orestfufalko.bulbasaurandroidclient.Presenter.Interface.GameApiInteractorListener;

/**
 * Created by orestfufalko on 17.12.2016.
 */

public interface GameApiInteractorInterface {

    void getUsersByGameId(long id, GameApiInteractorListener listener);
    void addGame(long gameId, GameApiInteractorListener listener);
    void deleteGame(long gameId, GameApiInteractorListener listener);
}
