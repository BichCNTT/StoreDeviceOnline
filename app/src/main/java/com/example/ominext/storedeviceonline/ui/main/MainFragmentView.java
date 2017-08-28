package com.example.ominext.storedeviceonline.ui.main;


import com.example.ominext.storedeviceonline.model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ominext on 8/28/2017.
 */

public interface MainFragmentView {
    void getListProductSuccess(ArrayList<Product> products);

    void getListProductFailed(String s);
}
