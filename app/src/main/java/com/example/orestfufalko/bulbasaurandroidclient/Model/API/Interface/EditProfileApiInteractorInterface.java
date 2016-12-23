package com.example.orestfufalko.bulbasaurandroidclient.Model.API.Interface;

import android.content.Context;

import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.EditProfileDTO;
import com.example.orestfufalko.bulbasaurandroidclient.Presenter.Interface.EditProfileApiInteractorListener;

/**
 * Created by orestfufalko on 18.12.2016.
 */

public interface EditProfileApiInteractorInterface {

    void editProfile(EditProfileDTO editProfileDTO, EditProfileApiInteractorListener listener, Context context);
}
