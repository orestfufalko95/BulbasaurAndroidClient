package com.example.orestfufalko.bulbasaurandroidclient.Service;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.os.ResultReceiver;
import android.widget.ProgressBar;

/**
 * Created by Vitalii Khorobchuk on 18.12.2016.
 */

public class DownloadReceiver extends ResultReceiver {

    private ProgressDialog pgDownload;

    public DownloadReceiver(Handler handler, ProgressDialog pgDownload) {
        super(handler);
        this.pgDownload = pgDownload;
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        super.onReceiveResult(resultCode, resultData);
        if (resultCode == FileDownloadService.UPDATE_PROGRESS){
            int progress = resultData.getInt("progress");
            pgDownload.setProgress(progress);
            if (progress == 100){
                pgDownload.dismiss();
            }
        }
    }
}
