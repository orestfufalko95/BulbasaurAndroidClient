package com.example.orestfufalko.bulbasaurandroidclient.utils;

import android.util.Log;

/**
 * Created by Vitalii Khorobchuk on 18.12.2016.
 */

public  class MediaUrlParser {

    private static final String TAG = "MediaUrlParserTAG";
    private static final String[] IMAGE_EXTENSIONS = {".jpg", ".jpeg", ".png", ".bmp"};
    private static final String[] VIDEO_EXTENSIONS= {".mp4", ".avi", ".mkv"};
    private static final String[] AUDIO_EXTENSIONS = {".mp3", ".wav"};
    public static boolean isAudioFile(String url){
        String urlExt = url.substring(url.lastIndexOf('.'));
        for (String ext :
                AUDIO_EXTENSIONS) {
            if (urlExt.equalsIgnoreCase(ext)){
                return true;
            }
        }
        return false;
    }
    public static boolean isVideoFile(String url){
        String urlExt = url.substring(url.lastIndexOf('.'));

        for (String ext :
                VIDEO_EXTENSIONS) {
            if (urlExt.equalsIgnoreCase(ext)){
                return true;
            }
        }
        return false;
    }
    public static boolean isImageFile(String url){
        String urlExt = url.substring(url.lastIndexOf('.'));
        for (String ext :
                IMAGE_EXTENSIONS) {
            if (urlExt.equalsIgnoreCase(ext)){
                return true;
            }
        }
        return false;
    }
}
