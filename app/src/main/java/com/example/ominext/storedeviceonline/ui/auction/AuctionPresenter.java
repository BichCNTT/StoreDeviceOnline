package com.example.ominext.storedeviceonline.ui.auction;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.ominext.storedeviceonline.model.UserAuction;
import com.example.ominext.storedeviceonline.until.Server;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
public class AuctionPresenter {
    int mId;
    int mIdProduct;
    int mIdUser;
    String mNameAccount;
    String mDateUpload;
    String mNameProduct;
    int mPrice;
    int mNumber;
    AuctionView mAuctionView;
    Context mContext;
    List<UserAuction> mUserAuctionList = new ArrayList<>();

    public AuctionPresenter(Context mContext, AuctionView mAuctionView) {
        this.mContext = mContext;
        this.mAuctionView = mAuctionView;
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
                            mIdProduct = object.getInt("idProduct");
                            mIdUser = object.getInt("idUser");
                            mPrice = object.getInt("price");
                            mNumber = object.getInt("number");
                            mNameAccount = object.getString("nameUser");
                            mDateUpload = object.getString("auctiondate");
                            mNameProduct = object.getString("nameProduct");
                            mUserAuctionList.add(new UserAuction(mId, mIdProduct, mIdUser, mPrice, mNumber, mNameAccount, mDateUpload, mNameProduct));
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
        });
        requestQueue.add(arrayRequest);
    }
}
