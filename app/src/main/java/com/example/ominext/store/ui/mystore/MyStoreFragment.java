package com.example.ominext.store.ui.mystore;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.ominext.store.R;
import com.example.ominext.store.ui.home.HomeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MyStoreFragment extends Fragment {
    @BindView(R.id.img_auction)
    ImageView imgAuction;
    @BindView(R.id.img_buy)
    ImageView imgBuy;
    @BindView(R.id.img_sell)
    ImageView imgSell;
    Unbinder unbinder;

    public MyStoreFragment() {
    }

    public static MyStoreFragment newInstance() {
        MyStoreFragment fragment = new MyStoreFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_store, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.img_auction, R.id.img_buy, R.id.img_sell})
    public void onViewClicked(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.img_auction:
                bundle.putString("key", "1");
                break;
            case R.id.img_buy:
                bundle.putString("key", "2");
                break;
            case R.id.img_sell:
                bundle.putString("key", "3");
                break;
        }
        Fragment fragment = new DetailMyStoreFragment();
        fragment.setArguments(bundle);
        ((HomeActivity) getActivity()).replaceFragment(fragment);
    }
}
