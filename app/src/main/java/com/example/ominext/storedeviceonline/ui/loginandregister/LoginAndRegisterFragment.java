package com.example.ominext.storedeviceonline.ui.loginandregister;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ominext.storedeviceonline.R;
import com.example.ominext.storedeviceonline.helper.KeyboardUtil;
import com.example.ominext.storedeviceonline.model.User;
import com.example.ominext.storedeviceonline.ui.home.HomeActivity;
import com.example.ominext.storedeviceonline.ui.login.LoginFragment;
import com.example.ominext.storedeviceonline.ui.login.LoginPresenter;
import com.example.ominext.storedeviceonline.ui.login.LoginView;
import com.example.ominext.storedeviceonline.ui.main.MainFragment;
import com.example.ominext.storedeviceonline.ui.register.RegisterFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Ominext on 9/21/2017.
 */
//Hiển thị thông tin tài khoản
//    Họ tên, số điện thoại, địa chỉ giao hàng, tên tài khoản, email, mật khẩu. Nếu tên kp đăng xuất thì hiện view lên. Db lấy ở csdl
public class LoginAndRegisterFragment extends Fragment implements LoginView {
    @BindView(R.id.tabs)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    Unbinder mUnbinder;
    @BindView(R.id.tv_name_user)
    TextView tvNameUser;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_account_name)
    TextView tvAccountName;
    @BindView(R.id.tv_email)
    TextView tvEmail;
    @BindView(R.id.tv_pass_word)
    TextView tvPassWord;
    @BindView(R.id.linear_layout_user_detail)
    LinearLayout linearLayoutUserDetail;
    @BindView(R.id.linear_layout_login_and_register)
    LinearLayout linearLayoutLoginAndRegister;
    @BindView(R.id.btn_logout)
    Button btnLogout;
    private LoginPresenter mLoginPresenter;
    private List<User> mListUser = new ArrayList<>();
    private User user = new User();

    public LoginAndRegisterFragment() {

    }

    public static LoginAndRegisterFragment newInstance() {
        LoginAndRegisterFragment fragment = new LoginAndRegisterFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_register, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (HomeActivity.listProductType.get(0).getNameProductType().equals("Đăng nhập")) {
            linearLayoutUserDetail.setVisibility(View.GONE);
            linearLayoutLoginAndRegister.setVisibility(View.VISIBLE);
            setupViewPager(mViewPager);
            mTabLayout.setupWithViewPager(mViewPager);
        } else {
            mLoginPresenter = new LoginPresenter(getContext(), this);
            mLoginPresenter.getListUser();
            linearLayoutLoginAndRegister.setVisibility(View.GONE);
            linearLayoutUserDetail.setVisibility(View.VISIBLE);
            user = LoginFragment.user;
            tvNameUser.setText(user.getName());
            tvPhone.setText(user.getId() + "");
            tvAddress.setText(user.getAddress());
            tvAccountName.setText(user.getNameUser());
            tvEmail.setText(user.getEmail());
            tvPassWord.setText(user.getPassword());
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter mAdapter = new ViewPagerAdapter(getFragmentManager());
        mAdapter.addFragment(new LoginFragment());
        mAdapter.addFragment(new RegisterFragment());
        viewPager.setAdapter(mAdapter);
        viewPager.setTag(mAdapter);
    }

    @Override
    public void getListUserSuccessfully(List<User> users) {
        this.mListUser = users;
    }

    @Override
    public void getListUserFailed(String s) {

    }

    @OnClick(R.id.btn_logout)
    public void onViewClicked() {
        Toast.makeText(getContext(), "Đã đăng xuất", Toast.LENGTH_SHORT).show();
        HomeActivity.listProductType.get(0).setNameProductType("Đăng nhập");
        HomeActivity.productTypeAdapter.notifyDataSetChanged();
        MainFragment fragment = MainFragment.newInstance();
        ((HomeActivity) getActivity()).replaceFragment(fragment);
        getActivity().setTitle("Trang chủ");
    }

    class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private String tabTitles[] = new String[]{"Đăng nhập", "Đăng kí"};

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment) {
            mFragmentList.add(fragment);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
