package com.example.ominext.storedeviceonline.model;

/**
 * Created by Ominext on 8/29/2017.
 */

public class Cart {
    private String mName;
    private String mImage;
    private int mNumber;
    private int mPrice;

    public Cart() {
    }

    public Cart(String name, String image, int number, int price) {
        this.mName = name;
        this.mImage = image;
        this.mNumber = number;
        this.mPrice = price;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        this.mImage = image;
    }

    public int getNumber() {
        return mNumber;
    }

    public void setNumber(int number) {
        this.mNumber = number;
    }

    public int getPrice() {
        return mPrice;
    }

    public void setPrice(int price) {
        this.mPrice = price;
    }
}
