package com.irontrain;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.StringBuilderPrinter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.irontrain.action.MenuListener;
import com.irontrain.action.OCListener;
import com.irontrain.adapter.PlanDayExerciceAdapter;
import com.irontrain.business.PlanDayExercice;
import com.irontrain.storage.daos.DAOPlanDay;
import com.irontrain.business.PlanDay;
import com.irontrain.storage.daos.DAOPlanDayExercice;
import com.irontrain.tools.Tools;

import java.util.List;
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

        Button cancelButton = (Button) findViewById(R.id.cancelPlanDay);
        cancelButton.setTag(planDay.getPlan());
        cancelButton.setOnClickListener(OCListener.getPlanListListener());

        Button saveButton = (Button)findViewById(R.id.savePlanDay);
        Button addExerciceButton = (Button) findViewById(R.id.addExercice);
        addExerciceButton.setTag(planDay);
        addExerciceButton.setOnClickListener(OCListener.getNewExerciceListener());
        name = (EditText) findViewById(R.id.planDayName);
        description = (EditText) findViewById(R.id.planDayDescription);
        if(planDay.getId()!=null){
            name.setText(planDay.getName());
            description.setText(planDay.getDescription());
        }
        saveButton.setTag(planDay);
        saveButton.setOnClickListener(onSaveListener);
        if(null != planDay.getId()) {
            List<PlanDayExercice> planDayExercices = DAOPlanDayExercice.getAllPlanDayExercicesByPlanDay(getApplicationContext(), planDay.getId());
            initPlanDayExerciceList(planDayExercices);
        }

    }

    private void initPlanDayExerciceList(List<PlanDayExercice> planDayExerciceList){
        PlanDayExerciceAdapter adapter = new PlanDayExerciceAdapter(this,
                R.layout.custom_planday_list_item, planDayExerciceList);
        ListView listView = (ListView)findViewById(R.id.planDayExercices);
        listView.setAdapter(adapter);
        listView.setTag(planDayExerciceList);
        listView.setOnItemClickListener(OCListener.getOpenPlanDayExerciceListener());
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
                Tools.showToast(getApplicationContext(),getString(R.string.saveMessage));
            }
        } catch (Exception e) {
            Log.d(LOG_TAG, "Error in savePlanDay " + e);
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
