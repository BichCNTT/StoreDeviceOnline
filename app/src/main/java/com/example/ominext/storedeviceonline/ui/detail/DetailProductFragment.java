package com.example.ominext.storedeviceonline.ui.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ominext.storedeviceonline.R;
import com.example.ominext.storedeviceonline.helper.ImageViewUtil;
import com.example.ominext.storedeviceonline.helper.PriceFormatUtil;
import com.example.ominext.storedeviceonline.model.Cache;
import com.example.ominext.storedeviceonline.ui.cart.CartFragment;
import com.example.ominext.storedeviceonline.ui.home.HomeActivity;
import com.mikepenz.actionitembadge.library.ActionItemBadge;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

//nhận dữ liệu từ listview sản phẩm mới nhất:
//tên sản phẩm, giá, chi tiết sản phẩm, hình ảnh
//nhận dữ liệu từ FragmentLaptop, FragmentPhone
public class DetailProductFragment extends Fragment {

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.btn_order)
    Button btnOrder;
    @BindView(R.id.tv_product_detail)
    TextView tvProductDetail;
    Unbinder unbinder;
    String name = "";
    int price = 0;
    String describe = "";
    String image = "";
    int id = 0;
    @BindView(R.id.img_product)
    ImageView imgProduct;
    @BindView(R.id.spinner_quantity)
    Spinner spinnerQuantity;

    public DetailProductFragment() {
    }

    public static DetailProductFragment newInstance() {
        DetailProductFragment fragment = new DetailProductFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        getActivity().setTitle("Chi tiết sản phẩm");
        Bundle bundle = this.getArguments();
        id = bundle.getInt("id");
        name = bundle.getString("name");
        price = bundle.getInt("price");
        describe = bundle.getString("describe");
        image = bundle.getString("image");
        tvName.setText(name);
        PriceFormatUtil.priceFormat(tvPrice, price);
        tvProductDetail.setText(describe);
        ImageViewUtil.loadImg(getContext(), image, imgProduct);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.quantity_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerQuantity.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_product, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_order)
    public void onViewClicked() {
//        kích vào đặt hàng thì truyền list và số lượng sp sang -> dùng bundle
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        bundle.putInt("number", Integer.parseInt(spinnerQuantity.getSelectedItem().toString()));
        bundle.putString("name", name);
        bundle.putString("image", image);
        bundle.putInt("price", price);
        bundle.putInt("key", 1);
        Fragment fragment = CartFragment.newInstance();
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frame_layout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}