package com.example.ominext.storedeviceonline.ui.auctiondialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ominext.storedeviceonline.R;
import com.example.ominext.storedeviceonline.helper.ImageViewUtil;
import com.example.ominext.storedeviceonline.helper.PriceFormatUtil;
import com.example.ominext.storedeviceonline.ui.auction.AuctionAdapter;
import com.example.ominext.storedeviceonline.ui.auction.AuctionFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class AuctionDialogFragment extends DialogFragment implements AuctionDialogView {
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.btn_accept)
    Button btnAccept;
    Unbinder unbinder;
    @BindView(R.id.pay)
    EditText pay;
    AuctionDialogPresenter mPresenter;
    int id;
    @BindView(R.id.number)
    EditText number;

    public AuctionDialogFragment() {
    }

    public static AuctionDialogFragment newInstance() {
        AuctionDialogFragment fragment = new AuctionDialogFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_auction_dialog, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    public void init() {
        mPresenter = new AuctionDialogPresenter(getContext(), this);
        Bundle bundle = getArguments();
        name.setText(bundle.getString("name"));
        id = bundle.getInt("id");
        PriceFormatUtil.priceFormat(price, bundle.getInt("price"));
        ImageViewUtil.loadImg(getContext(), bundle.getString("image"), image);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_accept)
    public void onViewClicked() {
        if (!pay.getText().toString().isEmpty() && !number.getText().toString().isEmpty()) {
            mPresenter.postUserAuction(id, Integer.parseInt(pay.getText().toString()), Integer.parseInt(number.getText().toString()));
            Toast.makeText(getContext(), "Đấu giá thành công", Toast.LENGTH_SHORT).toString();
            getDialog().dismiss();

        } else {
            String pay = this.pay.getText().toString();
            String number = this.number.getText().toString();
            if (TextUtils.isEmpty(pay)) {
                this.pay.setError("Nhập giá");
                this.pay.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(number)) {
                this.number.setError("Nhập giá");
                this.number.requestFocus();
                return;
            }
        }
    }

    @Override
    public void postUserAuctionSuccessfully() {

    }

    @Override
    public void postUserAuctionFailed(String s) {
        Toast.makeText(getContext(), "Đấu giá thất bại. Không thể kết nối được với máy chủ", Toast.LENGTH_SHORT).toString();
    }
}