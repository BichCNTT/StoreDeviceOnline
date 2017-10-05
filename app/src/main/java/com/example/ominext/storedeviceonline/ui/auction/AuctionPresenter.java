package com.example.ominext.storedeviceonline.ui.auction;

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
import com.example.ominext.storedeviceonline.model.UserAuction;
import com.example.ominext.storedeviceonline.ui.login.LoginFragment;
import com.example.ominext.storedeviceonline.until.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ominext on 10/5/2017.
 */

public class AuctionPresenter {
    int mId;
    int mIdProduct;
    int mIdUser;
    String mNameAccount;
    String mDateUpload;
    int mPrice;
    int mNumber;
    AuctionView mAuctionView;
    Context mContext;
    List<UserAuction> mUserAuctionList = new ArrayList<>();

    public AuctionPresenter(int idProduct, Context mContext, AuctionView mAuctionView) {
        this.mContext = mContext;
        this.mAuctionView = mAuctionView;
        this.mIdProduct = idProduct;
    }

    public void getUserAuction() {
        final RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Server.urlgetUserAuction, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null && response.length() > 0) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject object = response.getJSONObject(i);
                            mId = i + 1;
                            mIdUser = object.getInt("idUser");
                            mPrice = object.getInt("price");
                            mNumber = object.getInt("number");
                            mNameAccount = object.getString("nameUser");
                            mDateUpload = object.getString("auctiondate");
                            mUserAuctionList.add(new UserAuction(mId, mIdUser, mPrice, mNumber, mNameAccount, mDateUpload));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                mAuctionView.getUserAuctionSuccessfully(mUserAuctionList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("============>", error.toString());
                mAuctionView.getUserAuctionFailed(error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<>();
                JSONArray jsonArray = new JSONArray();
                JSONObject object = new JSONObject();
                try {
                    object.put("idProduct", mIdProduct);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                jsonArray.put(object);
                hashMap.put("json", jsonArray.toString());
                return hashMap;
            }
        };
        requestQueue.add(arrayRequest);
    }

    public void postUserAuction(final int idProduct, final int price, final int number) {
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.getUrlPostUserAuction, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("============>", error.toString());
                mAuctionView.getUserAuctionFailed(error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<>();
                JSONArray jsonArray = new JSONArray();
                JSONObject object = new JSONObject();
                try {
//                    đẩy idUser, idProduct, price, number, auctiondate lên server
                    object.put("idUser", LoginFragment.user.getId());
                    object.put("idProduct", idProduct);//truyền vào id product từ fragment chi tiết sản phẩm
                    object.put("price", price);//truyền vào price mà ng dùng trả giá từ DialogFragment đấu giá
                    object.put("number", number);//truyền vào số lượng sản phẩm mà người dùng đấu giá
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                jsonArray.put(object);
                hashMap.put("json", jsonArray.toString());
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }
}
