package com.lzy.ui;

import android.content.Context;
import android.widget.ImageView;

import java.util.List;

public abstract class NineGridViewAdapter<T> {

    private List<T> mData;

    public NineGridViewAdapter(List<T> list) {
        mData = list;
    }

    /**
     * 需要子类实现该方法，以确定如何加载和显示图片
     *
     * @param context   上下文
     * @param imageView 需要展示图片的ImageView
     * @param t         携带有图片地址的数据
     */
    protected abstract void onDisplayImage(Context context, ImageView imageView, T t);

    /**
     * 如果要实现图片点击的逻辑，重写此方法即可
     *
     * @param context 上下文
     * @param index   当前点击图片的的索引
     * @param list    携带有图片地址的数据集合
     */
    protected void onImageItemClick(Context context, int index, List<T> list) {
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

    public List<T> getData() {
        return mData;
    }

    public void setData(List<T> data) {
        mData = data;
    }
}