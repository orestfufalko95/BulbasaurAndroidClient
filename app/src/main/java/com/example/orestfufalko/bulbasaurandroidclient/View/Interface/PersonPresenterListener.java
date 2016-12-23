package com.example.orestfufalko.bulbasaurandroidclient.View.Interface;

import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.UserProfileDTO;

/**
 * Created by orestfufalko on 04.12.2016.
 */

public interface PersonPresenterListener  {
    void onPersonByIdPresenterRequestSuccess(UserProfileDTO profileDTO);
    void onPersonByIdPresenterRequestFailure(long id, int statusCode);

}
