package com.example.ominext.storedeviceonline.ui.laptop;

import android.content.Context;
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

public class LaptopFragment extends Fragment implements OnItemClickListener, LaptopView {
    @BindView(R.id.rv_laptop)
    RecyclerView rvLaptop;
    Unbinder unbinder;
    LaptopAdapter adapter;
//    View itemView;
    List<Product> productList = new ArrayList<>();
    LaptopPresenter mPresenter;

    public LaptopFragment() {
    }

    public static LaptopFragment newInstance() {
        LaptopFragment fragment = new LaptopFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        itemView = inflater.inflate(R.layout.progressbar, null);
        init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_laptop, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void init() {
        adapter = new LaptopAdapter(productList, getContext());
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        rvLaptop.setLayoutManager(layoutManager);
        rvLaptop.setHasFixedSize(true);
        rvLaptop.setAdapter(adapter);
        adapter.setClickListener(this);
        mPresenter = new LaptopPresenter(LaptopFragment.this.getContext(), this);
        mPresenter.getListLaptop();
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
        ((HomeActivity) getActivity()).addFragment(fragment);
    }

    @Override
    public void getListLaptopSuccess(List<Product> products) {
        productList.clear();
        productList.addAll(products);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getListLaptopFailed(String s) {
        Toast.makeText(getContext(), "Lỗi tải trang", Toast.LENGTH_SHORT).show();
    }
}
