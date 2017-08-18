package com.example.ominext.storedeviceonline.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ominext.storedeviceonline.R;
import com.example.ominext.storedeviceonline.helper.ImageViewUtil;
import com.example.ominext.storedeviceonline.model.ProductType;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by Ominext on 8/17/2017.
 */

public class ProductTypeAdapter extends BaseAdapter {
    ArrayList<ProductType> productTypeArrayList;
    Context context;

    public ProductTypeAdapter(ArrayList<ProductType> productTypeArrayList, Context context) {
        this.productTypeArrayList = productTypeArrayList;
        this.context = context;
    }

    //lấy số lượng pt có trong list kiểu "loại sản phẩm"
    @Override
    public int getCount() {
        return productTypeArrayList.size();
    }

    // lấy ra đối tượng kiểu object ở vị trí thứ i
    @Override
    public Object getItem(int i) {
        return productTypeArrayList.get(i);
    }

    // lấy ra id của mục
    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder {
        ImageView imgProductType;
        TextView tvProductType;
    }

    // lấy ra view
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
//        nếu ko giữ view thì ktạo 1 đtượng kiểu view holder, set view bằng 1 dòng của listview loại sp. giữ gtrị của tv và img của list view hiện tại
        if (view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row_listview_producttype, null);
            holder.tvProductType = (TextView) view.findViewById(R.id.tv_product_type);
            holder.imgProductType = (ImageView) view.findViewById(R.id.img_product_type);
//            set tag để xoay màn hình k bị mất view
            view.setTag(holder);
        } else {
//            ngược lại nếu giữ view thì lấy ra
            holder = (ViewHolder) view.getTag();

        }
//        lấy ra loại sản phẩm thứ i set giá trị cho chúng, dùng picasso để chuyển từ link ảnh sang ảnh, rồi trả kq về view (dòng)
        ProductType productType = (ProductType) getItem(i);
        holder.tvProductType.setText(productType.getNameProductType());
        ImageViewUtil.loadImg(context,productType.getImageProductType(),holder.imgProductType);
        return view;
    }


}
