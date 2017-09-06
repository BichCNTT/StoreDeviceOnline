package com.example.ominext.storedeviceonline.ui.home;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ominext.storedeviceonline.R;
import com.example.ominext.storedeviceonline.helper.ImageViewUtil;
import com.example.ominext.storedeviceonline.listener.OnItemClickListener;
import com.example.ominext.storedeviceonline.model.Product;
import com.example.ominext.storedeviceonline.ui.detail.DetailProductFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Ominext on 8/17/2017.
 */

public class FindAdapter extends BaseAdapter implements Filterable {
    List<Product> productArrayList;
    List<Product> productArrayListData;
    Context context;
    private OnItemClickListener clickListener;
    public int position = 0;


    public FindAdapter(List<Product> productArrayList, Context context) {
        this.productArrayList = productArrayList;
        this.context = context;
        productArrayListData = productArrayList;
    }

    public void setClickListener(OnItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    @Override
    public int getCount() {
        return productArrayListData.size();
    }

    @Override
    public Object getItem(int i) {
        return productArrayListData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public class ViewHolder {
        ImageView imgFind;
        TextView tvNameFind;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        position = i;
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row_find, null);
            holder.tvNameFind = (TextView) view.findViewById(R.id.tv_name_find);
            holder.imgFind = (ImageView) view.findViewById(R.id.img_find);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        Product product = (Product) getItem(i);
        holder.tvNameFind.setText(product.getNameProduct());
        ImageViewUtil.loadImg(context, product.getImageProduct(), holder.imgFind);
        return view;
    }

    @Override
    public Filter getFilter() {
//        productArrayListFilter = new ArrayList<>();
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults results = new FilterResults();
                ArrayList<Product> FilteredArrayNames = new ArrayList<Product>();
                // perform your search here using the searchConstraint String.
                if (TextUtils.isEmpty(charSequence)) {
                    FilteredArrayNames.clear();
                    FilteredArrayNames.addAll(productArrayList);
                    results.count = FilteredArrayNames.size();
                    results.values = FilteredArrayNames;
                } else {
                    charSequence = charSequence.toString().toLowerCase();
                    for (int i = 0; i < productArrayList.size(); i++) {
                        String dataNames = productArrayList.get(i).getNameProduct();
                        if (dataNames.toLowerCase().startsWith(charSequence.toString())) {
                            FilteredArrayNames.add(productArrayList.get(i));
                        }
                    }
                    results.count = FilteredArrayNames.size();
                    results.values = FilteredArrayNames;
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                productArrayListData = (List<Product>) filterResults.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }
}


//    private List<Product> data;
//    private String[] suggestions;
//    private Drawable suggestionIcon;
//    private LayoutInflater inflater;
//    private boolean ellipsize;
//
//    public FindAdapter(Context context, String[] suggestions) {
//        inflater = LayoutInflater.from(context);
//        data = new ArrayList<>();
//        this.suggestions = suggestions;
//    }
//
//    public FindAdapter(Context context, String[] suggestions, Drawable suggestionIcon, boolean ellipsize) {
//        inflater = LayoutInflater.from(context);
//        data = new ArrayList<>();
//        this.suggestions = suggestions;
//        this.suggestionIcon = suggestionIcon;
//        this.ellipsize = ellipsize;
//    }
//
//    @Override
//    public int getCount() {
//        return data.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return data.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//
//        ViewHolder viewHolder;
//
//        if (convertView == null) {
//            convertView = inflater.inflate(R.layout.row_find, parent, false);
//            viewHolder = new ViewHolder(convertView);
//            convertView.setTag(viewHolder);
//        } else {
//            viewHolder = (ViewHolder) convertView.getTag();
//        }
//
//        String currentListData = (String) getItem(position);
//
//        viewHolder.textView.setText(currentListData);
//        if (ellipsize) {
//            viewHolder.textView.setSingleLine();
//            viewHolder.textView.setEllipsize(TextUtils.TruncateAt.END);
//        }
//
//        return convertView;
//    }
//
//    private class ViewHolder {
//
//        TextView textView;
//        ImageView imageView;
//
//        public ViewHolder(View convertView) {
//            textView = (TextView) convertView.findViewById(R.id.img_find);
//            if (suggestionIcon != null) {
//                imageView = (ImageView) convertView.findViewById(R.id.tv_name_find);
//                imageView.setImageDrawable(suggestionIcon);
//            }
//        }
//    }
//}