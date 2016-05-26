package com.irontrain;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.irontrain.action.MenuListener;
import com.irontrain.action.OCListener;
import com.irontrain.adapter.ExerciceAdapter;
import com.irontrain.business.Exercice;
import com.irontrain.business.PlanDayExercice;
import com.irontrain.storage.daos.DAOExercice;
import com.irontrain.storage.daos.DAOPlanDayExercice;
import com.irontrain.tools.Tools;

import java.util.List;
import java.util.UUID;

/**
 * PlanDayExercice Activity
 * save update and select Exercice handling here.
 * insert own name crete automaticly an exercice
 *
 * */

public class EditPlanDayExerciceActivity extends AppCompatActivity {
    private static final String LOG_TAG = EditPlanDayExerciceActivity.class.getSimpleName();
    private AutoCompleteTextView exercicesView;
    private EditText setCountView;
    private EditText repeatView;
    private EditText exerciceDescriptionView;
    private List<Exercice> exerciceList;
    private Button cancelButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        setContentView(R.layout.activity_edit_exercice);
        // Receiving the Data
        PlanDayExercice planDayExercice;
        setCountView = (EditText)findViewById(R.id.exerciceSet);
        repeatView = (EditText)findViewById(R.id.exerciceRepeats);
        exerciceDescriptionView = (EditText)findViewById(R.id.ExerciceDescription);
        exerciceList = DAOExercice.getAllExercices(getApplicationContext());
        exercicesView = (AutoCompleteTextView)findViewById(R.id.exerciceName);
        cancelButton = (Button)findViewById(R.id.cancel);

        if(i.hasExtra("planDay")){
            planDayExercice = new PlanDayExercice.Builder().planDay(i.getExtras().getString("planDay")).build();
        }else{
            setPlanDayExist();
            planDayExercice = DAOPlanDayExercice.getPlanDayById(getApplicationContext(),i.getStringExtra("planDayExercice"));
            setCountView.setText(String.valueOf(planDayExercice.getSetCount()));
            repeatView.setText(planDayExercice.getRepeat());
            exerciceDescriptionView.setText(planDayExercice.getDescription());
            exercicesView.setText(getTextForId(planDayExercice.getExercice()));
        }
        String planDay = planDayExercice.getPlanDay();
        initExerciceAutoComplete(exerciceList);
        Button saveButton = (Button)findViewById(R.id.saveExercice);
        saveButton.setTag(planDayExercice);
        saveButton.setOnClickListener(onSaveListener);
        cancelButton.setTag(planDay);
        cancelButton.setOnClickListener(OCListener.getInstance().getOpenPlanDayDirectListener());
    }

    private void initExerciceAutoComplete( List<Exercice> exerciceList){
        ExerciceAdapter exerciceAdapter = new ExerciceAdapter(getApplicationContext(), R.layout.custom_exercice_list_item, exerciceList);
        exerciceAdapter.setNotifyOnChange(true);
        exercicesView.setAdapter(exerciceAdapter);
        exercicesView.setOnItemClickListener(exerciceListener);
    }

    private AdapterView.OnItemClickListener exerciceListener =  new  AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id){
            try {
                Exercice exercice = exerciceList.get(position);
                exerciceDescriptionView.setText(exercice.getDescription());
            }catch(Exception e){
                Log.d(LOG_TAG,"Error in exerciceListener " + e );
            }
        }
    };


    private View.OnClickListener onSaveListener = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            savePlanDayExercice(v,true);
        }
    };

    private String getTextForId(String id){
        for(Exercice ex : exerciceList){
            if(null != ex.getId() && ex.getId().equals(id)){
                return ex.getName();
            }
        }
        return "";
    }

    private void savePlanDayExercice(View v, boolean showMessage){
        try {
            PlanDayExercice planDayExercice = (PlanDayExercice) v.getTag();
            String repeat = repeatView.getEditableText().toString();
            planDayExercice.setRepeat(repeat);
            planDayExercice.setSetCount(Integer.parseInt(setCountView.getEditableText().toString()));
            planDayExercice.setDescription(exerciceDescriptionView.getEditableText().toString());
            String exerciceName = exercicesView.getText().toString();
            //here handle selected Token
            if(exerciceName.isEmpty()){
                Tools.getInstance().showToast(v.getContext(),getString(R.string.fillName));
                Tools.getInstance().setErrorColor(exercicesView,true);
                return;
            }else{
                Tools.getInstance().setErrorColor(exercicesView,false);
            }
            String exerciceId = chcekExercice(exerciceName);
            planDayExercice.setExercice(exerciceId);
            if(null == planDayExercice.getId()) {
                setPlanDayExist();
                planDayExercice.setId(UUID.randomUUID().toString());
                DAOPlanDayExercice.newPlanDayExercice(planDayExercice,getApplicationContext());
            }else{
                DAOPlanDayExercice.updatePlanDayExercice(planDayExercice,getApplicationContext());
            }
            if(showMessage) {
                Tools.getInstance().showToast(getApplicationContext(),getString(R.string.saveMessage));
            }
        } catch (Exception e) {
            Log.d(LOG_TAG, "Error in savePlanDay " + e);
        }
    }

    private void setPlanDayExist(){
        cancelButton.setText(getResources().getString(R.string.toPlanDay));
    }

    private String chcekExercice(String exercice){
        String id;
        for(Exercice e : exerciceList){
            if(e.getName().equals(exercice)){
                return e.getId();
            }
        }
        id = UUID.randomUUID().toString();
        Exercice newExercice = new Exercice.Builder().id(id).name(exercice).build();
        DAOExercice.createExercice(newExercice,getApplicationContext());
        return id;
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
