package com.example.orestfufalko.bulbasaurandroidclient.Model.API.Contract;

import android.content.Context;
import android.util.Log;

import com.example.orestfufalko.bulbasaurandroidclient.Model.API.Interface.SignUpApiInteractorInterface;
import com.example.orestfufalko.bulbasaurandroidclient.Model.API.Interface.SignUpBulbasaurApiInterface;
import com.example.orestfufalko.bulbasaurandroidclient.View.Interface.SignUpRequestListener;
import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.SignUpInputDTO;
import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.SignUpResponseDTO;
import com.example.orestfufalko.bulbasaurandroidclient.R;
import com.example.orestfufalko.bulbasaurandroidclient.utils.RetrofitUtil;

import java.lang.annotation.Annotation;
import java.util.Arrays;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by orestfufalko on 04.12.2016.
 */

public class SignUpApiInteractor implements SignUpApiInteractorInterface {

    private final String TAG = getClass().getSimpleName();

    private SignUpBulbasaurApiInterface signUpBulbasaurApiInterface;

    public SignUpApiInteractor(SignUpBulbasaurApiInterface signUpBulbasaurApiInterface) {
        this.signUpBulbasaurApiInterface = signUpBulbasaurApiInterface;
    }

    @Override
    public void SignUp(final Context context, final SignUpInputDTO signUpInputDTO, final SignUpRequestListener listener) {
        Log.i(TAG, "SignUp: called");
        final Call<SignUpResponseDTO> signUpResponseDTOCall = signUpBulbasaurApiInterface.getUserProfileDTOById(signUpInputDTO);
        signUpResponseDTOCall.enqueue(new Callback<SignUpResponseDTO>() {
            @Override
            public void onResponse(Call<SignUpResponseDTO> call, Response<SignUpResponseDTO> response) {
                try {
                    if (response.code() == 200){
                        listener.onSignUpSuccess();
                    }else {
                        Retrofit instanceWithAuthHeader = RetrofitUtil.getInstance().getInstanceWithAuthHeader(context.getString(R.string.default_localhost_address_on_emulator), context);
                        Converter<ResponseBody, SignUpResponseDTO> errorConverter =
                                ((Retrofit) instanceWithAuthHeader).responseBodyConverter(SignUpResponseDTO.class, new Annotation[0]);

                            SignUpResponseDTO signUpResponse = errorConverter.convert(response.errorBody());
                        listener.onSignUpError(signUpResponse);
                    }

                } catch (Exception e){
                    Log.e(TAG, "onResponse: ", e);
                    listener.onSignUpFailure(response.message(), null);
                }
            }

            @Override
            public void onFailure(Call<SignUpResponseDTO> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                listener.onSignUpFailure(t.getMessage(), Arrays.toString(t.getStackTrace()));
            }
        });
    }
}
