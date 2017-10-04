package com.example.ominext.storedeviceonline.ui.auction;

/**
 * Created by Ominext on 10/4/2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ominext.storedeviceonline.model.UserAuction;

import java.util.ArrayList;
import java.util.List;

public class AuctionAdapter extends RecyclerView.Adapter<AuctionAdapter.RecyclerViewHolder> {
    LayoutInflater inflater;
    List<UserAuction> userAuctionList = new ArrayList<>();

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        public RecyclerViewHolder(View itemView) {
            super(itemView);
        }
    }
}