package com.example.ominext.store.ui.auctiondialog;

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

import com.example.ominext.store.R;
import com.example.ominext.store.helper.ImageViewUtil;
import com.example.ominext.store.helper.PriceFormatUtil;
import com.example.ominext.store.model.UserAuction;
import com.example.ominext.store.ui.auction.AuctionFragment;
import com.example.ominext.store.ui.auction.AuctionPresenter;
import com.example.ominext.store.ui.auction.AuctionView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class AuctionDialogFragment extends DialogFragment implements AuctionDialogView, AuctionView {
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
    private int mPrice;

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
        mPrice = bundle.getInt("price");
        PriceFormatUtil.priceFormat(price, mPrice);
        ImageViewUtil.loadImg(getContext(), bundle.getString("image"), image);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_accept)
    public void onViewClicked() {
        if (!pay.getText().toString().isEmpty() && (Integer.parseInt(pay.getText().toString()) >= mPrice) && !number.getText().toString().isEmpty() && (Integer.parseInt(number.getText().toString()) > 0)) {
            mPresenter.postUserAuction(id, Integer.parseInt(pay.getText().toString()), Integer.parseInt(number.getText().toString()));
            Toast.makeText(getContext(), "Đấu giá thành công", Toast.LENGTH_SHORT).toString();
            getDialog().dismiss();
            AuctionPresenter mAuctionPresenter = new AuctionPresenter(getContext(), this);
            mAuctionPresenter.getUserAuction();
        } else {
            String pay = this.pay.getText().toString();
            String number = this.number.getText().toString();
            if (TextUtils.isEmpty(pay)) {
                this.pay.setError("Nhập giá");
                this.pay.requestFocus();
                return;
            }
            if (Integer.parseInt(this.pay.getText().toString()) < mPrice) {
                this.pay.setError("Giá trả phải lớn hơn hoặc bằng giá hiện tại");
                this.pay.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(number)) {
                this.number.setError("Nhập số lượng");
                this.number.requestFocus();
                return;
            }
            if (!(Integer.parseInt(this.number.getText().toString()) > 0)) {
                this.number.setError("Số lượng phải lớn hơn 0");
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

    @Override
    public void getUserAuctionSuccessfully(List<UserAuction> userAuctionList) {
        AuctionFragment.mUserAuctionList.clear();
        for (int i = 0; i < userAuctionList.size(); i++) {
            if (id == userAuctionList.get(i).getIdProduct()) {
                AuctionFragment.mUserAuctionList.add(userAuctionList.get(i));
            }
        }
        AuctionFragment.mAuctionAdapter.notifyDataSetChanged();
    }

    @Override
    public void getUserAuctionFailed(String s) {
    }
}
