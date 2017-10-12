package com.example.ominext.store.ui.laptop;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ominext.store.R;
import com.example.ominext.store.model.Product;
import com.example.ominext.store.helper.ImageViewUtil;
import com.example.ominext.store.helper.PriceFormatUtil;
import com.example.ominext.store.listener.OnItemClickListener;
import com.example.ominext.store.until.Server;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ominext on 8/22/2017.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.RecyclerViewHolder> {
    List<Product> productList = new ArrayList<>();
    private OnItemClickListener clickListener;
    Context context;
    LayoutInflater inflater;
    private int change = 1;

    public ProductAdapter(int change, List<Product> productList, Context context) {
        this.change = change;
        this.productList = productList;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public ProductAdapter(List<Product> productList, Context context) {
        this.productList = productList;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_product, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        Product product = productList.get(position);
        if(!product.getImageProduct().contains("http")){
            product.setImageProduct("http://" + Server.localhost+"/server/"+product.getImageProduct());
        }
        if (change == 1) {
            ImageViewUtil.loadImg(context, product.getImageProduct(), holder.imgLaptop);
            holder.tvName.setText(product.getNameProduct());
            PriceFormatUtil.priceFormat(holder.tvPrice, product.getPriceProduct());
            holder.tvDescribe.setText(productList.get(position).getDescribeProduct());
        } else {
            ImageViewUtil.loadImg(context, product.getImageProduct(), holder.imgLaptop);
            holder.tvName.setText(product.getNameProduct());
            PriceFormatUtil.priceFormat(holder.tvPrice, product.getPriceProduct());
            holder.tvDescribe.setVisibility(View.GONE);
        }
        Log.e("====================>",product.getImageProduct());
    }

    public void setClickListener(OnItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.img_laptop)
        ImageView imgLaptop;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_describe)
        TextView tvDescribe;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setTag(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onItemClick(view, getAdapterPosition());
        }
    }
}
