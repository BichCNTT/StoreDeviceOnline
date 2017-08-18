package com.example.ominext.storedeviceonline.model;

/**
 * Created by Ominext on 8/17/2017.
 */

public class ProductType {
    private int mId;
    private String mNameProductType;
    private String mImageProductType;

    public ProductType(int mId, String mNameProductType, String mImageProductType) {
        this.mId = mId;
        this.mNameProductType = mNameProductType;
        this.mImageProductType = mImageProductType;
    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public String getNameProductType() {
        return mNameProductType;
    }

    public void setNameProductType(String mNameProductType) {
        this.mNameProductType = mNameProductType;
    }

    public String getImageProductType() {
        return mImageProductType;
    }

    public void setImageProductType(String mImageProductType) {
        this.mImageProductType = mImageProductType;
    }
}
