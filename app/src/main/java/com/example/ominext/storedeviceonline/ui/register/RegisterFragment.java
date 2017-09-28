package com.example.ominext.storedeviceonline.ui.register;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ominext.storedeviceonline.R;
import com.example.ominext.storedeviceonline.helper.KeyboardUtil;
import com.example.ominext.storedeviceonline.model.User;
import com.example.ominext.storedeviceonline.ui.home.HomeActivity;
import com.example.ominext.storedeviceonline.ui.login.LoginPresenter;
import com.example.ominext.storedeviceonline.ui.login.LoginView;
import com.example.ominext.storedeviceonline.ui.main.MainFragment;
import com.example.ominext.storedeviceonline.ui.notifi.NotificationFragment;
import com.example.ominext.storedeviceonline.until.CheckConnectionInternet;
import com.example.ominext.storedeviceonline.until.Server;
import com.mikepenz.actionitembadge.library.ActionItemBadge;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Ominext on 9/21/2017.
 */
//Đẩy dữ liệu lên bảng user gồm id, email, password, name, address.
public class RegisterFragment extends Fragment implements LoginView {

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
    LoginPresenter mPresenter;

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
        mPresenter = new LoginPresenter(getContext(), this);
        mPresenter.getListUser();
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
        email = edtEmail.getText().toString();
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
            if (CheckConnectionInternet.haveNetWorkConnection(getContext())) {
                int check = 0;
                for (int i = 0; i < listUser.size(); i++) {
                    if (String.valueOf(listUser.get(i).getId()) == phone || listUser.get(i).getEmail().equals(email)) {
                        check = 1;
                    }
                }
                if (check != 1) {
                    RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                    StringRequest clientInfoStringRequest = new StringRequest(Request.Method.POST, Server.urlUser, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(getContext(), "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                            MainFragment mainFragment = MainFragment.newInstance();
                            ((HomeActivity) getActivity()).addFragment(mainFragment);
                            getActivity().setTitle("Trang chủ");
                            KeyboardUtil.hideKeyBoard(getView(), getActivity());
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("=========>error", error.toString());
                            Toast.makeText(getContext(), "Số điện thoại hoặc tài khoản đã được sử dụng", Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String, String> hashMap;
                            JSONArray jsonArray = new JSONArray();
                            JSONObject object = new JSONObject();
                            try {
                                object.put("id", phone);
                                object.put("name", name);
                                object.put("address", address);
                                object.put("password", password);
                                object.put("email", email);
                                object.put("nameUser", accountName);
//                                object.put("avartar",null);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            jsonArray.put(object);
                            hashMap = new HashMap<>();
                            hashMap.put("json", jsonArray.toString());
                            return hashMap;
                        }
                    };
                    requestQueue.add(clientInfoStringRequest);
                } else {
                    Toast.makeText(getContext(), "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(getContext(), "Kiểm tra lại kết nối của bạn", Toast.LENGTH_LONG).show();
            }
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
}