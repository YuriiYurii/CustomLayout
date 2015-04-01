package globallogic.yuriitsap.com.customlayout;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class LayoutActivity extends ActionBarActivity {

    private AnotherCustomView mAnotherCustomView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_layout);
        mAnotherCustomView = (AnotherCustomView) findViewById(R.id.horizontal_scroll_view);
        for (int i = 0; i < 100; i++) {
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
            button.setText("Button N" + i);
            mAnotherCustomView.addView(button);

        }
    }
}
