package com.example.ominext.storedeviceonline.ui.main;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.example.ominext.storedeviceonline.helper.ImageViewUtil;
import com.example.ominext.storedeviceonline.listener.OnItemClickListener;
import com.example.ominext.storedeviceonline.model.Product;
import com.example.ominext.storedeviceonline.ui.detail.DetailProductFragment;
import com.example.ominext.storedeviceonline.ui.home.HomeActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

//khi mà refresh thì gọi lại cái page đầu tiên trong loadmore. Viết lại loadmore. Sửa api, sửa ở trong
//fragment chứa giao diện chính <- giao diện lúc mở máy lên
public class MainFragment extends Fragment implements MainFragmentView, OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.view_flipper)
    ViewFlipper viewFlipper;
    @BindView(R.id.view_main)
    RecyclerView mViewMain;
    Unbinder mUnbinder;

    ArrayList<Product> mListProduct = new ArrayList<>();
    NewProductAdapter mProductAdapter;
    MainFragmentPresenter mPresenter;
    @BindView(R.id.swipe_refresh_layout_main)
    SwipeRefreshLayout mSwipeRefreshLayoutMain;

    public MainFragment() {

    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    private void init() {
        getActivity().setTitle("Trang chủ");
        mProductAdapter = new NewProductAdapter(getContext(), mListProduct);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(MainFragment.this.getContext(), 2);
        mViewMain.setLayoutManager(layoutManager);
        mViewMain.setHasFixedSize(true);
        mViewMain.setAdapter(mProductAdapter);
        mProductAdapter.setClickListener(this);
        mPresenter = new MainFragmentPresenter(MainFragment.this.getContext(), this);
        mSwipeRefreshLayoutMain.setOnRefreshListener(this);
        mSwipeRefreshLayoutMain.post(new Runnable() {
                                         @Override
                                         public void run() {
                                             mSwipeRefreshLayoutMain.setRefreshing(true);
                                             refreshContent();
                                         }
                                     }
        );
    }

    private void refreshContent() {
        mSwipeRefreshLayoutMain.setRefreshing(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ActionViewFlipper();
                mPresenter.getListProduct();
                mSwipeRefreshLayoutMain.setRefreshing(false);
            }
        }, 1000);
    }

    //tao hinh anh di chuyen (chay quang cao) va lam mo 1 danh sach anh cho truoc
    public void ActionViewFlipper() {
        ArrayList<String> urlImageList = new ArrayList<>();
        urlImageList.add("https://i.ytimg.com/vi/W6banMHMwq4/maxresdefault.jpg");
        urlImageList.add("http://sangotunhienpbs.com/wp-content/uploads/2015/12/31.jpg");
        urlImageList.add("http://2sao.vietnamnetjsc.vn/2016/06/25/09/09/streetstylegioitre1.jpg");
        urlImageList.add("http://trangsuclopa.com/uploads/advertise/BANNER2s.jpg");
        urlImageList.add("https://i.homeadore.com/2012/11/18.jpg");
        for (int i = 0; i < urlImageList.size(); i++) {
            ImageView imageView = new ImageView(getContext());
            ImageViewUtil.loadImg(getContext(), urlImageList.get(i), imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setAutoStart(true);
        viewFlipper.setFlipInterval(5000);
        viewFlipper.startFlipping();
        Animation animation_slide_in = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_left);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_right);
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);
    }

    @Override
    public void onItemClick(View view, int position) {
        DetailProductFragment fragment = DetailProductFragment.newInstance();
        Bundle bundle = new Bundle();
        Product product = mListProduct.get(position);
        bundle.putInt("id", product.getIdProduct());
        bundle.putString("name", product.getNameProduct());
        bundle.putInt("price", product.getPriceProduct());
        bundle.putString("describe", product.getDescribeProduct());
        bundle.putString("image", product.getImageProduct());
        fragment.setArguments(bundle);
        ((HomeActivity) getActivity()).addFragment(fragment);
    }

    @Override
    public void getListProductSuccess(ArrayList<Product> products) {
        mListProduct.clear();
        mListProduct.addAll(products);
        mProductAdapter.notifyDataSetChanged();
    }

    @Override
    public void getListProductFailed(String s) {
        Toast.makeText(getContext(), "Lỗi tải trang", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        refreshContent();
    }
}
