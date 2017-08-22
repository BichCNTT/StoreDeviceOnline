package com.example.ominext.storedeviceonline.data.remote;

import com.example.ominext.storedeviceonline.data.model.Product;
import com.example.ominext.storedeviceonline.data.model.ProductType;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Ominext on 8/21/2017.
 */
//thực hiện các yêu cầu lên service như get post put patch và delete
public interface SOService {
    @GET("/server/getProductType.php")
    Call<List<ProductType>> getProductType();
    @GET("/server/getnewproduct.php")
    Call<List<Product>> getNewProduct(@Query("tagged") String tags);
}
