package com.example.ominext.storedeviceonline.ui.login;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.ominext.storedeviceonline.model.Product;
import com.example.ominext.storedeviceonline.model.User;
import com.example.ominext.storedeviceonline.ui.laptop.ProductView;
import com.example.ominext.storedeviceonline.until.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ominext on 9/25/2017.
 */

public class LoginPresenter {
    private Context mContext;
    private LoginView mLoginView;

    int id = 0;
    String email = "";
    String name = "";
    String address = "";
    String nameUser = "";
    String password = "";
    String avartar = "";
    RequestQueue requestQueue;
//    int requestCount = 1;

    public LoginPresenter(Context mContext, LoginView mLoginView) {
        this.mContext = mContext;
        this.mLoginView = mLoginView;
    }

    public void getListUser() {
        requestQueue = Volley.newRequestQueue(mContext);
        final List<User> users = new ArrayList<>();
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Server.urlGetUser, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if ((response != null) && (response.length() > 0)) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            email = jsonObject.getString("email");
                            name = jsonObject.getString("name");
                            address = jsonObject.getString("address");
                            nameUser = jsonObject.getString("nameUser");
                            password = jsonObject.getString("password");
                            avartar = jsonObject.getString("avartar");
                            users.add(new User(id, email, password, name, address, nameUser, avartar));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                mLoginView.getListUserSuccessfully(users);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("==============>", error.toString());
                mLoginView.getListUserFailed(error.toString());
            }
        });
        requestQueue.add(arrayRequest);
    }
}
