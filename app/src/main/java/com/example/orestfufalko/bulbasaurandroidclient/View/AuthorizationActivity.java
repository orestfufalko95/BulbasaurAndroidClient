package com.example.orestfufalko.bulbasaurandroidclient.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orestfufalko.bulbasaurandroidclient.Model.API.Contract.LoginApiInteractor;
import com.example.orestfufalko.bulbasaurandroidclient.Model.API.Contract.SignUpApiInteractor;
import com.example.orestfufalko.bulbasaurandroidclient.Model.API.Interface.LoginApiInteractorInterface;
import com.example.orestfufalko.bulbasaurandroidclient.Model.API.Interface.LoginBulbasaurApiInterface;
import com.example.orestfufalko.bulbasaurandroidclient.Model.API.Interface.SignUpApiInteractorInterface;
import com.example.orestfufalko.bulbasaurandroidclient.Model.API.Interface.SignUpBulbasaurApiInterface;
import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.UserProfileDTO;
import com.example.orestfufalko.bulbasaurandroidclient.Presenter.Contract.LoginPresenter;
import com.example.orestfufalko.bulbasaurandroidclient.Presenter.Contract.SignUpPresenter;
import com.example.orestfufalko.bulbasaurandroidclient.Presenter.Interface.LoginPresenterInterface;
import com.example.orestfufalko.bulbasaurandroidclient.Presenter.Interface.SignUpPresenterInterface;
import com.example.orestfufalko.bulbasaurandroidclient.R;
import com.example.orestfufalko.bulbasaurandroidclient.View.Interface.LoginResponseView;
import com.example.orestfufalko.bulbasaurandroidclient.View.Interface.SignUpResponseView;
import com.example.orestfufalko.bulbasaurandroidclient.utils.RetrofitUtil;
import com.example.orestfufalko.bulbasaurandroidclient.utils.TokenUtil;

import retrofit2.Retrofit;

public class AuthorizationActivity extends AppCompatActivity implements LoginResponseView, SignUpResponseView{

    private Retrofit retrofit;

    private LinearLayout llLogin;
    private LinearLayout llSignUp;
    private LinearLayout llTotal;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etSecondPassword;
    private EditText etName;
    private EditText etSurname;
    private TextView tvError;
    private ProgressDialog pgWaitForResponse;


    private LoginPresenterInterface loginPresenter;
    private SignUpPresenterInterface signUpPresenter;

    private final String TAG = "AuthorizationTAG";
    public static final String USER_PROFILE_EXTRA = "userProfile";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);

        retrofit = RetrofitUtil.getInstance().getInstanceWithAuthHeader(getString(R.string.default_localhost_address_on_emulator),
                getApplicationContext());
        LoginApiInteractorInterface loginApiInteractor = new LoginApiInteractor(retrofit.create(LoginBulbasaurApiInterface.class));
        loginPresenter = new LoginPresenter(loginApiInteractor, this);
        llLogin = (LinearLayout) findViewById(R.id.ll_login);
        llSignUp = (LinearLayout) findViewById(R.id.ll_sign_up);
        llTotal = (LinearLayout) findViewById(R.id.ll_login_total);
        etEmail = (EditText) findViewById(R.id.etAuthorizationEmail);
        etPassword = (EditText) findViewById(R.id.etAuthorizationPassword);
        tvError = (TextView) findViewById(R.id.tv_login_error);


        initProgressDialog();
        initLoginButton();
        initGoToSignUpBtn();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        Toast.makeText(this, R.string.exit_message, Toast.LENGTH_SHORT).show();
        this.finishAffinity();
    }

    private void initLoginButton(){
        Button login = (Button) findViewById(R.id.btn_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvError.setVisibility(View.GONE);
                if(checkEditText()){
                    loginPresenter.login(etEmail.getText().toString(), etPassword.getText().toString());

                    pgWaitForResponse.show();
                }
            }
        });
    }


    private void initGoToSignUpBtn(){
        Button goToSignUp = (Button) findViewById(R.id.btn_goto_signUp);
        goToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llLogin.setVisibility(View.GONE);
                llSignUp.setVisibility(View.VISIBLE);
                clearLoginFields();
                initBackButton();
                initSignUp();
                initSignUpBtn();
            }
        });
    }

    private void initSignUp(){
        etName = (EditText) findViewById(R.id.etSignUpName);
        etSurname= (EditText) findViewById(R.id.etSignUpSurName);
        etSecondPassword = (EditText) findViewById(R.id.etPasswordRepeat);

        SignUpApiInteractorInterface signUpApi = new SignUpApiInteractor(retrofit.create(SignUpBulbasaurApiInterface.class));
        signUpPresenter = new SignUpPresenter(this, signUpApi);
    }

    private void initProgressDialog(){
        pgWaitForResponse = new ProgressDialog(AuthorizationActivity.this);
        pgWaitForResponse.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pgWaitForResponse.setMessage("Please wait...");
        pgWaitForResponse.setIndeterminate(true);
        pgWaitForResponse.setCancelable(false);
    }

    private void initSignUpBtn(){
        Button signUp = (Button) findViewById(R.id.btn_sign_up);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etPassword.getText().toString().equals(etSecondPassword.getText().toString())) {
                    signUpPresenter.signUp(getApplicationContext(),
                            etEmail.getText().toString(),
                            etPassword.getText().toString(),
                            etName.getText().toString(),
                            etSurname.getText().toString());
                    pgWaitForResponse.show();
                }
                else etPassword.setError("Passwords don`t match");
            }
        });
    }

    private boolean checkEditText(){
        boolean isOk = true;
        for(int i = 0; i < llTotal.getChildCount(); i++){
            if (llTotal.getChildAt(i) instanceof EditText) {
                EditText et = (EditText) llTotal.getChildAt(i);
                if (et.getText().toString().trim().equalsIgnoreCase("")){
                        et.setError(getString(R.string.blank_field));
                    isOk = false;
                }
            }
        }
        return isOk;
    }

    private void initBackButton(){
        Button back = (Button) findViewById(R.id.btn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llLogin.setVisibility(View.VISIBLE);
                llSignUp.setVisibility(View.GONE);
                clearLoginFields();
                clearSignUpFields();
            }
        });
    }

    @Override
    public void showResponse(UserProfileDTO userProfileDTO) {

        pgWaitForResponse.dismiss();
        TokenUtil.getInstance().saveToken(userProfileDTO.getTokenResponse(), getApplicationContext());
        Intent loginResult = new Intent();

        loginResult.putExtra(USER_PROFILE_EXTRA, userProfileDTO);
        setResult(RESULT_OK, loginResult);
        finish();
    }

    @Override
    public void showWrongData() {
        tvError.setVisibility(View.VISIBLE);
        pgWaitForResponse.dismiss();
    }

    @Override
    public void showLoginError(int code, String message, String stackTrace) {
        Toast.makeText(this, getString(R.string.error_message), Toast.LENGTH_LONG).show();
        Log.e(TAG, "LoginError: " + code + " - " + message);
       // Log.e(TAG, stackTrace);
        pgWaitForResponse.dismiss();
    }

    @Override
    public void showSignUpOk() {
        Toast.makeText(this, R.string.sign_up_success, Toast.LENGTH_LONG).show();
        llLogin.setVisibility(View.VISIBLE);
        llSignUp.setVisibility(View.GONE);
        clearLoginFields();
        clearSignUpFields();
        pgWaitForResponse.dismiss();
    }

    @Override
    public void showSignUpError(int etId, String message) {
        EditText et =  (EditText) findViewById(etId);
        et.setError(message);
        pgWaitForResponse.dismiss();
    }

    @Override
    public void showError(String message, @Nullable String stackTrace) {
        Toast.makeText(this, R.string.error_message, Toast.LENGTH_LONG).show();
        Log.e(TAG, "message: " + message);
        Log.e(TAG, "trace : " + stackTrace);
        pgWaitForResponse.dismiss();
    }

    private void clearLoginFields(){
        etEmail.setText("");
        etEmail.setError(null);
        etPassword.setText("");
        etPassword.setError(null);
        tvError.setVisibility(View.GONE);
    }
    private void clearSignUpFields(){
        etName.setText("");
        etSurname.setText("");
        etName.setError(null);
        etName.setError(null);
    }
}
