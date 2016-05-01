package com.mirko_eberlein.irontrain;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mirko_eberlein.irontrain.action.OCListener;
import com.mirko_eberlein.irontrain.business.Plan;
import com.mirko_eberlein.irontrain.storage.daos.DAOPlan;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Ebi on 16.02.2016.
 */
public class EditPlanActivity extends AppCompatActivity {
    private Plan plan;
    private static final String LOG_TAG = HomeActivity.class.getSimpleName();
    private EditText name;
    EditText description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_plan);
        Intent i = getIntent();
        // Receiving the Data
        if(!i.hasExtra("plan")){
            plan = new Plan();
        }else{
            plan = DAOPlan.getPlanById(this.getBaseContext(),i.getStringExtra("plan"));

        }
        Button saveButton = (Button) findViewById(R.id.savePlan);
        name = (EditText) findViewById(R.id.planName);
        description = (EditText) findViewById(R.id.planDescription);
        if(plan.getId()!=null){
            name.setText(plan.getName());
            description.setText(plan.getDescription());
        }
        saveButton.setTag(plan);
        saveButton.setOnClickListener(onSaveListener);


        Button cancelButton = (Button) findViewById(R.id.cancelEditPlan);
        cancelButton.setOnClickListener(OCListener.getPlanListListener());

        Button newPlanDayButton = (Button) findViewById(R.id.addPlanDay);
        newPlanDayButton.setTag(plan);
        newPlanDayButton.setOnClickListener(OCListener.getNewPlanDayListener());

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

    public Plan getPlan(){
        return plan;
    }

    private View.OnClickListener onSaveListener = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            try {
                Plan plan = (Plan) v.getTag();
                Log.d(LOG_TAG, "Planname: "+ name.getText().toString());
                Log.d(LOG_TAG, "Planbeschreibung: "+name.getEditableText().toString());
                plan.setName(name.getEditableText().toString());
                plan.setDescription(description.getText().toString());
                if(plan.getId()==null) {
                    plan.setId(UUID.randomUUID().toString());
                    plan.setCreatedon(new Date());
                    DAOPlan.newPlan(plan, v.getContext());
                }else{
                    DAOPlan.updatePlan(plan,v.getContext());
                }


                Log.d(LOG_TAG,"Plan gespeichert");
                Toast toast = Toast.makeText(getApplicationContext(),getResources().getString(R.string.saveMessage),Toast.LENGTH_LONG);
                toast.setGravity(Gravity.BOTTOM, 0, 10);
                toast.show();
            } catch (Exception e) {
                Log.d(LOG_TAG, "Error in getNewPlanListener " + e);
            }
        }
    };
}
