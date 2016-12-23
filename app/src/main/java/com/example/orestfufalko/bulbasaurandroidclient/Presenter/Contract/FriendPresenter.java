package com.example.orestfufalko.bulbasaurandroidclient.Presenter.Contract;

import com.example.orestfufalko.bulbasaurandroidclient.Event.OnUnauthorizedEvent;
import com.example.orestfufalko.bulbasaurandroidclient.Model.API.Interface.FriendApiInteractorInterface;
import com.example.orestfufalko.bulbasaurandroidclient.Presenter.Interface.FriendApiInteractorListener;
import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.UserInfoForSearchDTO;
import com.example.orestfufalko.bulbasaurandroidclient.Presenter.Interface.FriendPresenterInterface;
import com.example.orestfufalko.bulbasaurandroidclient.View.Interface.FriendPresenterListener;

import org.greenrobot.eventbus.EventBus;

import java.net.HttpURLConnection;
import java.util.ArrayList;

/**
 * Created by orestfufalko on 17.12.2016.
 */

public class FriendPresenter implements FriendPresenterInterface, FriendApiInteractorListener {

    private FriendApiInteractorInterface friendApiInteractor;
    private FriendPresenterListener friendPresenterListener;

    public FriendPresenter(FriendApiInteractorInterface friendApiInteractor, FriendPresenterListener friendPresenterListener) {
        this.friendApiInteractor = friendApiInteractor;
        this.friendPresenterListener = friendPresenterListener;
    }

    @Override
    public void getFriendsById(long id) {
        friendApiInteractor.getFriendsById(id, this);
    }

    @Override
    public void OnFriendsByIdApiRequestSuccess(ArrayList<UserInfoForSearchDTO> friends) {
        friendPresenterListener.onFriendPresenterRequestSuccess(friends);
    }

    @Override
    public void OnFriendsByIdApiRequestFailure(long id, int statusCode) {
        if (statusCode == HttpURLConnection.HTTP_UNAUTHORIZED){
            EventBus.getDefault().post(new OnUnauthorizedEvent());
        } else {
            friendPresenterListener.onFriendPresenterRequestFailure(id, statusCode);
        }
    }
}
