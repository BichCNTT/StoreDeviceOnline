package com.example.ominext.store.ui.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ominext.store.R;
import com.example.ominext.store.helper.ImageViewUtil;
import com.example.ominext.store.helper.PriceFormatUtil;
import com.example.ominext.store.ui.auction.AuctionFragment;
import com.example.ominext.store.ui.cart.CartFragment;
import com.example.ominext.store.ui.home.HomeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class DetailProductFragment extends Fragment {

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.btn_order)
    Button btnOrder;
    @BindView(R.id.tv_product_detail)
    TextView tvProductDetail;
    @BindView(R.id.img_product)
    ImageView imgProduct;
    @BindView(R.id.spinner_quantity)
    Spinner spinnerQuantity;
    @BindView(R.id.linear_layout_auction)
    LinearLayout linearLayoutAuction;
    @BindView(R.id.btn_auction)
    Button btnAuction;
    Unbinder unbinder;
    String name;
    int price;
    String describe;
    String image;
    int id;
    int auction;
    String dateStart;
    String dateStop;

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
        linearLayoutAuction.setVisibility(View.GONE);
        Bundle bundle = this.getArguments();
        id = bundle.getInt("id");
        name = bundle.getString("name");
        price = bundle.getInt("price");
        describe = bundle.getString("describe");
        image = bundle.getString("image");
        auction = bundle.getInt("auction");
        dateStart = bundle.getString("dateStart");
        dateStop = bundle.getString("dateStop");
        if (auction == 1) {
            linearLayoutAuction.setVisibility(View.VISIBLE);
            btnOrder.setVisibility(View.GONE);
            spinnerQuantity.setVisibility(View.GONE);
        }
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

    @OnClick({R.id.btn_order, R.id.btn_auction})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_order:
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
                break;
            case R.id.btn_auction:
                if (HomeActivity.listProductType.get(0).getNameProductType().equals("Đăng nhập")) {
                    Toast.makeText(getContext(), "Bạn cần đăng nhập trước khi tiến hành đấu giá cho sản phẩm", Toast.LENGTH_SHORT).show();
                } else {
                    Bundle bundleAuction = new Bundle();
                    bundleAuction.putInt("id", id);
                    bundleAuction.putString("name", name);
                    bundleAuction.putInt("price", price);
                    bundleAuction.putString("image", image);
                    bundleAuction.putString("dateStart", dateStart);
                    bundleAuction.putString("dateStop", dateStop);
                    AuctionFragment auctionFragment = new AuctionFragment();
                    auctionFragment.setArguments(bundleAuction);
                    ((HomeActivity) getActivity()).addFragment(auctionFragment);
                }
                break;
        }
    }

}