package com.example.orestfufalko.bulbasaurandroidclient.View.Contract;

import android.support.v4.app.Fragment;
import android.widget.Toast;



public abstract class BaseFragment extends Fragment {

    protected final long NEGATIVE_ID_TO_GET_CURRENT_USER = -1;

    protected void showToast(String message, int duration) {
        if (isAdded())
        Toast.makeText(getContext(), message, duration).show();
    }

}
