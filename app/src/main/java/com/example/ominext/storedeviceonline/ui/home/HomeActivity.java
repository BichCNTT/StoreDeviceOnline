package com.example.ominext.storedeviceonline.ui.home;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import com.example.ominext.storedeviceonline.ui.notifi.NotificationFragment;
import com.example.ominext.storedeviceonline.ui.order.OrderFragment;
import com.example.ominext.storedeviceonline.ui.pet.PetFragment;
import com.example.ominext.storedeviceonline.ui.phone.PhoneFragment;
import com.example.ominext.storedeviceonline.ui.sport.SportFragment;
import com.example.ominext.storedeviceonline.ui.stationery.StationeryFragment;
import com.example.ominext.storedeviceonline.ui.technologyequipment.TechnologyEquipmentFragment;
import com.example.ominext.storedeviceonline.ui.userinfo.UserInfoFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

//TASK 0: Hoàn thành hết tất cả các nhóm sản phẩm
//TASK 1: Sửa lại keyboard cho scoll lại vs màn hình đăng kí
//TASK 4: Hiển thị được địa chỉ người bán
//TASK 6: Làm thêm load more và refresh ứng dụng
//11/9/2017: Đọc hiểu cơ chế back stack
public class HomeActivity extends AppCompatActivity implements HomeView, OnItemClickListener, FragmentManager.OnBackStackChangedListener {
    @BindView(R.id.list_item)
    ListView listItem;
    @BindView(R.id.navigation_view_main)
    NavigationView navigationViewMain;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    List<ProductType> listProductType = new ArrayList<>();
    ProductTypeAdapter productTypeAdapter;
    Fragment fragmentCurrent = null;
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
//      cái đã thêm
        getSupportFragmentManager().addOnBackStackChangedListener(this);
    }

    private void init() {
        mPresenter = new HomePresenter(HomeActivity.this, this);
        ActionBar();
        mPresenter.getListProductType();
    }

    //truyền dữ liệu giữa main activity và fragmentCurrent
    public void changeHome(int pos) {
        setTitle(listProductType.get(pos).getNameProductType());
        tabCurrent = pos;
        switch (pos) {
            case 0:
                fragmentCurrent = MainFragment.newInstance();
                break;
            case 1:
                fragmentCurrent = PhoneFragment.newInstance();
                break;
            case 2:
                fragmentCurrent = LaptopFragment.newInstance();
                break;
            case 3:
                fragmentCurrent = FashionFragment.newInstance();
                break;
            case 4:
                fragmentCurrent = FurnitureFragment.newInstance();
                break;
            case 5:
                fragmentCurrent = SportFragment.newInstance();
                break;
            case 6:
                fragmentCurrent = MotherKidFragment.newInstance();
                break;
            case 7:
                fragmentCurrent = CleanningStuffFragment.newInstance();
                break;
            case 8:
                fragmentCurrent = KitchenFragment.newInstance();
                break;
            case 9:
                fragmentCurrent = TechnologyEquipmentFragment.newInstance();
                break;
            case 10:
                fragmentCurrent = StationeryFragment.newInstance();
                break;
            case 11:
                fragmentCurrent = JewelryFragment.newInstance();
                break;
            case 12:
                fragmentCurrent = PetFragment.newInstance();
                break;
            case 13:
                fragmentCurrent = ContactFragment.newInstance();
                break;
            case 14:
                fragmentCurrent = InformationFragment.newInstance();
                break;
            default:
                break;
        }
        if (fragmentCurrent != null) {
            replaceFragment(fragmentCurrent);
        }
    }

    private void replaceFragment(Fragment fragment) {
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
        getMenuInflater().inflate(R.menu.main_menu, menu);
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

    //xử lý cùng cấp (khi replace) -> màn menu
    @Override
    public void onBackPressed() {
//        khi ấn nút back nếu fragment hiện tại là main fragment thì out ra
        if (fragmentCurrent instanceof MainFragment) {
            finish();
            Log.e("------>", "thoát");
        } else {
//          ngược lại nếu trong ngăn xếp vẫn còn thì quay lại fragment trước đó
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                super.onBackPressed();
                Log.e("------>", "quay lại");
            }
//            ngược lại nếu tab hiện tại khác 0 thì quay về màn home để set lại title cho nó và thay nó về màn home
            else if (tabCurrent != 0) {
                changeHome(0);
            }
        }
    }

    //khi backStack thay đổi->khi add 1 fragment vào
    @Override
    public void onBackStackChanged() {
        FragmentManager frm = getSupportFragmentManager();
//        Log.e("-------->", frm.getBackStackEntryCount() + "");
//        nếu trong ngăn xếp số các fragment được add vào =0 -> không còn thì set tiêu đề bằng tiêu đề hiện tại
        if (frm.getBackStackEntryCount() == 0) {
            setTitle(listProductType.get(tabCurrent).getNameProductType());
//            nếu tab hiện tại bằng 0 thì setFragmentCurrent= MainFragment
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
        if (fragment instanceof PhoneFragment) {
            setTitle("Điện thoại & máy tính bảng");
            return;
        }
        if (fragment instanceof LaptopFragment) {
            setTitle("Máy tính");
            Log.e("----->fragmentCurrent ", ": " + getTitle());
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
        if (fragment instanceof UserInfoFragment) {
            setTitle("Thông tin khách hàng");
            return;
        }
    }
}
//sư khác biệt giữa replace và add
//replace là thay thế hoàn toàn: khi chạy nó sẽ vào hàm onCreateView -> OnResume
//add: là thêm mới, các fragment vẫn đang chạy song song khi thêm add to backstack thì nó cũng sẽ không phải gọi lại các hàm trong vòng đời để dừng hđộng của fragment hiện tại lại
//khi ấn onbackpress thì ko phải dectach lần lượt từng fragment mà theo cơ chế xen kẽ
//replace: là remove hoàn toàn sau đó khi ta add to backstack thì nó sẽ ktạo lại toàn bộ tức là chạy lại các hàm như oncreateView->...->onResume
//add thì thêm addToBackStack
//replace thì k thêm addToBackStack