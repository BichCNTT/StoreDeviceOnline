package com.example.ominext.storedeviceonline.data.model;

/**
 * Created by Ominext on 8/18/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {


    @SerializedName("IdProduct")
    private int idProduct;
    @SerializedName("nameProduct")
    private String nameProduct;
    @SerializedName("priceProduct")
    private int priceProduct;
    @SerializedName("imageProduct")
    private String imageProduct;
    @SerializedName("describeProduct")
    private String describeProduct;
    @SerializedName("IdProductType")
    private int idProductType;

    public Product(int idProduct, String nameProduct, int priceProduct, String imageProduct, String describeProduct, int idProductType) {
        this.idProduct = idProduct;
        this.nameProduct = nameProduct;
        this.priceProduct = priceProduct;
        this.imageProduct = imageProduct;
        this.describeProduct = describeProduct;
        this.idProductType = idProductType;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public int getPriceProduct() {
        return priceProduct;
    }

    public void setPriceProduct(int priceProduct) {
        this.priceProduct = priceProduct;
    }

    public String getImageProduct() {
        return imageProduct;
    }

    public void setImageProduct(String imageProduct) {
        this.imageProduct = imageProduct;
    }

    public String getDescribeProduct() {
        return describeProduct;
    }

    public void setDescribeProduct(String describeProduct) {
        this.describeProduct = describeProduct;
    }

    public int getIdProductType() {
        return idProductType;
    }

    public void setIdProductType(int idProductType) {
        this.idProductType = idProductType;
    }
}
