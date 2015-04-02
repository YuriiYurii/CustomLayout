package globallogic.yuriitsap.com.customlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by yuriitsap on 31.03.15.
 */
public class AnotherCustomView extends ViewGroup {

    private static final String TAG = "AnotherCustomView";
    private float mStartPoint;
    private int mTouchSlop;
    private ArrayList<Float> interceptTouchMatrix = new ArrayList<>();
    private ArrayList<Float> touchMatrix = new ArrayList<>();


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
                mStartPoint = ev.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                float endPoint = ev.getX();
                Log.e(TAG,"onInterceptTouchEvent ACTION_MOVE startPoint"+ev.getX());
                if (mTouchSlop < Math.abs(endPoint - mStartPoint)) {
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
                return true;
            case MotionEvent.ACTION_MOVE:
                Log.e(TAG,"onTouchEvent ACTION_MOVE startPoint"+event.getX());
                float endPoint = event.getX();
                int offset = (int) (endPoint - mStartPoint);
                if (intersectsLeftBorder(offset) || intersectsRightBorder(offset)) {
                    break;
                }
                for (int i = getChildCount() - 1; i >= 0; i--) {
                    getChildAt(i).offsetLeftAndRight(offset);
                }
                mStartPoint = endPoint;
                return true;
        }
        return false;
    }

    private boolean intersectsLeftBorder(int offset) {
        return getChildAt(0).getLeft() + offset > getPaddingLeft();


    }

    private boolean intersectsRightBorder(int offset) {
        return getChildAt(getChildCount() - 1).getRight() + offset
                < (getRight() - getLeft()) - getPaddingRight();

    }

    private int resolveMeasureSpec(int parentSize) {
        return MeasureSpec.makeMeasureSpec(parentSize, MeasureSpec.UNSPECIFIED);

    }
}
