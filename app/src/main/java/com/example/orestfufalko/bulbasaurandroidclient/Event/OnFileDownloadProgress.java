package com.example.orestfufalko.bulbasaurandroidclient.Event;

/**
 * Created by Vitalii Khorobchuk on 19.12.2016.
 */

public class OnFileDownloadProgress {
    private long progress;

    public OnFileDownloadProgress(long progress) {
        this.progress = progress;
    }

    public long getProgress() {
        return progress;
    }
}
