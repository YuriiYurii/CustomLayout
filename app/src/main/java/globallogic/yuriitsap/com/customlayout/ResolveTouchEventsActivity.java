package globallogic.yuriitsap.com.customlayout;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;


public class ResolveTouchEventsActivity extends Activity {

    private LinearLayout mLinearLayout;
    private static final String TAG = "ResolveTouch";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_resolve_touch_events);

        mLinearLayout = (LinearLayout) findViewById(R.id.parent_view);
        for (int i = 0; i <= 100; i++) {
            final Button button = new Button(ResolveTouchEventsActivity.this);
            button.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            final String number=String.valueOf(i);
            button.setText("Button N"+number);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    Log.e(TAG, "Butoon was pressed" + number);
                }
            });
            mLinearLayout.addView(button);
        }
    }
}
