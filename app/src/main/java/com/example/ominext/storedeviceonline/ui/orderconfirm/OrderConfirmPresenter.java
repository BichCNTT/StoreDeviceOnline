package com.example.ominext.storedeviceonline.ui.orderconfirm;

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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ominext.storedeviceonline.R;
import com.example.ominext.storedeviceonline.model.Cart;
import com.example.ominext.storedeviceonline.ui.home.HomeActivity;
import com.example.ominext.storedeviceonline.ui.notifi.NotificationFragment;
import com.example.ominext.storedeviceonline.until.CheckConnectionInternet;
import com.example.ominext.storedeviceonline.until.Server;
import com.mikepenz.actionitembadge.library.ActionItemBadge;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ominext on 9/12/2017.
 */

public class OrderConfirmPresenter {
    private Context mContext;
    private OrderConfirmView mOrderView;
    private int mId;

    public OrderConfirmPresenter(Context mContext, OrderConfirmView mOrderView) {
        this.mContext = mContext;
        this.mOrderView = mOrderView;
    }

    public void getIdOrderProduct() {
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        final List<Integer> listId = new ArrayList<>();
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Server.urlGetIdOrderProduct, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if ((response != null) && (response.length() > 0)) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            mId = jsonObject.getInt("id");
                            listId.add(mId);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                mOrderView.getIdOrderProductSuccessfully(listId);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("==============>", error.toString());
                mOrderView.getIdOrderProductFailed(error.toString());
            }
        });
        requestQueue.add(arrayRequest);
    }

    public void postOrderDetail(final String name, final String path, final String fileName, final List<Cart> cartList, final List<Integer> listId) {
        if (CheckConnectionInternet.haveNetWorkConnection(mContext)) {
            RequestQueue requestQueue = Volley.newRequestQueue(mContext);
            StringRequest detailStringRequest = new StringRequest(Request.Method.POST, Server.urlPostDetail, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("========>", response);
                    Bundle bundle = new Bundle();
                    bundle.putString("name", name);
                    Fragment fragment = NotificationFragment.newInstance();
                    fragment.setArguments(bundle);
                    ((HomeActivity) mContext).addFragment(fragment);
//                  sau khi đặt hàng xong xóa giỏ hàng khỏi bộ nhớ đệm
                    File f = new File(path + fileName);
                    f.delete();
                    ActionItemBadge.update(((HomeActivity) mContext).optionsMenu.findItem(R.id.menu_cart), Integer.MIN_VALUE);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("=========>error", error.toString());
                    Toast.makeText(mContext, "Đơn hàng chưa được đặt. Có lỗi trong quá trình xác nhận đặt hàng", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> hashMap;
                    JSONArray jsonArray = new JSONArray();
                    for (int i = 0; i < cartList.size(); i++) {
                        JSONObject object = null;
                        try {
                            object = new JSONObject();
                            object.put("id", null);
                            object.put("idOrder", listId.get(listId.size() - 1));
                            object.put("idProduct", cartList.get(i).getId());
                            object.put("number", cartList.get(i).getNumber());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        jsonArray.put(object);
                    }
                    hashMap = new HashMap<>();
                    hashMap.put("json", jsonArray.toString());
                    return hashMap;
                }
            };
            requestQueue.add(detailStringRequest);
        } else {
            Toast.makeText(mContext, "Đơn hàng chưa được đặt. Kiểm tra lại kết nối", Toast.LENGTH_LONG).show();
        }
    }
}
