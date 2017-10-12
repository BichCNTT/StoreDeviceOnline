package com.example.ominext.store.ui.home;

import com.example.ominext.store.model.ProductType;

import java.util.List;

/**
 * Created by Ominext on 8/28/2017.
 */

public interface HomeView {
    void getListProductTypeSuccess(List<ProductType> productTypes);

    void getListProductTypeFailed(String s);
}
