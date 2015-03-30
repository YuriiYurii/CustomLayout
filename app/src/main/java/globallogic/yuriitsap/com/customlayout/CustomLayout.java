package globallogic.yuriitsap.com.customlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.Random;


/**
 * Created by yurii.tsap on 3/24/2015.
 */
public class CustomLayout extends ViewGroup {

    private final Random mRandom;
    /** The flag that allows views to overlap each other */
    private boolean overlap;

    public CustomLayout(Context context, AttributeSet attr) {
        super(context, attr);
        mRandom = new Random();
        initFromAttributes(context, attr);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measuredWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measuredHeight = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode;
        int heightMode;
        setMeasuredDimension(measuredWidth, heightMeasureSpec);

        for (int i = getChildCount() - 1; i >= 0; --i) {
            View child = getChildAt(i);
            LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
            switch (layoutParams.height) {
                case LayoutParams.WRAP_CONTENT:
                    heightMode = MeasureSpec.AT_MOST;
                    break;
                case LayoutParams.MATCH_PARENT:
                    heightMode = MeasureSpec.EXACTLY;
                    break;
                default:
                    heightMode = MeasureSpec.UNSPECIFIED;
                    break;
            }
            switch (layoutParams.width) {
                case LayoutParams.WRAP_CONTENT:
                    widthMode = MeasureSpec.AT_MOST;
                    break;
                case LayoutParams.MATCH_PARENT:
                    widthMode = MeasureSpec.AT_MOST;
                    break;
                default:
                    widthMode = MeasureSpec.UNSPECIFIED;
                    break;
            }
            int childWidthMeasureSpec = MeasureSpec
                    .makeMeasureSpec(measuredWidth, widthMode);
            int childHeightMeasureSpec = MeasureSpec
                    .makeMeasureSpec(measuredHeight, heightMode);
            child.measure(childWidthMeasureSpec, childHeightMeasureSpec);


        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int parentLeft = l + this.getPaddingLeft();
        int parenRight = r - this.getPaddingRight();
        int parentTop = t + this.getPaddingTop();
        int parentBottom = b - this.getPaddingBottom();

        for (int i = getChildCount() - 1; i >= 0; i--) {
            View child = getChildAt(i);

            //afer view is once laid out it's mLaid is never changed
            //it is just set while first layout traversal
            if (((LayoutParams) child.getLayoutParams()).isLaid && !viewIntersectsParentBounds(
                    child, parentLeft, parentTop, parenRight, parentBottom)) {
                continue;
            }

            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            int currentLeft = mRandom.nextInt(parenRight - childWidth - parentLeft) + parentLeft;
            int currentTop = mRandom.nextInt(parentBottom - childHeight - parentTop) + parentTop;
            int currentRight = currentLeft + childWidth;
            int currentBottom = currentTop + childHeight;

            child.layout(currentLeft, currentTop, currentRight, currentBottom);
            ((LayoutParams) child.getLayoutParams()).isLaid = true;


        }
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

    private void initFromAttributes(Context context, AttributeSet attributeSet) {
        final TypedArray typedArray = context.obtainStyledAttributes(attributeSet,
                R.styleable.CustomLayout);
        for (int n = typedArray.getIndexCount(); n >= 0; n--) {
            int attr = typedArray.getIndex(n);
            switch (attr) {
                case R.styleable.CustomLayout_overlap:
                    overlap = typedArray.getBoolean(n, false);

            }
        }

        typedArray.recycle();

    }

    public boolean viewIntersectsParentBounds(View child, int parentLeft, int parentTop,
            int parentRight,
            int parentBottom) {
        if (child.getLeft() < parentLeft
                || child.getRight() > parentRight
                || child.getBottom() > parentBottom
                || child.getTop() < parentTop) {
            return true;

        }
        return false;

    }

    public static class LayoutParams extends ViewGroup.LayoutParams {


        public boolean isLaid;


        public LayoutParams(int width, int height) {
            super(width, height);
        }


        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);


        }

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }
    }
}
