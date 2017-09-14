package com.example.ominext.storedeviceonline.ui.phone;

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

/**
 * Created by Ominext on 8/28/2017.
 */

public class PhonePresenter {
    private Context mContext;
    private PhoneView mPhoneView;

    public PhonePresenter(Context context, PhoneView phoneView) {
        this.mContext = context;
        this.mPhoneView = phoneView;
    }

    int idProductType = 0;
    int idProduct = 0;
    String nameProduct = "";
    int priceProduct = 0;
    String imageProduct = "";
    String describeProduct = "";


    public void getList(String url) {
        final RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        final List<Product> productList = new ArrayList<>();
        JsonArrayRequest arrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if ((response != null) && (response.length() > 0)) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            idProductType = jsonObject.getInt("IdProductType");
                            idProduct = jsonObject.getInt("IdProduct");
                            nameProduct = jsonObject.getString("nameProduct");
                            priceProduct = jsonObject.getInt("priceProduct");
                            imageProduct = jsonObject.getString("imageProduct");
                            describeProduct = jsonObject.getString("describeProduct");
                            productList.add(new Product(idProduct, nameProduct, priceProduct, imageProduct, describeProduct, idProductType));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                mPhoneView.getListPhoneSuccess(productList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("==============>", error.toString());
                mPhoneView.getListPhoneFailed(error.toString());
            }
        });
        requestQueue.add(arrayRequest);
    }

    public void getListPhone() {
        getList(Server.urlPhone);
    }

    public void getListSortDownLaptop() {
        getList(Server.urlSortDownPhone);
    }

    public void getListSortUpLaptop() {
        getList(Server.urlSortUpPhone);
    }
}
