package com.example.ominext.storedeviceonline.ui.main;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.ominext.storedeviceonline.model.Product;
import com.example.ominext.storedeviceonline.until.CheckConnection;
import com.example.ominext.storedeviceonline.until.Server;
import com.example.ominext.storedeviceonline.ui.main.MainFragmentView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ominext on 8/28/2017.
 */

public class MainFragmentPresenter {
    int idProduct = 0;
    String nameProduct = "";
    int priceProduct = 0;
    String imageProduct = "";
    String describeProduct = "";
    int idProductType = 0;

    private Context mContext;
    private MainFragmentView mMainFragmentView;

    public MainFragmentPresenter(Context mContext, MainFragmentView mainFragmentView) {
        this.mContext = mContext;
        this.mMainFragmentView = mainFragmentView;
    }

    public void getListProduct() {
        final ArrayList<Product> listProduct = new ArrayList<>();
        final RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Server.urlNewProduct, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            idProductType = jsonObject.getInt("IdProductType");
                            idProduct = jsonObject.getInt("IdProduct");
                            nameProduct = jsonObject.getString("nameProduct");
                            priceProduct = jsonObject.getInt("priceProduct");
                            imageProduct = jsonObject.getString("imageProduct");
                            describeProduct = jsonObject.getString("describeProduct");
                            listProduct.add(new Product(idProduct, nameProduct, priceProduct, imageProduct, describeProduct, idProductType));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                mMainFragmentView.getListProductSuccess(listProduct);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.showToast(mContext, error.toString());
                Log.e("==============>", error.toString());
                mMainFragmentView.getListProductFailed(error.toString());
            }
        });
        requestQueue.add(arrayRequest);

    }
}
