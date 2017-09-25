package com.example.ominext.storedeviceonline.ui.login;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ominext.storedeviceonline.R;
import com.example.ominext.storedeviceonline.model.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Ominext on 9/21/2017.
 */
//kiểm tra xem có tài khoản đó tồn tại không, nếu không thì yêu cầu đăng nhập lại hoặc đăng kí tài khoản
public class LoginFragment extends Fragment implements LoginView {
    LoginPresenter mLoginPresenter;
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

    }

    @OnClick(R.id.btn_login)
    public void onViewClicked() {
        email = edtEmail.getText().toString();
        password = edtPassword.getText().toString();

        int check = 0;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getEmail().equals(email)&& users.get(i).getPassword().equals(password)) {
                Toast.makeText(getContext(), "Thành công", Toast.LENGTH_SHORT).show();
                check = 1;
                break;
            }
        }
        if (check == 0) {
            Toast.makeText(getContext(), "Tên tài khoản và mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
        }
    }
}