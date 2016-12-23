package com.example.orestfufalko.bulbasaurandroidclient.View.Contract;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.orestfufalko.bulbasaurandroidclient.Model.API.Contract.EditProfileApiInteractor;
import com.example.orestfufalko.bulbasaurandroidclient.Model.API.Contract.PersonApiInteractor;
import com.example.orestfufalko.bulbasaurandroidclient.Model.API.Interface.EditProfileBulbasaurApiInterface;
import com.example.orestfufalko.bulbasaurandroidclient.Model.API.Interface.PersonApiInteractorInterface;
import com.example.orestfufalko.bulbasaurandroidclient.Model.API.Interface.PersonBulbasaurApiInterface;
import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.EditProfileDTO;
import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.PropertyInfo;
import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.SignUpResponseDTO;
import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.UserDTO;
import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.UserProfileDTO;
import com.example.orestfufalko.bulbasaurandroidclient.Presenter.Contract.EditProfilePresenter;
import com.example.orestfufalko.bulbasaurandroidclient.Presenter.Contract.PersonPresenter;
import com.example.orestfufalko.bulbasaurandroidclient.Presenter.Interface.EditProfilePresenterInterface;
import com.example.orestfufalko.bulbasaurandroidclient.Presenter.Interface.PersonPresenterInterface;
import com.example.orestfufalko.bulbasaurandroidclient.R;
import com.example.orestfufalko.bulbasaurandroidclient.View.Interface.EditPresenterListener;
import com.example.orestfufalko.bulbasaurandroidclient.View.Interface.PersonPresenterListener;
import com.example.orestfufalko.bulbasaurandroidclient.utils.GlideUtil;
import com.example.orestfufalko.bulbasaurandroidclient.utils.RetrofitUtil;

import java.io.File;

import retrofit2.Retrofit;

/**
 * Created by orestfufalko on 19.12.2016.
 */

public class EditProfileFragment extends BaseFragment implements EditPresenterListener, PersonPresenterListener {

    private final String TAG = getClass().getSimpleName();

    private final long NEGATIVE_ID_TO_GET_CURRENT_USER = -1;
    private final double PROFILE_PHOTO_MAX_HEIGHT_COEFFICIENT = 0.7;


    private EditText emailEdTxt;
    private EditText nameEdTxt;
    private EditText surnameEdTxt;
    private EditText addressEdTxt;
    private EditText aboutEdTxt;
    private ImageView profilePhoto;
    private Button saveChangesBtn;

    private RadioButton maleSexRadioBtn;
    private RadioButton femaleSexRadioBtn;

    private EditProfilePresenterInterface editProfilePresenter;
    private PersonPresenterInterface personPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Retrofit retrofit = RetrofitUtil.getInstance().
                getInstanceWithAuthHeader(getContext().getString(R.string.default_localhost_address_on_emulator), getContext());
        EditProfileApiInteractor editProfileApiInteractor = new EditProfileApiInteractor(retrofit.create(EditProfileBulbasaurApiInterface.class));
        editProfilePresenter = new EditProfilePresenter(this, editProfileApiInteractor);

        PersonApiInteractorInterface personApiInteractor = new PersonApiInteractor(retrofit.create(PersonBulbasaurApiInterface.class));
        personPresenter = new PersonPresenter(personApiInteractor, this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        emailEdTxt = (EditText) view.findViewById(R.id.email_ed_txt_edit_fragment);
        nameEdTxt = (EditText) view.findViewById(R.id.name_ed_txt_edit_fragment);
        surnameEdTxt = (EditText) view.findViewById(R.id.surname_ed_txt_edit_fragment);
        addressEdTxt = (EditText) view.findViewById(R.id.address_ed_txt_edit_fragment);
        aboutEdTxt = (EditText) view.findViewById(R.id.about_ed_txt_edit_fragment);
        profilePhoto = (ImageView) view.findViewById(R.id.profile_image_view_edit_fragment);
        saveChangesBtn = (Button) view.findViewById(R.id.save_changes_btn_edit_fragment);
        saveChangesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveChanges();
            }
        });

        femaleSexRadioBtn = (RadioButton) view.findViewById(R.id.radio_female);
        maleSexRadioBtn = (RadioButton) view.findViewById(R.id.radio_male);

        return view;
    }

    private void saveChanges() {
        EditProfileDTO editProfileDTO = new EditProfileDTO();
        if(validateMandatoryFields()) {
            editProfileDTO.setName(nameEdTxt.getText().toString());
            editProfileDTO.setSurname(surnameEdTxt.getText().toString());
            editProfileDTO.setEmail(emailEdTxt.getText().toString());
            editProfileDTO.setAbout(aboutEdTxt.getText().toString());
            editProfileDTO.setAddress(addressEdTxt.getText().toString());

            editProfileDTO.setSex((byte) (maleSexRadioBtn.isChecked() ? 1 : 2));

//            String path = Environment.getExternalStorageDirectory().getPath();
//            String myJpgPath = path + "/Download/Sasamat-Lake-PeteFlickr.jpg";
//            File f = new File(myJpgPath);
//            editProfileDTO.setPhoto(f);

            editProfilePresenter.editProfile(editProfileDTO, getContext());
        }
    }

    private boolean validateMandatoryFields() {
        if (emailEdTxt.getText().equals("")) {
            emailEdTxt.setError("Can not be empty");
            return false;
        }  if (nameEdTxt.getText().equals("")) {
            nameEdTxt.setError("Can not be empty");
            return false;
        }  if(surnameEdTxt.getText().equals("")){
            surnameEdTxt.setError("Can not be empty");
            return false;
        }
        return true;
    }

    @Override
    public void onEditProfileRequestSuccess() {
        showToast("Profile data was saved successfully!", Toast.LENGTH_SHORT);
    }

    @Override
    public void onEditProfileRequestFailure(SignUpResponseDTO responseDTO) {
        for (PropertyInfo info:
                responseDTO.getPropertyInfos()) {
            if (info.getPropertyName().equalsIgnoreCase("email")){
                emailEdTxt.setError( info.getMessage());
            }
            if (info.getPropertyName().equalsIgnoreCase("name")){
                nameEdTxt.setError( info.getMessage());
            }
            if (info.getPropertyName().equalsIgnoreCase("surname")){
                surnameEdTxt.setError( info.getMessage());
            }
        }
    }

    @Override
    public void onEditProfileError() {
        showToast(getString(R.string.error_occurs), Toast.LENGTH_LONG);
    }

    @Override
    public void onStart() {
        super.onStart();
        if(editProfilePresenter == null || personPresenter == null){
            showToast(getString(R.string.error_occurs), Toast.LENGTH_LONG);
            Log.i(TAG, "onStart: editProfilePresenter or personPresenter IS NULL!!!");
        } else {
            personPresenter.getPersonInfoById(NEGATIVE_ID_TO_GET_CURRENT_USER);
        }
    }

    @Override
    public void onPersonByIdPresenterRequestSuccess(UserProfileDTO profileDTO) {
        fillView(profileDTO.getUserDTO());
    }

    private void fillView(UserDTO userDTO) {
        emailEdTxt.setText(userDTO.getEmail());
        nameEdTxt.setText(userDTO.getName());
        surnameEdTxt.setText(userDTO.getSurname());
        aboutEdTxt.setText(userDTO.getAbout());
        addressEdTxt.setText(userDTO.getAddress());

        boolean isMale = userDTO.getSex().equals("1");
        maleSexRadioBtn.setChecked(isMale);
        femaleSexRadioBtn.setChecked(!isMale);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenWidth = displaymetrics.widthPixels;
        int screenHeight = displaymetrics.heightPixels;

        String url = getString(R.string.default_localhost_address_on_emulator_for_media) + userDTO.getPicture();

        GlideUtil.loadImageWithWidthHeightParams(url, profilePhoto, screenWidth, (int) (screenHeight * PROFILE_PHOTO_MAX_HEIGHT_COEFFICIENT));
    }

    @Override
    public void onPersonByIdPresenterRequestFailure(long id, int statusCode) {
        Log.i(TAG, "onPersonByIdPresenterRequestFailure: id: " + id + "code: " + statusCode);
    }
}
