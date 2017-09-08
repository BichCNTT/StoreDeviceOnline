package com.example.ominext.storedeviceonline.ui.order;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ominext.storedeviceonline.R;
import com.example.ominext.storedeviceonline.helper.PriceFormatUtil;
import com.example.ominext.storedeviceonline.model.Cache;
import com.example.ominext.storedeviceonline.model.Cart;
import com.example.ominext.storedeviceonline.ui.infopro.InformationFragment;
import com.example.ominext.storedeviceonline.ui.notifi.NotificationFragment;
import com.example.ominext.storedeviceonline.ui.userinfo.UserInfoFragment;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class OrderFragment extends Fragment {

    @BindView(R.id.tv_name_user)
    TextView tvNameUser;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_edit)
    TextView tvEdit;
    @BindView(R.id.rv_product)
    RecyclerView rvProduct;
    @BindView(R.id.tv_total_money)
    TextView tvTotalMoney;
    @BindView(R.id.btn_order_product)
    Button btnOrderProduct;
    Unbinder unbinder;
    int totalMoney = 0;
    String name = "";
    String phone = "";
    String address = "";
    String path = null;
    String fileName = "cart.txt";
    File file = null;
    List<Cart> cartList = new ArrayList<>();
    OrderAdapter adapter;

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
        init();
        initFile();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_order_product)
    public void onViewClicked() {
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        Fragment fragment = NotificationFragment.newInstance();
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void init() {
        getActivity().setTitle("Đơn hàng của tôi");
        Bundle bundle = getArguments();
        totalMoney = bundle.getInt("totalMoney");
        name = bundle.getString("nameUser");
        phone = bundle.getString("phoneUser");
        address = bundle.getString("addressUser");
        PriceFormatUtil.priceFormat(tvTotalMoney, totalMoney);
        tvNameUser.setText(name);
        tvPhone.setText(phone);
        tvAddress.setText(address);
        tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("key", 0);
                bundle.putInt("totalMoney", totalMoney);
                bundle.putString("nameUser", name);
                bundle.putString("addressUser", address);
                bundle.putString("phoneUser", phone);
                Fragment fragment = UserInfoFragment.newInstance();
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.frame_layout, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
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
}
