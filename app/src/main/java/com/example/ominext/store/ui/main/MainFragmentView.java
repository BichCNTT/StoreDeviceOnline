package com.example.ominext.store.ui.main;


import com.example.ominext.store.model.Product;

import java.util.ArrayList;

/**
 * Created by Ominext on 8/28/2017.
 */

public interface MainFragmentView {
    void getListProductSuccess(ArrayList<Product> products);

    void getListProductFailed(String s);
}
