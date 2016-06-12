package com.irontrain;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.irontrain.action.MenuListener;
import com.irontrain.action.OCListener;


/** created by Mirko Eberlein
 * Home Activity only 4 Buttons and the Menu
 * Main Activity start of the app
 * Verantwortlich: Mirko Eberlein
 * */
public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initialize();
    }

    public void initialize(){
        Button updateButton = (Button) findViewById(R.id.updateButton);
        Button planListButton = (Button) findViewById(R.id.planListButton);
        Button trainButton = (Button) findViewById(R.id.trainButton);
        Button statsButton = (Button) findViewById(R.id.statsButton);
        if(updateButton != null) {
            updateButton.setOnClickListener(OCListener.getInstance().getUpdateListener());
        }
        if(planListButton != null){
            planListButton.setOnClickListener(OCListener.getInstance().getPlanListListener());
        }
        if(trainButton != null){
            trainButton.setOnClickListener(OCListener.getInstance().openTrainListener());
        }
        if(statsButton != null){
            statsButton.setOnClickListener(OCListener.getInstance().openStatsListener());
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Menuitem
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        MenuListener.getActionMenuComplete(this,id);
        return super.onOptionsItemSelected(item);
    }
}
