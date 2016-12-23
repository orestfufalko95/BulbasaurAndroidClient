package com.example.orestfufalko.bulbasaurandroidclient.Model.API.Contract;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.example.orestfufalko.bulbasaurandroidclient.Model.API.Interface.EditProfileApiInteractorInterface;
import com.example.orestfufalko.bulbasaurandroidclient.Model.API.Interface.EditProfileBulbasaurApiInterface;
import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.EditProfileDTO;
import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.SignUpResponseDTO;
import com.example.orestfufalko.bulbasaurandroidclient.Presenter.Interface.EditProfileApiInteractorListener;
import com.example.orestfufalko.bulbasaurandroidclient.R;
import com.example.orestfufalko.bulbasaurandroidclient.utils.RetrofitUtil;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.HttpURLConnection;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by orestfufalko on 18.12.2016.
 */

public class EditProfileApiInteractor implements EditProfileApiInteractorInterface {

    private final String TAG = getClass().getSimpleName();

    private EditProfileBulbasaurApiInterface setvice;

    public EditProfileApiInteractor(EditProfileBulbasaurApiInterface setvice) {
        this.setvice = setvice;
    }

    @Override
    public void editProfile(EditProfileDTO editProfileDTO, final EditProfileApiInteractorListener listener, final Context context) {

//        String path = Environment.getExternalStorageDirectory().getPath();
//        String myJpgPath = path + "/Download/Sasamat-Lake-PeteFlickr.jpg";
//        File f = new File(myJpgPath);

        Call<SignUpResponseDTO> call = setvice.editProfile(editProfileDTO.getEmail(), editProfileDTO.getName(), editProfileDTO.getSurname(),
                editProfileDTO.getPhoto(), editProfileDTO.getAddress(), editProfileDTO.getAbout(), editProfileDTO.isSex());

        call.enqueue(new Callback<SignUpResponseDTO>() {
            @Override
            public void onResponse(Call<SignUpResponseDTO> call, Response<SignUpResponseDTO> response) {
                int code = response.code();
                if (code == HttpURLConnection.HTTP_OK){
                    listener.onEditProfileRequestSuccess();
                } else {
                    Retrofit instanceWithAuthHeader = RetrofitUtil.getInstance().getInstanceWithAuthHeader(context.getString(R.string.default_localhost_address_on_emulator), context);
                    Converter<ResponseBody, SignUpResponseDTO> errorConverter =
                            ((Retrofit) instanceWithAuthHeader).responseBodyConverter(SignUpResponseDTO.class, new Annotation[0]);

                    SignUpResponseDTO signUpResponse = null;
                    try {
                        signUpResponse = errorConverter.convert(response.errorBody());
                    } catch (IOException e) {
                        Log.e(TAG, "onResponse: ", e);
                    }
                    listener.onEditProfileRequestFailure(code,signUpResponse);
                }
            }

            @Override
            public void onFailure(Call<SignUpResponseDTO> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                listener.onEditProfileErrorDuringRequest();
            }
        });


    }

}
