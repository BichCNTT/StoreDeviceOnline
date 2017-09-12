package com.example.ominext.storedeviceonline.ui.order;

import com.example.ominext.storedeviceonline.model.Product;

import java.util.List;

/**
 * Created by Ominext on 9/12/2017.
 */

public interface OrderView {
    void postOrderProductSuccess(List<Product> productList);

    void postOrderProductFailed(String s);

    void postClientInfoSuccess();

    void postClientInfoFailed(String s);
}
