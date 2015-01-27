package com.connect.connectfour.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.connect.connectfour.R;
import com.connect.connectfour.game.ConnectFour;

/**
 * Created by manuMohan on 15/01/27.
 */
public class BoardView extends View {
    private BoardViewListener mBoardViewListener;

    private Paint mBackgroundPaint;
    private Paint mCirclePaint;
    private Paint mOuterCirclePaint;
    private Paint mFirstCirclePaint;
    private Paint mSecondCirclePaint;

    private int mWidth, mHeight;
    private int mColumnIndex;
    private int mCircleRadius;

    private final int minHorizontalMargin = 20;
    private final int minVerticalMargin = 20;
    private  int horizontalMargin = 20;
    private  int verticalMargin = 20;

    private int strokeWidth = 5;

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

        mBackgroundPaint = new Paint(0);
        mBackgroundPaint.setColor(getContext().getResources().getColor(R.color.grey));

        mOuterCirclePaint = new Paint(0);
        mOuterCirclePaint.setStyle(Paint.Style.STROKE);
        mOuterCirclePaint.setStrokeWidth(strokeWidth);
        mOuterCirclePaint.setColor(getContext().getResources().getColor(R.color.black));

        mCirclePaint = new Paint(0);
        mCirclePaint.setColor(getContext().getResources().getColor(R.color.white));
        mCirclePaint.setStyle(Paint.Style.FILL);

        mFirstCirclePaint = new Paint(0);
        mFirstCirclePaint.setColor(getContext().getResources().getColor(R.color.first_player_color));
        mSecondCirclePaint = new Paint(0);
        mSecondCirclePaint.setColor(getContext().getResources().getColor(R.color.second_player_color));

        mGestureDetector = new GestureDetector(this.getContext(), new GestureListener());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mWidth = w;
        mHeight = h;

        mColumnIndex = w / 7;

        if (mWidth > mHeight) {
            mCircleRadius = (mHeight - 7 * minVerticalMargin) / 12;
        } else {
            mCircleRadius = (mWidth - 8 * minHorizontalMargin) / 14;
        }

        verticalMargin = (mHeight - 12 * mCircleRadius)/7;
        horizontalMargin = (mWidth - 14 * mCircleRadius)/8;

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(0, 0, mWidth, mHeight, mBackgroundPaint);

        ConnectFour connectFour = mBoardViewListener.getConnectFour();
        ConnectFour.Node[][] gameState = connectFour.mGameState;

        float cy = mHeight + mCircleRadius;
        for (int i = 0; i < ConnectFour.ROW_COUNT; ++i) {
            cy -= (2 * mCircleRadius + verticalMargin);
            float cx = -mCircleRadius;
            for (int j = 0; j < ConnectFour.COLUMN_COUNT; ++j) {
                cx += (2 * mCircleRadius + horizontalMargin);
                if (gameState[i][j].isEmpty()) {
                    canvas.drawCircle(cx, cy, mCircleRadius - strokeWidth, mCirclePaint);
                    canvas.drawCircle(cx, cy, mCircleRadius, mOuterCirclePaint);
                } else if (gameState[i][j].getPlayer() == ConnectFour.Player.FIRST_PLAYER) {
                    canvas.drawCircle(cx, cy, mCircleRadius - strokeWidth, mFirstCirclePaint);
                    canvas.drawCircle(cx, cy, mCircleRadius, mOuterCirclePaint);
                } else {
                    canvas.drawCircle(cx, cy, mCircleRadius - strokeWidth, mSecondCirclePaint);
                    canvas.drawCircle(cx, cy, mCircleRadius, mOuterCirclePaint);
                }

            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return mGestureDetector.onTouchEvent(event);
    }

    private int getColumn(int x) {
        return x / mColumnIndex;
    }

    public void setBoardViewListener(BoardViewListener mBoardViewListener) {
        this.mBoardViewListener = mBoardViewListener;
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            int x = (int) e.getX();
            int y = (int) e.getY();
            postInvalidate();
            mBoardViewListener.playColumn(getColumn(x));
            return super.onSingleTapUp(e);
        }
    }

    public static interface BoardViewListener {
        public ConnectFour getConnectFour();

        public void playColumn(int column);
    }
}
