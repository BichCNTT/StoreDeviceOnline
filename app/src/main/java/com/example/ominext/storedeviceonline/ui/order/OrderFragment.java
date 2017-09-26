package com.example.ominext.storedeviceonline.ui.order;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ominext.storedeviceonline.R;
import com.example.ominext.storedeviceonline.helper.PriceFormatUtil;
import com.example.ominext.storedeviceonline.model.Cache;
import com.example.ominext.storedeviceonline.model.Cart;
import com.example.ominext.storedeviceonline.ui.home.HomeActivity;
import com.example.ominext.storedeviceonline.ui.login.LoginFragment;
import com.example.ominext.storedeviceonline.ui.main.MainFragment;
import com.example.ominext.storedeviceonline.ui.notifi.NotificationFragment;
import com.example.ominext.storedeviceonline.ui.userinfo.UserInfoFragment;
import com.example.ominext.storedeviceonline.until.CheckConnectionInternet;
import com.example.ominext.storedeviceonline.until.Server;
import com.mikepenz.actionitembadge.library.ActionItemBadge;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.http.POST;

public class OrderFragment extends Fragment implements OrderView {

    @BindView(R.id.tv_name_user)
    TextView tvNameUser;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_address)
    TextView tvAddress;
//    @BindView(R.id.tv_edit)
//    TextView tvEdit;
    @BindView(R.id.rv_product)
    RecyclerView rvProduct;
    @BindView(R.id.tv_total_money)
    TextView tvTotalMoney;
    @BindView(R.id.btn_order_product)
    Button btnOrderProduct;
    Unbinder unbinder;
    int totalMoney = 0;
    String name = "";
    int phone = 0;
    String address = "";
    String path = null;
    int id = 0;
    int idProduct = 199;
    String fileName = "cart.txt";
    File file = null;
    List<Cart> cartList = new ArrayList<>();
    OrderAdapter adapter;
    List<Integer> listId = new ArrayList<>();
    OrderPresenter mPresenter;

    public OrderFragment() {
        // Required empty public constructor
    }

    public static OrderFragment newInstance() {
        OrderFragment fragment = new OrderFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = new OrderPresenter(getContext(), this);
        mPresenter.getIdOrderProduct();
        if (!listId.isEmpty()) {
            idProduct = listId.get(listId.size() - 1);
        }
        init();
        initFile();
        setMoney();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    //thêm dữ liệu lên bảng có khóa đã điền thông tin ->kbiết
    @OnClick(R.id.btn_order_product)
    public void onViewClicked() {
        if (CheckConnectionInternet.haveNetWorkConnection(getContext())) {
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
//            StringRequest clientInfoStringRequest = new StringRequest(Request.Method.POST, Server.urlPostClientInfo, new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//                    Log.e("========>", response);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("name", name);
//                    Fragment fragment = NotificationFragment.newInstance();
//                    fragment.setArguments(bundle);
//                    ((HomeActivity) getActivity()).addFragment(fragment);
////                    sau khi đặt hàng xong xóa giỏ hàng khỏi bộ nhớ đệm
//                    File f = new File(path + fileName);
//                    f.delete();
//                    ActionItemBadge.update(((HomeActivity) getActivity()).optionsMenu.findItem(R.id.menu_cart), Integer.MIN_VALUE);
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Log.e("=========>error", error.toString());
//                    Toast.makeText(getContext(), "Đơn hàng chưa được đặt. Không thể kết nối được với máy chủ", Toast.LENGTH_SHORT).show();
//                }
//            }) {
//                @Override
//                protected Map<String, String> getParams() throws AuthFailureError {
//                    HashMap<String, String> hashMap;
//                    JSONArray jsonArray = new JSONArray();
//                    for (int i = 0; i < cartList.size(); i++) {
//                        JSONObject object = new JSONObject();
//                        try {
//                            object.put("name", name);
//                            object.put("phone", phone);
//                            object.put("address", address);
//                            object.put("nameProduct", cartList.get(i).getName());
//                            object.put("priceProduct", cartList.get(i).getPrice());
//                            object.put("numberProduct", cartList.get(i).getNumber());
//                            object.put("moneyProduct", cartList.get(i).getMoney());
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                        jsonArray.put(object);
//                    }
//                    hashMap = new HashMap<>();
//                    hashMap.put("json", jsonArray.toString());
//                    return hashMap;
//                }
//            };
//            requestQueue.add(clientInfoStringRequest);
            StringRequest orderProductStringRequest = new StringRequest(Request.Method.POST, Server.urlPostOrderProduct, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
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
                    HashMap<String, String> hashMap = new HashMap<>();
                    JSONArray jsonArray = new JSONArray();
                    JSONObject object = new JSONObject();
                    try {
                        object.put("id", idProduct + 1);
                        object.put("idUser", LoginFragment.user.getId());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    jsonArray.put(object);
                    hashMap.put("json", jsonArray.toString());
                    return hashMap;
                }
            };
            requestQueue.add(orderProductStringRequest);

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
                    JSONObject object = new JSONObject();
                    for (int i = 0; i < cartList.size(); i++) {
                        try {
                            object.put("id", idProduct - 1);
                            object.put("idOrder", idProduct + 1);
                            object.put("idProduct", cartList.get(i).getId());
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

    private void setMoney() {
        int money;
        for (int i = 0; i < cartList.size(); i++) {
            money = cartList.get(i).getPrice() * cartList.get(i).getNumber();
            cartList.get(i).setMoney(money);
        }
    }

    public void init() {
        Bundle bundle = getArguments();
        id = bundle.getInt("id");
        totalMoney = bundle.getInt("totalMoney");
        name = LoginFragment.user.getName();
        phone = LoginFragment.user.getId();
        address = LoginFragment.user.getAddress();
        PriceFormatUtil.priceFormat(tvTotalMoney, totalMoney);
        tvNameUser.setText(name);
        tvPhone.setText(phone+"");
        tvAddress.setText(address);
//        tvEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Bundle bundle = new Bundle();
//                bundle.putInt("key", 0);
//                bundle.putInt("id", id);
//                bundle.putInt("totalMoney", totalMoney);
//                bundle.putString("nameUser", name);
//                bundle.putString("addressUser", address);
//                bundle.putInt("phoneUser", phone);
//                Fragment fragment = UserInfoFragment.newInstance();
//                fragment.setArguments(bundle);
//                ((HomeActivity) getActivity()).addFragment(fragment);
//            }
//        });
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
        adapter = new OrderAdapter(cartList, getContext());
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        rvProduct.setLayoutManager(layoutManager);
        rvProduct.setHasFixedSize(true);
        rvProduct.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getIdOrderProductSuccessfully(List<Integer> listId) {
        this.listId = listId;
    }

    @Override
    public void getIdOrderProductFailed(String s) {

    }
}
