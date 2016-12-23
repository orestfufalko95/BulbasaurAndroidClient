package com.example.orestfufalko.bulbasaurandroidclient.Model.API.Contract;

import android.util.Log;

import com.example.orestfufalko.bulbasaurandroidclient.Model.API.Interface.PersonApiInteractorInterface;
import com.example.orestfufalko.bulbasaurandroidclient.Model.API.Interface.PersonBulbasaurApiInterface;
import com.example.orestfufalko.bulbasaurandroidclient.Presenter.Interface.PersonApiInteractorRequestListener;
import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.UserProfileDTO;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by orestfufalko on 03.12.2016.
 */

public class PersonApiInteractor implements PersonApiInteractorInterface {
    private final String TAG = getClass().getSimpleName();


    // TODO: 03.12.2016 change this thing to have ability to set any API caller
    private PersonBulbasaurApiInterface personBulbasaurApiInterface;

    public PersonApiInteractor(PersonBulbasaurApiInterface personBulbasaurApiInterface) {
        this.personBulbasaurApiInterface = personBulbasaurApiInterface;
    }

    @Override
    public void getUserProfileDTOById(final long userId, final PersonApiInteractorRequestListener listener) {
        String userIdStr = "";
        if (userId >= 0){
            userIdStr = String.valueOf(userId);
        }

        final Call<UserProfileDTO> loggedUserProfileDTOCall = personBulbasaurApiInterface.getUserProfileDTOById(userIdStr);
        loggedUserProfileDTOCall.enqueue(new Callback<UserProfileDTO>() {
            @Override
            public void onResponse(Call<UserProfileDTO> call, Response<UserProfileDTO> response) {

                int responseStatusCode = response.code();
                UserProfileDTO userProfileDTO = null;

                if (responseStatusCode == 200){
                    try{
                        userProfileDTO = response.body();
                    } catch (Exception e){
                        Log.e(TAG, "onResponse: ", e);
                        listener.OnPersonByIdApiRequestFailure(userId, responseStatusCode);
                    }
                    if (userProfileDTO != null ){
                        Log.i(TAG, "onResponse: response" + (userProfileDTO.getUserDTO().getPicture() == null));
                        listener.OnPersonByIdApiRequestSuccess(userProfileDTO);
                    } else {
                        listener.OnPersonByIdApiRequestFailure(userId, responseStatusCode);
                    }
                } else {
                    listener.OnPersonByIdApiRequestFailure(userId, responseStatusCode);
                }
            }

            @Override
            public void onFailure(Call<UserProfileDTO> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                listener.OnPersonByIdApiRequestFailure(userId, HttpURLConnection.HTTP_INTERNAL_ERROR);
            }
        });
    }


}
