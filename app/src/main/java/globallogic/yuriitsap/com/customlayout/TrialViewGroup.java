package globallogic.yuriitsap.com.customlayout;

import android.content.Context;
import android.view.MotionEvent;
import android.view.ViewGroup;

/**
 * Created by yurii.tsap on 3/30/2015.
 */
public class TrialViewGroup extends ViewGroup {
    private int mTouchSlop;

    public TrialViewGroup(Context context) {
        super(context);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }
}
