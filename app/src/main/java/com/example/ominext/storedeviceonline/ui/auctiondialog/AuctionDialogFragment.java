package com.example.ominext.storedeviceonline.ui.auctiondialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ominext.storedeviceonline.R;
import com.example.ominext.storedeviceonline.helper.ImageViewUtil;
import com.example.ominext.storedeviceonline.helper.PriceFormatUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class AuctionDialogFragment extends DialogFragment {
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

    //đẩy tên sp, tiền mặc định của sp, ảnh lên dialog
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
        Bundle bundle = getArguments();
        name.setText(bundle.getString("name"));
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
//        đồng ý trả giá cái đẩy lên server luôn
        getDialog().dismiss();
    }

}
