package com.example.ominext.storedeviceonline.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.ominext.storedeviceonline.R;
import com.example.ominext.storedeviceonline.adapter.NewProductAdapter;
import com.example.ominext.storedeviceonline.adapter.ProductTypeAdapter;
import com.example.ominext.storedeviceonline.data.model.NewProduct;
import com.example.ominext.storedeviceonline.data.model.ProductType;
import com.example.ominext.storedeviceonline.helper.ImageViewUtil;
import com.example.ominext.storedeviceonline.until.CheckConnection;
import com.example.ominext.storedeviceonline.until.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

//fragment chứa giao diện chính <- giao diện lúc mở máy lên
public class MainFragment extends Fragment {

    @BindView(R.id.view_flipper)
    ViewFlipper viewFlipper;
    @BindView(R.id.view_main)
    RecyclerView viewMain;
    Unbinder unbinder;

    ArrayList<NewProduct> listNewProduct;
    NewProductAdapter productAdapter;

    int idProduct = 0;
    String nameProduct = "";
    int priceProduct = 0;
    String imageProduct = "";
    String describeProduct = "";
    int idProductType = 0;

    public MainFragment() {

    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }
    private void init() {
        if (CheckConnection.haveNetWorkConnection(getContext())) {
            ActionViewFlipper();
            getNewProduct();
        } else {
            CheckConnection.showToast(getContext(), "Haven't internet");
            Log.e("==============>", "Haven't internet");

        }

        listNewProduct = new ArrayList<>();
        productAdapter = new NewProductAdapter(getContext(), listNewProduct);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        viewMain.setLayoutManager(layoutManager);
        viewMain.setHasFixedSize(true);
        viewMain.setAdapter(productAdapter);
        productAdapter.notifyDataSetChanged();

    }
        private void getNewProduct() {
        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Server.urlNewProduct, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            Log.e("==============>", jsonObject.toString() + "");
                            idProductType = jsonObject.getInt("IdProductType");
                            Log.e("==============>", idProductType + "");

                            idProduct = jsonObject.getInt("IdProduct");
                            Log.e("==============>", idProduct + "");

                            nameProduct = jsonObject.getString("nameProduct");
                            Log.e("==============>", nameProduct + "");

                            priceProduct = jsonObject.getInt("priceProduct");
                            Log.e("==============>", priceProduct + "");
                            imageProduct = jsonObject.getString("imageProduct");

                            Log.e("==============>", imageProduct + "");
                            describeProduct = jsonObject.getString("describeProduct");
                            Log.e("==============>", describeProduct + "");

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
                CheckConnection.showToast(getContext(), error.toString());
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
            ImageView imageView = new ImageView(getContext());
            ImageViewUtil.loadImg(getContext(), urlImageList.get(i), imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_right);
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
