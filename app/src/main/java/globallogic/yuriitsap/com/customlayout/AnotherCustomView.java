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
            int childWidthMeasureSpec = resolveMeasureSpec(parentWidth);
            int childHeightMeasureSpec = resolveMeasureSpec(parentHeight);
            child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
            maxChildHeight = maxChildHeight > child.getMeasuredHeight() ? maxChildHeight
                    : child.getMeasuredHeight();
        }
        setMeasuredDimension(parentWidth, maxChildHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int currentLeft = l + getPaddingLeft();
        int currentTop = 0;
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
        if (motionInChild(ev.getX(), ev.getY())) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mStartPoint = ev.getX();
                    break;
                case MotionEvent.ACTION_MOVE:
                    mEndPoint = ev.getX();
                    if (mTouchSlop < Math.abs(mEndPoint - mStartPoint)) {
                        return true;
                    }
            }
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG, "ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                requestDisallowInterceptTouchEvent(true);
                mStartPoint = mEndPoint;
                mEndPoint = event.getX();
                int offset = (int) (mEndPoint - mStartPoint);
                for (int i = 0; i < getChildCount(); i++) {
                    getChildAt(i).offsetLeftAndRight(offset);
                }
                return true;
        }
        return false;
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new LayoutParams(layoutParams);
    }

    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
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
