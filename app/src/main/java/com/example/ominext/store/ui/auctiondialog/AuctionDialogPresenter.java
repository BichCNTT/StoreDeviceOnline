package com.example.ominext.store.ui.auctiondialog;

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
import com.example.ominext.store.ui.login.LoginFragment;
import com.example.ominext.store.until.CheckConnectionInternet;
import com.example.ominext.store.until.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AuctionDialogPresenter {
    Context mContext;
    AuctionDialogView mAuctionDialogView;

    public AuctionDialogPresenter(Context mContext, AuctionDialogView mAuctionDialogView) {
        this.mContext = mContext;
        this.mAuctionDialogView = mAuctionDialogView;
    }

    public void postUserAuction(final int idProduct, final int price, final int number) {
        if (CheckConnectionInternet.haveNetWorkConnection(mContext)) {
            RequestQueue requestQueue = Volley.newRequestQueue(mContext);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.urlPostUserAuction, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("============>", error.toString());
                    mAuctionDialogView.postUserAuctionFailed(error.toString());
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> hashMap = new HashMap<>();
                    JSONArray jsonArray = new JSONArray();
                    JSONObject object = new JSONObject();
                    try {
                        object.put("idUser", LoginFragment.user.getId());
                        object.put("idProduct", idProduct);
                        object.put("price", price);
                        object.put("number", number);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    jsonArray.put(object);
                    hashMap.put("json", jsonArray.toString());
                    return hashMap;
                }
            };
            requestQueue.add(stringRequest);
        } else {
            Toast.makeText(mContext, "Không thể đấu giá sản phẩm. Kiểm tra lại kết nối", Toast.LENGTH_SHORT).show();
        }
    }
}
