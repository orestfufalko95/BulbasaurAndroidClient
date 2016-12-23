package com.example.orestfufalko.bulbasaurandroidclient.Presenter.Interface;

import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.UserInfoForSearchDTO;

import java.util.List;

/**
 * Created by orestfufalko on 19.12.2016.
 */

public interface AllUsersApiInteractorListener {
    void onAllUsersRequestSuccess(List<UserInfoForSearchDTO> users);
    void onAllRequestFailure(int statusCode);
}
