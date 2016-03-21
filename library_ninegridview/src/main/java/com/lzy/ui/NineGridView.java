package com.lzy.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class NineGridView extends ViewGroup {

    public static final ImageView.ScaleType[] SCALE_TYPES = {//
            ImageView.ScaleType.MATRIX,//
            ImageView.ScaleType.FIT_XY,//
            ImageView.ScaleType.FIT_START,//
            ImageView.ScaleType.FIT_CENTER,//
            ImageView.ScaleType.FIT_END,//
            ImageView.ScaleType.CENTER,//
            ImageView.ScaleType.CENTER_CROP,//
            ImageView.ScaleType.CENTER_INSIDE};

    private int singleImageSize = 250;              // 单张图片时的最大大小,单位dp
    private int singleImageScaleType = 6;           // 单张图片的缩放模式
    private float singleImageRatio = 1.0f;          // 单张图片的宽高比(宽/高)
    private int maxImageSize = 9;                   // 最大显示的图片数
    private int gridSpacing = 3;                    // 宫格间距，单位dp

    private int columnCount;    // 列数
    private int rowCount;       // 行数
    private int gridWidth;      // 宫格宽度
    private int gridHeight;     // 宫格高度
    private ImageView.ScaleType mScaleType = SCALE_TYPES[singleImageScaleType];

    private List<ImageView> imageViews;
    private List<ImageInfo> mImageInfo;
    private NineGridViewAdapter mAdapter;

    public NineGridView(Context context) {
        this(context, null);
    }

    public NineGridView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NineGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        gridSpacing = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, gridSpacing, dm);
        singleImageSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, singleImageSize, dm);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.NineGridView);
        gridSpacing = (int) a.getDimension(R.styleable.NineGridView_gridSpacing, gridSpacing);
        singleImageSize = a.getDimensionPixelSize(R.styleable.NineGridView_singleImageSize, singleImageSize);
        singleImageRatio = a.getFloat(R.styleable.NineGridView_singleImageRatio, singleImageRatio);
        singleImageScaleType = a.getInt(R.styleable.NineGridView_singleImageScaleType, singleImageScaleType);
        mScaleType = SCALE_TYPES[singleImageScaleType];
        maxImageSize = a.getInt(R.styleable.NineGridView_maxSize, maxImageSize);
        a.recycle();

        imageViews = new ArrayList<>();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = 0;
        int totalWidth = width - getPaddingLeft() - getPaddingRight();
        if (mImageInfo != null && mImageInfo.size() > 0) {
            if (mImageInfo.size() == 1) {
                imageViews.get(0).setScaleType(mScaleType);
                gridWidth = singleImageSize > totalWidth ? totalWidth : singleImageSize;
                gridHeight = (int) (gridWidth / singleImageRatio);
                //矫正图片显示区域大小，不允许超过最大显示范围
                if (gridHeight > singleImageSize) {
                    float ratio = singleImageSize * 1.0f / gridHeight;
                    gridWidth = (int) (gridWidth * ratio);
                    gridHeight = singleImageSize;
                }
            } else {
                imageViews.get(0).setScaleType(ImageView.ScaleType.CENTER_CROP);
                gridWidth = gridHeight = (totalWidth - gridSpacing * (columnCount - 1)) / columnCount;
            }
            width = gridWidth * columnCount + gridSpacing * (columnCount - 1) + getPaddingLeft() + getPaddingRight();
            height = gridHeight * rowCount + gridSpacing * (rowCount - 1) + getPaddingTop() + getPaddingBottom();
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (mImageInfo == null) return;
        int childrenCount = mImageInfo.size();
        for (int i = 0; i < childrenCount; i++) {
            ImageView childrenView = (ImageView) getChildAt(i);
            if (mAdapter != null) {
                mAdapter.onDisplayImage(getContext(), childrenView, mImageInfo.get(i));
            }
            int rowNum = i / columnCount;
            int columnNum = i % columnCount;
            int left = (gridWidth + gridSpacing) * columnNum + getPaddingLeft();
            int top = (gridHeight + gridSpacing) * rowNum + getPaddingTop();
            int right = left + gridWidth;
            int bottom = top + gridHeight;
            childrenView.layout(left, top, right, bottom);
        }
    }

    /** 设置适配器 */
    public void setAdapter(@NonNull NineGridViewAdapter adapter) {
        mAdapter = adapter;
        List<ImageInfo> imageInfo = adapter.getImageInfo();

        if (imageInfo == null || imageInfo.isEmpty()) {
            setVisibility(GONE);
            return;
        } else {
            setVisibility(VISIBLE);
        }

        if (maxImageSize > 0 && imageInfo.size() > maxImageSize) {
            imageInfo = imageInfo.subList(0, maxImageSize);
        }

        //默认是3列显示，行数根据图片的数量决定
        rowCount = imageInfo.size() / 3 + (imageInfo.size() % 3 == 0 ? 0 : 1);
        columnCount = 3;

        //保证View的复用，避免重复创建
        if (mImageInfo == null) {
            for (int i = 0; i < imageInfo.size(); i++) {
                ImageView iv = getImageView(i);
                if (iv == null) return;
                addView(iv, generateDefaultLayoutParams());
            }
        } else {
            int oldViewCount = mImageInfo.size();
            int newViewCount = imageInfo.size();
            if (oldViewCount > newViewCount) {
                removeViews(newViewCount, oldViewCount - newViewCount);
            } else if (oldViewCount < newViewCount) {
                for (int i = oldViewCount; i < newViewCount; i++) {
                    ImageView iv = getImageView(i);
                    if (iv == null) return;
                    addView(iv, generateDefaultLayoutParams());
                }
            }
        }
        mImageInfo = imageInfo;
        requestLayout();
    }

    /** 获得 ImageView 保证了 ImageView 的重用 */
    private ImageView getImageView(final int position) {
        ImageView imageView;
        if (position < imageViews.size()) {
            imageView = imageViews.get(position);
        } else {
            imageView = mAdapter.generateImageView(getContext());
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAdapter.onImageItemClick(getContext(), NineGridView.this, position, mAdapter.getImageInfo());
                }
            });
            imageViews.add(imageView);
        }
        return imageView;
    }

    /** 设置宫格间距 */
    public void setGridSpacing(int spacing) {
        gridSpacing = spacing;
    }

    /** 设置只有一张图片时的宽 */
    public void setSingleImageSize(int maxImageSize) {
        singleImageSize = maxImageSize;
    }

    /** 设置只有一张图片时的宽高比 */
    public void setSingleImageRatio(float ratio) {
        singleImageRatio = ratio;
    }

    /** 设置只有一张图片时的缩放模式 */
    public void setSingleImageScaleType(int scaleType) {
        singleImageScaleType = scaleType;
        mScaleType = SCALE_TYPES[singleImageScaleType];
    }

    /** 设置只有一张图片时的缩放模式 */
    public void setSingleImageScaleType(ImageView.ScaleType scaleType) {
        mScaleType = scaleType;
        for (int i = 0; i < SCALE_TYPES.length; i++) {
            if (SCALE_TYPES[i] == scaleType) {
                singleImageScaleType = i;
                break;
            }
        }
    }

    /** 设置最大图片数 */
    public void setMaxSize(int maxSize) {
        maxImageSize = maxSize;
    }

    public int getMaxSize() {
        return maxImageSize;
    }
}