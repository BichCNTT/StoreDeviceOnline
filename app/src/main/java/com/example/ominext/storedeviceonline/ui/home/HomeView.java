package com.example.ominext.storedeviceonline.ui.home;

import com.example.ominext.storedeviceonline.model.Product;
import com.example.ominext.storedeviceonline.model.ProductType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ominext on 8/28/2017.
 */

public interface HomeView {
    void getListProductTypeSuccess(List<ProductType> productTypes);

    void getListProductTypeFailed(String s);

    void getListFindFailed(String s);

    void getListFindSuccess(List<Product> listFind);
}
