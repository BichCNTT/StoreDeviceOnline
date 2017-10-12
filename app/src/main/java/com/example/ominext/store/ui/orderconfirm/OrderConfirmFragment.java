package com.example.ominext.store.ui.orderconfirm;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ominext.store.R;
import com.example.ominext.store.model.Cache;
import com.example.ominext.store.model.Cart;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        mPresenter.postOrderDetail(name, path, fileName, cartList, listId);
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
