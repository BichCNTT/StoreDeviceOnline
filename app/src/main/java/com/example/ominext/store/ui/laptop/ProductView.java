package com.example.ominext.store.ui.laptop;

import com.example.ominext.store.model.Product;

import java.util.List;

/**
 * Created by Ominext on 8/28/2017.
 */

public interface ProductView {
    void getListProductSuccessFull(List<Product> products);

    void getListProductFailed(String s);
}
