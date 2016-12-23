package com.example.orestfufalko.bulbasaurandroidclient.Presenter.Contract;

import android.util.Log;

import com.example.orestfufalko.bulbasaurandroidclient.Event.OnUnauthorizedEvent;
import com.example.orestfufalko.bulbasaurandroidclient.Model.API.Interface.AllGamesApiInteractorInterface;
import com.example.orestfufalko.bulbasaurandroidclient.Model.API.Interface.EditProfileBulbasaurApiInterface;
import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.GameDTO;
import com.example.orestfufalko.bulbasaurandroidclient.Presenter.Interface.AllGamesApiInteractorListener;
import com.example.orestfufalko.bulbasaurandroidclient.Presenter.Interface.AllGamesPresenterInterface;
import com.example.orestfufalko.bulbasaurandroidclient.View.Interface.AllGamesPresenterListener;
import com.example.orestfufalko.bulbasaurandroidclient.utils.RetrofitUtil;

import org.greenrobot.eventbus.EventBus;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by orestfufalko on 18.12.2016.
 */

public class AllGamesPresenter implements AllGamesPresenterInterface, AllGamesApiInteractorListener {

    private AllGamesApiInteractorInterface allGamesApiInteractor;
    private AllGamesPresenterListener presenterListener;

    public AllGamesPresenter(AllGamesApiInteractorInterface allGamesApiInteractor, AllGamesPresenterListener allGamesPresenter) {
        this.allGamesApiInteractor = allGamesApiInteractor;
        this.presenterListener = allGamesPresenter;


    }

    @Override
    public void getAllGames() {
        Log.i("TAG", "getAllGames: ");
        allGamesApiInteractor.getAllGames(this);
    }

    @Override
    public void onRequestAllGamesSuccess(List<GameDTO> games) {
        Log.i("TAG", "onRequestAllGamesSuccess: ");
        presenterListener.onAllGamesPresenterRequestSuccess((ArrayList<GameDTO>) games);
    }

    @Override
    public void onRequestAllGamesFailure(int responseCode) {
        if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED){
            EventBus.getDefault().post(new OnUnauthorizedEvent());
        } else {
            presenterListener.onAllGamesPresenterRequestFailure();
        }
    }

}
