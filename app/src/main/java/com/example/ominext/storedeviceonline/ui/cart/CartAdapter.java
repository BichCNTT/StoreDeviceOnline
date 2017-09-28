package com.example.ominext.storedeviceonline.ui.cart;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
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
import com.example.ominext.storedeviceonline.model.Cache;
import com.example.ominext.storedeviceonline.model.Cart;
import com.example.ominext.storedeviceonline.ui.home.HomeActivity;
import com.mikepenz.actionitembadge.library.ActionItemBadge;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

// tính tổng tiền của các mặt hàng đã thêm vào giỏ hàng
public class CartAdapter extends RecyclerView.Adapter<CartAdapter.RecyclerViewHolder> {
    List<Cart> cartList = new ArrayList<>();
    Context context;
    LayoutInflater inflater;
    int position;
    private OnItemClickListener clickListener;
    private static CartAdapter adapter;
    String path = null;
    String fileNameInPut = "cart.txt";
    String fileNameOutPut = "cart1.txt";

    public CartAdapter() {
    }

    public CartAdapter(List<Cart> cartList, Context context) {
        this.cartList = cartList;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        adapter = this;
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
        this.position = position;
        holder.tvName.setText(cart.getName());
        PriceFormatUtil.priceFormat(holder.tvPrice, cart.getPrice());
        ImageViewUtil.loadImg(context, cart.getImage(), holder.imgProduct);
        holder.tvNumber.setText(cart.getNumber() + "");
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public void setClickListener(OnItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public void initFile() {
        File file1 = new File(Environment.getExternalStorageDirectory(), context.getPackageName());
        if (!file1.exists())
            file1.mkdir();
        path = file1.getAbsolutePath() + "/";
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_product)
        ImageView imgProduct;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.btn_addition)
        Button btnAddition;
        @BindView(R.id.tv_number)
        TextView tvNumber;
        @BindView(R.id.btn_subtraction)
        Button btnSubtraction;
        @BindView(R.id.img_delete)
        ImageView imgDelete;

        public RecyclerViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setTag(itemView);
            initFile();
            btnSubtraction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Cart cart = cartList.get(getAdapterPosition());
                    int number = cart.getNumber();
                    int price = cart.getPrice();
                    if (number > 1) {
//                  xóa sản phẩm
                        try {
                            String productDelete = Cache.writeJsonStream(cart);
                            Cache.delete(path, fileNameInPut, fileNameOutPut, productDelete);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        number--;
                        tvNumber.setText(number + "");
                        cart.setNumber(number);
                        int money = price * number;
                        cart.setMoney(money);
//                        thêm lại sản phẩm
                        try {
                            String productSave = Cache.writeJsonStream(cart);
                            Cache.saveToFile(path, fileNameInPut, productSave);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (clickListener != null)
                            clickListener.onItemClick(view, getAdapterPosition());
                    }
                }
            });
            btnAddition.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Cart cart = cartList.get(getAdapterPosition());
                    int number = cart.getNumber();
                    int price = cart.getPrice();
                    if (number < 10) {
                        try {
                            String productDelete = Cache.writeJsonStream(cart);
                            Cache.delete(path, fileNameInPut, fileNameOutPut, productDelete);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        number++;
                        tvNumber.setText(number + "");
                        cart.setNumber(number);
                        int money = price * number;
                        cart.setMoney(money);
                        try {
                            String productSave = Cache.writeJsonStream(cart);
                            Cache.saveToFile(path, fileNameInPut, productSave);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (clickListener != null)
                            clickListener.onItemClick(view, getAdapterPosition());
                    }
                }
            });
            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(context);
                    }
                    builder.setTitle("Cảnh báo")
                            .setMessage("Bạn có chắc chắn muốn xóa mặt hàng này")
                            .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    try {
                                        String productDelete = Cache.writeJsonStream(cartList.get(getAdapterPosition()));
                                        Cache.delete(path, fileNameInPut, fileNameOutPut, productDelete);
                                        cartList.remove(getAdapterPosition());
                                        adapter.notifyDataSetChanged();
                                        if (clickListener != null)
                                            clickListener.onItemClick(view, getAdapterPosition());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            })
                            .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            });
        }
    }
}