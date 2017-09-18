package com.example.ominext.storedeviceonline.ui.cart;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
public class CartFragment extends Fragment implements CartView, OnItemClickListener {
    @BindView(R.id.rv_cart)
    RecyclerView rvCart;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.btn_pay)
    Button btnPay;
    @BindView(R.id.btn_continue)
    Button btnContinue;
    Unbinder unbinder;
    CartAdapter adapter = new CartAdapter();
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
    String fileNameOutPut = "cart1.txt";

    public CartFragment() {
        // Required empty public constructor
    }

    //gop san pham giong nhau lam mot
//    truoc khi them vao list. ktra xem danh sach do co chua pham tu tuong tu hay khong. neu co thi so luong sp number se bang cai cu ma cong voi cai moi va them vao list trog ds
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
            cartList = Cache.readFile(path + fileName);
//            them vao nay->doc file txt ra xog ktra
            int check = 0;//check de ktra xem cai tong so luong co lon hon so luong san pham toi da co the co hay khong neu lon hon thi thong bao khong duoc vuot qua 10 sp
            if (!cartList.isEmpty()) {
                for (int i = 0; i < cartList.size(); i++) {
                    if ((cartList.get(i).getName().equals(name)) && (cartList.get(i).getImage().equals(image)) && (cartList.get(i).getPrice() == price)) {
                        if(number + cartList.get(i).getNumber() <= 10){
//                            xoa trong file text di sau do them vao 1 ptu moi
//                        bien phan tu thu i thanh chuoi json xong xoa
                            try {
                                number += cartList.get(i).getNumber();
                                Cache.delete(path, fileName, fileNameOutPut, Cache.writeJsonStream(cartList.get(i)));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                        } else {
                                check = 1;
                            }
                        }
                    }
                }
            if (check != 1) {
                Cart cart = new Cart(name, image, number, price);
                cartList.add(cart);
                try {
                    String jsonText = Cache.writeJsonStream(cart);
                    Cache.saveToFile(path, fileName, jsonText);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(getContext());
                }
                builder.setTitle("Lưu ý")
                        .setMessage("Mặt hàng này đã tồn tại. Số lượng sản phẩm cộng dồn lớn hơn mức cho phép. Bạn chỉ có thể thêm vào giỏ tối đa 10 sản phẩm cho mỗi mặt hàng")
                        .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        })
                        .setIcon(android.R.drawable.ic_secure)
                        .show();
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
        int money;
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
                if (cartList.isEmpty()) {
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(getContext());
                    }
                    builder.setTitle("Thông báo")
                            .setMessage("Bạn cần thêm hàng vào giỏ để tiến hành đặt hàng")
                            .setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putInt("key", 1);
                    bundle.putInt("totalMoney", getTotalMoney());
                    Fragment fragment = UserInfoFragment.newInstance();
                    fragment.setArguments(bundle);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.add(R.id.frame_layout, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
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
        if (cartList.isEmpty()) {
            tvNoData.setVisibility(View.VISIBLE);
        }
        setMoney();
        money = getTotalMoney();
        PriceFormatUtil.priceFormat(tvMoney, money);
    }
}
