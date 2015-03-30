package globallogic.yuriitsap.com.customlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ScrollView;

/**
 * Created by yurii.tsap on 3/30/2015.
 */
public class CustomScrollView extends ScrollView {

    private int mTouchSlop;
    private ViewConfiguration mViewConfiguration;
    private boolean mIsScrolling;
    private int mActivePointerId;
    private static final String TAG = "CustomScrollView";

    public CustomScrollView(Context context) {
        super(context);
        init();
    }

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
//        if (action == MotionEvent.ACTION_MOVE && mIsScrolling) {
//            return true;
//        }
        int index= ev.getActionIndex();
        mActivePointerId=ev.getPointerId(index);
        float currentY = ev.getY(mActivePointerId);
        System.out.println("adsasdasd!");
        switch (action){
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_DOWN:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        return super.onTouchEvent(ev);
    }

    private void init() {
        mViewConfiguration = ViewConfiguration.get(getContext());
        mTouchSlop = mViewConfiguration.getScaledTouchSlop();
    }
}
