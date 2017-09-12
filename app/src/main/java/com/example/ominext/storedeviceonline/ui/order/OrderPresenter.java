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
import com.example.ominext.storedeviceonline.ui.notifi.NotificationFragment;
import com.example.ominext.storedeviceonline.until.CheckConnectionInternet;
import com.example.ominext.storedeviceonline.until.Server;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ominext on 9/12/2017.
 */

public class OrderPresenter {
    private Context mContext;
    private OrderView mOrderView;
    private String mPath;
    private String mName, mPhone, mAddress;
    private String mFileName;

    public OrderPresenter(Context mContext, OrderView mOrderView, String mPath, String mName, String mPhone, String mAddress, String mFileName) {
        this.mContext = mContext;
        this.mOrderView = mOrderView;
        this.mPath = mPath;
        this.mName = mName;
        this.mPhone = mPhone;
        this.mAddress = mAddress;
        this.mFileName = mFileName;
    }
//
//    public OrderPresenter(Context mContext, OrderView mOrderView) {
//        this.mContext = mContext;
//        this.mOrderView = mOrderView;
//
//    }

    public void postClient() {
        if (CheckConnectionInternet.haveNetWorkConnection(mContext)) {
            RequestQueue requestQueue = Volley.newRequestQueue(mContext);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.urlPostClientInfo, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("========>", response);
                    Bundle bundle = new Bundle();
                    bundle.putString("name", mName);
                    Fragment fragment = NotificationFragment.newInstance();
                    fragment.setArguments(bundle);
                    ((HomeActivity) mContext).addFragment(fragment);
//                    sau khi đặt hàng xong xóa giỏ hàng khỏi bộ nhớ đệm
                    File f = new File(mPath + mFileName);
                    f.delete();
                    mOrderView.postClientInfoSuccess();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("=========>error", error.toString());
                    Toast.makeText(mContext, "Đơn hàng chưa được đặt. Không thể kết nối được với máy chủ", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("name", mName);
                    map.put("phone", mPhone);
                    map.put("address", mAddress);
                    return map;
                }
            };
            requestQueue.add(stringRequest);
        } else {
            Toast.makeText(mContext, "Đơn hàng chưa được đặt. Kiểm tra lại kết nối", Toast.LENGTH_LONG).show();
        }
    }
}
