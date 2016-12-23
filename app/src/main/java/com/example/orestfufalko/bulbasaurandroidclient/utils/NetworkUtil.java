package com.example.orestfufalko.bulbasaurandroidclient.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.example.orestfufalko.bulbasaurandroidclient.R;

/**
 * Created by orestfufalko on 15.12.2016.
 */

public class NetworkUtil {

    public static boolean isDeviceConnected(Context context){
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if (!isConnected) {
            Toast.makeText(context, R.string.no_internet_connection, Toast.LENGTH_LONG).show();
        }
        return isConnected;
    }
}
