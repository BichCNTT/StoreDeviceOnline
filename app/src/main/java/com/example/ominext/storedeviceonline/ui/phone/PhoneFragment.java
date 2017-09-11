package com.example.ominext.storedeviceonline.ui.phone;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ominext.storedeviceonline.R;
import com.example.ominext.storedeviceonline.model.Product;
import com.example.ominext.storedeviceonline.listener.OnItemClickListener;
import com.example.ominext.storedeviceonline.ui.home.HomeActivity;
import com.example.ominext.storedeviceonline.ui.detail.DetailProductFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class PhoneFragment extends Fragment implements OnItemClickListener, PhoneView {
    PhoneAdapter adapter;
    List<Product> productList = new ArrayList<>();
    @BindView(R.id.rv_phone)
    RecyclerView rvPhone;
    Unbinder unbinder;

    private PhonePresenter mPresenter;

    public PhoneFragment() {
        // Required empty public constructor
    }

    public static PhoneFragment newInstance() {
        PhoneFragment fragment = new PhoneFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_phone, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    private void init() {
        adapter = new PhoneAdapter(productList, getContext());
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        rvPhone.setLayoutManager(layoutManager);
        rvPhone.setHasFixedSize(true);
        rvPhone.setAdapter(adapter);
        adapter.setClickListener(this);
        mPresenter = new PhonePresenter(PhoneFragment.this.getContext(), this);
        mPresenter.getListPhone();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onItemClick(View view, int position) {
        DetailProductFragment fragment = DetailProductFragment.newInstance();
        Bundle bundle = new Bundle();
        Product product = productList.get(position);
        bundle.putString("name", product.getNameProduct());
        bundle.putInt("price", product.getPriceProduct());
        bundle.putString("describe", product.getDescribeProduct());
        bundle.putString("image", product.getImageProduct());
        fragment.setArguments(bundle);
//thêm vào
        ((HomeActivity) getActivity()).addFragment(fragment);
    }

    @Override
    public void getListPhoneSuccess(List<Product> products) {
        productList.clear();
        productList.addAll(products);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getListPhoneFailed(String s) {
        Toast.makeText(getContext(), "Lỗi tải trang", Toast.LENGTH_SHORT).show();
    }
}
