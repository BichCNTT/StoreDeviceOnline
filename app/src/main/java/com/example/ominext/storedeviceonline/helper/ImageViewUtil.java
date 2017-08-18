package com.example.ominext.storedeviceonline.helper;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.example.ominext.storedeviceonline.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Ominext on 8/18/2017.
 */

public class ImageViewUtil {
    public static void loadImg(Context context, String url, ImageView view) {
        Picasso.with(context).
                load(url).
                placeholder(R.drawable.ic_camera)
                .error(R.drawable.ic_cancel)
                .into(view);
    }
}
