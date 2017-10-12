package com.example.ominext.store.model;

/**
 * Created by Ominext on 9/25/2017.
 */

public class User {
    private int mId;
    private String mEmail;
    private String mPassword;
    private String mName;
    private String mAddress;
    private String mNameUser;
    private String mAvartar;

    public User() {
    }

    public User(int mId, String mEmail, String mPassword, String mName, String mAddress, String mNameUser, String mAvartar) {
        this.mId = mId;
        this.mEmail = mEmail;
        this.mPassword = mPassword;
        this.mName = mName;
        this.mAddress = mAddress;
        this.mNameUser = mNameUser;
        this.mAvartar = mAvartar;
    }

    public String getmAvartar() {
        return mAvartar;
    }

    public void setmAvartar(String mAvartar) {
        this.mAvartar = mAvartar;
    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
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

}
