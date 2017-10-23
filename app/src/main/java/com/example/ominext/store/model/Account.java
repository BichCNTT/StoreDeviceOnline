package com.example.ominext.store.model;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;

public class Account extends RealmObject {
    private String email;
    private String password;

    public Account() {

    }

    public Account(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

