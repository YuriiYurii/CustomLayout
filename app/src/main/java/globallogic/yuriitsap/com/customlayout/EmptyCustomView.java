package globallogic.yuriitsap.com.customlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

/**
 * Created by yuriitsap on 01.04.15.
 */
public class EmptyCustomView extends ViewGroup {

    private float mStartPoint;
    private float mEndPoint;
    private int mTouchSlop;


    private static final String TAG = "EmptyCustomView";


    public EmptyCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        getChildAt(0).layout(l, t, r, b);

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStartPoint = ev.getX();
                Log.e(TAG,
                        "Intercept ACTION_DOWN cordinates: y - " + ev.getY() + " x - " + ev.getX());
            case MotionEvent.ACTION_MOVE:
                Log.e(TAG,
                        "Intercept ACTION_MOVE cordinates: y - " + ev.getY() + " x - " + ev.getX());
                mEndPoint = ev.getX();
                if (mTouchSlop < Math.abs(mEndPoint - mStartPoint)) {
                    return true;
                }
                break;

        }
        return false;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG, "ACTION_DOWN cordinates: y - " + event.getY() + " x - " + event.getX());
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e(TAG, "ACTION_MOVE cordinates: y - " + event.getY() + " x - " + event.getX());
                break;
            case MotionEvent.ACTION_UP:
                Log.e(TAG, "ACTION_UP cordinates: y - " + event.getY() + " x - " + event.getX());
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.e(TAG,
                        "ACTION_CANCEL cordinates: y - " + event.getY() + " x - " + event.getX());
                break;
        }
        return true;
    }
}
