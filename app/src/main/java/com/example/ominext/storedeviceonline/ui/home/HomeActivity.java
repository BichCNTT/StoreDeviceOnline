package com.example.ominext.storedeviceonline.ui.home;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
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
import com.example.ominext.storedeviceonline.ui.addproduct.AddProductFragment;
import com.example.ominext.storedeviceonline.ui.auction.AuctionFragment;
import com.example.ominext.storedeviceonline.ui.login.LoginFragment;
import com.example.ominext.storedeviceonline.ui.cart.CartFragment;
import com.example.ominext.storedeviceonline.ui.cleanningstuff.CleanningStuffFragment;
import com.example.ominext.storedeviceonline.ui.contact.ContactFragment;
import com.example.ominext.storedeviceonline.ui.detail.DetailProductFragment;
import com.example.ominext.storedeviceonline.ui.fashion.FashionFragment;
import com.example.ominext.storedeviceonline.ui.find.FindFragment;
import com.example.ominext.storedeviceonline.ui.furniture.FurnitureFragment;
import com.example.ominext.storedeviceonline.ui.jewelry.JewelryFragment;
import com.example.ominext.storedeviceonline.ui.kitchen.KitchenFragment;
import com.example.ominext.storedeviceonline.ui.laptop.LaptopFragment;
import com.example.ominext.storedeviceonline.ui.main.MainFragment;
import com.example.ominext.storedeviceonline.ui.motherkid.MotherKidFragment;
import com.example.ominext.storedeviceonline.ui.notifi.NotificationFragment;
import com.example.ominext.storedeviceonline.ui.order.OrderFragment;
import com.example.ominext.storedeviceonline.ui.pet.PetFragment;
import com.example.ominext.storedeviceonline.ui.phone.PhoneFragment;
import com.example.ominext.storedeviceonline.ui.sport.SportFragment;
import com.example.ominext.storedeviceonline.ui.stationery.StationeryFragment;
import com.example.ominext.storedeviceonline.ui.storeinfo.StoreInfoFragment;
import com.example.ominext.storedeviceonline.ui.technologyequipment.TechnologyEquipmentFragment;
import com.mikepenz.actionitembadge.library.ActionItemBadge;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

//Nếu chuyển từ màn đầu tiên sang màn hình đăng nhập thì sẽ là add, còn chuyển từ màn hình giỏ hàng qua màn đăng nhập thì sẽ là replace
public class HomeActivity extends AppCompatActivity implements HomeView, OnItemClickListener, FragmentManager.OnBackStackChangedListener {
    @BindView(R.id.list_item)
    ListView listItem;
    @BindView(R.id.navigation_view_main)
    NavigationView navigationViewMain;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    public static DrawerLayout layout;
    public static List<ProductType> listProductType = new ArrayList<>();
    public static ProductTypeAdapter productTypeAdapter;
    Fragment fragmentCurrent = null;
    @BindView(R.id.tool_bar_main)
    Toolbar toolBarMain;
    @BindView(R.id.frame_layout)
    FrameLayout frameLayout;
    HomePresenter mPresenter;
    List<Product> listFind = new ArrayList<>();
    private int tabCurrent = 0;
    public int count = 0;
    public Menu optionsMenu;
// notification:
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
//      cái đã thêm
        getSupportFragmentManager().addOnBackStackChangedListener(this);
    }

    private void init() {
        layout = drawerLayout;
        ActivityCompat.requestPermissions(HomeActivity.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                1);
        mPresenter = new HomePresenter(HomeActivity.this, this);
        count = mPresenter.getSizeProduct();
        ActionBar();
        mPresenter.getListProductType();
    }

    //truyền dữ liệu giữa main activity và fragmentCurrent
    public void changeHome(int pos) {
        setTitle(listProductType.get(pos).getNameProductType());
        tabCurrent = pos;
        switch (pos) {
            case 0:
                fragmentCurrent = LoginFragment.newInstance();
                break;
            case 1:
                fragmentCurrent = MainFragment.newInstance();
                break;
            case 2:
                fragmentCurrent = PhoneFragment.newInstance();
                break;
            case 3:
                fragmentCurrent = LaptopFragment.newInstance();
                break;
            case 4:
                fragmentCurrent = FashionFragment.newInstance();
                break;
            case 5:
                fragmentCurrent = FurnitureFragment.newInstance();
                break;
            case 6:
                fragmentCurrent = SportFragment.newInstance();
                break;
            case 7:
                fragmentCurrent = MotherKidFragment.newInstance();
                break;
            case 8:
                fragmentCurrent = CleanningStuffFragment.newInstance();
                break;
            case 9:
                fragmentCurrent = KitchenFragment.newInstance();
                break;
            case 10:
                fragmentCurrent = TechnologyEquipmentFragment.newInstance();
                break;
            case 11:
                fragmentCurrent = StationeryFragment.newInstance();
                break;
            case 12:
                fragmentCurrent = JewelryFragment.newInstance();
                break;
            case 13:
                fragmentCurrent = PetFragment.newInstance();
                break;
            case 14:
                fragmentCurrent = ContactFragment.newInstance();
                break;
            case 15:
                fragmentCurrent = StoreInfoFragment.newInstance();
                break;
            default:
                break;
        }
        if (fragmentCurrent != null) {
            replaceFragment(fragmentCurrent);
        }
    }

    public void replaceFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .commit();
        Log.e("----->", "thay thế 1 fragment");
    }

    public void addFragment(Fragment fragment) {
        fragmentCurrent = fragment;
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frame_layout, fragment)
                .addToBackStack(fragment.getClass().getSimpleName())
                .commit();
        Log.e("----->", "thêm mới 1 fragment");
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
        optionsMenu = menu;
        getMenuInflater().inflate(R.menu.menu_main, menu);
        if (count == 0) {
            count = Integer.MIN_VALUE;
        }
        ActionItemBadge.update(this, menu.findItem(R.id.menu_cart), ContextCompat.getDrawable(this, R.drawable.ic_cart), ActionItemBadge.BadgeStyles.RED.getStyle(), count);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
        changeHome(1);
        setTitle(listProductType.get(1).getNameProductType());
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
        if (fragmentCurrent instanceof MainFragment) {
            finish();
            Log.e("------>", "thoát");
        } else {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                super.onBackPressed();
                Log.e("------>", "quay lại");
            } else if (tabCurrent != 1) {
                changeHome(1);
            }
        }
    }

    //khi backStack thay đổi->khi add 1 fragment vào
    @Override
    public void onBackStackChanged() {
        FragmentManager frm = getSupportFragmentManager();
        if (frm.getBackStackEntryCount() == 0) {
            setTitle(listProductType.get(tabCurrent).getNameProductType());
            if (tabCurrent == 0) {
                fragmentCurrent = MainFragment.newInstance();
            }
        }
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame_layout);
        if (fragment instanceof FindFragment) {
            setTitle("Tìm kiếm");
            return;
        }
        if (fragment instanceof DetailProductFragment) {
            setTitle("Chi tiết sản phẩm");
            return;
        }
        if (fragment instanceof CartFragment) {
            setTitle("Giỏ hàng");
            return;
        }
        if (fragment instanceof NotificationFragment) {
            setTitle("");
            return;
        }
        if (fragment instanceof OrderFragment) {
            setTitle("Đơn hàng của tôi");
            return;
        }
        if (fragment instanceof LoginFragment) {
            setTitle("Đăng nhập");
            return;
        }
        if (fragment instanceof MainFragment) {
            setTitle("Trang chủ");
            return;
        }
        if (fragment instanceof AddProductFragment) {
            setTitle("Thêm mặt hàng");
            return;
        }
        if (fragment instanceof AuctionFragment) {
            setTitle("Đấu giá");
            return;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(HomeActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
}
