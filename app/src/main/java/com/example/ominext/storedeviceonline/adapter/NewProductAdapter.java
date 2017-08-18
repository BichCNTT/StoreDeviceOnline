package com.example.ominext.storedeviceonline.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ominext.storedeviceonline.R;
import com.example.ominext.storedeviceonline.helper.ImageViewUtil;
import com.example.ominext.storedeviceonline.model.NewProduct;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.Format;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Ominext on 8/18/2017.
 */

public class NewProductAdapter extends RecyclerView.Adapter<NewProductAdapter.RecyclerViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<NewProduct> newProductList = new ArrayList<>();

    public NewProductAdapter(Context context, List<NewProduct> newProductList) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.newProductList = newProductList;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_new_product, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        NewProduct newProduct=newProductList.get(position);
        holder.tvNameNewProduct.setText(newProduct.getNameProduct());
        DecimalFormat format=new DecimalFormat("###,###,###");
        holder.tvPriceNewProduct.setText("Giá: "+format.format(newProduct.getPriceProduct())+" Đ");
        ImageViewUtil.loadImg(context,newProduct.getImageProduct(),holder.imgNewProduct);
    }

    @Override
    public int getItemCount() {
        return newProductList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        ImageView imgNewProduct;
        TextView tvNameNewProduct;
        TextView tvPriceNewProduct;
//        CardView cardView;
        public RecyclerViewHolder(View itemView) {
            super(itemView);
            imgNewProduct=(ImageView)itemView.findViewById(R.id.img_product_type);
            tvNameNewProduct=(TextView)itemView.findViewById(R.id.tv_product_type);
            tvPriceNewProduct=(TextView)itemView.findViewById(R.id.tv_price_new_product);
//            cardView=(CardView)itemView.findViewById(R.id.card_view);
        }
    }
}
