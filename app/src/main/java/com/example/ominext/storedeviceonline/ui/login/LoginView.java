package com.example.ominext.storedeviceonline.ui.login;

import com.example.ominext.storedeviceonline.model.Product;
import com.example.ominext.storedeviceonline.model.User;

import java.util.List;

/**
 * Created by Ominext on 9/25/2017.
 */

public interface LoginView {
    void getListUserSuccessfully(List<User> users);

    void getListUserFailed(String s);
}
