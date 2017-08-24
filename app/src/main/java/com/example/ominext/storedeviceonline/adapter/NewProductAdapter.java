package com.example.ominext.storedeviceonline.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ominext.storedeviceonline.R;
import com.example.ominext.storedeviceonline.helper.ImageViewUtil;
import com.example.ominext.storedeviceonline.data.model.Product;
import com.example.ominext.storedeviceonline.helper.PriceFormatUtil;
import com.example.ominext.storedeviceonline.listener.OnItemClickListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ominext on 8/18/2017.
 */

public class NewProductAdapter extends RecyclerView.Adapter<NewProductAdapter.RecyclerViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private OnItemClickListener clickListener;
    private List<Product> productList = new ArrayList<>();

    public NewProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.productList = productList;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_new_product, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.tvNameNewProduct.setText(product.getNameProduct());
        PriceFormatUtil.priceFormat(holder.tvPriceNewProduct, product.getPriceProduct());
        ImageViewUtil.loadImg(context, product.getImageProduct(), holder.imgNewProduct);
    }

    public void setClickListener(OnItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgNewProduct;
        TextView tvNameNewProduct;
        TextView tvPriceNewProduct;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            imgNewProduct = (ImageView) itemView.findViewById(R.id.img_new_product);
            tvNameNewProduct = (TextView) itemView.findViewById(R.id.tv_name_new_product);
            tvPriceNewProduct = (TextView) itemView.findViewById(R.id.tv_price_new_product);
            itemView.setTag(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onItemClick(view, getAdapterPosition());
        }
    }
}
