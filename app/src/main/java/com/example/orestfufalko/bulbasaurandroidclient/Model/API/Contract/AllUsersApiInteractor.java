package com.example.orestfufalko.bulbasaurandroidclient.Model.API.Contract;

import com.example.orestfufalko.bulbasaurandroidclient.Model.API.Interface.AllGamesBulbasaurApiInterface;
import com.example.orestfufalko.bulbasaurandroidclient.Model.API.Interface.AllUsersApiInteractorInterface;
import com.example.orestfufalko.bulbasaurandroidclient.Model.API.Interface.AllUsersBulbasaurApiInterface;
import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.GameDTO;
import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.UserInfoForSearchDTO;
import com.example.orestfufalko.bulbasaurandroidclient.Presenter.Interface.AllUsersApiInteractorListener;

import java.net.HttpURLConnection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by orestfufalko on 19.12.2016.
 */

public class AllUsersApiInteractor implements AllUsersApiInteractorInterface {

private AllUsersBulbasaurApiInterface allUsersBulbasaurApiInterface;

    public AllUsersApiInteractor(AllUsersBulbasaurApiInterface allUsersBulbasaurApiInterface) {
        this.allUsersBulbasaurApiInterface = allUsersBulbasaurApiInterface;
    }

    @Override
    public void getAllUsers(final AllUsersApiInteractorListener listener) {
        Call<List<UserInfoForSearchDTO>> call = allUsersBulbasaurApiInterface.getAllUsers();
        call.enqueue(new Callback<List<UserInfoForSearchDTO>>() {
            @Override
            public void onResponse(Call<List<UserInfoForSearchDTO>> call, Response<List<UserInfoForSearchDTO>> response) {
                int code = response.code();
                if (code == HttpURLConnection.HTTP_OK){
                    List<UserInfoForSearchDTO> users = null;
                    try {
                        users = response.body();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (users != null){
                        listener.onAllUsersRequestSuccess(users);
                    } else {
                        listener.onAllRequestFailure(code);
                    }
                } else {
                    listener.onAllRequestFailure(code);
                }
            }

            @Override
            public void onFailure(Call<List<UserInfoForSearchDTO>> call, Throwable t) {
                listener.onAllRequestFailure(HttpURLConnection.HTTP_INTERNAL_ERROR);
            }
        });
    }
}
