package com.example.orestfufalko.bulbasaurandroidclient.Model.API.Interface;

import com.example.orestfufalko.bulbasaurandroidclient.Presenter.Interface.FriendApiInteractorListener;

/**
 * Created by orestfufalko on 17.12.2016.
 */

public interface FriendApiInteractorInterface {
    void getFriendsById(long userId, FriendApiInteractorListener listener);
}
