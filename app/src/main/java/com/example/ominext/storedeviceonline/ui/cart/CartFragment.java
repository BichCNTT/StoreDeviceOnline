package com.example.ominext.storedeviceonline.ui.cart;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ominext.storedeviceonline.R;
import com.example.ominext.storedeviceonline.helper.PriceFormatUtil;
import com.example.ominext.storedeviceonline.listener.OnItemClickListener;
import com.example.ominext.storedeviceonline.model.Cache;
import com.example.ominext.storedeviceonline.model.Cart;
import com.example.ominext.storedeviceonline.model.Product;
import com.example.ominext.storedeviceonline.ui.home.HomeActivity;
import com.example.ominext.storedeviceonline.ui.info.InformationFragment;
import com.example.ominext.storedeviceonline.ui.userinfo.UserInfoFragment;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
//xóa trong file text
public class CartFragment extends Fragment implements CartView,OnItemClickListener {
    @BindView(R.id.rv_cart)
    RecyclerView rvCart;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.btn_pay)
    Button btnPay;
    @BindView(R.id.btn_continue)
    Button btnContinue;
    Unbinder unbinder;
    CartAdapter adapter;
    List<Cart> cartList = new ArrayList<>();
    String name = "";
    int price = 0;
    String image = "";
    int number = 0;
    int money = 0;
    int key;
    @BindView(R.id.tv_no_data)
    public TextView tvNoData;
    @BindView(R.id.tv_total)
    TextView tvTotal;

    String path = null;
    String fileName = "cart.txt";
    File file = null;

    //hiển thị các row dữ liệu, lấy số lượng hàng đã đặt set trong text của row
    public CartFragment() {
        // Required empty public constructor
    }

    public static CartFragment newInstance() {
        CartFragment fragment = new CartFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initFile();
        tvNoData.setVisibility(View.GONE);
        Bundle bundle = getArguments();
        key = bundle.getInt("key");
        if (key == 1) {
            name = bundle.getString("name");
            price = bundle.getInt("price");
            image = bundle.getString("image");
            number = bundle.getInt("number");
            Cart cart = new Cart(name, image, number, price);
            cartList.add(cart);
            try {
                String jsonText = Cache.writeJsonStream(cart);
                Cache.saveToFile(path, fileName, jsonText);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        cartList = Cache.readFile(path + fileName);
        if (cartList.isEmpty()) {
            tvNoData.setVisibility(View.VISIBLE);
            tvMoney.setVisibility(View.GONE);
            tvTotal.setVisibility(View.GONE);
            btnContinue.setVisibility(View.GONE);
            btnPay.setVisibility(View.GONE);
        }
        adapter = new CartAdapter(cartList, getContext());
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        rvCart.setLayoutManager(layoutManager);
        rvCart.setHasFixedSize(true);
        rvCart.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        setMoney();
        money = getTotalMoney();
        PriceFormatUtil.priceFormat(tvMoney, money);
        adapter.setClickListener(this);
    }

    private void setMoney() {
        int money = 0;
        for (int i = 0; i < cartList.size(); i++) {
            money = cartList.get(i).getPrice() * cartList.get(i).getNumber();
            cartList.get(i).setMoney(money);
        }
    }

    private int getTotalMoney() {
        int totalMoney = 0;
        for (int i = 0; i < cartList.size(); i++) {
            totalMoney += cartList.get(i).getMoney();
        }
        return totalMoney;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_pay, R.id.btn_continue})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_pay:
                Fragment fragment = UserInfoFragment.newInstance();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            case R.id.btn_continue:
                Intent intent = new Intent(getContext(), HomeActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void getListCartSuccess(List<Product> products) {

    }

    @Override
    public void getListCartFailed(String s) {

    }

    @Override
    public void onItemClick(View view, int position) {
        if(cartList.isEmpty()){
            tvNoData.setVisibility(View.VISIBLE);
        }
        setMoney();
        money = getTotalMoney();
        PriceFormatUtil.priceFormat(tvMoney, money);
    }
}
