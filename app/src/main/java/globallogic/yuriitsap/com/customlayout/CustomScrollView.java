package globallogic.yuriitsap.com.customlayout;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.OverScroller;
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
    private VelocityTracker mVelocityTracker = null;
    private float mStartPoint;
    private float mEndPoint;
    private static final int SMOOTH_SCROLING_PIXEL_UNIT = 40;
    private Handler mHandler;
    private OverScroller mOverScroller;

    public CustomScrollView(Context context) {
        this(context,null);
    }

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        int index = ev.getActionIndex();
        mActivePointerId = ev.getPointerId(index);

        switch (action) {

            case MotionEvent.ACTION_DOWN:
                mStartPoint = ev.getY(mActivePointerId);
                if (mVelocityTracker == null) {
                    mVelocityTracker = VelocityTracker.obtain();
                } else {
                    mVelocityTracker.clear();
                }
                mVelocityTracker.addMovement(ev);
                break;

            case MotionEvent.ACTION_MOVE:
                mVelocityTracker.addMovement(ev);
                mVelocityTracker.computeCurrentVelocity(1000);
//                Log.d("", "X velocity: " +
//                        mVelocityTracker.getXVelocity(mActivePointerId));
//                Log.d("", "Y velocity: " +
//                        mVelocityTracker.getYVelocity(mActivePointerId));
                break;
            case MotionEvent.ACTION_UP:
                float endY = ev.getY(mActivePointerId);
                mVelocityTracker.recycle();
                mVelocityTracker = null;
                int offset = (int) (endY - mStartPoint);
                Log.e(TAG, "Offset - " + offset + " start point - " + mStartPoint + "end point "
                        + endY);
                mOverScroller.computeScrollOffset();
                mOverScroller.startScroll(0, (int) mStartPoint, 0, (int)endY);


//                scrollSmoothly(getChildAt(0), offset);
                Log.e(TAG, "Recycled");
                break;
        }
        return true;
    }

    private void scrollSmoothly(final View view, int offset) {
        for (int i = 0; i < Math.abs(offset) / SMOOTH_SCROLING_PIXEL_UNIT; i++) {
            final int scrollingStage = offset > 0 ? SMOOTH_SCROLING_PIXEL_UNIT
                    : -SMOOTH_SCROLING_PIXEL_UNIT;

//            mHandler.post(new Runnable() {
//                @Override
//                public void run() {
//                    view.offsetTopAndBottom(scrollingStage);
//
//
//                }
//            });


        }

    }

    private void init() {
        mViewConfiguration = ViewConfiguration.get(getContext());
        mTouchSlop = mViewConfiguration.getScaledTouchSlop();
        mHandler = new Handler(Looper.getMainLooper());
        mOverScroller = new OverScroller(getContext());
    }
}
