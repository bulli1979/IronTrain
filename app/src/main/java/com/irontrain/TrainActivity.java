package com.irontrain;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import com.irontrain.action.MenuListener;
import com.irontrain.adapter.PlanDaySpinnerAdapter;
import com.irontrain.adapter.PlanSpinnerAdapter;
import com.irontrain.business.Plan;
import com.irontrain.business.PlanDay;
import com.irontrain.storage.daos.DAOPlan;
import com.irontrain.storage.daos.DAOPlanDay;
import com.irontrain.tools.Tools;

import java.util.List;

/**
 * createdBy Mirko
 * class to handle Activity train select train and select trainday
 *
 * */
public class TrainActivity extends AppCompatActivity {
    private Spinner planSpinner;
    private Spinner planDaySpinner;
    private List<Plan> planList;
    private static final String LOG_TAG = TrainActivity.class.getSimpleName();
    private LocationListener locationListener;
    private TextView planName;
    private TextView planDescription;
    private TextView planDayName;
    private TextView planDayDescription;
    LocationManager lm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train);
        planList = DAOPlan.getAllPlans(getApplicationContext());
        planSpinner = (Spinner)findViewById(R.id.plan);
        planDaySpinner = (Spinner) findViewById(R.id.planDay);
        planName = (TextView)findViewById(R.id.planName);
        planDescription = (TextView)findViewById(R.id.planDescription);
        planDayName = (TextView)findViewById(R.id.planDayName);
        planDayDescription = (TextView)findViewById(R.id.planDayDescription);
        initPlanSpinner();
        try {
            planSpinner.setOnItemSelectedListener(planSpinerSelect);
        }catch (Exception e){
            Log.d(LOG_TAG,"Error " + e.toString());
        }
        Button startTrain = (Button)findViewById(R.id.startTrain);
        startTrain.setOnClickListener(starTrainListener);
        lm = (LocationManager) getSystemService(android.content.Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d(LOG_TAG,"LogChanged " + location.getLatitude() + " : " + location.getLongitude());
            }
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                //not needed
            }
            @Override
            public void onProviderEnabled(String provider) {
                //not needed
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


    private  AdapterView.OnItemSelectedListener planSpinerSelect =  new  AdapterView.OnItemSelectedListener(){
        @Override
        public void onItemSelected(AdapterView<?> arg0, View view,
                                   int arg2, long arg3) {
            Plan plan = (Plan) planSpinner.getSelectedItem();
            planName.setText(plan.getName());
            planDescription.setText(plan.getDescription());
            planDayName.setText("");
            planDayDescription.setText("");
            List<PlanDay> planDayList = DAOPlanDay.getAllPlanDaysByPlan(getApplicationContext(), plan.getId());
            PlanDaySpinnerAdapter planDayAdapter = new PlanDaySpinnerAdapter(getApplicationContext(), R.layout.spinner, planDayList);
            planDaySpinner.setAdapter(planDayAdapter);
            planDaySpinner.setOnItemSelectedListener(planDaySpinerSelect);
            view.refreshDrawableState();
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            Log.d(LOG_TAG,"nothing sleected");
        }
    };

    private  AdapterView.OnItemSelectedListener planDaySpinerSelect =  new  AdapterView.OnItemSelectedListener(){
        @Override
        public void onItemSelected(AdapterView<?> arg0, View view,
                                   int arg2, long arg3) {
            PlanDay planDay = (PlanDay) planDaySpinner.getSelectedItem();
            planDayName.setText(planDay.getName());
            planDayDescription.setText(planDay.getDescription());
            view.refreshDrawableState();
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            Log.d(LOG_TAG,"nothing sleected");
        }
    };

    View.OnClickListener starTrainListener = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            PlanDay planDay = (PlanDay) planDaySpinner.getSelectedItem();
            if(null == planDay){
                Tools.getInstance().showToast(v.getContext(),getResources().getString(R.string.selectPlanDay));
                return;
            }


            AlertDialog alertDialog = new AlertDialog.Builder(TrainActivity.this).create();
            alertDialog.setTitle("Achtung");
            alertDialog.setMessage("Ort zum Training hinzufügen");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ja",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        LocationManager locationManager = (LocationManager) getSystemService(android.content.Context.LOCATION_SERVICE);
                        // get the last know location from your location manager.
                        int permission = checkCallingOrSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
                        Log.d(LOG_TAG," permission " + permission);
                        if(permission == PermissionChecker.PERMISSION_GRANTED)
                            dialog.dismiss();
                            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, locationListener);
                            Location location= locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            String lon = "";
                            String lat = "";
                            if(location != null) {
                                // now get the lat/lon from the location and write it down for the next Intent
                                lon = Double.toString(location.getLongitude());
                                lat = Double.toString(location.getLatitude());
                            }
                        doNext(lat,lon,v);

                    }
            });
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE,"Nein",
                    new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            doNext("","",v);
                        }
                    }
            );
            alertDialog.show();


        }
    };

    private void doNext(String lat,String lon,View v){
        Intent nextScreen = new Intent(getApplicationContext(), DoTrainActivity.class);
        nextScreen.putExtra("lat",lat);
        nextScreen.putExtra("lon",lon);
        PlanDay planDay = (PlanDay) planDaySpinner.getSelectedItem();
        nextScreen.putExtra("planDay",planDay.getId());
        v.getContext().startActivity(nextScreen);
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
