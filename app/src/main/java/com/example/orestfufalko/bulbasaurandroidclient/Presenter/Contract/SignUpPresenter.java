package com.example.orestfufalko.bulbasaurandroidclient.Presenter.Contract;

import android.content.Context;
import android.support.annotation.Nullable;

import com.example.orestfufalko.bulbasaurandroidclient.Model.API.Interface.SignUpApiInteractorInterface;
import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.PropertyInfo;
import com.example.orestfufalko.bulbasaurandroidclient.View.Interface.SignUpRequestListener;
import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.SignUpInputDTO;
import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.SignUpResponseDTO;
import com.example.orestfufalko.bulbasaurandroidclient.Presenter.Interface.SignUpPresenterInterface;
import com.example.orestfufalko.bulbasaurandroidclient.R;
import com.example.orestfufalko.bulbasaurandroidclient.View.Interface.SignUpResponseView;


public class SignUpPresenter implements SignUpPresenterInterface, SignUpRequestListener {

    private final String TAG = getClass().getSimpleName();

    private SignUpResponseView signUpResponseView;
    private SignUpApiInteractorInterface accountApiInteractor;

    public SignUpPresenter(SignUpResponseView signUpResponseView, SignUpApiInteractorInterface accountApiInteractor) {
        this.signUpResponseView = signUpResponseView;
        this.accountApiInteractor = accountApiInteractor;
    }


    @Override
    public void signUp(Context context, String email, String password, String name, String surname) {
        SignUpInputDTO signUpInput = new SignUpInputDTO();
        signUpInput.setEmail(email);
        signUpInput.setPassword(password);
        signUpInput.setName(name);
        signUpInput.setSurname(surname);
        accountApiInteractor.SignUp(context, signUpInput, this);
    }


    @Override
    public void onSignUpSuccess() {
        signUpResponseView.showSignUpOk();
    }

    @Override
    public void onSignUpError(SignUpResponseDTO signUpResponse) {

        for (PropertyInfo info:
             signUpResponse.getPropertyInfos()) {
            if (info.getPropertyName().equalsIgnoreCase("email")){
                signUpResponseView.showSignUpError(R.id.etAuthorizationEmail, info.getMessage());
            }
            else if (info.getPropertyName().equalsIgnoreCase("password")){
                signUpResponseView.showSignUpError(R.id.etAuthorizationPassword, info.getMessage());
            }
            else if (info.getPropertyName().equalsIgnoreCase("name")){
                signUpResponseView.showSignUpError(R.id.etSignUpName, info.getMessage());
            }
            else if (info.getPropertyName().equalsIgnoreCase("surname")){
                signUpResponseView.showSignUpError(R.id.etSignUpSurName, info.getMessage());
            }
        }
    }

    @Override
    public void onSignUpFailure(String message, @Nullable String stackTrace) {
        signUpResponseView.showError(message, stackTrace);
    }

}
