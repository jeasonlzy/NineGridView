package com.lzy.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * ================================================
 * 作    者：廖子尧
 * 版    本：1.0
 * 创建日期：2015/10/27
 * 描    述：类似微信朋友圈的图片展示，只有当图片数量为 1，2，或大于等于3 的时候，自动按照不同大小展示
 * 修订历史：
 * ================================================
 */
public class NineGridView1 extends ViewGroup {

    //以下是自定义属性  单位默认 dp
    private int imageWidth1 = 200;   //只有一张图片的最大宽度
    private int imageHeight1 = 200;  //只有一张图片的最大高度
    private int imageWidth2 = 120;   //只有两张图片的最大宽度
    private int imageHeight2 = 120;  //只有两张图片的最大高度
    private int imageWidth3 = 80;    //大于三张图片的最大宽度
    private int imageHeight3 = 80;   //大于三张图片的最大高度
    private int imageSpace = 3;      //图片之间的间距

    //以下是成员变量
    int childWidthSpec = 0;  //子View的宽度测量模式
    int childHeightSpec = 0; //子View的高度测量模式
    int containerWidth = 0;  //该容器的申请宽度
    int containerHeight = 0; //该容器的申请高度

    public NineGridView1(Context context) {
        this(context, null);
    }

    public NineGridView1(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NineGridView1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //初始化默认值
        DisplayMetrics dm = getResources().getDisplayMetrics();
        imageWidth1 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, imageWidth1, dm);
        imageHeight1 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, imageHeight1, dm);
        imageWidth2 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, imageWidth2, dm);
        imageHeight2 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, imageHeight2, dm);
        imageWidth3 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, imageWidth3, dm);
        imageHeight3 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, imageHeight3, dm);
        imageSpace = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, imageSpace, dm);

        //获取自定义属性
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.NineGridView1);
        imageWidth1 = a.getDimensionPixelSize(R.styleable.NineGridView1_imageWidth1, imageWidth1);
        imageHeight1 = a.getDimensionPixelSize(R.styleable.NineGridView1_imageHeight1, imageHeight1);
        imageWidth2 = a.getDimensionPixelSize(R.styleable.NineGridView1_imageWidth2, imageWidth2);
        imageHeight2 = a.getDimensionPixelSize(R.styleable.NineGridView1_imageHeight2, imageHeight2);
        imageWidth3 = a.getDimensionPixelSize(R.styleable.NineGridView1_imageWidth3, imageWidth3);
        imageHeight3 = a.getDimensionPixelSize(R.styleable.NineGridView1_imageHeight3, imageHeight3);
        imageSpace = a.getDimensionPixelSize(R.styleable.NineGridView1_imageSpace, imageSpace);
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (getChildCount() == 0) {
            if (widthMode == MeasureSpec.AT_MOST || widthMode == MeasureSpec.UNSPECIFIED) {
                containerWidth = getPaddingLeft() + getPaddingRight();
            }
            if (heightMode == MeasureSpec.AT_MOST || heightMode == MeasureSpec.UNSPECIFIED) {
                containerHeight = getPaddingTop() + getPaddingBottom();
            }
        } else if (getChildCount() == 1) {
            //得到图片的缩放大小
            BitmapDrawable drawable = (BitmapDrawable) ((ImageView) getChildAt(0)).getDrawable();
            int width = drawable.getIntrinsicWidth(), height = drawable.getIntrinsicHeight();
            float scaleX = width * 1.0f / imageWidth1, scaleY = height * 1.0f / imageHeight1;
            int imageWidth = imageWidth1, imageHeight = imageHeight1;
            if (scaleX > 1 && scaleX > scaleY) {
                imageHeight = (int) (height / scaleX);
            } else if (scaleY > 1 && scaleY > scaleX) {
                imageWidth = (int) (width / scaleY);
            } else if (scaleX < 1 && scaleY < 1) {
                imageHeight = height;
                imageWidth = width;
            }
            childWidthSpec = MeasureSpec.makeMeasureSpec(imageWidth, MeasureSpec.EXACTLY);
            childHeightSpec = MeasureSpec.makeMeasureSpec(imageHeight, MeasureSpec.EXACTLY);
            if (widthMode == MeasureSpec.AT_MOST || widthMode == MeasureSpec.UNSPECIFIED) {
                containerWidth = imageWidth + getPaddingLeft() + getPaddingRight();
            }
            if (heightMode == MeasureSpec.AT_MOST || heightMode == MeasureSpec.UNSPECIFIED) {
                containerHeight = imageHeight + getPaddingTop() + getPaddingBottom();
            }
        } else if (getChildCount() == 2) {
            childWidthSpec = MeasureSpec.makeMeasureSpec(imageWidth2, MeasureSpec.EXACTLY);
            childHeightSpec = MeasureSpec.makeMeasureSpec(imageHeight2, MeasureSpec.EXACTLY);
            if (widthMode == MeasureSpec.AT_MOST || widthMode == MeasureSpec.UNSPECIFIED) {
                containerWidth = imageWidth2 * 2 + imageSpace + getPaddingLeft() + getPaddingRight();
            }
            if (heightMode == MeasureSpec.AT_MOST || heightMode == MeasureSpec.UNSPECIFIED) {
                containerHeight = imageHeight2 + getPaddingTop() + getPaddingBottom();
            }
        } else if (getChildCount() >= 3) {
            childWidthSpec = MeasureSpec.makeMeasureSpec(imageWidth3, MeasureSpec.EXACTLY);
            childHeightSpec = MeasureSpec.makeMeasureSpec(imageHeight3, MeasureSpec.EXACTLY);
            if (widthMode == MeasureSpec.AT_MOST || widthMode == MeasureSpec.UNSPECIFIED) {
                containerWidth = imageWidth3 * 3 + imageSpace * 2 + getPaddingLeft() + getPaddingRight();
            }
            if (heightMode == MeasureSpec.AT_MOST || heightMode == MeasureSpec.UNSPECIFIED) {
                containerHeight = imageHeight3 * ((getChildCount() - 1) / 3 + 1) + imageSpace * ((getChildCount() - 1) / 3) + getPaddingTop() + getPaddingBottom();
            }
        }
        measureChildren(childWidthSpec, childHeightSpec);
        setMeasuredDimension(containerWidth, containerHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int left, top, right, bottom;
        if (getChildCount() == 1) {
            //默认靠左显示图片
            ((ImageView) getChildAt(0)).setScaleType(ImageView.ScaleType.FIT_START);
            getChildAt(0).layout(getPaddingLeft(), getPaddingTop(), getPaddingLeft() + imageWidth1, getPaddingTop() + imageHeight1);
        } else if (getChildCount() == 2) {
            for (int i = 0, count = getChildCount(); i < count; i++) {
                //默认居中缩放显示图片
                ((ImageView) getChildAt(i)).setScaleType(ImageView.ScaleType.CENTER_CROP);
                left = getPaddingLeft() + i % 3 * (imageWidth2 + imageSpace);
                top = getPaddingTop() + i / 3 * (imageHeight2 + imageSpace);
                right = left + imageWidth2;
                bottom = top + imageHeight2;
                getChildAt(i).layout(left, top, right, bottom);
            }
        } else if (getChildCount() >= 3) {
            for (int i = 0, count = getChildCount(); i < count; i++) {
                //默认居中缩放显示图片
                ((ImageView) getChildAt(i)).setScaleType(ImageView.ScaleType.CENTER_CROP);
                left = getPaddingLeft() + i % 3 * (imageWidth3 + imageSpace);
                top = getPaddingTop() + i / 3 * (imageHeight3 + imageSpace);
                right = left + imageWidth3;
                bottom = top + imageHeight3;
                getChildAt(i).layout(left, top, right, bottom);
            }
        }
    }

    /**
     * 最多只显示9张图片
     */
    @Override
    public void addView(View child) {
        if (getChildCount() >= 9)
            return;
        super.addView(child);
    }
}