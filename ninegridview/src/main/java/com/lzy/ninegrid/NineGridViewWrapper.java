package com.lzy.ninegrid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.widget.ImageView;

public class NineGridViewWrapper extends ImageView {

    private int moreNum = 0;              //显示更多的数量
    private int maskColor = 0x88000000;   //默认的遮盖颜色
    private float textSize = 35;          //显示文字的大小单位sp
    private int textColor = 0xFFFFFFFF;   //显示文字的颜色

    private TextPaint textPaint;              //文字的画笔
    private String msg = "";                  //要绘制的文字

    public NineGridViewWrapper(Context context) {
        this(context, null);
    }

    public NineGridViewWrapper(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NineGridViewWrapper(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //转化单位
        textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, textSize, getContext().getResources().getDisplayMetrics());

        textPaint = new TextPaint();
        textPaint.setTextAlign(Paint.Align.CENTER);  //文字居中对齐
        textPaint.setAntiAlias(true);                //抗锯齿
        textPaint.setTextSize(textSize);             //设置文字大小
        textPaint.setColor(textColor);               //设置文字颜色
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (moreNum > 0) {
            canvas.drawColor(maskColor);
            float baseY = getHeight() / 2 - (textPaint.ascent() + textPaint.descent()) / 2;
            canvas.drawText(msg, getWidth() / 2, baseY, textPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Drawable drawable = getDrawable();
                if (drawable != null) {
                    /**
                     * 默认情况下，所有的从同一资源（R.drawable.XXX）加载来的drawable实例都共享一个共用的状态，
                     * 如果你更改一个实例的状态，其他所有的实例都会收到相同的通知。
                     * 使用使 mutate 可以让这个drawable变得状态不定。这个操作不能还原（变为不定后就不能变为原来的状态）。
                     * 一个状态不定的drawable可以保证它不与其他任何一个drawabe共享它的状态。
                     * 此处应该是要使用的 mutate()，但是在部分手机上会出现点击后变白的现象，所以没有使用
                     * 目前这种解决方案没有问题
                     */
//                    drawable.mutate().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
                    drawable.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
                    ViewCompat.postInvalidateOnAnimation(this);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                Drawable drawableUp = getDrawable();
                if (drawableUp != null) {
//                    drawableUp.mutate().clearColorFilter();
                    drawableUp.clearColorFilter();
                    ViewCompat.postInvalidateOnAnimation(this);
                }
                break;
        }

        return super.onTouchEvent(event);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setImageDrawable(null);
    }

    public int getMoreNum() {
        return moreNum;
    }

    public void setMoreNum(int moreNum) {
        this.moreNum = moreNum;
        msg = "+" + moreNum;
        invalidate();
    }

    public int getMaskColor() {
        return maskColor;
    }

    public void setMaskColor(int maskColor) {
        this.maskColor = maskColor;
        invalidate();
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
        textPaint.setTextSize(textSize);
        invalidate();
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
        textPaint.setColor(textColor);
        invalidate();
    }
}