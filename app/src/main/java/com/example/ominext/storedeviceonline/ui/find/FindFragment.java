package com.example.ominext.storedeviceonline.ui.find;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.example.ominext.storedeviceonline.R;
import com.example.ominext.storedeviceonline.helper.KeyboardUtil;
import com.example.ominext.storedeviceonline.listener.OnItemClickListener;
import com.example.ominext.storedeviceonline.model.Product;
import com.example.ominext.storedeviceonline.ui.detail.DetailProductFragment;
import com.example.ominext.storedeviceonline.until.VietNamese;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FindFragment extends Fragment implements FindView, OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.edt_find)
    EditText edtFind;
    @BindView(R.id.swipe_refresh_layout_find)
    SwipeRefreshLayout swipeRefreshLayoutFind;
    private FindAdapter mAdapter;
    List<Product> productList = new ArrayList<>();
    private FindPresenter mPresenter;
    @BindView(R.id.rv_list_find)
    RecyclerView rvListFind;
    Unbinder unbinder;

    public FindFragment() {
    }

    public static FindFragment newInstance() {
        FindFragment fragment = new FindFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_find, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        KeyboardUtil.hideKeyBoard(getView(), getActivity());
        init();
    }

    public void init() {
        edtFind.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(edtFind, InputMethodManager.SHOW_IMPLICIT);
        mAdapter = new FindAdapter(getContext(), productList);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        rvListFind.setLayoutManager(layoutManager);
        rvListFind.setHasFixedSize(true);
//        rvListFind.setOnScrollChangeListener(this);
//        getActivity().setProgressBarIndeterminateVisibility(true);
        rvListFind.setAdapter(mAdapter);
        mAdapter.setClickListener(this);
        mPresenter = new FindPresenter(FindFragment.this.getContext(), this);
        swipeRefreshLayoutFind.setOnRefreshListener(this);
        swipeRefreshLayoutFind.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayoutFind.setRefreshing(true);
                refreshContent();
            }
        });
    }

    public void refreshContent() {
        swipeRefreshLayoutFind.setRefreshing(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                truyền vào 1 khi mà muốn load lại page 1 -> chức năng refresh
//                mPresenter.getListFind(1);
                mPresenter.getListFind();
                swipeRefreshLayoutFind.setRefreshing(false);
            }
        }, 1000);
    }

    @Override
    public void getListFindSuccess(final List<Product> listFind) {
        this.productList.clear();
        this.productList.addAll(listFind);
        mAdapter.notifyDataSetChanged();
        edtFind.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mAdapter.filter(charSequence.toString().trim(), listFind);
                rvListFind.invalidate();
                rvListFind.smoothScrollToPosition(0);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void getListFindFailed(String s) {
        Toast.makeText(getContext(), "Lỗi tải trang", Toast.LENGTH_SHORT).show();
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
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frame_layout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onRefresh() {
        refreshContent();
    }

//    private boolean isLastItemDisplaying(RecyclerView recyclerView) {
//        if (recyclerView.getAdapter().getItemCount() != 0) {
//            int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
//            if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.getAdapter().getItemCount() - 1) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    @Override
//    public void onScrollChange(View view, int i, int i1, int i2, int i3) {
//        if (isLastItemDisplaying(rvListFind)) {
////            truyền vào 0 khi vẫn tiếp tục loadmore
////            mPresenter.getListFind(0);
//            mPresenter.getListFind();
//        }
//    }
}
