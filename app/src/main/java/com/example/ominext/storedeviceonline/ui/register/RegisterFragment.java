package com.example.ominext.storedeviceonline.ui.register;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import com.example.ominext.storedeviceonline.model.User;
import com.example.ominext.storedeviceonline.ui.home.HomeActivity;
import com.example.ominext.storedeviceonline.ui.notifi.NotificationFragment;
import com.example.ominext.storedeviceonline.until.CheckConnectionInternet;
import com.example.ominext.storedeviceonline.until.Server;
import com.mikepenz.actionitembadge.library.ActionItemBadge;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Ominext on 9/21/2017.
 */
//Đẩy dữ liệu lên bảng user gồm id, email, password, name, address.
public class RegisterFragment extends Fragment {

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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_register)
    public void onViewClicked() {
//        khi kích vào btn đăng kí thì kiểm tra xem các ô có còn trống không. Nếu có 1 ô còn trống thì yc nhập lại
        if (!edtConfirmPassword.getText().toString().equals(edtConfirmPassword.getText().toString())) {
            edtConfirmPassword.setText("");
            edtPassword.setText("");
            if (!edtEmail.getText().toString().equals("")
                    && !edtAccountName.getText().toString().equals("")
                    && !edtAddress.getText().toString().equals("")
                    && !edtName.getText().toString().equals("")
                    && !edtPassword.getText().toString().equals("")
                    && !edtPhone.getText().toString().equals("")) {
                if (CheckConnectionInternet.haveNetWorkConnection(getContext())) {
                    RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                    StringRequest clientInfoStringRequest = new StringRequest(Request.Method.POST, Server.urlUser, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(getContext(), "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("=========>error", error.toString());
                            Toast.makeText(getContext(), "Tài khoản gmail đã tồn tại", Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String, String> hashMap;
                            JSONArray jsonArray = new JSONArray();
                            JSONObject object = new JSONObject();
                            try {
                                object.put("id", edtPhone.getText().toString());
                                object.put("name", edtName.getText().toString());
                                object.put("address", edtAddress.getText().toString());
                                object.put("password", edtPassword.getText().toString());
                                object.put("email", edtEmail.getText().toString());
                                object.put("nameUser", edtAccountName.getText().toString());
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
                    Toast.makeText(getContext(), "Kiểm tra lại kết nối của bạn", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getContext(), "Điền đầy đủ thông tin", Toast.LENGTH_LONG).show();
            }
        }
    }
}