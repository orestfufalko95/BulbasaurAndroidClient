package com.example.orestfufalko.bulbasaurandroidclient.Presenter.Interface;

import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.UserInfoForSearchDTO;

import java.util.ArrayList;

/**
 * Created by orestfufalko on 17.12.2016.
 */

public interface FriendApiInteractorListener {
    void OnFriendsByIdApiRequestSuccess(ArrayList<UserInfoForSearchDTO> friends);

    void OnFriendsByIdApiRequestFailure(long id, int statusCode);
}
