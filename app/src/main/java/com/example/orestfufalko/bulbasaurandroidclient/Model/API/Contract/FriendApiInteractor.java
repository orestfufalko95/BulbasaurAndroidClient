package com.example.orestfufalko.bulbasaurandroidclient.Model.API.Contract;

import android.util.Log;

import com.example.orestfufalko.bulbasaurandroidclient.Model.API.Interface.FriendApiInteractorInterface;
import com.example.orestfufalko.bulbasaurandroidclient.Presenter.Interface.FriendApiInteractorListener;
import com.example.orestfufalko.bulbasaurandroidclient.Model.API.Interface.FriendBulbasaurApiInterface;
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

public class FriendApiInteractor implements FriendApiInteractorInterface {

    private final String TAG = getClass().getSimpleName();
    private FriendBulbasaurApiInterface friendBulbasaurApiInterface;

    public FriendApiInteractor(FriendBulbasaurApiInterface friendBulbasaurApiInterface) {
        this.friendBulbasaurApiInterface = friendBulbasaurApiInterface;
    }

    @Override
    public void getFriendsById(final long userId, final FriendApiInteractorListener listener) {
        String userIdStr = "";
        if (userId >= 0){
            userIdStr = String.valueOf(userId);
        }

        Call<List<UserInfoForSearchDTO>> friendsCall = friendBulbasaurApiInterface.getFriendsById(userIdStr);

        friendsCall.enqueue(new Callback<List<UserInfoForSearchDTO>>() {
            @Override
            public void onResponse(Call<List<UserInfoForSearchDTO>> call, Response<List<UserInfoForSearchDTO>> response) {
                int responseStatusCode = response.code();
                List<UserInfoForSearchDTO> friends = null;

                if (responseStatusCode == HttpURLConnection.HTTP_OK){
                    try {
                        friends = response.body();
                    } catch (Exception e){
                        Log.e(TAG, "onResponse: ", e);
                        listener.OnFriendsByIdApiRequestFailure(userId, responseStatusCode);
                    }
                    if (friends !=null){
                        listener.OnFriendsByIdApiRequestSuccess((ArrayList<UserInfoForSearchDTO>) friends);
                    } else {
                        listener.OnFriendsByIdApiRequestFailure(userId, responseStatusCode);
                    }
                } else {
                    listener.OnFriendsByIdApiRequestFailure(userId, responseStatusCode);
                }
            }

            @Override
            public void onFailure(Call<List<UserInfoForSearchDTO>> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                listener.OnFriendsByIdApiRequestFailure(userId, HttpURLConnection.HTTP_INTERNAL_ERROR);
            }
        });
    }
}
