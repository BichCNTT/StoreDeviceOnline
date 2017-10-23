package com.example.ominext.store.model;

import io.realm.RealmObject;

/**
 * Created by Ominext on 9/25/2017.
 */

public class User {
    private String mId;
    private String mEmail;
    private String mPassword;
    private String mName;
    private String mAddress;
    private String mNameUser;
    private String mAvartar;
    private String mToken;

    public User(String mId, String mEmail, String mPassword, String mName, String mAddress, String mNameUser, String mAvartar, String mToken) {
        this.mId = mId;
        this.mEmail = mEmail;
        this.mPassword = mPassword;
        this.mName = mName;
        this.mAddress = mAddress;
        this.mNameUser = mNameUser;
        this.mAvartar = mAvartar;
        this.mToken = mToken;
    }

    public String getmAvartar() {
        return mAvartar;
    }

    public void setmAvartar(String mAvartar) {
        this.mAvartar = mAvartar;
    }

    public String getId() {
        return mId;
    }

    public void setId(String mId) {
        this.mId = mId;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public String getNameUser() {
        return mNameUser;
    }

    public void setNameUser(String mNameUser) {
        this.mNameUser = mNameUser;
    }

    public String getToken() {
        return mToken;
    }

    public void setToken(String mToken) {
        this.mToken = mToken;
    }

}
