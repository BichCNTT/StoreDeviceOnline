package com.example.ominext.storedeviceonline.ui.sport;

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
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ominext.storedeviceonline.R;
import com.example.ominext.storedeviceonline.listener.OnItemClickListener;
import com.example.ominext.storedeviceonline.model.Product;
import com.example.ominext.storedeviceonline.ui.detail.DetailProductFragment;
import com.example.ominext.storedeviceonline.ui.home.HomeActivity;
import com.example.ominext.storedeviceonline.ui.laptop.ProductAdapter;
import com.example.ominext.storedeviceonline.ui.laptop.ProductPresenter;
import com.example.ominext.storedeviceonline.ui.laptop.ProductView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SportFragment extends Fragment implements OnItemClickListener, ProductView, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.rv_product)
    RecyclerView rvProduct;
    Unbinder unbinder;
    ProductAdapter adapter;
    //    View itemView;
    List<Product> productList = new ArrayList<>();
    ProductPresenter mPresenter;
    @BindView(R.id.img_change)
    ImageView imgChange;
    @BindView(R.id.spinner_sort)
    Spinner spinnerFilter;
    @BindView(R.id.img_sort)
    ImageView imgFilter;
    int change = 1;
    @BindView(R.id.swipe_refresh_layout_product)
    SwipeRefreshLayout swipeRefreshLayoutProduct;

    public SportFragment() {
    }

    public static SportFragment newInstance() {
        SportFragment fragment = new SportFragment();
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
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void init() {
        change(change);
        mPresenter = new ProductPresenter(SportFragment.this.getContext(), this);
        final ArrayAdapter<CharSequence> adapterFilter = ArrayAdapter.createFromResource(getContext(),
                R.array.fitter_array, android.R.layout.simple_spinner_item);
        adapterFilter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFilter.setAdapter(adapterFilter);
        swipeRefreshLayoutProduct.setOnRefreshListener(this);
        swipeRefreshLayoutProduct.post(new Runnable() {
                                           @Override
                                           public void run() {
                                               swipeRefreshLayoutProduct.setRefreshing(true);
                                               refreshContent();
                                           }
                                       }
        );
    }

    private void refreshContent() {
        swipeRefreshLayoutProduct.setRefreshing(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mPresenter.getListSport();
                swipeRefreshLayoutProduct.setRefreshing(false);
            }
        }, 1000);
    }

    @Override
    public void onItemClick(View view, int position) {
        DetailProductFragment fragment = DetailProductFragment.newInstance();
        Bundle bundle = new Bundle();
        Product product = productList.get(position);
        bundle.putInt("id", product.getIdProduct());
        bundle.putString("name", product.getNameProduct());
        bundle.putInt("price", product.getPriceProduct());
        bundle.putString("describe", product.getDescribeProduct());
        bundle.putString("image", product.getImageProduct());
        fragment.setArguments(bundle);
        ((HomeActivity) getActivity()).addFragment(fragment);
    }

    @Override
    public void getListProductSuccessFull(List<Product> products) {
        productList.clear();
        productList.addAll(products);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getListProductFailed(String s) {
        Toast.makeText(getContext(), "Lỗi tải trang", Toast.LENGTH_SHORT).show();
    }

    public void change(int i) {
        adapter = new ProductAdapter(i, productList, getContext());
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), i);
        rvProduct.setLayoutManager(layoutManager);
        rvProduct.setHasFixedSize(true);
        rvProduct.setAdapter(adapter);
        adapter.setClickListener(this);
    }

    @OnClick({R.id.img_change, R.id.img_sort})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_change:
                if (change == 1) {
                    change = 2;
                    change(change);
                    imgChange.setImageResource(R.drawable.ic_two_column);
                } else {
                    change = 1;
                    change(change);
                    imgChange.setImageResource(R.drawable.ic_one_column);
                }
                break;
            case R.id.img_sort:
                String chose = spinnerFilter.getSelectedItem().toString();
                if (chose.equals("Giá từ thấp đến cao")) {
                    mPresenter.getListSortUpSport();
                } else {
                    mPresenter.getListSortDownSport();
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