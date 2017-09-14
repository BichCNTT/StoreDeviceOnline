package com.example.ominext.storedeviceonline.helper;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.ominext.storedeviceonline.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Ominext on 8/18/2017.
 */

public class ImageViewUtil {
    public static void loadImg(Context context, String url, ImageView view) {
        Glide.with(context)
                .load(url)
                .asBitmap()
                .error(R.drawable.ic_camera)
                .format(DecodeFormat.PREFER_ARGB_8888)
                .asIs()
                .into(view);
    }
}


