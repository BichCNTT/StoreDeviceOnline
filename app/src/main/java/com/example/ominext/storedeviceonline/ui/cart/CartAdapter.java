package com.example.ominext.storedeviceonline.ui.cart;

import android.content.Context;
import android.content.DialogInterface;
import android.preference.DialogPreference;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ominext on 8/29/2017.
 */
// tính tổng tiền của các mặt hàng đã thêm vào giỏ hàng
public class CartAdapter extends RecyclerView.Adapter<CartAdapter.RecyclerViewHolder> {
    List<Cart> cartList = new ArrayList<>();
    Context context;
    LayoutInflater inflater;
    int position;
    private OnItemClickListener clickListener;

    public CartAdapter(List<Cart> cartList, Context context) {
        this.cartList = cartList;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    //tăng số lượng sản phẩm lên. vd sản phẩm 1 từ 1->2
    // thì kích vào sản phẩm bất kì khác nó k reset lại giá trị bằng giá trị của dòng dữ liệu đang tác đông
// mà nó tính luôn bằng giá trị 2 tức là giá trị của dòng dl trước đó đã tác động??
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_cart, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        Cart cart = cartList.get(position);
        int number = cart.getNumber();
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

        public RecyclerViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setTag(itemView);
            btnSubtraction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Cart cart = cartList.get(getAdapterPosition());
                    int number = cart.getNumber();
                    int price = cart.getPrice();
                    if (number > 1) {
                        number--;
                        tvNumber.setText(number + "");
                        cart.setNumber(number);
                        int money = price * number;
                        cart.setMoney(money);
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
                        number++;
                        tvNumber.setText(number + "");
                        cart.setNumber(number);
                        int money = price * number;
                        cart.setMoney(money);
                    }
                }
            });
        }
    }
}