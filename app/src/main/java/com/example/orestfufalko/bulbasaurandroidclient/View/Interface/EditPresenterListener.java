package com.example.orestfufalko.bulbasaurandroidclient.View.Interface;

import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.SignUpResponseDTO;

/**
 * Created by orestfufalko on 19.12.2016.
 */

public interface EditPresenterListener {
    void onEditProfileRequestSuccess();
    void onEditProfileRequestFailure(SignUpResponseDTO responseDTO);
    void onEditProfileError();
}
