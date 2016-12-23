package com.example.orestfufalko.bulbasaurandroidclient.Model.API.Interface;

import com.example.orestfufalko.bulbasaurandroidclient.Presenter.Interface.PersonApiInteractorRequestListener;

/**
 * Created by orestfufalko on 03.12.2016.
 */

public interface PersonApiInteractorInterface {
    void getUserProfileDTOById(long userId, PersonApiInteractorRequestListener listener);
}
