package com.example.ominext.storedeviceonline.ui.home;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ominext.storedeviceonline.R;
import com.example.ominext.storedeviceonline.model.ProductType;
import com.example.ominext.storedeviceonline.until.CheckConnection;
import com.example.ominext.storedeviceonline.ui.contact.ContactFragment;
import com.example.ominext.storedeviceonline.ui.info.InformationFragment;
import com.example.ominext.storedeviceonline.ui.laptop.LaptopFragment;
import com.example.ominext.storedeviceonline.ui.main.MainFragment;
import com.example.ominext.storedeviceonline.ui.phone.PhoneFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


//tách ra 1 class
public class HomeActivity extends AppCompatActivity implements HomeView {
    @BindView(R.id.list_item)
    ListView listItem;
    @BindView(R.id.navigation_view_main)
    NavigationView navigationViewMain;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    List<ProductType> listProductType=new ArrayList<>();
    ProductTypeAdapter productTypeAdapter;

    Fragment fragment = null;
//    int id = 0;
//    String nameProductType = "";
//    String imageProductType = "";
    @BindView(R.id.tool_bar_main)
    Toolbar toolBarMain;
    @BindView(R.id.frame_layout)
    FrameLayout frameLayout;

    HomePresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        init();
        listItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    replaceFragment(i);
                    setTitle(listProductType.get(i).getNameProductType());
                    drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
    }

    private void init() {
        mPresenter = new HomePresenter(HomeActivity.this, this);
        if (CheckConnection.haveNetWorkConnection(getApplicationContext())) {
            ActionBar();
            mPresenter.getListProductType();
//            getProductType();
        } else {
            CheckConnection.showToast(getApplicationContext(), "Haven't internet");
            Log.e("==============>", "Haven't internet");
            finish();
        }
//        listProductType.add(new ProductType(0, "Trang chính", "https://image.flaticon.com/icons/png/512/25/25694.png"));
//        productTypeAdapter = new ProductTypeAdapter(listProductType, getApplicationContext());
//        listItem.setAdapter(productTypeAdapter);
//        productTypeAdapter.notifyDataSetChanged();
    }
//    private void getProductType(){
//        SOService soService= RetrofitClient.getClient().create(SOService.class);
//        Call<ProductType> call=soService.getProductType();
//        call.enqueue(new Callback<ProductType>() {
//            @Override
//            public void onResponse(Call<ProductType> call, retrofit2.Response<ProductType> response) {
//
//            }
//
//            @Override
//            public void onFailure(Call<ProductType> call, Throwable t) {
//
//            }
//        });
//    }
    //    lấy dữ liệu từ server
//    private void getProductType() {
//        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//        JsonArrayRequest arrayRequest = new JsonArrayRequest(Server.urlProductType, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                if (response != null) {
//                    for (int i = 0; i < response.length(); i++) {
//                        try {
////                            Post[] beanPostList = new Gson().fromJson(response, Post[].class);
//                            JSONObject jsonObject = response.getJSONObject(i);
//                            id = jsonObject.getInt("IdProductType");
//                            nameProductType = jsonObject.getString("NameProductType");
//                            imageProductType = jsonObject.getString("ImageProductType");
//                            listProductType.add(new ProductType(id, nameProductType, imageProductType));
//                            productTypeAdapter.notifyDataSetChanged();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    listProductType.add(new ProductType(3, "Liên hệ", "http://www.freeiconspng.com/uploads/phone-icon-old-phone-telephone-icon-9.png"));
//                    listProductType.add(new ProductType(4, "Thông tin", "http://www.freeiconspng.com/uploads/details-info-information-more-details-icon--icon-search-engine--7.png"));
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//                CheckConnection.showToast(getApplicationContext(), error.toString());
//                Log.e("==============>", error.toString());
//            }
//        });
//        int socketTimeout = 30000;//30 seconds - change to what you want
//        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
//        arrayRequest.setRetryPolicy(policy);
//        arrayRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 5, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        requestQueue.add(arrayRequest);
//    }

    //truyền dữ liệu giữa main activity và fragment
    public void replaceFragment(int pos) {
        switch (pos) {
            case 0:
                fragment = MainFragment.newInstance();
                break;
            case 1:
                fragment = PhoneFragment.newInstance();
                break;
            case 2:
                fragment = LaptopFragment.newInstance();
                break;
            case 3:
                fragment = ContactFragment.newInstance();
                break;
            case 4:
                fragment = InformationFragment.newInstance();
                break;
            default:
                break;
        }
        if (fragment != null) {
            //v4 getSupport, thường: getFragment
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
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

    @Override
    public void getListProductTypeSuccess(List<ProductType> productTypes) {
        listProductType = productTypes;
        productTypeAdapter = new ProductTypeAdapter(listProductType, getApplicationContext());
        listItem.setAdapter(productTypeAdapter);
        replaceFragment(0);
        setTitle(listProductType.get(0).getNameProductType());
        productTypeAdapter.notifyDataSetChanged();
    }

    @Override
    public void getListProductTypeFailed(String s) {
        Toast.makeText(getApplicationContext(),"Lỗi tải trang",Toast.LENGTH_SHORT).show();
    }
}
