package com.example.ominext.storedeviceonline.activity;

import android.app.Activity;
import android.content.Intent;
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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.ominext.storedeviceonline.R;
import com.example.ominext.storedeviceonline.adapter.ProductTypeAdapter;
import com.example.ominext.storedeviceonline.data.model.ProductType;
import com.example.ominext.storedeviceonline.fragment.ContactFragment;
import com.example.ominext.storedeviceonline.fragment.InformationFragment;
import com.example.ominext.storedeviceonline.fragment.LaptopFragment;
import com.example.ominext.storedeviceonline.fragment.MainFragment;
import com.example.ominext.storedeviceonline.fragment.PhoneFragment;
import com.example.ominext.storedeviceonline.until.CheckConnection;
import com.example.ominext.storedeviceonline.until.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

//tách ra 1 class
public class MainActivity extends AppCompatActivity {
    @BindView(R.id.list_item)
    ListView listItem;
    @BindView(R.id.navigation_view_main)
    NavigationView navigationViewMain;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    ArrayList<ProductType> listProductType = new ArrayList<>();
    ProductTypeAdapter productTypeAdapter;

    Fragment fragment = null;
    int id = 0;
    String nameProductType = "";
    String imageProductType = "";
    @BindView(R.id.tool_bar_main)
    Toolbar toolBarMain;
    @BindView(R.id.frame_layout)
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
        if (CheckConnection.haveNetWorkConnection(getApplicationContext())) {
            replaceFragment(0);
            setTitle(listProductType.get(0).getNameProductType());
        } else {
            CheckConnection.showToast(getApplicationContext(), "Haven't internet");
        }
        listItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (CheckConnection.haveNetWorkConnection(getApplicationContext())) {
                    replaceFragment(i);
                    setTitle(listProductType.get(i).getNameProductType());
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    CheckConnection.showToast(getApplicationContext(), "Haven't internet");
                }
            }
        });
    }

    private void init() {
        if (CheckConnection.haveNetWorkConnection(getApplicationContext())) {
            ActionBar();
            getProductType();
        } else {
            CheckConnection.showToast(getApplicationContext(), "Haven't internet");
            Log.e("==============>", "Haven't internet");
            finish();
        }

        listProductType.add(new ProductType(0, "Trang chính", "https://image.flaticon.com/icons/png/512/25/25694.png"));
        productTypeAdapter = new ProductTypeAdapter(listProductType, getApplicationContext());
        listItem.setAdapter(productTypeAdapter);
        productTypeAdapter.notifyDataSetChanged();
    }

    //    lấy dữ liệu từ server
    private void getProductType() {
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
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
}
