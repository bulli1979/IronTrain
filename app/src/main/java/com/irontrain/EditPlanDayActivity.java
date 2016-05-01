package com.irontrain;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.irontrain.storage.daos.DAOPlanDay;
import com.irontrain.business.PlanDay;

import java.util.UUID;

public class EditPlanDayActivity extends AppCompatActivity {
    private EditText name;
    private EditText description;
    private static final String LOG_TAG = EditPlanDayActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_plan_day);
        Intent i = getIntent();
        // Receiving the Data
        PlanDay planDay;
        if(i.hasExtra("plan")){
            planDay = new PlanDay.Builder().plan(i.getExtras().getString("plan")).build();
        }else{
            String id = i.getExtras().getString("planDay");
            planDay = DAOPlanDay.getPlanDayById(getApplicationContext(),id);
        }
        Button saveButton = (Button)findViewById(R.id.savePlanDay);

        name = (EditText) findViewById(R.id.planDayName);
        description = (EditText) findViewById(R.id.planDayDescription);
        if(planDay.getId()!=null){
            name.setText(planDay.getName());
            description.setText(planDay.getDescription());
        }
        saveButton.setTag(planDay);
        saveButton.setOnClickListener(onSaveListener);
    }


    private View.OnClickListener onSaveListener = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            savePlanDay(v,true);
        }
    };

    private void savePlanDay(View v,boolean showMessage){
        try {
            PlanDay planDay = (PlanDay) v.getTag();
            Log.d(LOG_TAG, name.getText().toString());
            Log.d(LOG_TAG, name.getEditableText().toString());
            planDay.setName(name.getEditableText().toString());
            planDay.setDescription(description.getText().toString());
            if(null == planDay.getId()) {
                Log.i(LOG_TAG,"h1 " + planDay.getName());
                planDay.setId(UUID.randomUUID().toString());
                DAOPlanDay.newPlanDay(planDay,getApplicationContext());
            }else{
                DAOPlanDay.updatePlanDay(planDay,getApplicationContext());
            }
            Log.d(LOG_TAG,"PlanDay gespeichert");
            if(showMessage) {
                Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.saveMessage), Toast.LENGTH_LONG);
                toast.setGravity(Gravity.BOTTOM, 0, 10);
                toast.show();
            }
        } catch (Exception e) {
            Log.d(LOG_TAG, "Error in savePlanDay " + e);
        }
    }



}
