package com.mirko_eberlein.irontrain;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.mirko_eberlein.irontrain.action.OCListener;
import com.mirko_eberlein.irontrain.storage.DBHelper;

public class HomeActivity extends AppCompatActivity {
    private Button updateButton;
    private Button planListButton;
    private static final String LOG_TAG = HomeActivity.class.getSimpleName();
    private DBHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //onCreateOptionsMenu(Menu)
        setContentView(R.layout.activity_home);
        initialize();
    }

    public void initialize(){
        updateButton = (Button) findViewById(R.id.updateButton);
        planListButton = (Button) findViewById(R.id.planListButton);
        Log.d(LOG_TAG,"APP initialized");
        updateButton.setOnClickListener(OCListener.getUpdateListener());
        planListButton.setOnClickListener(OCListener.getPlanListListener());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the HomeActivity/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
