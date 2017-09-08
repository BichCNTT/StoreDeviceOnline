package com.example.ominext.storedeviceonline.ui.motherkid;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ominext.storedeviceonline.R;


public class MotherKidFragment extends Fragment {

    public MotherKidFragment() {
        // Required empty public constructor
    }

    public static MotherKidFragment newInstance() {
        MotherKidFragment fragment = new MotherKidFragment();
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
        return inflater.inflate(R.layout.fragment_mother_kid, container, false);
    }
}
