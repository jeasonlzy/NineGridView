package com.lzy.ninegridview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;

import com.lzy.ninegridview.R;

public class CircleImageView extends ImageView {

    private static final ScaleType SCALE_TYPE = ScaleType.CENTER_CROP;      //只允许CENTER_CROP模式
    private static final Bitmap.Config BITMAP_CONFIG = Bitmap.Config.ARGB_8888;  //默认创建的格式
    private static final int COLORDRAWABLE_DIMENSION = 2;   //对于 colorDrawable 的大小

    //以下是自定义属性
    private int mBorderWidth = 1;            //默认边框的宽度，单位 dp
    private int mBorderColor = 0x88FFFFFF;   //默认边框的颜色
    private int mFillColor = 0x00000000;     //纯色的填充色
    private boolean mBorderOverlay = false;  //true表示边框会覆盖一部分图片，false表示边框不会覆盖在图片之上

    //以下是成员变量
    private final Matrix mShaderMatrix = new Matrix();  //对图片缩放的矩阵
    private final Paint mBitmapPaint = new Paint();     //图片的画笔
    private final Paint mBorderPaint = new Paint();     //边框的画笔
    private final Paint mFillPaint = new Paint();       //背景色的画笔
    private Bitmap mBitmap;            //设置的图片
    private float mBorderRadius;       //边框的半径，默认向内部偏移了 mBorderWidth/2 的长度，保证边框不超出有效绘画区域
    private float mDrawableRadius;     //内容的绘制半径，自动根据 mBorderOverlay 参数决定是否包括边框的半径
    private ColorFilter mColorFilter;  //滤色
    private boolean mSetupPending;     //是否执行了setUp方法

    public CircleImageView(Context context) {
        this(context, null);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        mBorderWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, mBorderWidth, getResources().getDisplayMetrics());

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView, defStyle, 0);
        mBorderWidth = a.getDimensionPixelSize(R.styleable.CircleImageView_civBorderWidth, mBorderWidth);
        mBorderColor = a.getColor(R.styleable.CircleImageView_civBorderColor, mBorderColor);
        mBorderOverlay = a.getBoolean(R.styleable.CircleImageView_civBorderOverlay, mBorderOverlay);
        mFillColor = a.getColor(R.styleable.CircleImageView_civFillColor, mFillColor);
        a.recycle();

        super.setScaleType(SCALE_TYPE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mSetupPending = true;
        setup();
    }

    private void setup() {
        if (!mSetupPending || getWidth() == 0 && getHeight() == 0) return;
        if (mBitmap == null) {
            invalidate();
            return;
        }

        BitmapShader bitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mBitmapPaint.setAntiAlias(true);
        mBitmapPaint.setShader(bitmapShader);

        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setColor(mBorderColor);
        mBorderPaint.setStrokeWidth(mBorderWidth);

        mFillPaint.setStyle(Paint.Style.FILL);
        mFillPaint.setAntiAlias(true);
        mFillPaint.setColor(mFillColor);

        int drawableWidth = getWidth() - getPaddingLeft() - getPaddingRight();
        int drawableHeight = getHeight() - getPaddingTop() - getPaddingBottom();
        mDrawableRadius = Math.min(drawableWidth, drawableHeight) / 2;
        mBorderRadius = mDrawableRadius - mBorderWidth / 2;
        if (!mBorderOverlay) {
            mDrawableRadius -= mBorderWidth;
        }

        int bitmapHeight = mBitmap.getHeight();
        int bitmapWidth = mBitmap.getWidth();
        float dx = (drawableWidth - mDrawableRadius * 2) / 2 + getPaddingLeft();
        float dy = (drawableHeight - mDrawableRadius * 2) / 2 + getPaddingTop();
        float scale;
        mShaderMatrix.set(null);
        if (bitmapWidth > bitmapHeight) {
            scale = mDrawableRadius * 2 / bitmapHeight;            //图片的宽高比 大于 有效绘制区域的宽高比，此时缩放比以 高度的缩放比为基准
            dx += (mDrawableRadius * 2 - bitmapWidth * scale) / 2; //dx 为负值，表示向左平移
        } else {
            scale = mDrawableRadius * 2 / bitmapWidth;             //图片的宽高比 小于 有效绘制区域的宽高比，此时缩放比以 宽度的缩放比为基准
            dy += (mDrawableRadius * 2 - bitmapHeight * scale) / 2;//dy 为负值，表示向上平移
        }
        mShaderMatrix.postScale(scale, scale);   //设置图片的缩放大小
        mShaderMatrix.postTranslate((int) (dx + 0.5f), (int) (dy + 0.5f)); //设置图片的平移距离
        bitmapShader.setLocalMatrix(mShaderMatrix);  //最后赋值给BitmapShader

        invalidate();
    }

    /** 没有调用父类的 super 方法，全完靠自定义控件绘制出来 */
    @Override
    protected void onDraw(Canvas canvas) {
        if (mBitmap != null)
            canvas.drawCircle(getWidth() / 2.0f, getHeight() / 2.0f, mDrawableRadius, mBitmapPaint);//绘制图片
        else if (mFillColor != Color.TRANSPARENT)
            canvas.drawCircle(getWidth() / 2.0f, getHeight() / 2.0f, mDrawableRadius, mFillPaint);  //绘制纯色背景
        if (mBorderWidth != 0)
            canvas.drawCircle(getWidth() / 2.0f, getHeight() / 2.0f, mBorderRadius, mBorderPaint);  //绘制边框
    }

    /** 将传入的drawable转换成bitmap */
    private Bitmap getBitmapFromDrawable(Drawable drawable) {
        if (drawable == null) return null;
        if (drawable instanceof BitmapDrawable) return ((BitmapDrawable) drawable).getBitmap();
        try {
            Bitmap bitmap;
            if (drawable instanceof ColorDrawable) {
                bitmap = Bitmap.createBitmap(COLORDRAWABLE_DIMENSION, COLORDRAWABLE_DIMENSION, BITMAP_CONFIG);
            } else {
                bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), BITMAP_CONFIG);
            }
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ScaleType getScaleType() {
        return SCALE_TYPE;
    }

    @Override
    public void setScaleType(ScaleType scaleType) {
        if (scaleType != SCALE_TYPE) {
            throw new IllegalArgumentException(String.format("ScaleType %s not supported.", scaleType));
        }
    }

    @Override
    public void setAdjustViewBounds(boolean adjustViewBounds) {
        if (adjustViewBounds) {
            throw new IllegalArgumentException("adjustViewBounds not supported.");
        }
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        mBitmap = bm;
        setup();
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        mBitmap = getBitmapFromDrawable(drawable);
        setup();
    }

    @Override
    public void setImageResource(@DrawableRes int resId) {
        super.setImageResource(resId);
        mBitmap = getBitmapFromDrawable(getDrawable());
        setup();
    }

    @Override
    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        mBitmap = uri != null ? getBitmapFromDrawable(getDrawable()) : null;
        setup();
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        if (cf == mColorFilter) return;
        mColorFilter = cf;
        mBitmapPaint.setColorFilter(mColorFilter);
        invalidate();
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        super.setPadding(left, top, right, bottom);
        setup();
    }

    public int getBorderColor() {
        return mBorderColor;
    }

    public void setBorderColor(@ColorInt int borderColor) {
        if (borderColor == mBorderColor) return;
        mBorderColor = borderColor;
        mBorderPaint.setColor(mBorderColor);
        invalidate();
    }

    public void setBorderColorResource(@ColorRes int borderColorRes) {
        setBorderColor(getContext().getResources().getColor(borderColorRes));
    }

    public int getFillColor() {
        return mFillColor;
    }

    public void setFillColor(@ColorInt int fillColor) {
        if (fillColor == mFillColor) return;
        mFillColor = fillColor;
        mFillPaint.setColor(fillColor);
        invalidate();
    }

    public void setFillColorResource(@ColorRes int fillColorRes) {
        setFillColor(getContext().getResources().getColor(fillColorRes));
    }

    public int getBorderWidth() {
        return mBorderWidth;
    }

    public void setBorderWidth(int borderWidth) {
        if (borderWidth == mBorderWidth) return;
        mBorderWidth = borderWidth;
        setup();
    }

    public boolean isBorderOverlay() {
        return mBorderOverlay;
    }

    public void setBorderOverlay(boolean borderOverlay) {
        if (borderOverlay == mBorderOverlay) return;
        mBorderOverlay = borderOverlay;
        setup();
    }
}
