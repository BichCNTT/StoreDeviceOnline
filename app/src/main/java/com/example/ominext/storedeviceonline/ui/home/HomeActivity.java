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
import com.example.ominext.storedeviceonline.listener.OnItemClickListener;
import com.example.ominext.storedeviceonline.model.Product;
import com.example.ominext.storedeviceonline.model.ProductType;
import com.example.ominext.storedeviceonline.ui.cart.CartFragment;
import com.example.ominext.storedeviceonline.ui.contact.ContactFragment;
import com.example.ominext.storedeviceonline.ui.detail.DetailProductFragment;
import com.example.ominext.storedeviceonline.ui.fashion.FashionFragment;
import com.example.ominext.storedeviceonline.ui.furniture.FurnitureFragment;
import com.example.ominext.storedeviceonline.ui.info.InformationFragment;
import com.example.ominext.storedeviceonline.ui.laptop.LaptopFragment;
import com.example.ominext.storedeviceonline.ui.main.MainFragment;
import com.example.ominext.storedeviceonline.ui.phone.PhoneFragment;
import com.example.ominext.storedeviceonline.ui.sport.SportFragment;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.miguelcatalan.materialsearchview.SearchAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
//hoàn thiện chức năng tìm kiếm
//trong mục tìm kiếm phải hiển thị lên được tên sản phẩm và hình ảnh cho sản phẩm đó
//làm 1 cái adapter cho mục tìm kiếm
//row tìm kiếm
//truyền list vào lấy dữ liệu từ trên localhost

public class HomeActivity extends AppCompatActivity implements HomeView, OnItemClickListener {
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
    List<Product> listFind = new ArrayList<>();
    FindAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        init();
        Log.e("===============>", String.valueOf(listFind.size()));
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
        Log.e("===============>", String.valueOf(listFind.size()));
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
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void getListProductTypeSuccess(List<ProductType> productTypes) {
        listProductType = productTypes;
        productTypeAdapter = new ProductTypeAdapter(listProductType, this);
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

    //không tìm theo tên được
    @Override
    public void getListFindSuccess(final List<Product> listFind) {
        this.listFind = listFind;
        adapter = new FindAdapter(this.listFind, this);
        searchView.setAdapter(adapter);
        searchView.dismissSuggestions();
        final List<Product> products = new ArrayList<>();
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                for (Product product : listFind) {
                    if (newText.equals(product.getNameProduct())) {
                        products.add(product);
                    }
                }
                searchView.showSuggestions();
//                adapter = new FindAdapter(products, HomeActivity.this);
//                searchView.setAdapter(adapter);
                return true;
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        DetailProductFragment fragment = DetailProductFragment.newInstance();
        Bundle bundle = new Bundle();
        Product product = listFind.get(position);
        bundle.putString("name", product.getNameProduct());
        bundle.putInt("price", product.getPriceProduct());
        bundle.putString("describe", product.getDescribeProduct());
        bundle.putString("image", product.getImageProduct());
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
//        view.setOnClickListener(new View.OnClickListener() {
//@Override
//public void onClick(View view) {
//        DetailProductFragment fragment = DetailProductFragment.newInstance();
//        Bundle bundle = new Bundle();
//        Product product = productArrayList.get(i);
//        bundle.putString("name", product.getNameProduct());
//        bundle.putInt("price", product.getPriceProduct());
//        bundle.putString("describe", product.getDescribeProduct());
//        bundle.putString("image", product.getImageProduct());
//        fragment.setArguments(bundle);
//        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.frame_layout, fragment);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();
//        }
//        });