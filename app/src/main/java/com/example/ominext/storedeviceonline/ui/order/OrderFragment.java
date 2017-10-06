package com.example.ominext.storedeviceonline.ui.order;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ominext.storedeviceonline.R;
import com.example.ominext.storedeviceonline.helper.PriceFormatUtil;
import com.example.ominext.storedeviceonline.model.Cache;
import com.example.ominext.storedeviceonline.model.Cart;
import com.example.ominext.storedeviceonline.ui.home.HomeActivity;
import com.example.ominext.storedeviceonline.ui.login.LoginFragment;
import com.example.ominext.storedeviceonline.ui.orderconfirm.OrderConfirmFragment;
import com.example.ominext.storedeviceonline.ui.orderconfirm.OrderConfirmPresenter;
import com.example.ominext.storedeviceonline.ui.orderconfirm.OrderConfirmView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class OrderFragment extends Fragment implements OrderView, OrderConfirmView {

    @BindView(R.id.tv_name_user)
    TextView tvNameUser;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_address)
    TextView tvAddress;
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
    String fileName = "cart.txt";
    File file = null;
    List<Cart> cartList = new ArrayList<>();
    OrderAdapter adapter;
    List<Integer> listId = new ArrayList<>();
    OrderConfirmPresenter mOrderConfirmPresenter;
    OrderPresenter mOrderPresenter;

    public OrderFragment() {

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

        View view = inflater.inflate(R.layout.fragment_order, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Đơn hàng của tôi");
        mOrderConfirmPresenter = new OrderConfirmPresenter(getContext(), this);
        mOrderPresenter = new OrderPresenter(getContext(), this);
        init();
        initFile();
        setMoney();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_order_product)
    public void onViewClicked() {
        mOrderPresenter.postOrderProduct(name);
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
        tvPhone.setText(phone + "");
        tvAddress.setText(address);
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

    @Override
    public void postOrderProductSuccessfully() {
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        Fragment fragment = OrderConfirmFragment.newInstance();
        fragment.setArguments(bundle);
        ((HomeActivity) getActivity()).addFragment(fragment);
    }

    @Override
    public void postOrderProductFailed(String s) {
        Toast.makeText(getContext(), "Đơn hàng chưa được đặt. Không thể kết nối được với máy chủ", Toast.LENGTH_SHORT).show();
    }
}
