package com.example.ominext.storedeviceonline.ui.stationery;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ominext.storedeviceonline.R;

public class StationeryFragment extends Fragment {
    public StationeryFragment() {
        // Required empty public constructor
    }

    public static StationeryFragment newInstance() {
        StationeryFragment fragment = new StationeryFragment();
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
        return inflater.inflate(R.layout.fragment_stationery, container, false);
    }
}
