package com.example.ominext.store.ui.order;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ominext.store.R;
import com.example.ominext.store.helper.ImageViewUtil;
import com.example.ominext.store.helper.PriceFormatUtil;
import com.example.ominext.store.model.Cart;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ominext on 9/8/2017.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private List<Cart> mCartList = new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;

    public OrderAdapter(List<Cart> mCartList, Context mContext) {
        this.mCartList = mCartList;
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_order_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Cart cart = mCartList.get(position);
        holder.tvNameProduct.setText(cart.getName());
        holder.tvNumber.setText(cart.getNumber() + "");
        ImageViewUtil.loadImg(mContext, cart.getImage(), holder.imgProductOrder);
        PriceFormatUtil.priceFormat(holder.tvPriceProduct, cart.getPrice());
        PriceFormatUtil.priceFormat(holder.tvMoneyTotalProduct, cart.getNumber() * cart.getPrice());
    }

    @Override
    public int getItemCount() {
        return mCartList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_product_order)
        ImageView imgProductOrder;
        @BindView(R.id.tv_price_product)
        TextView tvPriceProduct;
        @BindView(R.id.tv_number)
        TextView tvNumber;
        @BindView(R.id.tv_money_total_product)
        TextView tvMoneyTotalProduct;
        @BindView(R.id.tv_name_product)
        TextView tvNameProduct;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
