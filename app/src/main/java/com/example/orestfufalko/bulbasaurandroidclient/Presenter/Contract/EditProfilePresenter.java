package com.example.orestfufalko.bulbasaurandroidclient.Presenter.Contract;

import android.content.Context;

import com.example.orestfufalko.bulbasaurandroidclient.Event.OnUnauthorizedEvent;
import com.example.orestfufalko.bulbasaurandroidclient.Model.API.Interface.EditProfileApiInteractorInterface;
import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.EditProfileDTO;
import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.SignUpResponseDTO;
import com.example.orestfufalko.bulbasaurandroidclient.Presenter.Interface.EditProfileApiInteractorListener;
import com.example.orestfufalko.bulbasaurandroidclient.Presenter.Interface.EditProfilePresenterInterface;
import com.example.orestfufalko.bulbasaurandroidclient.View.Interface.EditPresenterListener;

import org.greenrobot.eventbus.EventBus;

import static java.net.HttpURLConnection.HTTP_UNAUTHORIZED;

/**
 * Created by orestfufalko on 19.12.2016.
 */

public class EditProfilePresenter implements EditProfilePresenterInterface, EditProfileApiInteractorListener{

    private EditPresenterListener editPresenterListener;
    private EditProfileApiInteractorInterface editProfileApiInteractor;

    public EditProfilePresenter(EditPresenterListener editPresenterListener, EditProfileApiInteractorInterface editProfileApiInteractor) {
        this.editPresenterListener = editPresenterListener;
        this.editProfileApiInteractor = editProfileApiInteractor;
    }

    @Override
    public void editProfile(EditProfileDTO editProfileDTO, Context context) {
        editProfileApiInteractor.editProfile(editProfileDTO, this, context);
    }

    @Override
    public void onEditProfileRequestFailure(int statusCode, SignUpResponseDTO responseDTO) {
         if (statusCode == HTTP_UNAUTHORIZED){
            EventBus.getDefault().post(new OnUnauthorizedEvent());
        } else {
            editPresenterListener.onEditProfileRequestFailure(responseDTO);
        }
    }

    @Override
    public void onEditProfileRequestSuccess() {
        editPresenterListener.onEditProfileRequestSuccess();
    }

    @Override
    public void onEditProfileErrorDuringRequest() {
        editPresenterListener.onEditProfileError();
    }

}
