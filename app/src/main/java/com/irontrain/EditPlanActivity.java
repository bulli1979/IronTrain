package com.irontrain;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.irontrain.action.MenuListener;
import com.irontrain.storage.daos.DAOPlanDay;
import com.irontrain.action.OCListener;
import com.irontrain.adapter.PlanDayAdapter;
import com.irontrain.business.Plan;
import com.irontrain.business.PlanDay;
import com.irontrain.storage.daos.DAOPlan;
import com.irontrain.tools.Tools;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Ebi on 16.02.2016.
 * Activity for edit plan
 * controll all Items and click Events in this view
 * Verantwortlich: Andreas Züger
 */
public class EditPlanActivity extends AppCompatActivity {
    private static final String LOG_TAG = EditPlanActivity.class.getSimpleName();
    private Plan plan;
    private EditText name;
    private EditText description;
    private Button cancelButton;
    private Button newPlanDayButton;
    private List<PlanDay> planDayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_plan);
        Intent i = getIntent();
        //initialize Views
        cancelButton = (Button) findViewById(R.id.cancelEditPlan);
        cancelButton.setOnClickListener(OCListener.getInstance().getPlanListListener());
        newPlanDayButton = (Button) findViewById(R.id.addPlanDay);
        name = (EditText) findViewById(R.id.planName);
        description = (EditText) findViewById(R.id.planDescription);
        Button saveButton = (Button) findViewById(R.id.savePlan);
        // Receiving the Data
        if(!i.hasExtra("plan")){
            plan = new Plan.Builder().createdOn(new Date()).build();
        }else{
            plan = DAOPlan.getPlanById(this.getBaseContext(),i.getStringExtra("plan"));
            setPlanExist();
        }

        if(plan != null){
            if(plan.getId()!=null) {
                name.setText(plan.getName());
                description.setText(plan.getDescription());
            }
            if(saveButton != null) {
                saveButton.setTag(plan);
                saveButton.setOnClickListener(onSaveListener);
            }
            newPlanDayButton.setTag(plan);
            newPlanDayButton.setOnClickListener(OCListener.getInstance().getNewPlanDayListener());
            planDayList = DAOPlanDay.getAllPlanDaysByPlan(getApplicationContext(),plan.getId());
            initPlanDayList(planDayList);
        }
    }

    private void setPlanExist(){
        cancelButton.setText(getResources().getString(R.string.toPlanList));
        newPlanDayButton.setEnabled(true);
    }


    private void initPlanDayList(List<PlanDay> planDayList){
        PlanDayAdapter adapter = new PlanDayAdapter(this,
                R.layout.custom_planday_list_item, planDayList);
        ListView listView = (ListView)findViewById(R.id.planDays);
        if(listView != null) {
            listView.setAdapter(adapter);
            listView.setTag(planDayList);
            listView.setOnItemClickListener(listListener);
        }
    }

    public Plan getPlan(){
        return plan;
    }

    private View.OnClickListener onSaveListener = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            try {
                Plan plan = (Plan) v.getTag();
                String nameString = name.getEditableText().toString();
                if(nameString.isEmpty()){
                    Tools.getInstance().showToast(v.getContext(),getString(R.string.fillName));
                    Tools.getInstance().setErrorColor(name,true);
                    return;
                }else{
                    Tools.getInstance().setErrorColor(name,false);
                }
                plan.setName(nameString);

                plan.setDescription(description.getText().toString());
                if(null == plan.getId()) {
                    plan.setId(UUID.randomUUID().toString());
                    plan.setCreatedon(new Date());
                    DAOPlan.newPlan(plan, v.getContext());
                    setPlanExist();
                }else{
                    DAOPlan.updatePlan(plan,v.getContext());
                }
                Tools.getInstance().showToast(getApplicationContext(),getResources().getString(R.string.saveMessage));

            } catch (Exception e) {
                Log.d(LOG_TAG, "Error in onSaveListener " + e);
            }
        }
    };


    private AdapterView.OnItemClickListener listListener = new  AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id){
            try {
                PlanDay planDay = planDayList.get(position);
                Intent nextScreen = new Intent(view.getContext(), EditPlanDayActivity.class);
                nextScreen.putExtra("planDay", planDay.getId());
                view.getContext().startActivity(nextScreen);
            }catch(Exception e){
                Log.d(LOG_TAG,"Error in getNewPlanListener " + e );
            }
        }
    };


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
