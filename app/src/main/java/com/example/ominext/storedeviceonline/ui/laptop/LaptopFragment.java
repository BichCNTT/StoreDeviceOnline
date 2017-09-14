package com.example.ominext.storedeviceonline.ui.laptop;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ominext.storedeviceonline.R;
import com.example.ominext.storedeviceonline.listener.OnItemClickListener;
import com.example.ominext.storedeviceonline.model.Product;
import com.example.ominext.storedeviceonline.ui.detail.DetailProductFragment;
import com.example.ominext.storedeviceonline.ui.home.HomeActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class LaptopFragment extends Fragment implements OnItemClickListener, LaptopView, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.rv_laptop)
    RecyclerView rvLaptop;
    Unbinder unbinder;
    LaptopAdapter adapter;
    //    View itemView;
    List<Product> productList = new ArrayList<>();
    LaptopPresenter mPresenter;
    @BindView(R.id.img_change)
    ImageView imgChange;
    @BindView(R.id.spinner_filter)
    Spinner spinnerFilter;
    @BindView(R.id.img_filter)
    ImageView imgFilter;
    int change = 1;
    @BindView(R.id.swipe_refresh_layout_laptop)
    SwipeRefreshLayout swipeRefreshLayoutLaptop;

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
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), change);
        rvLaptop.setLayoutManager(layoutManager);
        rvLaptop.setHasFixedSize(true);
        rvLaptop.setAdapter(adapter);
        adapter.setClickListener(this);
        mPresenter = new LaptopPresenter(LaptopFragment.this.getContext(), this);
//        mPresenter.getListLaptop();
        final ArrayAdapter<CharSequence> adapterFilter = ArrayAdapter.createFromResource(getContext(),
                R.array.fitter_array, android.R.layout.simple_spinner_item);
        adapterFilter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFilter.setAdapter(adapterFilter);
        swipeRefreshLayoutLaptop.setOnRefreshListener(this);
        swipeRefreshLayoutLaptop.post(new Runnable() {
                                          @Override
                                          public void run() {
                                              swipeRefreshLayoutLaptop.setRefreshing(true);
                                              refreshContent();
                                          }
                                      }
        );
    }

    private void refreshContent() {
        swipeRefreshLayoutLaptop.setRefreshing(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mPresenter.getListLaptop();
                swipeRefreshLayoutLaptop.setRefreshing(false);
            }
        }, 1000);
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

    public void change(int i) {
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), i);
        rvLaptop.setLayoutManager(layoutManager);
        rvLaptop.setHasFixedSize(true);
    }

    @OnClick({R.id.img_change, R.id.img_filter})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_change:
                if (change == 1) {
                    change = 2;
                    change(change);
                } else {
                    change = 1;
                    change(change);
                }

                break;
            case R.id.img_filter:
                String chose = spinnerFilter.getSelectedItem().toString();
                if (chose.equals("Giá từ thấp đến cao")) {
                    mPresenter.getListSortUpLaptop();
                } else {
                    mPresenter.getListSortDownLaptop();
                }
                adapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void onRefresh() {
        refreshContent();
    }
}
