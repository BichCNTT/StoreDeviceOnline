package com.example.ominext.storedeviceonline.until;

import com.example.ominext.storedeviceonline.until.Server;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ominext on 8/21/2017.
 */
public class RetrofitClient {

    public static final String BASE_URL = "http://192.168.2.26/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
