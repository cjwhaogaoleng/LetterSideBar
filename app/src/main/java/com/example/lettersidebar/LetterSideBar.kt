package com.example.lettersidebar

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View


class LetterSideBar  constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    View(context, attrs, defStyleAttr) {
    private val mPaint: Paint
    private val mTouchPaint: Paint
    private val mTextColor: Int
    private val mTouchColor = 0
    private val mTextSize = 0
    private val a = 'A'

    //当前触摸的位置的字母
    private var mCurrentTouchLetter = 0.toChar()
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //计算宽度  字母宽度(画笔）+padding
        val textWidth = mPaint.measureText("A").toInt()
        val width = textWidth + paddingLeft + paddingRight
        //高度直接获取
        val height = MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        val itemHeight = (height - paddingBottom - paddingTop) / 26
        var c: Char
        var x: Int
        for (i in 0..25) {
            val letterCenterY = itemHeight / 2 + i * itemHeight + paddingTop
            val metrics = mPaint.fontMetrics
            val dy = ((metrics.bottom - metrics.top) / 2 - metrics.bottom).toInt()
            val baseLine = letterCenterY + dy
            c = (a + i).toChar()
            x = ((width - mPaint.measureText(c.toString())) / 2).toInt()

            //优化，最好用两个画笔
            if (c == mCurrentTouchLetter) {
                canvas.drawText(c.toString(), x.toFloat(), baseLine.toFloat(), mPaint)
            } else {
                canvas.drawText(c.toString(), x.toFloat(), baseLine.toFloat(), mTouchPaint)
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val itemHeight = (height - paddingBottom - paddingTop) / 26
        when (event.action) {
            MotionEvent.ACTION_MOVE, MotionEvent.ACTION_DOWN -> {
                val currentMoveY = event.y
                var currentPosition = (currentMoveY - paddingTop) / itemHeight
                if (currentPosition < 0) {
                    currentPosition = 0f
                }
                if (currentPosition > 25) {
                    currentPosition = 25f
                }
                val touchLetter: Char = a + currentPosition.toInt()

                //优化，不重复绘制
                mCurrentTouchLetter = if (mCurrentTouchLetter == touchLetter) {
                    return true
                } else touchLetter
                if (letterTouchListener != null) {
                    letterTouchListener!!.touch(mCurrentTouchLetter, true)
                }
                invalidate()
            }
            MotionEvent.ACTION_UP -> if (letterTouchListener != null) {
                letterTouchListener!!.touch(mCurrentTouchLetter, false)
            }
        }
        return true
    }

    private var letterTouchListener: LetterTouchListener? = null

    init {
        mTextColor = Color.BLUE
        mPaint = Paint()
        mPaint.isAntiAlias = true
        //自定义属性
        mPaint.textSize = util.sp2px(context, 20).toFloat()
        mPaint.color = mTextColor
        mTouchPaint = Paint()
        mTouchPaint.color = Color.RED
        mTouchPaint.isAntiAlias = true
        mTouchPaint.textSize = util.sp2px(context, 20).toFloat()
    }

    fun setLetterTouchListener(letterTouchListener: LetterTouchListener?) {
        this.letterTouchListener = letterTouchListener
    }

    interface LetterTouchListener {
        fun touch(letter: Char, ifTouch: Boolean)
    }
}