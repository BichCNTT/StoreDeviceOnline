package com.example.ominext.storedeviceonline.helper;

import android.widget.TextView;

import java.text.DecimalFormat;

/**
 * Created by Ominext on 8/22/2017.
 */

public class PriceFormatUtil {
    public static void priceFormat(TextView textView, int price){
        DecimalFormat format=new DecimalFormat("###,###,###");
        textView.setText("Giá: "+format.format(price)+" Đ");
    }
}
