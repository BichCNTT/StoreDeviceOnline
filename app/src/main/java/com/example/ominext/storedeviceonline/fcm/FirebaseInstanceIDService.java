package com.example.ominext.storedeviceonline.fcm;

import android.app.Service;
import android.util.Log;
import android.widget.Toast;

import com.example.ominext.storedeviceonline.until.Server;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Ominext on 10/10/2017.
 */

public class FirebaseInstanceIDService extends FirebaseInstanceIdService {

    private String token=null;

    @Override
    public void onTokenRefresh() {
        token = FirebaseInstanceId.getInstance().getToken();
        registerToken(token);
        Toast.makeText(this, "onTokenRefresh", Toast.LENGTH_SHORT).show();
    }
    public String getToken(){
        return token;
    }
//file php có vấn đề
    private void registerToken(String token) {
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("Token", token)
                .build();
        Request request = new Request.Builder().
                url(Server.urlRegister)
                .post(requestBody)
                .build();
        try {
            client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
