package com.example.ominext.storedeviceonline.ui.userinfo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ominext.storedeviceonline.R;
import com.example.ominext.storedeviceonline.ui.home.HomeActivity;
import com.example.ominext.storedeviceonline.ui.order.OrderFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class UserInfoFragment extends Fragment {
    @BindView(R.id.edt_name_user)
    EditText edtNameUser;
    @BindView(R.id.edt_phone)
    EditText edtPhone;
    @BindView(R.id.edt_address)
    EditText edtAddress;
    @BindView(R.id.btn_accept)
    Button btnAccept;
    Unbinder unbinder;
    int totalMoney = 0;

    public UserInfoFragment() {
        // Required empty public constructor
    }

    public static UserInfoFragment newInstance() {
        UserInfoFragment fragment = new UserInfoFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_info, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Thông tin khách hàng");
        Bundle bundle = getArguments();
        totalMoney = bundle.getInt("totalMoney");
        if (bundle.getInt("key") == 0) {
            edtNameUser.setText(bundle.getString("nameUser"));
            edtAddress.setText(bundle.getString("addressUser"));
            edtPhone.setText(bundle.getString("phoneUser"));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_accept)
    public void onViewClicked() {
        if ((!edtNameUser.getText().toString().isEmpty()) && (!edtAddress.getText().toString().isEmpty()) && (!edtPhone.getText().toString().isEmpty())) {
            OrderFragment fragment = OrderFragment.newInstance();
            Bundle bundle = new Bundle();
            bundle.putInt("totalMoney", totalMoney);
            bundle.putString("nameUser", edtNameUser.getText().toString());
            bundle.putString("phoneUser", edtPhone.getText().toString());
            bundle.putString("addressUser", edtAddress.getText().toString());
            fragment.setArguments(bundle);
            ((HomeActivity) getActivity()).addFragment(fragment);
        } else {
            Toast.makeText(getContext(), "Bạn cần điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        }
    }
}
