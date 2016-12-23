package com.example.orestfufalko.bulbasaurandroidclient.Presenter.Interface;

import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.SignUpResponseDTO;

/**
 * Created by orestfufalko on 19.12.2016.
 */

public interface EditProfileApiInteractorListener {
    void onEditProfileRequestFailure(int statusCode, SignUpResponseDTO responseDTO);
    void onEditProfileRequestSuccess();
    void onEditProfileErrorDuringRequest();
}
