package com.example.orestfufalko.bulbasaurandroidclient.Presenter.Interface;

import android.content.Context;

import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.EditProfileDTO;

/**
 * Created by orestfufalko on 19.12.2016.
 */

public interface EditProfilePresenterInterface {
    void editProfile(EditProfileDTO editProfileDTO, Context context);
}
