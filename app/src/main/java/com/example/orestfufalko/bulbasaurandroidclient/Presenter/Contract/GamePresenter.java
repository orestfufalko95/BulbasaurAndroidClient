package com.example.orestfufalko.bulbasaurandroidclient.Presenter.Contract;

import android.util.Log;

import com.example.orestfufalko.bulbasaurandroidclient.Event.OnUnauthorizedEvent;
import com.example.orestfufalko.bulbasaurandroidclient.Model.API.Interface.GameApiInteractorInterface;
import com.example.orestfufalko.bulbasaurandroidclient.Presenter.Interface.GameApiInteractorListener;
import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.UserInfoForSearchDTO;
import com.example.orestfufalko.bulbasaurandroidclient.Presenter.Interface.GamePresenterInterface;
import com.example.orestfufalko.bulbasaurandroidclient.View.Interface.GamePresenterListener;

import org.greenrobot.eventbus.EventBus;

import java.net.HttpURLConnection;
import java.util.ArrayList;

/**
 * Created by orestfufalko on 17.12.2016.
 */

public class GamePresenter implements GamePresenterInterface, GameApiInteractorListener {

    private GameApiInteractorInterface gameApiInteractor;
    private GamePresenterListener gamePresenterListener;

    public GamePresenter(GameApiInteractorInterface gameApiInteractor, GamePresenterListener gamePresenterListener) {
        this.gameApiInteractor = gameApiInteractor;
        this.gamePresenterListener = gamePresenterListener;
    }

    @Override
    public void addGame(long id) {
        gameApiInteractor.addGame(id, this);
    }

    @Override
    public void deleteGame(long id) {
        gameApiInteractor.deleteGame(id,this);
    }

    @Override
    public void getUsersByGame(long id) {
        gameApiInteractor.getUsersByGameId(id, this);
    }

    //listeners-------------------------------------
    @Override
    public void onAddGameSuccess() {
        gamePresenterListener.onAddGameSuccess();
    }

    @Override
    public void onAddGameFailure(int responseStatusCode) {
        if (responseStatusCode == HttpURLConnection.HTTP_UNAUTHORIZED){
            EventBus.getDefault().post(new OnUnauthorizedEvent());
        } else {
            gamePresenterListener.onAddGameFailure();
        }
    }

    @Override
    public void onDeleteGameSuccess() {
        gamePresenterListener.onDeleteGameSuccess();
    }

    @Override
    public void onDeleteGameFailure(int responseStatusCode) {
        if (responseStatusCode == HttpURLConnection.HTTP_UNAUTHORIZED){
            EventBus.getDefault().post(new OnUnauthorizedEvent());
        } else {
            Log.i(getClass().getSimpleName(), "onDeleteGameFailure: code: " + responseStatusCode);
            gamePresenterListener.onDeleteGameFailure();
        }
    }

    @Override
    public void onGetUsersByGameSuccess(ArrayList<UserInfoForSearchDTO> users) {
        gamePresenterListener.onGetUsersByGameSuccess(users);
    }

    @Override
    public void onGetUsersByNameFailure(int responseStatusCode) {
        if (responseStatusCode == HttpURLConnection.HTTP_UNAUTHORIZED){
            EventBus.getDefault().post(new OnUnauthorizedEvent());

        } else {
            gamePresenterListener.onGetUsersByNameFailure();
        }
    }

}
