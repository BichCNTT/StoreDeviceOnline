package com.example.ominext.storedeviceonline.ui.phone;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.ominext.storedeviceonline.R;
import com.example.ominext.storedeviceonline.listener.OnItemClickListener;
import com.example.ominext.storedeviceonline.listener.OnLoadMoreListener;
import com.example.ominext.storedeviceonline.model.Product;
import com.example.ominext.storedeviceonline.ui.detail.DetailProductFragment;
import com.example.ominext.storedeviceonline.ui.home.HomeActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class PhoneFragment extends Fragment implements OnItemClickListener, PhoneView, SwipeRefreshLayout.OnRefreshListener {
    PhoneAdapter adapter;
    List<Product> productList = new ArrayList<>();
    List<Product> products = new ArrayList<>();
    @BindView(R.id.rv_phone)
    RecyclerView rvPhone;
    Unbinder unbinder;
    @BindView(R.id.spinner_filter)
    Spinner spinnerFilter;
    @BindView(R.id.img_filter)
    ImageView imgFilter;
    @BindView(R.id.img_change)
    ImageView imgChange;
    int change = 1;
    @BindView(R.id.swipe_refresh_layout_phone)
    SwipeRefreshLayout swipeRefreshLayoutPhone;
    @BindView(R.id.empty)
    TextView tvEmpty;
    private PhonePresenter mPresenter;
    protected Handler handler;

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
        handler = new Handler();
        adapter = new PhoneAdapter(productList, getContext(), rvPhone);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), change);
        rvPhone.setLayoutManager(layoutManager);
        rvPhone.setHasFixedSize(true);
        rvPhone.setAdapter(adapter);
        adapter.setClickListener(this);
        mPresenter = new PhonePresenter(PhoneFragment.this.getContext(), this);
        final ArrayAdapter<CharSequence> adapterFilter = ArrayAdapter.createFromResource(getContext(),
                R.array.fitter_array, android.R.layout.simple_spinner_item);
        adapterFilter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFilter.setAdapter(adapterFilter);

        swipeRefreshLayoutPhone.setOnRefreshListener(this);
        swipeRefreshLayoutPhone.post(new Runnable() {
                                         @Override
                                         public void run() {
                                             swipeRefreshLayoutPhone.setRefreshing(true);
                                             refreshContent();
                                         }
                                     }
        );
        products.addAll(productList);
        adapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                productList.clear();
                adapter.notifyItemInserted(productList.size() - 1);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        productList.remove(productList.size() - 1);
                        adapter.notifyItemRemoved(productList.size());
                        int start = productList.size();
                        int end = start + 5;
                        for (int i = start + 1; i <= end; i++) {
                            productList.add(products.get(i));
                            adapter.notifyItemInserted(productList.size());
                        }
                        adapter.setLoading();
                    }
                }, 2000);
            }
        });
    }

    private void refreshContent() {
        swipeRefreshLayoutPhone.setRefreshing(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mPresenter.getListPhone();
                swipeRefreshLayoutPhone.setRefreshing(false);
            }
        }, 1000);
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

    public void change(int i) {
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), i);
        rvPhone.setLayoutManager(layoutManager);
        rvPhone.setHasFixedSize(true);
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
