package com.example.orestfufalko.bulbasaurandroidclient.Model.API.Contract;

import android.util.Log;

import com.example.orestfufalko.bulbasaurandroidclient.Model.API.Interface.AllGamesApiInteractorInterface;
import com.example.orestfufalko.bulbasaurandroidclient.Model.API.Interface.AllGamesBulbasaurApiInterface;
import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.GameDTO;
import com.example.orestfufalko.bulbasaurandroidclient.Presenter.Interface.AllGamesApiInteractorListener;

import java.net.HttpURLConnection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by orestfufalko on 17.12.2016.
 */

public class AllGamesApiInteractor implements AllGamesApiInteractorInterface {

    private final String TAG = getClass().getSimpleName();

    private AllGamesBulbasaurApiInterface allGamesBulbasaurApiInterface;

    public AllGamesApiInteractor(AllGamesBulbasaurApiInterface allGamesBulbasaurApiInterface) {
        this.allGamesBulbasaurApiInterface = allGamesBulbasaurApiInterface;
    }

    @Override
    public void getAllGames(final AllGamesApiInteractorListener listener) {
        Log.i(TAG, "getAllGames: start");
        Call<List<GameDTO>> call = allGamesBulbasaurApiInterface.getAllGames();
        call.enqueue(new Callback<List<GameDTO>>() {

            @Override
            public void onResponse(Call<List<GameDTO>> call, Response<List<GameDTO>> response) {
                int responseCode = response.code();
                if (responseCode == HttpURLConnection.HTTP_OK){
                    Log.i(TAG, "onResponse: HTTP_OK");
                    List<GameDTO> games = null;
                    try {
                        games = response.body();
                    } catch (Exception e){
                        Log.e(TAG, "onResponse: ", e);
                    }
                    if (games != null){
                        listener.onRequestAllGamesSuccess(games);
                    } else {
                        listener.onRequestAllGamesFailure(responseCode);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<GameDTO>> call, Throwable t) {
                Log.i(TAG, "onFailure: ");
                listener.onRequestAllGamesFailure(HttpURLConnection.HTTP_INTERNAL_ERROR);
            }
        });
    }
}
