package com.example.ominext.storedeviceonline.ui.cart;

import android.content.Context;
import android.os.Bundle;

import com.example.ominext.storedeviceonline.helper.ImageViewUtil;
import com.example.ominext.storedeviceonline.helper.PriceFormatUtil;

/**
 * Created by Ominext on 8/29/2017.
 */

public class CartPresenter {
    private Context mContext;
    private CartView mCartView;

    public CartPresenter(Context context, CartView cartView) {
        this.mContext = context;
        this.mCartView = cartView;
    }
}
