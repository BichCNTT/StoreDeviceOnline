package com.example.ominext.storedeviceonline.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.ominext.storedeviceonline.R;
import com.example.ominext.storedeviceonline.adapter.NewProductAdapter;
import com.example.ominext.storedeviceonline.adapter.ProductTypeAdapter;
import com.example.ominext.storedeviceonline.helper.ImageViewUtil;
import com.example.ominext.storedeviceonline.model.NewProduct;
import com.example.ominext.storedeviceonline.model.ProductType;
import com.example.ominext.storedeviceonline.until.CheckConnection;
import com.example.ominext.storedeviceonline.until.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tool_bar_main)
    Toolbar toolBarMain;
    @BindView(R.id.view_flipper)
    ViewFlipper viewFlipper;
    @BindView(R.id.view_main)
    RecyclerView recyclerViewMain;
    @BindView(R.id.list_item)
    ListView listItem;
    @BindView(R.id.navigation_view_main)
    NavigationView navigationViewMain;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    ArrayList<ProductType> listProductType;
    ArrayList<NewProduct> listNewProduct;
    ProductTypeAdapter productTypeAdapter;
    NewProductAdapter productAdapter;

    int id = 0;
    String nameProductType = "";
    String imageProductType = "";

    int idProduct = 0;
    String nameProduct = "";
    int priceProduct = 0;
    String imageProduct = "";
    String describeProduct = "";
    int idProductType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        listNewProduct = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerViewMain.setLayoutManager(layoutManager);
        recyclerViewMain.setHasFixedSize(true);
        productAdapter = new NewProductAdapter(getApplicationContext(),listNewProduct);
        recyclerViewMain.setAdapter(productAdapter);
        productAdapter.notifyDataSetChanged();
        init();
    }

    private void init() {
        if (CheckConnection.haveNetWorkConnection(getApplicationContext())) {
            ActionBar();
            ActionViewFlipper();
            getProductType();
            getNewProduct();
        } else {
            CheckConnection.showToast(getApplicationContext(), "Haven't internet");
            Log.e("==============>", "Haven't internet");
            finish();
        }
        listProductType = new ArrayList<>();
        listProductType.add(new ProductType(0, "Trang chính", "https://image.flaticon.com/icons/png/512/25/25694.png"));
        productTypeAdapter = new ProductTypeAdapter(listProductType, getApplicationContext());
        listItem.setAdapter(productTypeAdapter);
        productTypeAdapter.notifyDataSetChanged();
    }

    private void getNewProduct() {
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Server.urlNewProduct, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            idProduct = jsonObject.getInt("IdProduct");
                            nameProduct = jsonObject.getString("nameProduct");
                            priceProduct = jsonObject.getInt("priceProduct");
                            imageProduct = jsonObject.getString("imageProduct");
                            describeProduct = jsonObject.getString("describeProduct");
                            idProductType = jsonObject.getInt("idProductType");
                            listNewProduct.add(new NewProduct(idProduct, nameProduct, priceProduct, imageProduct, describeProduct, idProductType));
                            productAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.showToast(getApplicationContext(), error.toString());
                Log.e("==============>", error.toString());
            }
        });
        requestQueue.add(arrayRequest);
    }

    //lấy dữ liệu từ server
    private void getProductType() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Server.urlProductType, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("IdProductType");
                            nameProductType = jsonObject.getString("NameProductType");
                            imageProductType = jsonObject.getString("ImageProductType");
                            listProductType.add(new ProductType(id, nameProductType, imageProductType));
                            productTypeAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    listProductType.add(new ProductType(3, "Liên hệ", "http://hddfhm.com/images/contact-clipart-18.png"));
                    listProductType.add(new ProductType(4, "Thông tin", "https://image.freepik.com/free-icon/information-circle_318-27255.jpg"));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.showToast(getApplicationContext(), error.toString());
                Log.e("==============>", error.toString());
            }
        });
        requestQueue.add(arrayRequest);
    }

    //tao hinh anh di chuyen (chay quang cao) va lam mo 1 danh sach anh cho truoc
    private void ActionViewFlipper() {
        ArrayList<String> urlImageList = new ArrayList<>();
        urlImageList.add("http://cms.kienthuc.net.vn/zoom/1000/uploaded/nguyenvan/2016_12_07/song/anh-quang-cao-dien-thoai-don-tim-khan-gia-cua-song-joong-ki-hinh-4.jpg");
        urlImageList.add("http://kenh14cdn.com/Images/Uploaded/Share/2011/06/14/b89110614tekb8.jpg");
        urlImageList.add("http://dichvuseolentop.com/wp-content/uploads/2016/09/quang-cao-facebook-bang-fanpage-h1.jpg");
        urlImageList.add("http://3.bp.blogspot.com/_QeQ79KKL88Q/SuZJHkKwNsI/AAAAAAAADKY/5LgOg_wIkAY/s400/SuperJuniorM_oppo2_mogocafe.jpg");
        urlImageList.add("http://kenh14cdn.com/Images/Uploaded/Share/2011/06/27/110627tekLG5.jpg");
        for (int i = 0; i < urlImageList.size(); i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            ImageViewUtil.loadImg(getApplicationContext(), urlImageList.get(i), imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);
    }

    private void ActionBar() {
        setSupportActionBar(toolBarMain);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBarMain.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolBarMain.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }
}
