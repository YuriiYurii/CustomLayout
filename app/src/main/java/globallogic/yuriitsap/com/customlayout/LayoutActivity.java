package globallogic.yuriitsap.com.customlayout;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class LayoutActivity extends ActionBarActivity {

    private AnotherCustomView mAnotherCustomView;
    private static final String TAG="LayoutActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_layout);
        mAnotherCustomView = (AnotherCustomView) findViewById(R.id.horizontal_scroll_view);
        for (int i = 0; i < 10; i++) {
            Button button = new Button(LayoutActivity.this);
            button
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(LayoutActivity.this, "Your answer is correct!",
                                    Toast.LENGTH_SHORT).
                                    show();

                        }
                    });
            button.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            Log.e(TAG, "Button onTouchEvent ACTION_DOWN");
                            break;
                        case MotionEvent.ACTION_MOVE:
                            Log.e(TAG, "Button onTouchEvent ACTION_MOVE");
                            break;
                        case MotionEvent.ACTION_CANCEL:
                            Log.e(TAG, "Button onTouchEvent ACTION_CANCEL");
                            break;
                    }
                    return false;
                }
            });
            button.setText("Button N" + i);
            mAnotherCustomView.addView(button);

        }
    }
}
