package globallogic.yuriitsap.com.customlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

/**
 * Created by yuriitsap on 02.04.15.
 */
public class GestureScrollView extends ViewGroup implements GestureDetector.OnGestureListener {

    private GestureDetector mGestureDetector;
    private static final String TAG = "GestureScrollView";
    private float mStartPoint;

    public GestureScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mGestureDetector = new GestureDetector(getContext(), this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        int parentHeight = MeasureSpec.getSize(heightMeasureSpec);
        for (int i = getChildCount() - 1; i >= 0; i--) {
            getChildAt(i).measure(MeasureSpec.makeMeasureSpec(parentWidth, MeasureSpec.AT_MOST),
                    MeasureSpec.makeMeasureSpec(parentHeight, MeasureSpec.AT_MOST));
        }
        setMeasuredDimension(parentWidth, parentHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int currentTop = getPaddingTop();
        for (int i = getChildCount() - 1; i >= 0; i--) {
            View child = getChildAt(i);
            child.layout(getPaddingLeft(), currentTop,
                    getPaddingLeft() + getMeasuredWidth(),
                    currentTop + child.getMeasuredHeight());
            currentTop += child.getMeasuredHeight();


        }


    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                Log.e(TAG,"onInterceptTouchEvent e"+event.getX());
                break;
            case MotionEvent.ACTION_MOVE:
                if (ViewConfiguration.get(getContext()).getScaledTouchSlop() < Math
                        .abs(event.getX() - mStartPoint)) {
                    requestDisallowInterceptTouchEvent(true);
                    return mGestureDetector.onTouchEvent(event);
                }
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        Log.e(TAG, "onTouchEvent");
        return mGestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        Log.e(TAG, "onDown");
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
//        Log.e(TAG, "onShowPress");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
//        Log.e(TAG, "onSingleTapUp");
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        int offset = (int) (e2.getY() - e2.getY() < 0 ? distanceY : -distanceY);
        for (int i = getChildCount() - 1; i >= 0; i--) {
            View child = getChildAt(i);
            child.offsetTopAndBottom(offset);
        }
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
//        Log.e(TAG, "onLongPress");

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//        Log.e(TAG, "onFling");
        return false;
    }
}
