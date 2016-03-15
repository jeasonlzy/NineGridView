package com.lzy.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class NineGridView extends ViewGroup {

    public final static int STYLE_GRID = 0;     // 宫格布局
    public final static int STYLE_FILL = 1;     // 全填充布局

    private int maxImageSize = 9;               // 最大图片数
    private int showStyle = STYLE_GRID;         // 显示风格
    private int gap = 3;                        // 宫格间距
    private int singleImgSize = -1;             // 单张图片时的尺寸

    private int mColumnCount;    // 列数
    private int mRowCount;       // 行数
    private int gridSize;        // 宫格大小,即图片大小

    private List<ImageView> imageViews = new ArrayList<>();
    private List<String> imgUrls;
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
        gap = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, gap, dm);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.NineGridView);
        gap = (int) a.getDimension(R.styleable.NineGridView_imgGap, gap);
        singleImgSize = a.getDimensionPixelSize(R.styleable.NineGridView_singleImgSize, singleImgSize);
        showStyle = a.getInt(R.styleable.NineGridView_showStyle, showStyle);
        maxImageSize = a.getInt(R.styleable.NineGridView_maxSize, maxImageSize);
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height;
        int totalWidth = width - getPaddingLeft() - getPaddingRight();
        if (imgUrls != null && imgUrls.size() > 0) {
            if (imgUrls.size() == 1 && singleImgSize != -1) {
                gridSize = singleImgSize > totalWidth ? totalWidth : singleImgSize;
            } else {
                imageViews.get(0).setScaleType(ImageView.ScaleType.CENTER_CROP);
                gridSize = (totalWidth - gap * (mColumnCount - 1)) / mColumnCount;
            }
            height = gridSize * mRowCount + gap * (mRowCount - 1) + getPaddingTop() + getPaddingBottom();
            setMeasuredDimension(width, height);
        } else {
            height = width;
            setMeasuredDimension(width, height);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        layoutChildrenView();
    }

    /**
     * 布局 ImageView
     */
    private void layoutChildrenView() {
        if (imgUrls == null) {
            return;
        }
        int childrenCount = imgUrls.size();
        for (int i = 0; i < childrenCount; i++) {
            ImageView childrenView = (ImageView) getChildAt(i);
            if (mAdapter != null) {
                mAdapter.onDisplayImage(getContext(), childrenView, imgUrls.get(i));
            }
            int rowNum = i / mColumnCount;
            int columnNum = i % mColumnCount;
            int left = (gridSize + gap) * columnNum + getPaddingLeft();
            int top = (gridSize + gap) * rowNum + getPaddingTop();
            int right = left + gridSize;
            int bottom = top + gridSize;

            childrenView.layout(left, top, right, bottom);
        }
    }

    /** 设置图片数据 */
    private void setImagesData(List lists) {
        if (lists == null || lists.isEmpty()) {
            this.setVisibility(GONE);
            return;
        } else {
            this.setVisibility(VISIBLE);
        }

        if (maxImageSize > 0 && lists.size() > maxImageSize) {
            lists = lists.subList(0, maxImageSize);
        }

        int[] gridParam = calculateGridParam(lists.size(), showStyle);
        mRowCount = gridParam[0];
        mColumnCount = gridParam[1];
        if (imgUrls == null) {
            int i = 0;
            while (i < lists.size()) {
                ImageView iv = getImageView(i);
                if (iv == null) {
                    return;
                }
                addView(iv, generateDefaultLayoutParams());
                i++;
            }
        } else {
            int oldViewCount = imgUrls.size();
            int newViewCount = lists.size();
            if (oldViewCount > newViewCount) {
                removeViews(newViewCount, oldViewCount - newViewCount);
            } else if (oldViewCount < newViewCount) {
                for (int i = oldViewCount; i < newViewCount; i++) {
                    ImageView iv = getImageView(i);
                    if (iv == null) {
                        return;
                    }
                    addView(iv, generateDefaultLayoutParams());
                }
            }
        }
        imgUrls = lists;
        requestLayout();
    }

    /** 获得 ImageView 保证了 ImageView 的重用 */
    private ImageView getImageView(final int position) {
        if (position < imageViews.size()) {
            return imageViews.get(position);
        } else {
            if (mAdapter != null) {
                ImageView imageView = mAdapter.generateImageView(getContext());
                imageViews.add(imageView);
                imageView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mAdapter.onImageItemClick(getContext(), position);
                    }
                });
                return imageView;
            } else {
                Log.e("NineGirdImageView", "Your must set a NineGridViewAdapter for NineGirdImageView");
                return null;
            }
        }
    }

    /** 设置 宫格参数 ridParam[0] 宫格行数 gridParam[1] 宫格列数 */
    protected static int[] calculateGridParam(int imagesSize, int showStyle) {
        int[] gridParam = new int[2];
        switch (showStyle) {
            case STYLE_FILL:
                if (imagesSize < 3) {
                    gridParam[0] = 1;
                    gridParam[1] = imagesSize;
                } else if (imagesSize <= 4) {
                    gridParam[0] = 2;
                    gridParam[1] = 2;
                } else {
                    gridParam[0] = imagesSize / 3 + (imagesSize % 3 == 0 ? 0 : 1);
                    gridParam[1] = 3;
                }
                break;
            default:
            case STYLE_GRID:
                gridParam[0] = imagesSize / 3 + (imagesSize % 3 == 0 ? 0 : 1);
                gridParam[1] = 3;
        }
        return gridParam;
    }

    /** 设置适配器 */
    public void setAdapter(NineGridViewAdapter adapter) {
        mAdapter = adapter;
        setImagesData(adapter.getUrls());
    }

    /** 设置宫格间距 */
    public void setGap(int gap) {
        this.gap = gap;
    }

    /** 设置显示风格 */
    public void setShowStyle(int showStyle) {
        this.showStyle = showStyle;
    }

    /** 设置只有一张图片时的尺寸大小 */
    public void setSingleImgSize(int singleImgSize) {
        this.singleImgSize = singleImgSize;
    }

    /** 设置最大图片数 */
    public void setMaxSize(int maxSize) {
        maxImageSize = maxSize;
    }
}