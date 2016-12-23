package com.example.orestfufalko.bulbasaurandroidclient.Presenter.Interface;

import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.UserProfileDTO;

/**
 * Created by orestfufalko on 03.12.2016.
 */
public interface PersonApiInteractorRequestListener  {
    void OnPersonByIdApiRequestSuccess(UserProfileDTO profileDTO);

    void OnPersonByIdApiRequestFailure(long id, int statusCode);
}
