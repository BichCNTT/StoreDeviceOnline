package com.example.ominext.storedeviceonline.ui.find;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.ominext.storedeviceonline.ui.home.HomeView;
import com.example.ominext.storedeviceonline.until.CheckConnection;
import com.example.ominext.storedeviceonline.until.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ominext on 9/5/2017.
 */
//khi kích vào nút search. Cửa sổ search hiện lên
public class FindPresenter {
    String nameProduct = "";
    private Context mContext;

    public FindPresenter(Context mContext, FindView mFindView) {
        this.mContext = mContext;
        this.mFindView = mFindView;
    }

    private FindView mFindView;

    public void getListFind() {
        final ArrayList<String> listFind = new ArrayList<>();
        final RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Server.urlProduct, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            nameProduct = jsonObject.getString("nameProduct");
                            listFind.add(nameProduct);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                mFindView.getListFindSuccess(listFind);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.showToast(mContext, error.toString());
                Log.e("==============>", error.toString());
                mFindView.getListFindFailed(error.toString());
            }
        });
        requestQueue.add(arrayRequest);
    }
}
