package com.example.ominext.storedeviceonline.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ominext.storedeviceonline.R;
import com.example.ominext.storedeviceonline.model.ProductType;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by Ominext on 8/17/2017.
 */

public class ProductTypeAdapter extends BaseAdapter {
    ArrayList<ProductType> productTypeArrayList;
    Context context;

    public ProductTypeAdapter(ArrayList<ProductType> productTypeArrayList, Context context) {
        this.productTypeArrayList = productTypeArrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return productTypeArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return productTypeArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder {
        ImageView imgProductType;
        TextView tvProductType;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row_listview_producttype, null);
            holder.tvProductType=(TextView)view.findViewById(R.id.tv_product_type);
            holder.imgProductType=(ImageView)view.findViewById(R.id.img_product_type);
            view.setTag(holder);
        } else {
            holder=(ViewHolder)view.getTag();

        }
        ProductType productType=(ProductType)getItem(i);
        holder.tvProductType.setText(productType.getNameProductType());
        Picasso.with(context).load(productType.getImageProductType())
                .placeholder(R.drawable.ic_camera).error(R.drawable.ic_cancel)
                .into(holder.imgProductType);
        return view;
    }


}
