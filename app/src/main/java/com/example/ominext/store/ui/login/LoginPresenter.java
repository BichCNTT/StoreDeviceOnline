package com.example.ominext.store.ui.login;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ominext.store.model.User;
import com.example.ominext.store.ui.register.RegisterView;
import com.example.ominext.store.until.CheckConnectionInternet;
import com.example.ominext.store.until.Server;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ominext on 9/25/2017.
 */

public class LoginPresenter {
    private Context mContext;
    private LoginView mLoginView;
    String id = "";
    String email = "";
    String name = "";
    String address = "";
    String nameUser = "";
    String password = "";
    String avartar = "";
    String token = "";
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
                            id = jsonObject.getString("id");
                            email = jsonObject.getString("email");
                            name = jsonObject.getString("name");
                            address = jsonObject.getString("address");
                            nameUser = jsonObject.getString("nameUser");
                            password = jsonObject.getString("password");
                            avartar = jsonObject.getString("avartar");
                            token = jsonObject.getString("Token");
                            users.add(new User(id, email, password, name, address, nameUser, avartar, token));
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

    public void updateAccount(final String mId, final String token) {
        if (CheckConnectionInternet.haveNetWorkConnection(mContext)) {
            RequestQueue requestQueue = Volley.newRequestQueue(mContext);
            StringRequest clientInfoStringRequest = new StringRequest(Request.Method.POST, Server.urlUpdateUser, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("=========>error", response.toString());
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("=========>error", error.toString());
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> hashMap;
                    JSONArray jsonArray = new JSONArray();
                    JSONObject object = new JSONObject();
                    try {
                        object.put("id", mId);
                        object.put("Token", token);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    jsonArray.put(object);
                    hashMap = new HashMap<>();
                    hashMap.put("json", jsonArray.toString());
                    return hashMap;
                }
            };
            requestQueue.add(clientInfoStringRequest);

        } else {
            Toast.makeText(mContext, "Kiểm tra lại kết nối của bạn", Toast.LENGTH_LONG).show();
        }
    }
}
