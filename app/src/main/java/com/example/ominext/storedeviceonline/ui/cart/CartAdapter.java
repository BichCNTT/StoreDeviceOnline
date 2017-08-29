package com.example.ominext.storedeviceonline.ui.cart;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ominext.storedeviceonline.R;
import com.example.ominext.storedeviceonline.helper.ImageViewUtil;
import com.example.ominext.storedeviceonline.helper.PriceFormatUtil;
import com.example.ominext.storedeviceonline.listener.OnItemClickListener;
import com.example.ominext.storedeviceonline.model.Cart;
import com.example.ominext.storedeviceonline.model.Product;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ominext on 8/29/2017.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.RecyclerViewHolder> {
    List<Cart> cartList = new ArrayList<>();
    private OnItemClickListener clickListener;
    Context context;
    LayoutInflater inflater;
    int number;

    public CartAdapter(List<Cart> cartList, Context context) {
        this.cartList = cartList;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_cart, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        Cart cart = cartList.get(position);
        number=cart.getNumber();
//        tên, giá, ảnh, chưa có số lượng sản phẩm
        holder.tvName.setText(cart.getName());
        PriceFormatUtil.priceFormat(holder.tvPrice, cart.getPrice());
        ImageViewUtil.loadImg(context, cart.getImage(), holder.imgProduct);
        holder.number.setText(number+"");
    }

    public void setClickListener(OnItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    @OnClick({R.id.btn_addition, R.id.btn_subtraction})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_addition:
                number--;
                break;
            case R.id.btn_subtraction:
                number++;
                break;
        }
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.img_product)
        ImageView imgProduct;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.btn_addition)
        Button btnAddition;
        @BindView(R.id.number)
        TextView number;
        @BindView(R.id.btn_subtraction)
        Button btnSubtraction;

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