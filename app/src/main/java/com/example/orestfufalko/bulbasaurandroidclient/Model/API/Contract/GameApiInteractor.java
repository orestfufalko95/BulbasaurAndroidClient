package com.example.orestfufalko.bulbasaurandroidclient.Model.API.Contract;

import android.util.Log;

import com.example.orestfufalko.bulbasaurandroidclient.Model.API.Interface.GameApiInteractorInterface;
import com.example.orestfufalko.bulbasaurandroidclient.Presenter.Interface.GameApiInteractorListener;
import com.example.orestfufalko.bulbasaurandroidclient.Model.API.Interface.GameBulbasaurApiInterface;
import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.UserInfoForSearchDTO;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by orestfufalko on 17.12.2016.
 */

public class GameApiInteractor implements GameApiInteractorInterface {

    private final String TAG = getClass().getSimpleName();
    private GameBulbasaurApiInterface gameBulbasaurApi;

    public GameApiInteractor(GameBulbasaurApiInterface gameBulbasaurApi) {
        this.gameBulbasaurApi = gameBulbasaurApi;
    }

    @Override
    public void getUsersByGameId(long id, final GameApiInteractorListener listener) {
        Log.i(TAG, "getUsersByGameId: id: " + id);
        Call<List<UserInfoForSearchDTO>> call = gameBulbasaurApi.getUsersByGame(String.valueOf(id));

        call.enqueue(new Callback<List<UserInfoForSearchDTO>>() {
            @Override
            public void onResponse(Call<List<UserInfoForSearchDTO>> call, Response<List<UserInfoForSearchDTO>> response) {
                int responseStatusCode = response.code();

                if (responseStatusCode == HttpURLConnection.HTTP_OK){
                    List<UserInfoForSearchDTO> users = null;
                    try {
                        users = response.body();
                    } catch (Exception e){
                        Log.e(TAG, "onResponse: ", e);
                        listener.onGetUsersByNameFailure( responseStatusCode);
                    }
                    if (users !=null){
                        Log.i(TAG, "onResponse: success");
                        listener.onGetUsersByGameSuccess((ArrayList<UserInfoForSearchDTO>) users);
                    } else {
                        Log.i(TAG, "onResponse: failure");
                        listener.onGetUsersByNameFailure( responseStatusCode);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<UserInfoForSearchDTO>> call, Throwable t) {
                listener.onGetUsersByNameFailure(HttpURLConnection.HTTP_INTERNAL_ERROR);
            }
        });
    }

    @Override
    public void addGame(long gameId, final GameApiInteractorListener listener) {
        Call<Void> call = gameBulbasaurApi.addGameToUser(gameId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                int responseStatusCode = response.code();
                if (responseStatusCode == HttpURLConnection.HTTP_OK){
                    listener.onAddGameSuccess();
                } else {
                    listener.onAddGameFailure(responseStatusCode);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                listener.onAddGameFailure(HttpURLConnection.HTTP_INTERNAL_ERROR);
            }
        });
    }

    @Override
    public void deleteGame(long gameId, final GameApiInteractorListener listener) {
        Call<Void> call = gameBulbasaurApi.deleteGameFromUser((int) gameId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                int responseStatusCode = response.code();
                if (responseStatusCode == HttpURLConnection.HTTP_OK){
                    listener.onDeleteGameSuccess();
                } else {
                    Log.i(TAG, "onResponse: fail call request: " + call.request());
                    listener.onDeleteGameFailure(responseStatusCode);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.i(TAG, "onFailure: ");
                listener.onDeleteGameFailure(HttpURLConnection.HTTP_INTERNAL_ERROR);
            }
        });
    }
}
