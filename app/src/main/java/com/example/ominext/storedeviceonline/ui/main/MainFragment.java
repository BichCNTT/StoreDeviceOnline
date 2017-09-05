package com.example.ominext.storedeviceonline.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.ominext.storedeviceonline.R;
import com.example.ominext.storedeviceonline.model.Product;
import com.example.ominext.storedeviceonline.helper.ImageViewUtil;
import com.example.ominext.storedeviceonline.listener.OnItemClickListener;
import com.example.ominext.storedeviceonline.ui.detail.DetailProductFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

//fragment chứa giao diện chính <- giao diện lúc mở máy lên
public class MainFragment extends Fragment implements MainFragmentView, OnItemClickListener {

    @BindView(R.id.view_flipper)
    ViewFlipper viewFlipper;
    @BindView(R.id.view_main)
    RecyclerView viewMain;
    Unbinder unbinder;

    ArrayList<Product> listProduct = new ArrayList<>();
    NewProductAdapter productAdapter;

    MainFragmentPresenter mPresenter;

    public MainFragment() {

    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
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

    private void init() {
        mPresenter = new MainFragmentPresenter(MainFragment.this.getContext(), this);
        mPresenter.getListProduct();
        productAdapter = new NewProductAdapter(getContext(), listProduct);
        ActionViewFlipper();
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(MainFragment.this.getContext(), 2);
        viewMain.setLayoutManager(layoutManager);
        viewMain.setHasFixedSize(true);
        viewMain.setAdapter(productAdapter);
        productAdapter.setClickListener(this);
        productAdapter.notifyDataSetChanged();
    }
    //tao hinh anh di chuyen (chay quang cao) va lam mo 1 danh sach anh cho truoc
    public void ActionViewFlipper() {
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
        viewFlipper.setFlipInterval(500);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_right);
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);
    }

    @Override
    public void onItemClick(View view, int position) {
        DetailProductFragment fragment = DetailProductFragment.newInstance();
        Bundle bundle = new Bundle();
        Product product = listProduct.get(position);
        bundle.putString("name", product.getNameProduct());
        bundle.putInt("price", product.getPriceProduct());
        bundle.putString("describe", product.getDescribeProduct());
        bundle.putString("image", product.getImageProduct());
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    @Override
    public void getListProductSuccess(ArrayList<Product> products) {
        listProduct = products;
        productAdapter = new NewProductAdapter(getContext(), listProduct);
        viewMain.setAdapter(productAdapter);
        productAdapter.setClickListener(this);
        productAdapter.notifyDataSetChanged();
    }

    @Override
    public void getListProductFailed(String s) {
        Toast.makeText(getContext(), "Lỗi tải trang", Toast.LENGTH_SHORT).show();
    }
}
