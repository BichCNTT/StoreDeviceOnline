package com.example.ominext.storedeviceonline.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ominext.storedeviceonline.R;
import com.example.ominext.storedeviceonline.data.model.Product;
import com.example.ominext.storedeviceonline.helper.ImageViewUtil;
import com.example.ominext.storedeviceonline.helper.PriceFormatUtil;
import com.example.ominext.storedeviceonline.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Ominext on 8/22/2017.
 */

public class PhoneAdapter extends RecyclerView.Adapter<PhoneAdapter.RecyclerViewHolder> {

    private List<Product> productList = new ArrayList<>();
    private Context context;
    private LayoutInflater inflater;
    private OnItemClickListener clickListener;

    public PhoneAdapter(List<Product> productList, Context context) {
        this.productList = productList;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_phone, parent, false);
        return new RecyclerViewHolder(view);
    }

    public void setClickListener(OnItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        Product product = productList.get(position);
        ImageViewUtil.loadImg(context, product.getImageProduct(), holder.imgPhone);
        holder.tvName.setText(product.getNameProduct());
        PriceFormatUtil.priceFormat(holder.tvPrice, product.getPriceProduct());
        holder.tvDescribe.setText(productList.get(position).getDescribeProduct());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.img_phone)
        ImageView imgPhone;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_describe)
        TextView tvDescribe;
        Unbinder unbinder;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
            itemView.setTag(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onItemClick(view, getAdapterPosition());
        }
    }
}
