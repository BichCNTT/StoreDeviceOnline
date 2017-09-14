package com.example.ominext.storedeviceonline.ui.phone;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ominext.storedeviceonline.R;
import com.example.ominext.storedeviceonline.listener.OnLoadMoreListener;
import com.example.ominext.storedeviceonline.model.Product;
import com.example.ominext.storedeviceonline.helper.ImageViewUtil;
import com.example.ominext.storedeviceonline.helper.PriceFormatUtil;
import com.example.ominext.storedeviceonline.listener.OnItemClickListener;
import com.example.ominext.storedeviceonline.ui.cart.CartAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Ominext on 8/22/2017.
 */

public class PhoneAdapter extends RecyclerView.Adapter {

    private List<Product> productList = new ArrayList<>();
    private Context context;
    private LayoutInflater inflater;
    private OnItemClickListener clickListener;

    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private int visitableThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;

    public PhoneAdapter(List<Product> productList, Context context, RecyclerView recyclerView) {
        this.productList = productList;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            recyclerView
                    .addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);
                            totalItemCount = linearLayoutManager.getItemCount();
                            lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                            if (!loading && totalItemCount <= (lastVisibleItem + visitableThreshold)) {
                                if (onLoadMoreListener != null) {
                                    onLoadMoreListener.onLoadMore();
                                }
                                loading = true;
                            }
                        }
                    });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return productList.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == VIEW_ITEM) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.row_phone, parent, false);
            viewHolder = new RecyclerViewHolder(view);
        } else {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.progressbar, parent, false);
            viewHolder = new LoadingViewHolder(view);
        }
        return (RecyclerViewHolder) viewHolder;
    }

    public void setClickListener(OnItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RecyclerViewHolder) {
            Product product = productList.get(position);
            ImageViewUtil.loadImg(context, product.getImageProduct(), ((RecyclerViewHolder) holder).imgPhone);
            ((RecyclerViewHolder) holder).tvName.setText(product.getNameProduct());
            PriceFormatUtil.priceFormat(((RecyclerViewHolder) holder).tvPrice, product.getPriceProduct());
            ((RecyclerViewHolder) holder).tvDescribe.setText(productList.get(position).getDescribeProduct());
        } else {
            ((LoadingViewHolder) holder).progressBar.setIndeterminate(true);
        }
    }

    public void setLoading() {
        loading = false;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
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

    public static class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressbar);
        }
    }
}
