package globallogic.yuriitsap.com.customlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

/**
 * Created by yuriitsap on 31.03.15.
 */
public class AnotherCustomView extends ViewGroup {

    private static final String TAG = "AnotherCustomView";
    private float mStartPoint;
    private float mEndPoint;
    private int mTouchSlop;

    public AnotherCustomView(Context context) {
        super(context);
    }

    public AnotherCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        int parentHeight = MeasureSpec.getSize(heightMeasureSpec);
        int maxChildHeight = parentHeight;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            int childWidthMeasureSpec = resolveMeasureSpec(
                    parentWidth - (getPaddingLeft() + getPaddingRight()));
            int childHeightMeasureSpec = resolveMeasureSpec(parentHeight);
            child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
            maxChildHeight = Math.max(maxChildHeight, child.getMeasuredHeight());
        }
        setMeasuredDimension(parentWidth, maxChildHeight * 4);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int currentLeft = getPaddingLeft();
        int currentTop = getPaddingTop();
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            int currentBottom = currentTop + child.getMeasuredHeight();
            int currentRight = currentLeft + child.getMeasuredWidth();
            child.layout(currentLeft, currentTop, currentRight,
                    currentBottom);
            currentLeft = currentRight;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG, "onInterceptTouchEvent ACTION_DOWN");
                mStartPoint = ev.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e(TAG, "onInterceptTouchEvent ACTION_MOVE");
                mEndPoint = ev.getX();
                if (mTouchSlop < Math.abs(mEndPoint - mStartPoint)) {
                    requestDisallowInterceptTouchEvent(true);
                    return true;
                }
        }

        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (motionInChild(event.getX(), event.getY())) {
                    mStartPoint = event.getY();
                }
//                mStartPoint = event.getX();
                Log.e(TAG, "onTouchEvent ACTION_DOWN");

                return true;
            case MotionEvent.ACTION_MOVE:
                Log.e(TAG, "onTouchEvent ACTION_MOVE");
//                if(getY()<)
                mStartPoint = mEndPoint;
                mEndPoint = event.getX();
                int offset = (int) (mEndPoint - mStartPoint);
                if (getChildAt(0).getLeft() + offset >= getLeft()
                        || getChildAt(getChildCount() - 1).getRight() + offset <= getRight()) {
                    break;
                }
                for (int i = getChildCount() - 1; i >= 0; i--) {
                    getChildAt(i).offsetLeftAndRight(offset);
                }
//                Log.e(TAG, "Start = " + mStartPoint + " End = " + mEndPoint);
                return true;
        }
        return false;
    }

    private boolean motionInChild(float x, float y) {
        if ((x > getLeft() + getPaddingLeft() && x < getRight() - getPaddingRight() && (
                y > getPaddingTop() && y < getBottom() - getTop() - getPaddingBottom()))) {
            return true;
        }
        return false;
    }

    private int resolveMeasureSpec(int parentSize) {
        return MeasureSpec.makeMeasureSpec(parentSize, MeasureSpec.UNSPECIFIED);

    }
}
