package com.example.ominext.storedeviceonline.model;

import com.android.volley.toolbox.StringRequest;

/**
 * Created by Ominext on 10/4/2017.
 */
//lấy dữ liệu từ bảng đấu giá về
public class UserAuction {
    private int mId;
    private int mIdUser;
    private int mPrice;
    private int mNumber;
    private String mNameAccount;
    private String mDateUpload;

    public UserAuction(int id, int mIdUser, int mPrice, int mNumber, String mNameAccount, String mDateUpload) {
        this.mId = id;
        this.mIdUser = mIdUser;
        this.mPrice = mPrice;
        this.mNumber = mNumber;
        this.mNameAccount = mNameAccount;
        this.mDateUpload = mDateUpload;
    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
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

}
