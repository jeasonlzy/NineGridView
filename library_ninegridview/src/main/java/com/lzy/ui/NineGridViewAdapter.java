package com.lzy.ui;

import android.content.Context;
import android.widget.ImageView;

import java.util.List;

public abstract class NineGridViewAdapter {

    private List<String> mUrls;

    public NineGridViewAdapter(List<String> urls) {
        mUrls = urls;
    }

    /**
     * 需要子类实现该方法，以确定如何加载和显示图片
     *
     * @param context   上下文
     * @param imageView 需要展示图片的ImageView
     * @param url       图片的地址
     */
    protected abstract void onDisplayImage(Context context, ImageView imageView, String url);

    /**
     * 如果要实现图片点击的逻辑，重写此方法即可
     *
     * @param context 上下文
     * @param index   当前点击图片的的索引
     */
    protected void onImageItemClick(Context context, int index) {
    }

    /**
     * 生成ImageView容器的方式，默认使用NineGridImageViewWrapper类，即点击图片后，图片会有蒙板效果
     * 如果需要自定义图片展示效果，重写此方法即可
     *
     * @param context 上下文
     * @return 生成的 ImageView
     */
    protected ImageView generateImageView(Context context) {
        NineGridViewWrapper imageView = new NineGridViewWrapper(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return imageView;
    }

    public List<String> getUrls() {
        return mUrls;
    }

    public void setUrls(List<String> mUrls) {
        this.mUrls = mUrls;
    }
}