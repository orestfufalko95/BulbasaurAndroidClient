package com.example.orestfufalko.bulbasaurandroidclient.View.Interface;

import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.UserInfoForSearchDTO;

import java.util.ArrayList;

/**
 * Created by orestfufalko on 17.12.2016.
 */

public interface FriendPresenterListener   {
    void onFriendPresenterRequestSuccess(ArrayList<UserInfoForSearchDTO> list);
    void onFriendPresenterRequestFailure(long id, int statusCode);
}
