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

    private ViewGroup.LayoutParams mLayoutParams;

    public CustomLayout(Context context, AttributeSet attr) {
        super(context, attr);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int measuredWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measuredHeight = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(measuredWidth, heightMeasureSpec);


        for (int i = getChildCount() - 1; i >= 0; --i) {
            View child = getChildAt(i);
            child.setLayoutParams(generateDefaultLayoutParams());
            int widthMode = MeasureSpec.EXACTLY;
            int heightMode = MeasureSpec.EXACTLY;
            int newChildWidthMeasureSpec = MeasureSpec.makeMeasureSpec(child.getLayoutParams().width, widthMode);
            int newChildHeightMeasureSpec = MeasureSpec.makeMeasureSpec(child.getLayoutParams().width, heightMode);
            child.measure(newChildWidthMeasureSpec, newChildHeightMeasureSpec);
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
            if (child.getVisibility() != GONE) {

                int childWidth = child.getMeasuredWidth();
                int childHeight = child.getMeasuredHeight();
                int currentLeft = random.nextInt(parenRight - childWidth - parentLeft) + parentLeft;
                int currentTop = random.nextInt(parentBottom - childHeight - parentTop) + parentTop;
                int currentRight = currentLeft + childWidth;
                int currentBottom = currentTop + childHeight;
                child.layout(currentLeft, currentTop, currentRight, currentBottom);

            }

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
        int childWidth = this.getMeasuredWidth()/getChildCount();
        int childHeight = this.getMeasuredHeight()/getChildCount();
        boolean measured = true;
        return new LayoutParams(childWidth,childHeight,measured);
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

        public int height;
        public int width;
        public boolean sizeMeasured;

        /**
         * Continue,Break,Return - done
         * implement own logic of measuring views without helper functions - done
         * code convention really needed
         * UI overview
         * layout params with boolean
         * workflow of parent and child
         * we don't need seting layout params out of the view
         * x,y != width,height
         * Layout must be random
         */


        public LayoutParams(int width, int height, boolean measured) {
            super(width, height);
            this.width = width;
            this.height = height;
            this.sizeMeasured = measured;
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.width = layoutParams.width;
            this.height = layoutParams.height;

        }

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }
    }
}
