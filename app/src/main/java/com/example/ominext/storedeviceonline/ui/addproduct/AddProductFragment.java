package com.example.ominext.storedeviceonline.ui.addproduct;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ominext.storedeviceonline.R;
import com.example.ominext.storedeviceonline.helper.DatePickerDialogUtil;
import com.example.ominext.storedeviceonline.ui.home.HomeActivity;
import com.example.ominext.storedeviceonline.ui.login.LoginFragment;
import com.example.ominext.storedeviceonline.ui.main.MainFragment;
import com.example.ominext.storedeviceonline.until.CheckConnectionInternet;
import com.example.ominext.storedeviceonline.until.Server;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

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
    @BindView(R.id.cb_add_auction)
    CheckBox cbAddAuction;
    @BindView(R.id.tv_from)
    TextView tvFrom;
    @BindView(R.id.tv_to)
    TextView tvTo;
    @BindView(R.id.linear_layout_add_auction)
    LinearLayout linearLayoutAddAuction;
    private String image = null;
    private int idProductType = 3;
    private String nameProduct = "";
    private String priceProduct = "";
    private String describeProduct = "";
    private String dateFrom = "";
    private String dateTo = "";
    private int check = 0;

    public AddProductFragment() {

    }

    public static AddProductFragment newInstance() {
        AddProductFragment fragment = new AddProductFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_product, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        linearLayoutAddAuction.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.img_add_product, R.id.tv_type, R.id.btn_add_product, R.id.cb_add_auction, R.id.tv_from, R.id.tv_to})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_add_product:
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 1995);
                break;
            case R.id.tv_type:
                PopupMenu popupMenu = new PopupMenu(getContext(), tvType);
                popupMenu.getMenuInflater().inflate(R.menu.menu_popup_type, popupMenu.getMenu());
                for (int i = 2; i < HomeActivity.listProductType.size() - 2; i++) {
                    popupMenu.getMenu().add(i, i, i, HomeActivity.listProductType.get(i).getNameProductType());
                }
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        tvType.setText(menuItem.getTitle());
                        idProductType = menuItem.getItemId() - 1;
                        return true;
                    }
                });
                popupMenu.show();
                break;
            case R.id.btn_add_product:
                if (!HomeActivity.listProductType.get(0).getNameProductType().equals("Đăng nhập")) {
                    nameProduct = edtNameProduct.getText().toString().trim();
                    priceProduct = edtPrice.getText().toString().trim();
                    describeProduct = edtDescribe.getText().toString().trim();
                    if (TextUtils.isEmpty(nameProduct)) {
                        edtNameProduct.setError("Nhập tên sản phẩm");
                        edtNameProduct.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(String.valueOf(describeProduct))) {
                        edtDescribe.setError("Nhập mô tả sản phẩm");
                        edtDescribe.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(priceProduct)) {
                        edtPrice.setError("Nhập giá sản phẩm");
                        edtPrice.requestFocus();
                        return;
                    }
                    if (image != null) {
                        if (CheckConnectionInternet.haveNetWorkConnection(getContext())) {
                            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                            StringRequest orderProductStringRequest = new StringRequest(Request.Method.POST, Server.urlPostProduct, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.e("============>", response);
                                    Toast.makeText(getContext(), "Đăng mặt hàng thành công", Toast.LENGTH_SHORT).show();
                                    Fragment fragment = MainFragment.newInstance();
                                    ((HomeActivity) getActivity()).addFragment(fragment);
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.e("=========>error", error.toString());
                                    Toast.makeText(getContext(), "Mặt hàng chưa được đăng. Không thể kết nối được với máy chủ", Toast.LENGTH_SHORT).show();
                                }
                            }) {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    HashMap<String, String> hashMap = new HashMap<>();
                                    hashMap.put("nameProduct", nameProduct);
                                    hashMap.put("priceProduct", priceProduct);
                                    hashMap.put("imageProduct", image);
                                    hashMap.put("describeProduct", describeProduct);
                                    hashMap.put("IdProductType", idProductType + "");
                                    hashMap.put("idUser", LoginFragment.user.getId() + "");
                                    hashMap.put("auction", null);
                                    hashMap.put("dateStart", dateFrom);
                                    hashMap.put("dateStop", dateTo);
                                    if (check == 1) {
                                        hashMap.put("auction", 1 + "");
                                    }
                                    return hashMap;
                                }
                            };
                            requestQueue.add(orderProductStringRequest);
                        } else {
                            Toast.makeText(getContext(), "Mặt hàng chưa được đăng. Kiểm tra lại kết nối", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "Bạn chưa thêm ảnh cho mặt hàng", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.tv_from:
                android.app.DialogFragment FromFragment;
                FromFragment = new DatePickerDialogUtil(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar cal = GregorianCalendar.getInstance();
                        cal.set(year, monthOfYear, dayOfMonth);
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        dateFrom = formatter.format(cal.getTime());
                        tvFrom.setText(dateFrom);
                        formatter = new SimpleDateFormat("yyyy/MM/dd");
                        dateFrom = formatter.format(cal.getTime());
                    }
                }, getContext());
                FromFragment.show(getActivity().getFragmentManager(), "FromDate");
                break;
            case R.id.tv_to:
                android.app.DialogFragment ToFragment;
                ToFragment = new DatePickerDialogUtil(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar cal = GregorianCalendar.getInstance();
                        cal.set(year, monthOfYear, dayOfMonth);
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        dateTo = formatter.format(cal.getTime());
                        tvTo.setText(dateTo);
                        formatter = new SimpleDateFormat("yyyy/MM/dd");
                        dateTo = formatter.format(cal.getTime());
                    }
                }, getContext());
                ToFragment.show(getActivity().getFragmentManager(), "ToDate");
                break;
            case R.id.cb_add_auction:
                if (check == 0) {
                    linearLayoutAddAuction.setVisibility(View.VISIBLE);
                    check = 1;
                } else {
                    linearLayoutAddAuction.setVisibility(View.GONE);
                    check = 0;
                }
                break;
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1995 && resultCode == Activity.RESULT_OK) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            byte b[];
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            b = bos.toByteArray();
            image = Base64.encodeToString(b, Base64.DEFAULT);
            imgAddProduct.setImageBitmap(bitmap);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
