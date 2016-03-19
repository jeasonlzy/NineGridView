package com.lzy.ninegridview.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lzy.ninegridview.R;
import com.lzy.ninegridview.bean.ImageDetail;
import com.lzy.ui.NineGridViewAdapter;

import java.util.List;

/**
 * ================================================
 * 作    者：廖子尧
 * 版    本：1.0
 * 创建日期：2016/3/19
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class DefaultNineGridViewAdapter extends NineGridViewAdapter<ImageDetail> {

    public DefaultNineGridViewAdapter(List<ImageDetail> list) {
        super(list);
    }

    @Override
    protected ImageView generateImageView(Context context) {
        return super.generateImageView(context);
    }

    @Override
    protected void onDisplayImage(Context context, ImageView imageView, ImageDetail imageDetail) {
        Glide.with(context)//
                .load(imageDetail.getUrl())//
                .placeholder(R.mipmap.ic_default_image)//
                .diskCacheStrategy(DiskCacheStrategy.ALL)//
                .into(imageView);
    }

    @Override
    protected void onImageItemClick(Context context, int index, List<ImageDetail> list) {
        Toast.makeText(context, "图片条目：" + index, Toast.LENGTH_SHORT).show();
    }
}
