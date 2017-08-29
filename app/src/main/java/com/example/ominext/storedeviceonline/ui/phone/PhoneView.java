package com.example.ominext.storedeviceonline.ui.phone;

import com.example.ominext.storedeviceonline.model.Product;

import java.util.List;

/**
 * Created by Ominext on 8/28/2017.
 */

public interface PhoneView {
    void getListPhoneSuccess(List<Product> products);

    void getListPhoneFailed(String s);
}
