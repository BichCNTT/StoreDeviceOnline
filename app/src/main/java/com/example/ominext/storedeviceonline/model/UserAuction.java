package com.example.ominext.storedeviceonline.model;

public class UserAuction {
    private int mId;
    private int mIdProduct;
    private int mIdUser;
    private int mPrice;
    private int mNumber;
    private String mNameAccount;
    private String mDateUpload;
    private String mNameProduct;

    public UserAuction(int id, int mIdProduct, int mIdUser, int mPrice, int mNumber, String mNameAccount, String mDateUpload, String mNameProduct) {
        this.mId = id;
        this.mIdProduct = mIdProduct;
        this.mIdUser = mIdUser;
        this.mPrice = mPrice;
        this.mNumber = mNumber;
        this.mNameAccount = mNameAccount;
        this.mDateUpload = mDateUpload;
        this.mNameProduct = mNameProduct;
    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public int getIdProduct() {
        return mIdProduct;
    }

    public void setIdProduct(int mIdProduct) {
        this.mIdProduct = mIdProduct;
    }

    public int getIdUser() {
        return mIdUser;
    }

    public void setIdUser(int mIdUser) {
        this.mIdUser = mIdUser;
    }

    public int getPrice() {
        return mPrice;
    }

    public void setPrice(int mPrice) {
        this.mPrice = mPrice;
    }

    public int getNumber() {
        return mNumber;
    }

    public void setNumber(int mNumber) {
        this.mNumber = mNumber;
    }

    public String getNameAccount() {
        return mNameAccount;
    }

    public void setNameAccount(String mNameAccount) {
        this.mNameAccount = mNameAccount;
    }

    public String getDateUpload() {
        return mDateUpload;
    }

    public void setDateUpload(String mDateUpload) {
        this.mDateUpload = mDateUpload;
    }

    public String getNameProduct() {
        return mNameProduct;
    }

    public void setNameProduct(String mNameProduct) {
        this.mNameProduct = mNameProduct;
    }
}
