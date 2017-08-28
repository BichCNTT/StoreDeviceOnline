package com.example.ominext.storedeviceonline.until;

import com.example.ominext.storedeviceonline.model.Product;
import com.example.ominext.storedeviceonline.model.ProductType;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Ominext on 8/21/2017.
 */
//thực hiện các yêu cầu lên service như get post put patch và delete
public interface SOService {
    @GET("/server/getproducttype.php")
    Call<ProductType> getProductType();
    @GET("/server/getnewproduct.php")
    Call<Product> getNewProduct(@Query("tagged") String tags);
}
