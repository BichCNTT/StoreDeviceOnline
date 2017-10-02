package com.example.ominext.storedeviceonline.ui.addproduct;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ominext.storedeviceonline.R;
import com.example.ominext.storedeviceonline.ui.home.HomeActivity;
import com.example.ominext.storedeviceonline.ui.main.MainFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class AddProductFragment extends Fragment {
    @BindView(R.id.img_add_product)
    ImageView imgAddProduct;
    @BindView(R.id.edt_name_product)
    EditText edtNameProduct;
    @BindView(R.id.edt_describe)
    EditText edtDescribe;
    @BindView(R.id.edt_price)
    EditText edtPrice;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.btn_add_product)
    Button btnAddProduct;
    Unbinder unbinder;

    public AddProductFragment() {

    }

    public static AddProductFragment newInstance() {
        AddProductFragment fragment = new AddProductFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_product, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Lựa chọn loại sản phẩm");
        for (int i = 1; i < HomeActivity.listProductType.size() - 1; i++) {
            menu.add(i, getId(), i, HomeActivity.listProductType.get(i).getNameProductType());
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int i = 1;
        while (i < HomeActivity.listProductType.size() - 1) {
            tvType.setText(HomeActivity.listProductType.get(i).getNameProductType());
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.img_add_product, R.id.tv_type, R.id.btn_add_product})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_add_product:
//                lấy ảnh từ thư viện hoặc ảnh chụp. vđề nảy sinh khi chuyển từ dạng file ảnh hoặc dạng http sang dạng ảnh để hthị lên máy
                break;
            case R.id.tv_type:
//                thêm menu lựa chọn các loại sản phẩm
                break;
            case R.id.btn_add_product:
//                Đăng mặt hàng. Lưu file ảnh lên server
                break;
        }
    }
}
