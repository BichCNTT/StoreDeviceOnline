package com.example.ominext.storedeviceonline.ui.cart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ominext.storedeviceonline.R;
import com.example.ominext.storedeviceonline.helper.PriceFormatUtil;
import com.example.ominext.storedeviceonline.model.Cart;
import com.example.ominext.storedeviceonline.model.Product;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

//khoong lay duoc gia tri
public class CartFragment extends Fragment implements CartView {
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
    @BindView(R.id.tv_no_data)
    TextView tvNoData;
    @BindView(R.id.tv_total)
    TextView tvTotal;

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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvNoData.setVisibility(View.GONE);
        Bundle bundle = getArguments();
        name = bundle.getString("name");
        price = bundle.getInt("price");
        image = bundle.getString("image");
        number = bundle.getInt("number");

        cartList.add(new Cart(name, image, number, price));
        if (name.isEmpty()) {
            cartList.remove(0);
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
        money = price * number;
        PriceFormatUtil.priceFormat(tvMoney, money);
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
                break;
            case R.id.btn_continue:
                break;
        }
    }

    @Override
    public void getListCartSuccess(List<Product> products) {

    }

    @Override
    public void getListCartFailed(String s) {

    }
}
