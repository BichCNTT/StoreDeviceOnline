package com.example.ominext.storedeviceonline.ui.orderconfirm;

import com.example.ominext.storedeviceonline.model.Product;

import java.util.List;

/**
 * Created by Ominext on 9/12/2017.
 */

public interface OrderConfirmView {
    void getIdOrderProductSuccessfully(List<Integer> listId);
    void getIdOrderProductFailed(String s);

}
