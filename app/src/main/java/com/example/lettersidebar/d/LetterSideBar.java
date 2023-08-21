package com.example.lettersidebar.d;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.lettersidebar.util;


public class LetterSideBar extends View {

    private Paint mPaint, mTouchPaint;

    private int mTextColor, mTouchColor;

    private int mTextSize;
    private char a = 'A';
    //当前触摸的位置的字母
    private char mCurrentTouchLetter;


    public LetterSideBar(Context context) {
        this(context, null);
    }

    public LetterSideBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LetterSideBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mTextColor = Color.BLUE;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        //自定义属性
        mPaint.setTextSize(util.sp2px(context, 20));
        mPaint.setColor(mTextColor);

        mTouchPaint = new Paint();
        mTouchPaint.setColor(Color.RED);
        mTouchPaint.setAntiAlias(true);
        mTouchPaint.setTextSize(util.sp2px(context, 20));

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //计算宽度  字母宽度(画笔）+padding

        int textWidth = (int) mPaint.measureText("A");
        int width = textWidth + getPaddingLeft() + getPaddingRight();
        //高度直接获取
        int height = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(width, height);

    }

    @Override
    protected void onDraw(Canvas canvas) {

        int itemHeight = (getHeight() - getPaddingBottom() - getPaddingTop()) / 26;
        char c;
        int x;
        for (int i = 0; i < 26; i++) {
            int letterCenterY = itemHeight / 2 + i * itemHeight + getPaddingTop();
            Paint.FontMetrics metrics = mPaint.getFontMetrics();

            int dy = (int) ((metrics.bottom - metrics.top) / 2 - metrics.bottom);
            int baseLine = letterCenterY + dy;

            c = (char) (a + i);
            x = (int) ((getWidth() - mPaint.measureText(String.valueOf(c))) / 2);

            //优化，最好用两个画笔

            if (c == mCurrentTouchLetter) {
                canvas.drawText(String.valueOf(c), x, baseLine, mPaint);
            } else {
                canvas.drawText(String.valueOf(c), x, baseLine, mTouchPaint);
            }

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int itemHeight = (getHeight() - getPaddingBottom() - getPaddingTop()) / 26;
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_DOWN:

                float currentMoveY = event.getY();
                float currentPosition = (currentMoveY - getPaddingTop()) / itemHeight;

                if (currentPosition < 0) {
                    currentPosition = 0;
                }
                if (currentPosition > 25) {
                    currentPosition = 25;
                }
                char touchLetter = (char) (a + currentPosition);

                //优化，不重复绘制
                if (mCurrentTouchLetter == touchLetter) {
                    return true;
                } else mCurrentTouchLetter = touchLetter;

                if (letterTouchListener != null) {
                    letterTouchListener.touch(mCurrentTouchLetter, true);
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                if (letterTouchListener != null) {
                    letterTouchListener.touch(mCurrentTouchLetter, false);
                }
                break;
        }
        return true;
    }

    private LetterTouchListener letterTouchListener;

    public void setLetterTouchListener(LetterTouchListener letterTouchListener) {
        this.letterTouchListener = letterTouchListener;
    }

    public interface LetterTouchListener {
        void touch(char letter, boolean ifTouch);
    }
}
