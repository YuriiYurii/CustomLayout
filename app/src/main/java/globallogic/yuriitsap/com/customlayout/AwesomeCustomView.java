package globallogic.yuriitsap.com.customlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by yuriitsap on 31.03.15.
 */
public class AwesomeCustomView extends ViewGroup {

    public AwesomeCustomView(Context context) {
        super(context);
    }

    public AwesomeCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        int parentHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(parentWidth, parentHeight);
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).measure(MeasureSpec.makeMeasureSpec(parentWidth, MeasureSpec.AT_MOST),
                    MeasureSpec.makeMeasureSpec(parentWidth, MeasureSpec.AT_MOST));

        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
//            child.layout();

        }

    }
}
