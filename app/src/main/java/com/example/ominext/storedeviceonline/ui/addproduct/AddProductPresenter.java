package com.example.ominext.storedeviceonline.ui.addproduct;

import android.content.Context;
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
import com.example.ominext.storedeviceonline.ui.main.MainFragment;
import com.example.ominext.storedeviceonline.until.CheckConnectionInternet;
import com.example.ominext.storedeviceonline.until.Server;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ominext on 10/6/2017.
 */

public class AddProductPresenter {
    Context mContext;

    public AddProductPresenter(Context mContext) {
        this.mContext = mContext;
    }

    public void postProduct(final String nameProduct, final int priceProduct, final String image, final int idProductType, final String describeProduct, final String dateFrom, final String dateTo, final int check) {
        if (CheckConnectionInternet.haveNetWorkConnection(mContext)) {
            RequestQueue requestQueue = Volley.newRequestQueue(mContext);
            StringRequest orderProductStringRequest = new StringRequest(Request.Method.POST, Server.urlPostProduct, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("============>", response);
                    Toast.makeText(mContext, "Đăng mặt hàng thành công", Toast.LENGTH_SHORT).show();
                    Fragment fragment = MainFragment.newInstance();
                    ((HomeActivity) mContext).addFragment(fragment);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("=========>error", error.toString());
                    Toast.makeText(mContext, "Mặt hàng chưa được đăng. Không thể kết nối được với máy chủ", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("nameProduct", nameProduct);
                    hashMap.put("priceProduct", priceProduct + "");
                    hashMap.put("imageProduct", image);
                    hashMap.put("describeProduct", describeProduct);
                    hashMap.put("IdProductType", idProductType + "");
                    hashMap.put("idUser", LoginFragment.user.getId() + "");
                    hashMap.put("auction", null);
                    hashMap.put("dateStart", dateFrom);
                    hashMap.put("dateStop", dateTo);
                    if (check == 1) {
                        hashMap.put("auction", 1 + "");
                    }
                    return hashMap;
                }
            };
            requestQueue.add(orderProductStringRequest);
        } else {
            Toast.makeText(mContext, "Mặt hàng chưa được đăng. Kiểm tra lại kết nối", Toast.LENGTH_LONG).show();
        }
    }
}
