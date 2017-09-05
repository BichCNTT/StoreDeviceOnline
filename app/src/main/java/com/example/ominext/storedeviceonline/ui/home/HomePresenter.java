package com.example.ominext.storedeviceonline.ui.home;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.ominext.storedeviceonline.model.ProductType;
import com.example.ominext.storedeviceonline.until.CheckConnection;
import com.example.ominext.storedeviceonline.until.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ominext on 8/28/2017.
 */
//presenter để giao tiếp giữa view với model, ở đây sẽ tồn tại các phương thức sử lý yc để trả kquả về view
public class HomePresenter {

    int id = 0;
    String nameProductType = "";
    String imageProductType = "";
    String nameProduct = "";
    private Context mContext;
    private HomeView mHomeView;

    public HomePresenter(Context context, HomeView homeView) {
        this.mContext = context;
        this.mHomeView = homeView;
    }

    public void getListProductType() {
        final List<ProductType> listProductType = new ArrayList<>();
        listProductType.add(new ProductType(0, "Trang chủ", "https://image.flaticon.com/icons/png/512/25/25694.png"));
        final RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Server.urlProductType, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("IdProductType");
                            nameProductType = jsonObject.getString("NameProductType");
                            imageProductType = jsonObject.getString("ImageProductType");
                            listProductType.add(new ProductType(id, nameProductType, imageProductType));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    listProductType.add(new ProductType(13, "Liên hệ", "http://www.freeiconspng.com/uploads/phone-icon-old-phone-telephone-icon-9.png"));
                    listProductType.add(new ProductType(14, "Thông tin", "http://www.freeiconspng.com/uploads/details-info-information-more-details-icon--icon-search-engine--7.png"));
                    mHomeView.getListProductTypeSuccess(listProductType);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                CheckConnection.showToast(mContext, error.toString());
                Log.e("==============>", error.toString());
                mHomeView.getListProductTypeFailed(error.toString());
            }
        });
        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        arrayRequest.setRetryPolicy(policy);
        arrayRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 5, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(arrayRequest);
    }

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
                mHomeView.getListFindSuccess(listFind);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.showToast(mContext, error.toString());
                Log.e("==============>", error.toString());
                mHomeView.getListFindFailed(error.toString());
            }
        });
        requestQueue.add(arrayRequest);
    }
}
