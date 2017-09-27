package com.example.ominext.storedeviceonline.ui.orderconfirm;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ominext.storedeviceonline.R;
import com.example.ominext.storedeviceonline.model.Cache;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class OrderConfirmFragment extends Fragment implements OrderConfirmView {
    @BindView(R.id.btn_confirm)
    Button btnConfirm;
    Unbinder unbinder;
    OrderConfirmPresenter mPresenter;
    List<Integer> listId = new ArrayList<>();
    private String name = "";
    private String fileName = "cart.txt";
    String path = null;
    File file = null;
    List<Cart> cartList = new ArrayList<>();

    public OrderConfirmFragment() {
    }

    public static OrderConfirmFragment newInstance() {
        OrderConfirmFragment fragment = new OrderConfirmFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initFile();
        Bundle bundle = getArguments();
        name = bundle.getString("name");
        mPresenter = new OrderConfirmPresenter(getContext(), this);
        mPresenter.getIdOrderProduct();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_confirm, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_confirm)
    public void onViewClicked() {
        if (CheckConnectionInternet.haveNetWorkConnection(getContext())) {
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            StringRequest detailStringRequest = new StringRequest(Request.Method.POST, Server.urlPostDetail, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("========>", response);
                    Bundle bundle = new Bundle();
                    bundle.putString("name", name);
                    Fragment fragment = NotificationFragment.newInstance();
                    fragment.setArguments(bundle);
                    ((HomeActivity) getActivity()).addFragment(fragment);
//                    sau khi đặt hàng xong xóa giỏ hàng khỏi bộ nhớ đệm
                    File f = new File(path + fileName);
                    f.delete();
                    ActionItemBadge.update(((HomeActivity) getActivity()).optionsMenu.findItem(R.id.menu_cart), Integer.MIN_VALUE);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("=========>error", error.toString());
                    Toast.makeText(getContext(), "Đơn hàng chưa được đặt. Không thể kết nối được với máy chủ", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getContext(), "Đơn hàng chưa được đặt. Kiểm tra lại kết nối", Toast.LENGTH_LONG).show();
        }
    }

    public void initFile() {
        File file1 = new File(Environment.getExternalStorageDirectory(), getContext().getPackageName());
        if (!file1.exists())
            file1.mkdir();
        path = file1.getAbsolutePath() + "/";

        File file2 = new File(path);
        if (!file2.exists()) {
            file2.mkdirs();
        }
        file = new File(path + fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        cartList = Cache.readFile(path + fileName);
    }

    @Override
    public void getIdOrderProductSuccessfully(List<Integer> listId) {
        this.listId = listId;
    }

    @Override
    public void getIdOrderProductFailed(String s) {

    }
}
