package globallogic.yuriitsap.com.customlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.Random;


/**
 * Created by yurii.tsap on 3/24/2015.
 */
public class CustomLayout extends ViewGroup {

    private AttributeSet mAttributeSet;

    public CustomLayout(Context context, AttributeSet attr) {
        super(context, attr);
        mAttributeSet = attr;

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int measuredWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measuredHeight = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(measuredWidth, heightMeasureSpec);

        for (int i = getChildCount() - 1; i >= 0; --i) {
            View child = getChildAt(i);
            int widthMode = MeasureSpec.AT_MOST;
            int heightMode = MeasureSpec.AT_MOST;
            child.setLayoutParams(generateDefaultLayoutParams());
            int newChildWidthMeasureSpec = MeasureSpec
                    .makeMeasureSpec(measuredWidth, widthMode);
            int newChildHeightMeasureSpec = MeasureSpec
                    .makeMeasureSpec(measuredHeight, heightMode);
            child.measure(newChildWidthMeasureSpec, newChildHeightMeasureSpec);
            ((LayoutParams) child.getLayoutParams()).setSizeMeasured(true);

        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        Random random = new Random();
        int parentLeft = l + this.getPaddingLeft();
        int parenRight = r - this.getPaddingRight();
        int parentTop = t + this.getPaddingTop();
        int parentBottom = b - this.getPaddingBottom();

        for (int i = getChildCount() - 1; i >= 0; i--) {
            View child = getChildAt(i);
            if (((LayoutParams) child.getLayoutParams()).isSizeMeasured()) {
                continue;
            }

            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            int currentLeft = random.nextInt(parenRight - childWidth - parentLeft) + parentLeft;
            int currentTop = random.nextInt(parentBottom - childHeight - parentTop) + parentTop;
            int currentRight = currentLeft + childWidth;
            int currentBottom = currentTop + childHeight;
            child.layout(currentLeft, currentTop, currentRight, currentBottom);


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

    @Deprecated
    public void gradualLayout(View child) {

        int parentLeft = this.getPaddingLeft();
        int parentTop = this.getPaddingTop();
        int parentRight = this.getMeasuredWidth() - this.getPaddingRight();
        int childBottom = this.getMeasuredHeight() - this.getPaddingBottom();
        int currentLeft = parentLeft;
        int currentTop = parentTop;
        int maxHeight = 0;
        if (child.getVisibility() != GONE) {
            int currentWidth = child.getMeasuredWidth();
            int currentHeight = child.getMeasuredHeight();
            child.layout(currentLeft, currentTop, currentLeft + currentWidth,
                    currentTop + currentHeight);
            currentLeft += currentWidth;
            if (currentLeft + currentWidth >= parentRight) {
                currentLeft = parentLeft;
                currentTop += maxHeight;
                maxHeight = 0;
            }
            maxHeight = maxHeight < currentHeight ? currentHeight : maxHeight;
            Log.e("TAG", "Current top" + currentTop);

        }

    }

    public static class LayoutParams extends ViewGroup.LayoutParams {


        public boolean sizeMeasured;


        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(int width, int height, boolean measured) {
            super(width, height);
            this.sizeMeasured = measured;
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);


        }

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public boolean isSizeMeasured() {
            return sizeMeasured;
        }

        public void setSizeMeasured(boolean sizeMeasured) {
            this.sizeMeasured = sizeMeasured;
        }
    }
}
