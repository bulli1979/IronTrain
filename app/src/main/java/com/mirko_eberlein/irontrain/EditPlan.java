package com.mirko_eberlein.irontrain;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.mirko_eberlein.irontrain.action.OCListener;
import com.mirko_eberlein.irontrain.business.Plan;
import com.mirko_eberlein.irontrain.storage.DAOPlan;

import java.util.UUID;

/**
 * Created by Ebi on 16.02.2016.
 */
public class EditPlan extends AppCompatActivity {
    private Plan plan;
    private static final String LOG_TAG = Home.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createplan);
        Intent i = getIntent();
        // Receiving the Data
        if(!i.hasExtra("plan")){
            plan = new Plan();
            plan.setId(UUID.randomUUID().toString());
        }else{
            plan = DAOPlan.getPlanById(this.getBaseContext(),i.getStringExtra("plan"));

        }

        Button saveButton = (Button) findViewById(R.id.savePlan);
        saveButton.setTag(plan);
        saveButton.setOnClickListener(OCListener.getPlanSaveListener());
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
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public Plan getPlan(){
        return plan;
    }
}
