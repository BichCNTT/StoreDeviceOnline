package com.example.ominext.store.ui.login;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ominext.store.R;
import com.example.ominext.store.helper.KeyboardUtil;
import com.example.ominext.store.model.Account;
import com.example.ominext.store.model.User;
import com.example.ominext.store.ui.home.HomeActivity;
import com.example.ominext.store.ui.main.MainFragment;
import com.example.ominext.store.ui.mystore.MyStoreFragment;
import com.example.ominext.store.ui.register.RegisterFragment;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.realm.Realm;

//sau khi đăng nhập xong lấy thông tin tài khoản lưu ra file text->
public class LoginFragment extends Fragment implements LoginView {
    public LoginPresenter mLoginPresenter;
    @BindView(R.id.edt_email)
    EditText edtEmail;
    @BindView(R.id.edt_password)
    EditText edtPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    Unbinder unbinder;
    List<User> users = new ArrayList<>();
    String email;
    String password;
    @BindView(R.id.cb_show_pass_word)
    CheckBox cbShowPassWord;
    @BindView(R.id.btn_register)
    Button btnRegister;
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
    @BindView(R.id.btn_logout)
    Button btnLogout;
    @BindView(R.id.linear_layout_user_detail)
    LinearLayout linearLayoutUserDetail;
    @BindView(R.id.linear_layout_login)
    LinearLayout linearLayoutLogin;
    @BindView(R.id.btn_mystore)
    Button btnMyStore;
    private boolean mCheck = false;
    public static User user;
    private Realm mRealm;

    public LoginFragment() {
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (HomeActivity.listProductType.get(0).getNameProductType().equals("Đăng nhập")) {
//            mRealm = Realm.getInstance(getContext());
//            Account account = mRealm.createObject(Account.class);
//            edtEmail.setText(account.getEmail().toString().trim());
//            edtPassword.setText(account.getPassword().toString().trim());
            mLoginPresenter = new LoginPresenter(getContext(), this);
            mLoginPresenter.getListUser();
            linearLayoutUserDetail.setVisibility(View.GONE);
            linearLayoutLogin.setVisibility(View.VISIBLE);
        } else {
            linearLayoutUserDetail.setVisibility(View.VISIBLE);
            linearLayoutLogin.setVisibility(View.GONE);
            init();
        }
    }

    public void init() {
        mLoginPresenter = new LoginPresenter(getContext(), this);
        mLoginPresenter.getListUser();
        tvNameUser.setText(user.getName());
        tvPhone.setText(user.getId() + "");
        tvAddress.setText(user.getAddress());
        tvAccountName.setText(user.getNameUser());
        tvEmail.setText(user.getEmail());
        tvPassWord.setText(user.getPassword());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void getListUserSuccessfully(final List<User> users) {
        this.users.clear();
        this.users.addAll(users);
    }

    @Override
    public void getListUserFailed(String s) {
        Toast.makeText(getContext(), "Không thể kết nối được với máy chủ", Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.cb_show_pass_word, R.id.btn_login, R.id.btn_register, R.id.btn_logout, R.id.btn_mystore})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cb_show_pass_word:
                if (!mCheck) {
                    edtPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                    mCheck = true;
                } else {
                    edtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    mCheck = false;
                }
                break;
            case R.id.btn_login:
                email = edtEmail.getText().toString().toLowerCase();
                password = edtPassword.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    edtEmail.setError("Nhập vào một email");
                    edtEmail.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    edtEmail.setError("Email của bạn không đúng");
                    edtEmail.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    edtPassword.setError("Nhập mật khẩu");
                    edtPassword.requestFocus();
                    return;
                }
                int check = 0;
                for (int i = 0; i < users.size(); i++) {
                    if (email.equals(users.get(i).getEmail()) && password.equals(users.get(i).getPassword())) {
                        user = users.get(i);
                        Toast.makeText(getContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
//                        mRealm = Realm.getInstance(getContext());
//                        mRealm.beginTransaction();
//                        Account account = mRealm.createObject(Account.class);
//                        account.setEmail(email);
//                        account.setPassword(password);
//                        mRealm.commitTransaction();
                        KeyboardUtil.hideKeyBoard(view, getActivity());
                        MainFragment fragment = MainFragment.newInstance();
                        ((HomeActivity) getActivity()).addFragment(fragment);
                        HomeActivity.listProductType.get(0).setNameProductType("Hi! " + users.get(i).getNameUser());
                        HomeActivity.productTypeAdapter.notifyDataSetChanged();
                        check = 1;
                        mLoginPresenter.updateAccount(user.getId(), FirebaseInstanceId.getInstance().getToken().toString());
                        break;
                    }
                }
                if (check == 0) {
                    Toast.makeText(getContext(), "Tên tài khoản hoặc mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_logout:
                Toast.makeText(getContext(), "Đã đăng xuất", Toast.LENGTH_SHORT).show();
                Fragment fragment = MainFragment.newInstance();
                ((HomeActivity) getActivity()).addFragment(fragment);
                HomeActivity.listProductType.get(0).setNameProductType("Đăng nhập");
                HomeActivity.productTypeAdapter.notifyDataSetChanged();
                mLoginPresenter.updateAccount(user.getId(), "");
                break;
            case R.id.btn_register:
                fragment = new RegisterFragment();
                ((HomeActivity) getActivity()).replaceFragment(fragment);
                break;
            case R.id.btn_mystore:
                fragment = new MyStoreFragment();
                ((HomeActivity) getActivity()).replaceFragment(fragment);
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}