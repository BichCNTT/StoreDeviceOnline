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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ominext.storedeviceonline.R;
import com.example.ominext.storedeviceonline.model.ProductType;
import com.example.ominext.storedeviceonline.ui.cart.CartFragment;
import com.example.ominext.storedeviceonline.ui.contact.ContactFragment;
import com.example.ominext.storedeviceonline.ui.fashion.FashionFragment;
import com.example.ominext.storedeviceonline.ui.furniture.FurnitureFragment;
import com.example.ominext.storedeviceonline.ui.info.InformationFragment;
import com.example.ominext.storedeviceonline.ui.laptop.LaptopFragment;
import com.example.ominext.storedeviceonline.ui.main.MainFragment;
import com.example.ominext.storedeviceonline.ui.phone.PhoneFragment;
import com.example.ominext.storedeviceonline.ui.sport.SportFragment;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

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

    List<ProductType> listProductType = new ArrayList<>();
    ProductTypeAdapter productTypeAdapter;
    Fragment fragment = null;
    @BindView(R.id.tool_bar_main)
    Toolbar toolBarMain;
    @BindView(R.id.frame_layout)
    FrameLayout frameLayout;
    HomePresenter mPresenter;
    @BindView(R.id.search_view)
    MaterialSearchView searchView;
    @BindView(R.id.list_view_find)
    ListView listViewFind;
    private ArrayList<String> listFind=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        listViewFind.setVisibility(View.GONE);
        frameLayout.setVisibility(View.VISIBLE);
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
        ActionBar();
        mPresenter.getListProductType();
        mPresenter.getListFind();
//        setSearchView();

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
                fragment = FashionFragment.newInstance();
                break;
            case 4:
                fragment = FurnitureFragment.newInstance();
                break;
            case 5:
                fragment = SportFragment.newInstance();
                break;
            case 13:
                fragment = ContactFragment.newInstance();
                break;
            case 14:
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

//    private void setSearchView() {
//        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
//        searchView.setAdapter(adapter);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_cart:
                Bundle bundle = new Bundle();
                bundle.putInt("key", 0);
                fragment = CartFragment.newInstance();
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            case R.id.action_search:
                listViewFind.setVisibility(View.VISIBLE);
                frameLayout.setVisibility(View.GONE);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
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
        Toast.makeText(getApplicationContext(), "Lỗi tải trang", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getListFindFailed(String s) {
        Toast.makeText(getApplicationContext(), "Lỗi tải dữ liệu tìm kiếm", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getListFindSuccess(ArrayList<String> listFind) {
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listFind);
        searchView.setAdapter(adapter);
    }
}
