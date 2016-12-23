package com.example.orestfufalko.bulbasaurandroidclient.Presenter.Interface;

/**
 * Created by orestfufalko on 17.12.2016.
 */

public interface GamePresenterInterface {
    void addGame(long id);
    void deleteGame(long id);
//    void getAllGames();
    void getUsersByGame(long id);
}
