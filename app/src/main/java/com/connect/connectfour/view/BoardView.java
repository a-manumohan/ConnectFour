package com.connect.connectfour.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.connect.connectfour.R;

/**
 * Created by manuMohan on 15/01/27.
 */
public class BoardView extends View {
    private Paint mLinePaint;

    private int mWidth, mHeight;
    private int mXlineIndex, mYlineIndex;

    private int mCircleCenterX,mCircleCenterY;

    private GestureDetector mGestureDetector;

    public BoardView(Context context) {
        super(context);
    }

    public BoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BoardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public BoardView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(getContext().getResources().getColor(R.color.board_line_color));

        mGestureDetector = new GestureDetector(this.getContext(), new GestureListener());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mWidth = w;
        mHeight = h;

        mXlineIndex = w / 7;
        mYlineIndex = h / 6;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i <= 7; ++i) {
            canvas.drawLine(i * mXlineIndex, 0, i * mXlineIndex, mHeight, mLinePaint);
        }
        for (int i = 0; i <= 6; ++i) {
            canvas.drawLine(0, i * mYlineIndex, mWidth, i * mYlineIndex, mLinePaint);
        }

        canvas.drawCircle(mCircleCenterX,mCircleCenterY,50.0f,mLinePaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return mGestureDetector.onTouchEvent(event);
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            mCircleCenterX  = (int) e.getX();
            mCircleCenterY = (int) e.getY();
            postInvalidate();

            return super.onSingleTapUp(e);
        }
    }
}
