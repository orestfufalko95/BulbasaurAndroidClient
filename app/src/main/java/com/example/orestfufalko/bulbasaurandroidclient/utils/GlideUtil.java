package com.example.orestfufalko.bulbasaurandroidclient.utils;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.orestfufalko.bulbasaurandroidclient.R;

/**
 * Created by orestfufalko on 15.12.2016.
 */

public class GlideUtil {
    private static String TAG = "GlideUtilTAG";

    public static void loadGameImage(String url, ImageView imageView) {
        Log.i(TAG, "loadImage: starts");
        Context context = imageView.getContext();
//        ColorDrawable cd = new ColorDrawable(ContextCompat.getColor(context, R.color.$greyColor));
        try {
            Glide.with(context)
                    .load(url)
                    .placeholder(R.mipmap.default_person)
                    .crossFade()
                    .centerCrop()
                    .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .into(imageView);
        } catch (Exception e){
            Log.e(TAG, "loadImage: ", e);
        }
    }

    public static void loadImageWithWidthHeightParams(String url, ImageView imageView, int width, int height) {
        Log.i(TAG, "loadImage: starts");
        Context context = imageView.getContext();
        try {
            Glide.with(context)
                    .load(url)
                    .placeholder(R.mipmap.default_person)
                    .crossFade()
                    .centerCrop()
                    .fitCenter()
                    .override(width, height)
                    .into(imageView);
        } catch (Exception e){
            Log.e(TAG, "loadImage: ", e);
        }
    }

    public static void loadProfileIcon(String url, ImageView imageView) {
        Context context = imageView.getContext();
        try {
            Glide.with(context)
                    .load(url)
                    .placeholder(R.mipmap.default_person)
                    .dontAnimate()
                    .fitCenter()
                    .into(imageView);
        } catch (Exception e){
            Log.e(TAG, "loadProfileIcon: ", e);
        }

    }
}
