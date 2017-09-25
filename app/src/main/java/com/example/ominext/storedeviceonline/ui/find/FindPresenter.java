package com.example.ominext.storedeviceonline.ui.find;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.ominext.storedeviceonline.model.Product;
import com.example.ominext.storedeviceonline.until.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//khi kích vào nút search. Cửa sổ search hiện lên
public class FindPresenter {
    String mNameProduct = "";
    int mIdProduct = 0;
    int mTdProductType = 0;
    String mDescribeProduct = "";
    String mImgProduct = "";
    int mPriceProduct = 0;
    private Context mContext;
    private FindView mFindView;
    private RequestQueue mRequestQueue;
//    private int mRequestCount = 1;
//    final List<Product> listFind = new ArrayList<>();

    public FindPresenter(Context mContext, FindView mFindView) {
        this.mContext = mContext;
        this.mFindView = mFindView;
    }

    public void getListFind() {
        mRequestQueue = Volley.newRequestQueue(mContext);
        final List<Product> listFind = new ArrayList<>();
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Server.urlProduct, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            mIdProduct = jsonObject.getInt("IdProduct");
                            mTdProductType = jsonObject.getInt("IdProductType");
                            mDescribeProduct = jsonObject.getString("describeProduct");
                            mImgProduct = jsonObject.getString("imageProduct");
                            mPriceProduct = jsonObject.getInt("priceProduct");
                            mNameProduct = jsonObject.getString("nameProduct");
                            listFind.add(new Product(mIdProduct, mNameProduct, mPriceProduct, mImgProduct, mDescribeProduct, mTdProductType));
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
                Log.e("==============>", error.toString());
                mFindView.getListFindFailed(error.toString());
            }
        });
        mRequestQueue.add(arrayRequest);
    }
//    //nếu mà load more
//    public void getListFind(int page) {
//        mRequestQueue = Volley.newRequestQueue(mContext);
//        getData(page);
//    }
//
//    //    page=0 cho tự động tăng, page=1 cho load từ đầu
//    public void getData(int page) {
//        if (page == 0) {
//            mRequestQueue.add(getDataFromSerVer(mRequestCount));
//            mRequestCount++;
//        } else {
//            mRequestCount = page;
//            mRequestQueue.add(getDataFromSerVer(mRequestCount));
//        }
//    }
//
//    private JsonArrayRequest getDataFromSerVer(int requestCount) {
//        JsonArrayRequest arrayRequest = new JsonArrayRequest(Server.urlFind + String.valueOf(requestCount), new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                if (response != null) {
//                    for (int i = 0; i < response.length(); i++) {
//                        try {
//                            JSONObject jsonObject = response.getJSONObject(i);
//                            mIdProduct = jsonObject.getInt("IdProduct");
//                            mTdProductType = jsonObject.getInt("IdProductType");
//                            mDescribeProduct = jsonObject.getString("describeProduct");
//                            mImgProduct = jsonObject.getString("imageProduct");
//                            mPriceProduct = jsonObject.getInt("priceProduct");
//                            mNameProduct = jsonObject.getString("nameProduct");
//                            listFind.add(new Product(mIdProduct, mNameProduct, mPriceProduct, mImgProduct, mDescribeProduct, mTdProductType));
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//                mFindView.getListFindSuccess(listFind);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e("==============>", error.toString());
//                mFindView.getListFindFailed(error.toString());
//            }
//        });
//        return arrayRequest;
//    }
}
