package com.example.ominext.storedeviceonline.ui.find;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ominext.storedeviceonline.R;
import com.example.ominext.storedeviceonline.helper.ImageViewUtil;
import com.example.ominext.storedeviceonline.listener.OnItemClickListener;
import com.example.ominext.storedeviceonline.model.Product;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ominext on 9/5/2017.
 */

public class FindAdapter extends RecyclerView.Adapter<FindAdapter.RecyclerViewHolder> {
    List<Product> productList;
    private List<Product> mFilteredList;
    private Context mContext;
    private LayoutInflater mInflater;
    private OnItemClickListener clickListener;

    public void setClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public FindAdapter(Context context, List<Product> productList) {
        this.mContext = context;
        this.productList = productList;
        this.mFilteredList = productList;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_find, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.tvNameFind.setText(mFilteredList.get(position).getNameProduct());
        ImageViewUtil.loadImg(mContext, mFilteredList.get(position).getImageProduct(), holder.imgFind);
    }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.img_find)
        ImageView imgFind;
        @BindView(R.id.tv_name_find)
        TextView tvNameFind;

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
