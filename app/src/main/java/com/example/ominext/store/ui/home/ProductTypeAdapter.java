package com.example.ominext.store.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ominext.store.R;
import com.example.ominext.store.helper.ImageViewUtil;
import com.example.ominext.store.model.ProductType;

import java.util.List;

public class ProductTypeAdapter extends BaseAdapter {
    List<ProductType> productTypeArrayList;
    Context context;

    public ProductTypeAdapter(List<ProductType> productTypeArrayList, Context context) {
        this.productTypeArrayList = productTypeArrayList;
        this.context = context;
    }

    //  lấy số lượng pt có trong list kiểu "loại sản phẩm"
    @Override
    public int getCount() {
        return productTypeArrayList.size();
    }

    //  lấy ra đối tượng kiểu object ở vị trí thứ i
    @Override
    public Object getItem(int i) {
        return productTypeArrayList.get(i);
    }

    //  lấy ra id của mục
    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder {
        ImageView imgProductType;
        TextView tvProductType;
    }

    //  lấy ra HomeView
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        //   nếu ko giữ HomeView thì ktạo 1 đtượng kiểu HomeView holder, set HomeView bằng 1 dòng của listview loại sp. giữ gtrị của tv và img của list HomeView hiện tại
        if (view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row_listview_producttype, null);
            holder.tvProductType = (TextView) view.findViewById(R.id.tv_product_type);
            holder.imgProductType = (ImageView) view.findViewById(R.id.img_product_type);
            view.setTag(holder);
        } else {
        //   ngược lại nếu giữ HomeView thì lấy ra
            holder = (ViewHolder) view.getTag();

        }
        //  lấy ra loại sản phẩm thứ i set giá trị cho chúng, dùng picasso để chuyển từ link ảnh sang ảnh, rồi trả kq về HomeView (dòng)
        ProductType productType = (ProductType) getItem(i);
        holder.tvProductType.setText(productType.getNameProductType());
        ImageViewUtil.loadImg(context, productType.getImageProductType(), holder.imgProductType);
        return view;
    }
}
