package com.example.ominext.storedeviceonline.ui.orderconfirm;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.ominext.storedeviceonline.until.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ominext on 9/12/2017.
 */

public class OrderConfirmPresenter {
    private Context mContext;
    private OrderConfirmView mOrderView;
    private int mId;

    public OrderConfirmPresenter(Context mContext, OrderConfirmView mOrderView) {
        this.mContext = mContext;
        this.mOrderView = mOrderView;
    }

    public void getIdOrderProduct() {
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        final List<Integer> listId = new ArrayList<>();
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Server.urlGetIdOrderProduct, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if ((response != null) && (response.length() > 0)) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            mId = jsonObject.getInt("id");
                            listId.add(mId);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                mOrderView.getIdOrderProductSuccessfully(listId);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("==============>", error.toString());
                mOrderView.getIdOrderProductFailed(error.toString());
            }
        });
        requestQueue.add(arrayRequest);
    }
}
