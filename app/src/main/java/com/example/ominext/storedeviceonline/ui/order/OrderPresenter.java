package com.example.ominext.storedeviceonline.ui.order;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ominext.storedeviceonline.ui.home.HomeActivity;
import com.example.ominext.storedeviceonline.ui.login.LoginFragment;
import com.example.ominext.storedeviceonline.ui.orderconfirm.OrderConfirmFragment;
import com.example.ominext.storedeviceonline.until.CheckConnectionInternet;
import com.example.ominext.storedeviceonline.until.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ominext on 10/6/2017.
 */

public class OrderPresenter {
    Context mContext;
    OrderView mOrderView;

    public OrderPresenter(Context mContext, OrderView mOrderView) {
        this.mContext = mContext;
        this.mOrderView = mOrderView;
    }

    public void postOrderProduct(final String name) {
        if (CheckConnectionInternet.haveNetWorkConnection(mContext)) {
            RequestQueue requestQueue = Volley.newRequestQueue(mContext);
            StringRequest orderProductStringRequest = new StringRequest(Request.Method.POST, Server.urlPostOrderProduct, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    mOrderView.postOrderProductSuccessfully();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("=========>error", error.toString());
                    mOrderView.postOrderProductFailed(error.toString());
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> hashMap = new HashMap<>();
                    JSONArray jsonArray = new JSONArray();
                    JSONObject object = new JSONObject();
                    try {
                        object.put("id", null);
                        object.put("idUser", LoginFragment.user.getId());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    jsonArray.put(object);
                    hashMap.put("json", jsonArray.toString());
                    return hashMap;
                }
            };
            requestQueue.add(orderProductStringRequest);
        } else {
            Toast.makeText(mContext, "Đơn hàng chưa được đặt. Kiểm tra lại kết nối", Toast.LENGTH_LONG).show();
        }
    }
}
