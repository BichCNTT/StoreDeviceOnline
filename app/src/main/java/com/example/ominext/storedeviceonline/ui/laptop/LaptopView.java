package com.example.ominext.storedeviceonline.ui.laptop;

import com.example.ominext.storedeviceonline.model.Product;

import java.util.List;

/**
 * Created by Ominext on 8/28/2017.
 */

public interface LaptopView {
    void getListLaptopSuccess(List<Product> products);

    void getListLaptopFailed(String s);
}
