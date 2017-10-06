package com.example.ominext.storedeviceonline.ui.register;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ominext.storedeviceonline.R;
import com.example.ominext.storedeviceonline.model.User;
import com.example.ominext.storedeviceonline.ui.home.HomeActivity;
import com.example.ominext.storedeviceonline.ui.login.LoginFragment;
import com.example.ominext.storedeviceonline.ui.login.LoginPresenter;
import com.example.ominext.storedeviceonline.ui.login.LoginView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Ominext on 9/21/2017.
 */
//Đẩy dữ liệu lên bảng user gồm id, email, password, name, address.
public class RegisterFragment extends Fragment implements LoginView, RegisterView {

    @BindView(R.id.edt_account_name)
    EditText edtAccountName;
    @BindView(R.id.edt_email)
    EditText edtEmail;
    @BindView(R.id.edt_password)
    EditText edtPassword;
    @BindView(R.id.edt_confirm_password)
    EditText edtConfirmPassword;
    @BindView(R.id.edt_name)
    EditText edtName;
    @BindView(R.id.edt_phone)
    EditText edtPhone;
    @BindView(R.id.edt_address)
    EditText edtAddress;
    @BindView(R.id.btn_register)
    Button btnRegister;
    Unbinder unbinder;
    String phone;
    String name;
    String address;
    String password;
    String email;
    String accountName;
    String confirmPassWord;
    List<User> listUser = new ArrayList<>();
    LoginPresenter mLoginPresenter;
    RegisterPresenter mRegisterPresenter;

    public RegisterFragment() {
    }

    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Đăng kí");
        mLoginPresenter = new LoginPresenter(getContext(), this);
        mLoginPresenter.getListUser();
        mRegisterPresenter = new RegisterPresenter(getContext(), this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_register)
    public void onViewClicked() {
        phone = edtPhone.getText().toString();
        name = edtName.getText().toString();
        address = edtAddress.getText().toString();
        password = edtPassword.getText().toString();
        email = edtEmail.getText().toString().toLowerCase();
        accountName = edtAccountName.getText().toString();
        confirmPassWord = edtConfirmPassword.getText().toString();

        if (TextUtils.isEmpty(accountName)) {
            edtAccountName.setError("Nhập tên tài khoản");
            edtAccountName.requestFocus();
            return;
        }
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
        if (password.length() < 6) {
            edtPassword.setError("Mật khẩu phải lớn hơn 5 kí tự");
            edtPassword.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(confirmPassWord)) {
            edtConfirmPassword.setError("Nhập lại mật khẩu");
            edtConfirmPassword.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(name)) {
            edtName.setError("Nhập họ tên");
            edtName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            edtPhone.setError("Nhập số điện thoại");
            edtPhone.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(address)) {
            edtAddress.setError("Nhập địa chỉ");
            edtAddress.requestFocus();
            return;
        }
        if (confirmPassWord.equals(password)) {
            mRegisterPresenter.registerAccount(listUser, phone, email, name, address, password, accountName);
        } else {
            edtConfirmPassword.setText("");
            edtConfirmPassword.setError("Nhập lại mật khẩu");
            edtConfirmPassword.requestFocus();
            return;
        }
    }

    @Override
    public void getListUserSuccessfully(List<User> users) {
        listUser = users;
    }

    @Override
    public void getListUserFailed(String s) {

    }

    @Override
    public void registerSuccessfully() {
        Toast.makeText(getContext(), "Đăng kí thành công", Toast.LENGTH_SHORT).show();
        LoginFragment loginFragment = LoginFragment.newInstance();
        ((HomeActivity) getActivity()).replaceFragment(loginFragment);
    }

    @Override
    public void registerFailed(String s) {
        Toast.makeText(getContext(), "Số điện thoại hoặc tài khoản đã được sử dụng", Toast.LENGTH_SHORT).show();
    }
}