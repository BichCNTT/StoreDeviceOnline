package com.example.ominext.store.ui.login;

import com.example.ominext.store.model.User;

import java.util.List;

/**
 * Created by Ominext on 9/25/2017.
 */

public interface LoginView {
    void getListUserSuccessfully(List<User> users);

    void getListUserFailed(String s);
}
