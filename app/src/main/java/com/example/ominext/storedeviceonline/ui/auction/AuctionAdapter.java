package com.example.ominext.storedeviceonline.ui.auction;

/**
 * Created by Ominext on 10/4/2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ominext.storedeviceonline.R;
import com.example.ominext.storedeviceonline.helper.PriceFormatUtil;
import com.example.ominext.storedeviceonline.model.UserAuction;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AuctionAdapter extends RecyclerView.Adapter<AuctionAdapter.RecyclerViewHolder> {
    Context mContext;
    LayoutInflater mInflater;
    List<UserAuction> mUserAuctionList = new ArrayList<>();

    public AuctionAdapter(Context context, List<UserAuction> userAuctionList) {
        this.mContext = context;
        this.mUserAuctionList = userAuctionList;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_auction, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        UserAuction userAuction = mUserAuctionList.get(position);
        holder.tvNumber.setText(userAuction.getId() + "");
        holder.tvAccountName.setText(userAuction.getNameAccount());
        PriceFormatUtil.priceFormat(holder.tvPricePay, userAuction.getPrice());
        holder.tvTime.setText(userAuction.getDateUpload());
    }

    @Override
    public int getItemCount() {
        return mUserAuctionList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_number)
        TextView tvNumber;
        @BindView(R.id.tv_account_name)
        TextView tvAccountName;
        @BindView(R.id.tv_price_pay)
        TextView tvPricePay;
        @BindView(R.id.tv_time)
        TextView tvTime;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setTag(itemView);
        }
    }
}