package com.example.orestfufalko.bulbasaurandroidclient.Service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.os.ResultReceiver;
import android.util.Log;

import com.example.orestfufalko.bulbasaurandroidclient.Event.OnFileDownloadProgress;
import com.example.orestfufalko.bulbasaurandroidclient.Event.OnMediaClick;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Vitalii Khorobchuk on 18.12.2016.
 */

public class FileDownloadService extends IntentService {
    public static final int UPDATE_PROGRESS = 8344;
    public FileDownloadService() {
        super("DownloadService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String urlToDownload = intent.getStringExtra("url");
        String fileName =  intent.getStringExtra("fileName");
        try {
            URL url = new URL(urlToDownload);
            URLConnection connection = url.openConnection();
            connection.connect();
            int fileLength = connection.getContentLength();
            InputStream input = new BufferedInputStream(connection.getInputStream());
            Log.i("DownloadTAG", "onHandleIntent: " + Environment.getExternalStorageDirectory().getPath() + "/download/" + fileName);
            OutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory().getPath() + "/download/" + fileName);

            byte data[] = new byte[1024];
            long total = 0;
            int count;
            while ((count = input.read(data)) != -1) {
                total += count;
                EventBus.getDefault().post(new OnFileDownloadProgress(total * 100 / fileLength));
                output.write(data, 0, count);
            }
            output.flush();
            output.close();
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
