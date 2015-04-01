package globallogic.yuriitsap.com.customlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;

/**
 * Created by yuriitsap on 01.04.15.
 */
public class CustomButton extends Button {
    private static final String TAG = "CustomButton";

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG, "ACTION_DOWN cordinates: y - " + event.getY() + " x - " + event.getX());
                performClick();
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
