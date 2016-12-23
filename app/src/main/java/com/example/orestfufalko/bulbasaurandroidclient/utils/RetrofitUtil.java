package com.example.orestfufalko.bulbasaurandroidclient.utils;


import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtil {
    private static Retrofit instance = null;
    private static RetrofitUtil utilInstance;

    public static RetrofitUtil getInstance() {
        if(instance == null) {
            Class clazz = RetrofitUtil.class;

            synchronized (clazz) {
                utilInstance = new RetrofitUtil();
            }
        }
        return utilInstance;
    }

    public Retrofit getInstanceWithAuthHeader(String apiURL, final Context context){
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();

                // Request customization: add request headers
                Request.Builder requestBuilder = original.newBuilder()
                        .header("Authorization", "Bearer " + TokenUtil.getInstance().getToken(context)); // <-- this is the important line

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });

        OkHttpClient client = httpClient.build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        instance = new Retrofit.Builder()
                .baseUrl(apiURL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        return instance;
    }

    private RetrofitUtil() {

    }

}
