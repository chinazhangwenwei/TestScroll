package com.baoyz.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by admin on 2017/3/6.
 */

public class TestRefreshLayout extends ViewGroup {
    private TextView imagegView;
    private View target;
    SwipeRefreshLayout refreshLayout;

    private int distance = 100;
    private int currentDistance = 0;

    private boolean isRfresh = false;

    public TestRefreshLayout(Context context) {
        this(context, null);
    }

    public TestRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initParams(context);

    }

    private void initParams(Context context) {
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        setWillNotDraw(false);
        imagegView = new TextView(getContext());
        ViewGroup.LayoutParams params = new ViewGroup.MarginLayoutParams(100, 50);
        imagegView.setLayoutParams(params);
//        imagegView.setBackgroundColor(Color.BLUE);
        imagegView.setVisibility(View.VISIBLE);
        imagegView.setText("测试");
        addView(imagegView, -1);
        ViewCompat.setChildrenDrawingOrderEnabled(this, true);

    }

    private static final String TAG = "TestRefreshLayout";

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d(TAG, "onMeasure: " + "测量开始");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (target == null) {
            setTarget();
        }

        if (target == null) {
            return;
        }
        int widthMeasureSpecs = MeasureSpec.makeMeasureSpec
                (getMeasuredWidth() - getPaddingRight() - getPaddingLeft(), MeasureSpec.EXACTLY);
        int heightMeasureSpecs = MeasureSpec.makeMeasureSpec(getMeasuredHeight() - getPaddingTop()
                - getPaddingBottom(), MeasureSpec.EXACTLY);
        imagegView.measure(widthMeasureSpecs, heightMeasureSpecs);
        target.measure(widthMeasureSpecs, heightMeasureSpecs);
        Log.d(TAG, "onMeasure: " + "测量完毕" + getMeasuredHeight() + "宽度" + getMeasuredWidth());
    }

    private boolean flag = true;

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        final int action = MotionEventCompat.getActionMasked(ev);
//        switch (action) {
//            case MotionEvent.ACTION_DOWN:
//                Log.d(TAG, "dispatchTouchEvent: ACTION_DOWN");
//                break;
//            case MotionEvent.ACTION_MOVE:
//                Log.d(TAG, "dispatchTouchEvent: ACTION_MOVE");
//                break;
//
//            case MotionEvent.ACTION_POINTER_DOWN:
//                Log.d(TAG, "dispatchTouchEvent: ACTION_POINTER_DOWN");
//                break;
//            case MotionEvent.ACTION_POINTER_UP:
//                Log.d(TAG, "dispatchTouchEvent: ACTION_POINTER_UP");
//                break;
//            case MotionEvent.ACTION_UP:
//                Log.d(TAG, "dispatchTouchEvent: ACTION_UP");
//                break;
//            case MotionEvent.ACTION_CANCEL:
//                Log.d(TAG, "dispatchTouchEvent: ACTION_CANCEL");
//                break;
//        }
//        return super.dispatchTouchEvent(ev);
//    }

    private boolean isIntercept = false;
    private int actionId;
    private boolean isRefresh = false;
    private float initY;
    private int dragPercent;
    private int offset;
    private boolean dispatchTouch;
    private int mTouchSlop;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!isEnabled() || (canChildScrollUp() && !isRfresh)) {
            Log.d(TAG, "onInterceptTouchEvent: canChildScrollUp");
            return false;
        }

        final int action = MotionEventCompat.getActionMasked(ev);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (!isRfresh) {
                    //初始化drawable状态
                }
                actionId = MotionEventCompat.getPointerId(ev, 0);
                isIntercept = false;
                float initYs = getMotionEventY(ev, actionId);
                if (initYs == -1) {
                    return false;
                }
                initY = initYs;
                dragPercent = 0;
                offset = 0;
                dispatchTouch = false;
                Log.d(TAG, "onInterceptHoverEvent: ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                if (actionId == -1) {
                    return false;
                }
                final float y = getMotionEventY(ev, actionId);
                float diff = y - initY;

                if (isRfresh) {
                    isIntercept = !(diff < 0 && offset <= 0);
                } else {
                    if (diff > mTouchSlop && !isIntercept) {
                        isIntercept = true;
                    }
                }
                Log.d(TAG, "onInterceptHoverEvent: ACTION_MOVE" + diff);
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                Log.d(TAG, "onInterceptHoverEvent: ACTION_POINTER_DOWN");
                break;
            case MotionEvent.ACTION_POINTER_UP:
                Log.d(TAG, "onInterceptHoverEvent: ACTION_POINTER_UP");
                onSecondaryPointerUp(ev);
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "onInterceptHoverEvent: ACTION_UP");
            case MotionEvent.ACTION_CANCEL:
                isIntercept = false;
                actionId = -1;
                Log.d(TAG, "onInterceptHoverEvent: ACTION_CANCEL");
                break;

        }
        return true;

    }

    private boolean isTouch = true;
    private int totalCurrent;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        target.dispatchTouchEvent(event);
        if (!isIntercept) {

        }
        final int action = MotionEventCompat.getActionMasked(event);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "onTouchEvent: ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                final int pointIndex = MotionEventCompat.findPointerIndex(event, actionId);
                if (pointIndex == -1) {
                    return super.onTouchEvent(event);
                }
                final float tempCurrentY = MotionEventCompat.getY(event, pointIndex);
                final float yDiff = initY - tempCurrentY;
                if (Math.abs(yDiff) > 0 && yDiff < 0) {
                    initY = tempCurrentY;
                    totalCurrent += yDiff;
                    target.offsetTopAndBottom(-1 * (int) yDiff);
                    Log.d(TAG, "onTouchEvent: yDiff" + yDiff);
                    Log.d(TAG, "onTouchEvent: getTop" + target.getTop());
                }
                if (yDiff > 0) {
                    if (totalCurrent < 0) {
                        initY = tempCurrentY;
                        totalCurrent += (int) yDiff;
                        if (totalCurrent > 0) {
                            Log.d(TAG, "onTouchEvent: shangyi " + target.getTop());
                            Log.d(TAG, "onTouchEvent: totalCurrent" + totalCurrent);
                            Log.d(TAG, "onTouchEvent: yDiff" + yDiff);
                            target.offsetTopAndBottom(-1 * target.getTop());
                            //复原代码逻辑
                        } else {
                            target.offsetTopAndBottom(-1 * (int) yDiff);
                        }
                    } else {
                        if (isTouch) {
                            Log.d(TAG, "onTouchEvent: actionMovie" + totalCurrent);
                            MotionEvent events = MotionEvent.obtain(event);
                            events.setAction(MotionEvent.ACTION_DOWN);
                            target.dispatchTouchEvent(events);
                            isTouch = false;

                        } else {
                            Log.d(TAG, "onTouchEvent: actionDown" + totalCurrent);
                            target.dispatchTouchEvent(event);
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                Log.d(TAG, "onTouchEvent: ACTION_POINTER_DOWN");
                break;
            case MotionEvent.ACTION_POINTER_UP:
                Log.d(TAG, "onTouchEvent: ACTION_POINTER_UP");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "onTouchEvent: action_up");
                break;

            case MotionEvent.ACTION_CANCEL:
                Log.d(TAG, "ACTION_CANCEL: action_up");
                break;
        }
        return true;
//        return super.onTouchEvent(event);
    }

    private void onSecondaryPointerUp(MotionEvent ev) {
        final int pointerIndex = MotionEventCompat.getActionIndex(ev);
        final int pointerId = MotionEventCompat.getPointerId(ev, pointerIndex);
        if (pointerId == actionId) {
            final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
            actionId = MotionEventCompat.getPointerId(ev, newPointerIndex);
        }
    }

    SwipeRefreshLayout refresh;

    private float getMotionEventY(MotionEvent ev, int activePointerId) {
        final int index = MotionEventCompat.findPointerIndex(ev, activePointerId);
        if (index < 0) {
            return -1;
        }
        return MotionEventCompat.getY(ev, index);
    }

    private boolean canChildScrollUp() {

//        if (android.os.Build.VERSION.SDK_INT < 14) {
//            if (mTarget instanceof AbsListView) {
//                final AbsListView absListView = (AbsListView) mTarget;
//                return absListView.getChildCount() > 0
//                        && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
//                        .getTop() < absListView.getPaddingTop());
//            } else {
//                return mTarget.getScrollY() > 0;
//            }
//        } else {
        return ViewCompat.canScrollVertically(target, -1);
//        }
    }

    private void setTarget() {
        if (target != null) {
            return;
        }
        View view;
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            view = getChildAt(i);
            if (view != imagegView) {
                target = view;
            }
        }
    }


    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (target == null) {
            setTarget();
        }
        if (target == null) {
            return;
        }
        int height = getMeasuredHeight();
        int width = getMeasuredWidth();
        int left = getPaddingLeft();
        int top = getPaddingTop();
        int right = getPaddingRight();
        int bottom = getPaddingBottom();
        target.layout(left, top + target.getTop(), left + width - right, top + height - bottom + target.getTop());
        imagegView.layout(left, top, left + width - right, top + height - bottom);
    }
}
