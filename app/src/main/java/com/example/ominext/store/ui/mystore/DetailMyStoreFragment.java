package com.example.ominext.store.ui.mystore;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ominext.store.R;

public class DetailMyStoreFragment extends Fragment {
    public DetailMyStoreFragment() {
    }

    public static DetailMyStoreFragment newInstance() {
        DetailMyStoreFragment fragment = new DetailMyStoreFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_my_store, container, false);
    }
}
