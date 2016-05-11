package com.irontrain;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.irontrain.adapter.PlanDaySpinnerAdapter;
import com.irontrain.adapter.PlanSpinnerAdapter;
import com.irontrain.business.Plan;
import com.irontrain.business.PlanDay;
import com.irontrain.storage.daos.DAOPlan;
import com.irontrain.storage.daos.DAOPlanDay;

import java.util.List;

public class TrainActivity extends AppCompatActivity {
    private Spinner planSpinner;
    private Spinner planDaySpinner;
    private List<Plan> planList;
    private List<PlanDay> planDayList;
    private static final String LOG_TAG = TrainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train);

        planList = DAOPlan.getAllPlans(getApplicationContext());
        planSpinner = (Spinner)findViewById(R.id.plan);
        planDaySpinner = (Spinner) findViewById(R.id.planDay);
        initPlanSpinner();
        try {
            planSpinner.setOnItemSelectedListener(planSpinerClick);
        }catch (Exception e){
            Log.d(LOG_TAG,"Error " + e.toString());
        }
        Button startTrain = (Button)findViewById(R.id.startTrain);
        startTrain.setOnClickListener(starTrainListener);
    }


    private void initPlanSpinner(){
            PlanSpinnerAdapter planAdapter =  new PlanSpinnerAdapter(this, R.layout.spinner, planList);
            planAdapter.setDropDownViewResource(R.layout.spinner);
            planSpinner.setAdapter(planAdapter);
    }


    private  AdapterView.OnItemSelectedListener planSpinerClick =  new  AdapterView.OnItemSelectedListener(){
        @Override
        public void onItemSelected(AdapterView<?> arg0, View view,
                                   int arg2, long arg3) {
            Plan plan = (Plan) planSpinner.getSelectedItem();
            planDayList = DAOPlanDay.getAllPlanDaysByPlan(getApplicationContext(), plan.getId());
            PlanDaySpinnerAdapter planDayAdapter = new PlanDaySpinnerAdapter(getApplicationContext(), R.layout.spinner, planDayList);
            planDaySpinner.setAdapter(planDayAdapter);
            view.refreshDrawableState();

        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub

        }
    };

    View.OnClickListener starTrainListener = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            AlertDialog alertDialog = new AlertDialog.Builder(TrainActivity.this).create();
            alertDialog.setTitle("Achtung");
            alertDialog.setMessage("Ort zum Training hinzufügen");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ja",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(LOG_TAG,"ja");
                        dialog.dismiss();
                    }
            });
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE,"Nein",
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(LOG_TAG,"nein");
                        dialog.dismiss();
                    }
                }
            );
            alertDialog.show();
        }
    };





}
