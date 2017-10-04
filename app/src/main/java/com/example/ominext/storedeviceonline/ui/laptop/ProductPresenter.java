package com.example.ominext.storedeviceonline.ui.laptop;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.ominext.storedeviceonline.model.Product;
import com.example.ominext.storedeviceonline.until.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ominext on 8/28/2017.
 */

public class ProductPresenter {
    private Context mContext;
    private ProductView mLaptopView;

    int idProductType = 0;
    int idProduct = 0;
    String nameProduct = "";
    int priceProduct = 0;
    String imageProduct = "";
    String describeProduct = "";
    RequestQueue requestQueue;
    int auction = 0;
    String dateStart = "";
    String dateStop = "";
//    int requestCount = 1;

    public ProductPresenter(Context mContext, ProductView mLaptopView) {
        this.mContext = mContext;
        this.mLaptopView = mLaptopView;
    }

    public void getList(String url) {
        requestQueue = Volley.newRequestQueue(mContext);
//        getData(url);
        final List<Product> productList = new ArrayList<>();
        JsonArrayRequest arrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if ((response != null) && (response.length() > 0)) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            idProductType = jsonObject.getInt("IdProductType");
                            idProduct = jsonObject.getInt("IdProduct");
                            nameProduct = jsonObject.getString("nameProduct");
                            priceProduct = jsonObject.getInt("priceProduct");
                            imageProduct = jsonObject.getString("imageProduct");
                            describeProduct = jsonObject.getString("describeProduct");
                            auction = jsonObject.getInt("auction");
                            dateStart = jsonObject.getString("dateStart");
                            dateStop = jsonObject.getString("dateStop");
                            productList.add(new Product(idProduct, nameProduct, priceProduct, imageProduct, describeProduct, idProductType, auction, dateStart, dateStop));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                mLaptopView.getListProductSuccessFull(productList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("==============>", error.toString());
                mLaptopView.getListProductFailed(error.toString());
            }
        });
        requestQueue.add(arrayRequest);
    }

//    public void getData(String url) {
//        requestQueue.add(getDataFromSerVer(requestCount, url));
//        requestCount++;
//    }
//
//    private JsonArrayRequest getDataFromSerVer(int requestCount, String url) {
//        final List<Product> productList = new ArrayList<>();
//        JsonArrayRequest arrayRequest = new JsonArrayRequest(url + requestCount, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                if ((response != null) && (response.length() > 0)) {
//                    for (int i = 0; i < response.length(); i++) {
//                        try {
//                            JSONObject jsonObject = response.getJSONObject(i);
//                            idProductType = jsonObject.getInt("IdProductType");
//                            idProduct = jsonObject.getInt("IdProduct");
//                            nameProduct = jsonObject.getString("nameProduct");
//                            priceProduct = jsonObject.getInt("priceProduct");
//                            imageProduct = jsonObject.getString("imageProduct");
//                            describeProduct = jsonObject.getString("describeProduct");
//                            productList.add(new Product(idProduct, nameProduct, priceProduct, imageProduct, describeProduct, idProductType));
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//                mLaptopView.getListProductSuccessFull(productList);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e("==============>", error.toString());
//                mLaptopView.getListProductFailed(error.toString());
//            }
//        });
//        return arrayRequest;
//    }

    //PHONE
    public void getListPhone() {
        getList(Server.urlPhone);
    }

    public void getListSortDownPhone() {
        getList(Server.urlSortDownPhone);
    }

    public void getListSortUpPhone() {
        getList(Server.urlSortUpPhone);
    }

    //LAPTOP
    public void getListLaptop() {
        getList(Server.urlLaptop);
    }

    public void getListSortDownLaptop() {
        getList(Server.urlSortDownLaptop);
    }

    public void getListSortUpLaptop() {
        getList(Server.urlSortUpLaptop);
    }

    //    FASHION
    public void getListSortDownFashion() {
        getList(Server.urlSortDownFashion);
    }

    public void getListSortUpFashion() {
        getList(Server.urlSortUpFashion);
    }

    public void getListFashion() {
        getList(Server.urlFashion);
    }

    //    Furniture
    public void getListSortDownFurniture() {
        getList(Server.urlSortDownFurniture);
    }

    public void getListSortUpFurniture() {
        getList(Server.urlSortUpFurniture);
    }

    public void getListFurniture() {
        getList(Server.urlFurniture);
    }

    //    SPORT
    public void getListSortDownSport() {
        getList(Server.urlSortDownSport);
    }

    public void getListSortUpSport() {
        getList(Server.urlSortUpSport);
    }

    public void getListSport() {
        getList(Server.urlSport);
    }


    //    MOTHER KID
    public void getListSortDownMotherKid() {
        getList(Server.urlSortDownMotherKid);
    }

    public void getListSortUpMotherKid() {
        getList(Server.urlSortUpMotherKid);
    }

    public void getListMotherKid() {
        getList(Server.urlMotherKid);
    }


    //   CleanningStuff
    public void getListSortDownCleanningStuff() {
        getList(Server.urlSortDownCleanningStuff);
    }

    public void getListSortUpCleanningStuff() {
        getList(Server.urlSortUpCleanningStuff);
    }

    public void getListCleanningStuff() {
        getList(Server.urlCleanningStuff);
    }


    //    Kitchen
    public void getListSortDownKitchen() {
        getList(Server.urlSortDownKitchen);
    }

    public void getListSortUpKitchen() {
        getList(Server.urlSortUpKitchen);
    }

    public void getListKitchen() {
        getList(Server.urlKitchen);
    }


    //   TechnologyEquipment
    public void getListSortDownTechnologyEquipment() {
        getList(Server.urlSortDownTechnologyEquipment);
    }

    public void getListSortUpTechnologyEquipment() {
        getList(Server.urlSortUpTechnologyEquipment);
    }

    public void getListTechnologyEquipment() {
        getList(Server.urlTechnologyEquipment);
    }


    //  Stationery
    public void getListSortDownStationery() {
        getList(Server.urlSortDownStationery);
    }

    public void getListSortUpStationery() {
        getList(Server.urlSortUpStationery);
    }

    public void getListStationery() {
        getList(Server.urlStationery);
    }


    //    Jewelry
    public void getListSortDownJewelry() {
        getList(Server.urlSortDownJewelry);
    }

    public void getListSortUpJewelry() {
        getList(Server.urlSortUpJewelry);
    }

    public void getListJewelry() {
        getList(Server.urlJewelry);
    }


    //  Pet
    public void getListSortDownPet() {
        getList(Server.urlSortDownPet);
    }

    public void getListSortUpPet() {
        getList(Server.urlSortUpPet);
    }

    public void getListPet() {
        getList(Server.urlPet);
    }
}
