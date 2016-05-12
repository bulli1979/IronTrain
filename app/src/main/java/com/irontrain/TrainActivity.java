package com.irontrain;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
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
    private LocationListener locationListener;
    LocationManager lm;
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
        lm = (LocationManager) getSystemService(getApplicationContext().LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d(LOG_TAG,"LogChanged " + location.getLatitude() + " : " + location.getLongitude());
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent internt = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(internt);
            }
        };


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
                        int geo =  ContextCompat.checkSelfPermission(v.getContext(),Manifest.permission.ACCESS_FINE_LOCATION);
                        if (geo == PermissionChecker.PERMISSION_GRANTED){
                            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, locationListener);
                            Location loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if(loc != null) {
                                //TODO save here the last location
                                Log.d(LOG_TAG, "Lat : " + loc.getLatitude() + " " + loc.getLongitude());
                            }
                        }

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
