package com.example.orestfufalko.bulbasaurandroidclient.utils;


import android.util.Log;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Vitalii Khorobchuk on 18.12.2016.
 */

public class TimeParseUtil {
    public static String parseTime(String dateTime){
        dateTime = dateTime.substring(0, dateTime.lastIndexOf("."));
        return dateTime.substring(dateTime.lastIndexOf('T') + 1);
    }

    public static String parseDate(String dateTime){
        return dateTime.substring(0, dateTime.lastIndexOf('T') );
    }
}
