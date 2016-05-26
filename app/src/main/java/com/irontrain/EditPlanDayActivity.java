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
import com.irontrain.action.OCListener;
import com.irontrain.adapter.PlanDayExerciceAdapter;
import com.irontrain.business.PlanDayExercice;
import com.irontrain.storage.daos.DAOPlanDay;
import com.irontrain.business.PlanDay;
import com.irontrain.storage.daos.DAOPlanDayExercice;
import com.irontrain.tools.Tools;

import java.util.List;
import java.util.UUID;


/**
 * Ebi
 * Class handle EditPlanDay Activity
 * fount all functionality here
 * */
public class EditPlanDayActivity extends AppCompatActivity {
    private EditText name;
    private EditText description;
    private final String LOG_TAG = EditPlanDayActivity.class.getSimpleName();
    private Button cancelButton;
    private Button addExerciceButton;
    private List<PlanDayExercice> planDayExerciceList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_plan_day);
        Intent i = getIntent();
        //initialize Views
        cancelButton = (Button) findViewById(R.id.cancelPlanDay);
        Button saveButton = (Button)findViewById(R.id.savePlanDay);
        addExerciceButton = (Button) findViewById(R.id.addExercice);

        name = (EditText) findViewById(R.id.planDayName);
        description = (EditText) findViewById(R.id.planDayDescription);


        // Receiving the Data
        PlanDay planDay;
        if(i.hasExtra("plan")){
            planDay = new PlanDay.Builder().plan(i.getExtras().getString("plan")).build();
        }else{
            String id = i.getExtras().getString("planDay");
            planDay = DAOPlanDay.getPlanDayById(getApplicationContext(),id);
            setPlanDayExist();
        }
        cancelButton.setTag(planDay.getPlan());
        cancelButton.setOnClickListener(OCListener.getInstance().getOpenPlanByIdListener());

        addExerciceButton.setTag(planDay);
        addExerciceButton.setOnClickListener(OCListener.getInstance().getNewExerciceListener());

        if(planDay.getId()!=null){
            name.setText(planDay.getName());
            description.setText(planDay.getDescription());
        }
        saveButton.setTag(planDay);
        saveButton.setOnClickListener(onSaveListener);

        if(null != planDay.getId()) {
            planDayExerciceList = DAOPlanDayExercice.getAllPlanDayExercicesByPlanDay(getApplicationContext(), planDay.getId());
            initPlanDayExerciceList();
        }
    }

    private void setPlanDayExist(){
        cancelButton.setText(getResources().getString(R.string.toPlan));
        addExerciceButton.setEnabled(true);
    }


    private void initPlanDayExerciceList(){
        PlanDayExerciceAdapter adapter = new PlanDayExerciceAdapter(this,
                R.layout.custom_planday_list_item, planDayExerciceList);
        ListView listView = (ListView)findViewById(R.id.planDayExercices);
        listView.setAdapter(adapter);
        listView.setTag(planDayExerciceList);
        listView.setOnItemClickListener(planDayExerciceListener);
    }

    private View.OnClickListener onSaveListener = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            savePlanDay(v);
        }
    };

    private void savePlanDay(View v){
        try {
            PlanDay planDay = (PlanDay) v.getTag();
            String nameString = name.getEditableText().toString();
            if(nameString.isEmpty()){
                Tools.getInstance().showToast(v.getContext(),getString(R.string.fillName));
                Tools.getInstance().setErrorColor(name,true);
                return;
            }else{
                Tools.getInstance().setErrorColor(name,false);
            }
            planDay.setName(nameString);
            planDay.setDescription(description.getText().toString());
            if(null == planDay.getId()) {
                setPlanDayExist();
                planDay.setId(UUID.randomUUID().toString());
                DAOPlanDay.newPlanDay(planDay,getApplicationContext());
            }else{
                DAOPlanDay.updatePlanDay(planDay,getApplicationContext());
            }
            Tools.getInstance().showToast(getApplicationContext(),getString(R.string.saveMessage));
        } catch (Exception e) {
            Log.d(LOG_TAG, "Error in savePlanDay " + e);
        }
    }

    private AdapterView.OnItemClickListener planDayExerciceListener =  new  AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id){
            try {
                PlanDayExercice planDayExercice = planDayExerciceList.get(position);
                Intent nextScreen = new Intent(view.getContext(), EditPlanDayExerciceActivity.class);
                nextScreen.putExtra("planDayExercice", planDayExercice.getId());
                view.getContext().startActivity(nextScreen);
            }catch(Exception e){
                Log.d(LOG_TAG,"Error in planDayExerciceListener " + e );
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
