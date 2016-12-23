package com.example.orestfufalko.bulbasaurandroidclient.Presenter.Contract;

import com.example.orestfufalko.bulbasaurandroidclient.Event.OnUnauthorizedEvent;
import com.example.orestfufalko.bulbasaurandroidclient.Model.API.Interface.PersonApiInteractorInterface;
import com.example.orestfufalko.bulbasaurandroidclient.Presenter.Interface.PersonApiInteractorRequestListener;
import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.UserProfileDTO;
import com.example.orestfufalko.bulbasaurandroidclient.Presenter.Interface.PersonPresenterInterface;

import org.greenrobot.eventbus.EventBus;

import java.net.HttpURLConnection;

/**
 * Created by orestfufalko on 03.12.2016.
 */

public class PersonPresenter implements PersonPresenterInterface, PersonApiInteractorRequestListener {

    private PersonApiInteractorInterface personApiInteractor;
    private com.example.orestfufalko.bulbasaurandroidclient.View.Interface.PersonPresenterListener personPresenterListener;

    public PersonPresenter(PersonApiInteractorInterface interactor, com.example.orestfufalko.bulbasaurandroidclient.View.Interface.PersonPresenterListener personPresenterListener) {
        personApiInteractor = interactor;
        this.personPresenterListener = personPresenterListener;
    }

    @Override
    public void getPersonInfoById(long id) {
        personApiInteractor.getUserProfileDTOById(id, this);
    }

    @Override
    public void OnPersonByIdApiRequestSuccess(UserProfileDTO userProfileDTO) {
        personPresenterListener.onPersonByIdPresenterRequestSuccess(userProfileDTO);
    }

    @Override
    public void OnPersonByIdApiRequestFailure(long id, int statusCode) {
        if (statusCode == HttpURLConnection.HTTP_UNAUTHORIZED){
            EventBus.getDefault().post(new OnUnauthorizedEvent());
        } else {
            personPresenterListener.onPersonByIdPresenterRequestFailure(id, statusCode);
        }
    }

}
