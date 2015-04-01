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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_layout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
