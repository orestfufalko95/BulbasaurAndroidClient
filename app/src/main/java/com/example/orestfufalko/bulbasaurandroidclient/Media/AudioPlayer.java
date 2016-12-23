package com.example.orestfufalko.bulbasaurandroidclient.Media;

import android.media.AudioManager;
import android.media.MediaPlayer;

import java.io.IOException;

/**
 * Created by Vitalii Khorobchuk on 18.12.2016.
 */

public class AudioPlayer {
    private  MediaPlayer mediaPlayer;
    private boolean isPlaying = false;

    public void play(String musicUrl) {
        mediaPlayer = new MediaPlayer();

        try {
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(musicUrl);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void pause(){
        mediaPlayer.pause();
    }
}
