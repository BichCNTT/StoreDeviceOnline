package com.example.ominext.storedeviceonline.ui.auction;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.ominext.storedeviceonline.R;
import com.example.ominext.storedeviceonline.model.UserAuction;
import com.example.ominext.storedeviceonline.ui.auctiondialog.AuctionDialogFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class AuctionFragment extends Fragment implements AuctionView {

    @BindView(R.id.rv_list_user_auction)
    RecyclerView rvListUserAuction;
    @BindView(R.id.btn_auction)
    Button btnAuction;
    Unbinder unbinder;
    AuctionPresenter mAuctionPresenter;
    public static AuctionAdapter mAuctionAdapter;
    List<UserAuction> mUserAuctionList = new ArrayList<>();
    int id = 0;
    String name = "";
    int price = 0;
    String image = "";
    String dateStart = "";
    String dateStop = "";

    public AuctionFragment() {

    }

    public static AuctionFragment newInstance() {
        AuctionFragment fragment = new AuctionFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_auction, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    public void init() {
        Bundle bundle = getArguments();
        id = bundle.getInt("id");
        name = bundle.getString("name");
        price = bundle.getInt("price");
        image = bundle.getString("image");
        dateStart = bundle.getString("dateStart");
        dateStop = bundle.getString("dateStop");
        mAuctionAdapter = new AuctionAdapter(id, getContext(), mUserAuctionList);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        rvListUserAuction.setLayoutManager(layoutManager);
        rvListUserAuction.setHasFixedSize(true);
        rvListUserAuction.setAdapter(mAuctionAdapter);
        mAuctionPresenter = new AuctionPresenter(getContext(), this);
        mAuctionPresenter.getUserAuction();
    }

    @Override
    public void getUserAuctionSuccessfully(List<UserAuction> userAuctionList) {
        mUserAuctionList.clear();
        mUserAuctionList.addAll(userAuctionList);
        mAuctionAdapter.notifyDataSetChanged();
    }

    @Override
    public void getUserAuctionFailed(String s) {
        Toast.makeText(getContext(), "Lỗi tải trang", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_auction)
    public void onViewClicked() {
        showDialog();
    }

    public void showDialog() {
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        bundle.putString("name", name);
        bundle.putInt("price", price);
        bundle.putString("image", image);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        AuctionDialogFragment auctionDialogFragment = AuctionDialogFragment.newInstance();
        auctionDialogFragment.setArguments(bundle);
        auctionDialogFragment.show(fragmentManager, "frm_auction");
    }
}
