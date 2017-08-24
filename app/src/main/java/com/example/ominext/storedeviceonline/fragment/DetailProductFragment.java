package com.example.ominext.storedeviceonline.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ominext.storedeviceonline.R;
import com.example.ominext.storedeviceonline.helper.ImageViewUtil;
import com.example.ominext.storedeviceonline.helper.PriceFormatUtil;

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
    @BindView(R.id.edt_quantity)
    EditText edtQuantity;
    @BindView(R.id.btn_order)
    Button btnOrder;
    @BindView(R.id.tv_product_detail)
    TextView tvProductDetail;
    Unbinder unbinder;
    String name = "";
    int price = 0;
    String describe = "";
    String image = "";
    @BindView(R.id.img_product)
    ImageView imgProduct;

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
        Bundle bundle = this.getArguments();
        name = bundle.getString("name");
        price = bundle.getInt("price");
        describe = bundle.getString("describe");
        image = bundle.getString("image");
        tvName.setText(name);
        PriceFormatUtil.priceFormat(tvPrice, price);
        tvProductDetail.setText(describe);
        if (image.isEmpty()) {
            imgProduct.setImageResource(R.drawable.ic_cancel);
        } else{
            ImageViewUtil.loadImg(getContext(), image, imgProduct);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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
    }
}