package com.example.ominext.storedeviceonline.ui.login;

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
import android.widget.Toast;

import com.example.ominext.storedeviceonline.R;
import com.example.ominext.storedeviceonline.helper.KeyboardUtil;
import com.example.ominext.storedeviceonline.model.User;
import com.example.ominext.storedeviceonline.ui.home.HomeActivity;
import com.example.ominext.storedeviceonline.ui.main.MainFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Ominext on 9/21/2017.
 */
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
    private boolean mCheck = false;
    public static User user = new User();

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
        mLoginPresenter = new LoginPresenter(getContext(), this);
        mLoginPresenter.getListUser();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void getListUserSuccessfully(final List<User> users) {
        this.users = users;
    }

    @Override
    public void getListUserFailed(String s) {
        Toast.makeText(getContext(), "Không thể kết nối được với máy chủ", Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.cb_show_pass_word, R.id.btn_login})
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
                email = edtEmail.getText().toString();
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
                        HomeActivity.listProductType.get(0).setNameProductType("Hi! " + users.get(i).getNameUser());
                        HomeActivity.productTypeAdapter.notifyDataSetChanged();
                        KeyboardUtil.hideKeyBoard(view, getActivity());
                        MainFragment fragment = MainFragment.newInstance();
                        ((HomeActivity) getActivity()).addFragment(fragment);
                        getActivity().setTitle("Trang chủ");
                        check = 1;
                        break;
                    }
                }
                if (check == 0) {
                    Toast.makeText(getContext(), "Tên tài khoản hoặc mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}