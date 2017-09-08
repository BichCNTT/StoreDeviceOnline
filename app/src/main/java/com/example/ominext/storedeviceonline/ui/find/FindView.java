package com.example.ominext.storedeviceonline.ui.find;

import com.example.ominext.storedeviceonline.model.Product;

import java.util.List;

/**
 * Created by Ominext on 9/5/2017.
 */

public interface FindView {
    void getListFindSuccess(List<Product> listFind);

    void getListFindFailed(String s);
}
