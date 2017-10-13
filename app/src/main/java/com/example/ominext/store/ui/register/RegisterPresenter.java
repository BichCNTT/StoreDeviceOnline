package com.example.ominext.store.ui.register;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ominext.store.model.User;
import com.example.ominext.store.until.CheckConnectionInternet;
import com.example.ominext.store.until.Server;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ominext on 10/6/2017.
 */

public class RegisterPresenter {
    Context mContext;
    RegisterView mRegisterView;

    public RegisterPresenter(Context mContext, RegisterView mRegisterView) {
        this.mContext = mContext;
        this.mRegisterView = mRegisterView;
    }

    public void registerAccount(final List<User> listUser, final String phone, final String email, final String name, final String address, final String password, final String accountName) {
        if (CheckConnectionInternet.haveNetWorkConnection(mContext)) {
            int check = 0;
            for (int i = 0; i < listUser.size(); i++) {
                if (String.valueOf(listUser.get(i).getId()) == phone || listUser.get(i).getEmail().equals(email)) {
                    check = 1;
                }
            }
            if (check != 1) {
                RequestQueue requestQueue = Volley.newRequestQueue(mContext);
                StringRequest clientInfoStringRequest = new StringRequest(Request.Method.POST, Server.urlUser, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mRegisterView.registerSuccessfully();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mRegisterView.registerFailed(error.toString());
                        Log.e("=========>error", error.toString());
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> hashMap;
                        JSONArray jsonArray = new JSONArray();
                        JSONObject object = new JSONObject();
                        try {
                            object.put("id", phone);
                            object.put("name", name);
                            object.put("address", address);
                            object.put("password", password);
                            object.put("email", email);
                            object.put("nameUser", accountName);
                            object.put("Token", FirebaseInstanceId.getInstance().getToken().toString());
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
                Toast.makeText(mContext, "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(mContext, "Kiểm tra lại kết nối của bạn", Toast.LENGTH_LONG).show();
        }
    }
}
