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
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ominext.storedeviceonline.R;
import com.example.ominext.storedeviceonline.listener.OnItemClickListener;
import com.example.ominext.storedeviceonline.model.Product;
import com.example.ominext.storedeviceonline.model.ProductType;
import com.example.ominext.storedeviceonline.ui.cart.CartFragment;
import com.example.ominext.storedeviceonline.ui.cleanningstuff.CleanningStuffFragment;
import com.example.ominext.storedeviceonline.ui.contact.ContactFragment;
import com.example.ominext.storedeviceonline.ui.detail.DetailProductFragment;
import com.example.ominext.storedeviceonline.ui.fashion.FashionFragment;
import com.example.ominext.storedeviceonline.ui.find.FindFragment;
import com.example.ominext.storedeviceonline.ui.furniture.FurnitureFragment;
import com.example.ominext.storedeviceonline.ui.infopro.InformationFragment;
import com.example.ominext.storedeviceonline.ui.jewelry.JewelryFragment;
import com.example.ominext.storedeviceonline.ui.kitchen.KitchenFragment;
import com.example.ominext.storedeviceonline.ui.laptop.LaptopFragment;
import com.example.ominext.storedeviceonline.ui.main.MainFragment;
import com.example.ominext.storedeviceonline.ui.motherkid.MotherKidFragment;
import com.example.ominext.storedeviceonline.ui.pet.PetFragment;
import com.example.ominext.storedeviceonline.ui.phone.PhoneFragment;
import com.example.ominext.storedeviceonline.ui.sport.SportFragment;
import com.example.ominext.storedeviceonline.ui.stationery.StationeryFragment;
import com.example.ominext.storedeviceonline.ui.technologyequipment.TechnologyEquipmentFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

//TASK 0: hoàn thành hết tất cả các nhóm sản phẩm
//
//TASK 1: Hoàn thành nốt các view
//sau khi đặt hàng xong, xóa dữ liệu của giỏ hàng trong máy, đẩy dl lên server
//DƠNE TASK 2
//TASK 3: Lọc sản phẩm theo giá từ thấp đến cao và từ cao đến thấp ở mỗi màn
//GET dữ liệu trên server về->cho hiển thị lại trên màn ds
//TASK 4: Hiển thị được địa chỉ người bán
//thông tin khách hàng về giỏ hàng
public class HomeActivity extends AppCompatActivity implements HomeView, OnItemClickListener, FragmentManager.OnBackStackChangedListener {
    @BindView(R.id.list_item)
    ListView listItem;
    @BindView(R.id.navigation_view_main)
    NavigationView navigationViewMain;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    List<ProductType> listProductType = new ArrayList<>();
    ProductTypeAdapter productTypeAdapter;
    Fragment fragmengCurrent = null;
    @BindView(R.id.tool_bar_main)
    Toolbar toolBarMain;
    @BindView(R.id.frame_layout)
    FrameLayout frameLayout;
    HomePresenter mPresenter;
    List<Product> listFind = new ArrayList<>();
    private int tabCurrent = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        init();
        listItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                changeHome(i);
                setTitle(listProductType.get(i).getNameProductType());
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        getSupportFragmentManager().addOnBackStackChangedListener(this);
    }

    private void init() {
        mPresenter = new HomePresenter(HomeActivity.this, this);
        ActionBar();
        mPresenter.getListProductType();
    }

    //truyền dữ liệu giữa main activity và fragmengCurrent
    public void changeHome(int pos) {
        setTitle(listProductType.get(pos).getNameProductType());
        tabCurrent = pos;
        switch (pos) {
            case 0:
                fragmengCurrent = MainFragment.newInstance();
                break;
            case 1:
                fragmengCurrent = PhoneFragment.newInstance();
                break;
            case 2:
                fragmengCurrent = LaptopFragment.newInstance();
                break;
            case 3:
                fragmengCurrent = FashionFragment.newInstance();
                break;
            case 4:
                fragmengCurrent = FurnitureFragment.newInstance();
                break;
            case 5:
                fragmengCurrent = SportFragment.newInstance();
                break;
            case 6:
                fragmengCurrent = MotherKidFragment.newInstance();
                break;
            case 7:
                fragmengCurrent = CleanningStuffFragment.newInstance();
                break;
            case 8:
                fragmengCurrent = KitchenFragment.newInstance();
                break;
            case 9:
                fragmengCurrent = TechnologyEquipmentFragment.newInstance();
                break;
            case 10:
                fragmengCurrent = StationeryFragment.newInstance();
                break;
            case 11:
                fragmengCurrent = JewelryFragment.newInstance();
                break;
            case 12:
                fragmengCurrent = PetFragment.newInstance();
                break;
            case 13:
                fragmengCurrent = ContactFragment.newInstance();
                break;
            case 14:
                fragmengCurrent = InformationFragment.newInstance();
                break;
            default:
                break;
        }
        if (fragmengCurrent != null) {
            replaceFragment(fragmengCurrent);
        }
    }

    private void replaceFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout,fragment)
                .commit();
    }

    public void addFragment(Fragment fragment){
        fragmengCurrent = fragment;
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frame_layout,fragment)
                .addToBackStack(fragment.getClass().getSimpleName())
                .commit();
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
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;
        switch (item.getItemId()) {
            case R.id.menu_cart:
                Bundle bundle = new Bundle();
                bundle.putInt("key", 0);
                Fragment fragment = CartFragment.newInstance();
                fragment.setArguments(bundle);
                addFragment(fragment);
                break;
            case R.id.action_search:
                addFragment(FindFragment.newInstance());
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
        changeHome(0);
        setTitle(listProductType.get(0).getNameProductType());
        productTypeAdapter.notifyDataSetChanged();
    }

    @Override
    public void getListProductTypeFailed(String s) {
        Toast.makeText(getApplicationContext(), "Lỗi tải trang", Toast.LENGTH_SHORT).show();
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
        addFragment(fragment);
    }

    @Override
    public void onBackPressed() {
        if(fragmengCurrent instanceof MainFragment){
          finish();
        }else{
            if(getSupportFragmentManager().getBackStackEntryCount()>0)
            super.onBackPressed();
            else if(tabCurrent != 0){
                changeHome(0);
            }
        }
    }

    @Override
    public void onBackStackChanged() {
        Log.i("HomeActivity","onBackStackChanged: "+getSupportFragmentManager().getBackStackEntryCount());
        FragmentManager frm = getSupportFragmentManager();
        if(frm.getBackStackEntryCount()==0){
            setTitle(listProductType.get(tabCurrent).getNameProductType());
           if(tabCurrent ==0){
               fragmengCurrent = MainFragment.newInstance();
           }
        }
       Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame_layout);
        if(fragment instanceof FindFragment){
            setTitle("Tìm kiếm");
            return;
        }
        if(fragment instanceof DetailProductFragment){
            setTitle("Chi tiết sản phẩm");
            return;
        }
        if(fragment instanceof CartFragment){
            setTitle("Giỏ hàng");
            return;
        }
        if(fragment instanceof PhoneFragment){
           setTitle("Điện thoại & máy tính bảng");
        }
    }
}