package com.example.orestfufalko.bulbasaurandroidclient.Event;

/**
 * Created by Vitalii Khorobchuk on 18.12.2016.
 */

public class OnMediaClick {
    private String mediaUrl;
    public OnMediaClick(String mediaUrl){
        this.mediaUrl = mediaUrl;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }
}
