package com.example.ominext.storedeviceonline.ui.find;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ominext.storedeviceonline.R;
import com.example.ominext.storedeviceonline.listener.OnItemClickListener;
import com.example.ominext.storedeviceonline.model.Product;
import com.example.ominext.storedeviceonline.ui.detail.DetailProductFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FindFragment extends Fragment implements FindView, OnItemClickListener {
    @BindView(R.id.edt_find)
    EditText edtFind;
    private FindAdapter mAdapter;
    List<Product> productList = new ArrayList<>();
    private FindPresenter mPresenter;
    @BindView(R.id.rv_list_find)
    RecyclerView rvListFind;
    Unbinder unbinder;

    public FindFragment() {
        // Required empty public constructor
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
        getActivity().setTitle("Tìm kiếm");
        init();
    }

    //khi thay đổi trong text thì thay đổi trong list
    public void init() {
        edtFind.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(edtFind, InputMethodManager.SHOW_IMPLICIT);
        mAdapter = new FindAdapter(getContext(), productList);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        rvListFind.setLayoutManager(layoutManager);
        rvListFind.setHasFixedSize(true);
        rvListFind.setAdapter(mAdapter);
        mAdapter.setClickListener(this);
        mPresenter = new FindPresenter(FindFragment.this.getContext(), this);
        mPresenter.getListFind();
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
                String stringSequence = charSequence.toString();
                if (stringSequence.isEmpty()) {
                    productList.clear();
                    productList.addAll(listFind);
                    mAdapter.notifyDataSetChanged();
                } else {
                    for (Product product : listFind) {
                        if (!product.getNameProduct().toLowerCase().contains(stringSequence)) {
                            productList.remove(product);
                        }
                    }
                    mAdapter.notifyDataSetChanged();
                }
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
}
