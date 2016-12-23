package com.example.orestfufalko.bulbasaurandroidclient.Presenter.Contract;

import com.example.orestfufalko.bulbasaurandroidclient.Event.OnUnauthorizedEvent;
import com.example.orestfufalko.bulbasaurandroidclient.Model.API.Interface.AllUsersApiInteractorInterface;
import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.UserInfoForSearchDTO;
import com.example.orestfufalko.bulbasaurandroidclient.Presenter.Interface.AllUsersApiInteractorListener;
import com.example.orestfufalko.bulbasaurandroidclient.Presenter.Interface.AllUsersPresenterInterface;
import com.example.orestfufalko.bulbasaurandroidclient.View.Interface.AllUsersPresenterListener;

import org.greenrobot.eventbus.EventBus;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by orestfufalko on 19.12.2016.
 */

public class AllUsersPresenter implements AllUsersApiInteractorListener, AllUsersPresenterInterface {

    private AllUsersApiInteractorInterface allUsersApiInteractor;
    private AllUsersPresenterListener listener;

    public AllUsersPresenter(AllUsersApiInteractorInterface allUsersApiInteractor, AllUsersPresenterListener listener) {
        this.allUsersApiInteractor = allUsersApiInteractor;
        this.listener = listener;
    }

    @Override
    public void onAllUsersRequestSuccess(List<UserInfoForSearchDTO> users) {
        listener.onRequestAllUsersSuccess((ArrayList<UserInfoForSearchDTO>) users);
    }

    @Override
    public void onAllRequestFailure(int statusCode) {
        if (statusCode == HttpURLConnection.HTTP_UNAUTHORIZED){
            EventBus.getDefault().post(new OnUnauthorizedEvent());
        } else {
            listener.onAllUsersRequestFailure();
        }
    }

    @Override
    public void getAllUsers() {
        allUsersApiInteractor.getAllUsers(this);
    }
}
