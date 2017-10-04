package com.example.ominext.storedeviceonline.model;

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
    @SerializedName("auction")
    private int auction;
    @SerializedName("dateStart")
    private String dateStart;
    @SerializedName("dateStop")
    private String dateStop;

    public Product(int idProduct, String nameProduct, int priceProduct, String imageProduct, String describeProduct, int idProductType, int auction, String dateStart, String dateStop) {
        this.idProduct = idProduct;
        this.nameProduct = nameProduct;
        this.priceProduct = priceProduct;
        this.imageProduct = imageProduct;
        this.describeProduct = describeProduct;
        this.idProductType = idProductType;
        this.auction = auction;
        this.dateStart = dateStart;
        this.dateStop = dateStop;
    }

    public Product(int idProduct, String nameProduct, int priceProduct, String imageProduct, String describeProduct, int idProductType) {
        this.idProduct = idProduct;
        this.nameProduct = nameProduct;
        this.priceProduct = priceProduct;
        this.imageProduct = imageProduct;
        this.describeProduct = describeProduct;
        this.idProductType = idProductType;
    }

    public int getAuction() {
        return auction;
    }

    public void setAuction(int auction) {
        this.auction = auction;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateStop() {
        return dateStop;
    }

    public void setDateStop(String dateStop) {
        this.dateStop = dateStop;
    }

    public int getIdProduct() {
        return idProduct;
    }


    public String getNameProduct() {
        return nameProduct;
    }


    public int getPriceProduct() {
        return priceProduct;
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

    public int getIdProductType() {
        return idProductType;
    }

    public void setIdProductType(int idProductType) {
        this.idProductType = idProductType;
    }
}

