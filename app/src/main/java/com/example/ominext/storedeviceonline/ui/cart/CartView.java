package com.example.ominext.storedeviceonline.ui.cart;

import com.example.ominext.storedeviceonline.model.Product;

import java.util.List;

/**
 * Created by Ominext on 8/29/2017.
 */

public interface CartView {
    void getListCartSuccess(List<Product> products);

    void getListCartFailed(String s);
}
