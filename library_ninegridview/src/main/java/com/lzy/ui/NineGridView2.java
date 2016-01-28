package com.lzy.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * ================================================
 * 作    者：廖子尧
 * 版    本：1.0
 * 创建日期：2015/10/27
 * 描    述：类似微信朋友圈的图片展示，只有当图片数量为 1，2，或大于等于3 的时候，自动按照不同大小展示
 * 修订历史：
 * ================================================
 */
public class NineGridView2 extends LinearLayout {

    //以下是自定义属性  单位默认 dp
    private int imageWidth1 = 200;   //只有一张图片的最大宽度
    private int imageHeight1 = 200;  //只有一张图片的最大高度
    private int imageWidth2 = 120;   //只有两张图片的最大宽度
    private int imageHeight2 = 120;  //只有两张图片的最大高度
    private int imageWidth3 = 80;    //大于三张图片的最大宽度
    private int imageHeight3 = 80;   //大于三张图片的最大高度
    private int imageSpace = 3;      //图片之间的间距

    //以下是成员变量
    private ArrayList<LinearLayout> rows;
    private ArrayList<View> images;
    private int childCount = 0;

    public NineGridView2(Context context) {
        this(context, null);
    }

    public NineGridView2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NineGridView2(Context context, AttributeSet attrs, int defStyleAttr) {
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
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.NineGridView);
        imageWidth1 = a.getDimensionPixelSize(R.styleable.NineGridView_imageWidth1, imageWidth1);
        imageHeight1 = a.getDimensionPixelSize(R.styleable.NineGridView_imageHeight1, imageHeight1);
        imageWidth2 = a.getDimensionPixelSize(R.styleable.NineGridView_imageWidth2, imageWidth2);
        imageHeight2 = a.getDimensionPixelSize(R.styleable.NineGridView_imageHeight2, imageHeight2);
        imageWidth3 = a.getDimensionPixelSize(R.styleable.NineGridView_imageWidth3, imageWidth3);
        imageHeight3 = a.getDimensionPixelSize(R.styleable.NineGridView_imageHeight3, imageHeight3);
        imageSpace = a.getDimensionPixelSize(R.styleable.NineGridView_imageSpace, imageSpace);
        a.recycle();

        //垂直的线性布局
        setOrientation(VERTICAL);
    }

    /**
     * 最多只显示9张图片
     */
    @Override
    public void addView(View child) {
        System.out.println("addView  " + getChildCount());
        if (child == null) {
            throw new IllegalArgumentException("添加的View不能为null");
        }
        if (rows == null) {
            rows = new ArrayList<>();
            images = new ArrayList<>();
        }

        childCount = images.size();
        //只有一张图片的情况
        if (childCount == 0) {
            LinearLayout row1 = new LinearLayout(getContext());
            rows.add(row1);
            //得到图片的缩放大小
            ImageView imageChild = (ImageView) child;
            BitmapDrawable drawable = (BitmapDrawable) imageChild.getDrawable();
            int drawableWidth = drawable.getIntrinsicWidth();
            int drawableHeight = drawable.getIntrinsicHeight();
            float scaleX = drawableWidth * 1.0f / imageWidth1;
            float scaleY = drawableHeight * 1.0f / imageHeight1;
            int imageWidthScale = imageWidth1;
            int imageHeightScale = imageHeight1;
            if (scaleX > 1 && scaleX > scaleY) {
                imageHeightScale = (int) (drawableHeight / scaleX);
            } else if (scaleY > 1 && scaleY > scaleX) {
                imageWidthScale = (int) (drawableWidth / scaleY);
            } else if (scaleX < 1 && scaleY < 1) {
                imageHeightScale = drawableHeight;
                imageWidthScale = drawableWidth;
            }
            row1.addView(imageChild);
            changeRowsParams(imageWidthScale, imageHeightScale);
            super.addView(row1);
        } else if (childCount == 1) {
            //两张图片的时候
            rows.get(0).addView(child);
            changeRowsParams(imageWidth2, imageHeight2);
        } else if (childCount >= 2) {
            //三张图片以上的时候
            if (childCount % 3 == 0) {
                LinearLayout row = new LinearLayout(getContext());
                rows.add(row);
                super.addView(row);
            }
            rows.get(childCount / 3).addView(child);
            changeRowsParams(imageWidth3, imageHeight3);
        }

        images.add(child);
    }

    private void changeRowsParams(int imageWidth, int imageHeight) {
        for (int i = 0; i < rows.size(); i++) {
            LinearLayout row = rows.get(i);
            for (int j = 0; j < row.getChildCount(); j++) {
                LayoutParams params = (LayoutParams) row.getChildAt(j).getLayoutParams();
                ImageView imageChild = (ImageView) row.getChildAt(j);
                imageChild.setScaleType(ImageView.ScaleType.CENTER_CROP);
                params.width = imageWidth;
                params.height = imageHeight;
                if (i > 0)
                    params.topMargin = imageSpace;
                if (j % 3 != 0)
                    params.leftMargin = imageSpace;
                imageChild.setLayoutParams(params);
            }
        }
    }

//    @Override
//    public View getChildAt(int index) {
//        if (index < 0 || index >= images.size()) {
//            return null;
//        }
//        return images.get(index);
//    }

    @Override
    public void removeAllViews() {
        super.removeAllViews();
        images.clear();
        images = null;
        rows.clear();
        rows = null;
    }

    @Override
    public void removeView(View view) {
//        System.out.println(getChildCount());
//        super.removeView(view);
        for (int i = 0; i < rows.size(); i++) {
            LinearLayout row = rows.get(i);
            row.removeView(view);
        }
        requestLayout();
        invalidate();
    }
}