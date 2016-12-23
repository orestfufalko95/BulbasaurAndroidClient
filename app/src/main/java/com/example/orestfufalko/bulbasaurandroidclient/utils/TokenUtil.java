package com.example.orestfufalko.bulbasaurandroidclient.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.example.orestfufalko.bulbasaurandroidclient.R;

/**
 * Created by orestfufalko on 15.12.2016.
 */
public class TokenUtil {
    private static TokenUtil ourInstance;

    public static TokenUtil getInstance() {
        if(ourInstance == null) {
            Class clazz = RetrofitUtil.class;

            synchronized (clazz) {
                ourInstance = new TokenUtil();
            }
        }

        return ourInstance;
    }

    private TokenUtil() {
    }

    public void saveToken(String value, Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.token), value);
        editor.commit();
    }

    public String getToken(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String  tokenStr = sharedPreferences.getString(context.getString(R.string.token), null) ;
//        Toast.makeText(context,tokenStr, Toast.LENGTH_LONG).show();
        return tokenStr;
    }
}
