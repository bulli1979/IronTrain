package com.irontrain;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.irontrain.action.OCListener;
import com.irontrain.adapter.ExerciceAdapter;
import com.irontrain.business.Exercice;
import com.irontrain.business.PlanDayExercice;
import com.irontrain.storage.daos.DAOExercice;
import com.irontrain.storage.daos.DAOPlanDayExercice;
import com.irontrain.tools.Tools;

import java.util.List;
import java.util.UUID;

public class EditPlanDayExerciceActivity extends AppCompatActivity {
    private static final String LOG_TAG = EditPlanDayExerciceActivity.class.getSimpleName();
    private String planDay;

    private AutoCompleteTextView exercices;
    private EditText setCount;
    private EditText repeat;
    private EditText exerciceDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        // Receiving the Data
        PlanDayExercice exercice;
        if(i.hasExtra("planDay")){
            exercice = new PlanDayExercice.Builder().planDay(i.getExtras().getString("planDay")).build();
        }else{
            exercice = DAOPlanDayExercice.getPlanDayById(getApplicationContext(),i.getStringExtra("planDayExercice"));
        }
        planDay = exercice.getPlanDay();
        setContentView(R.layout.activity_edit_exercice);



        List<Exercice> exerciceList = DAOExercice.getAllExercices(getApplicationContext());
        Log.d(LOG_TAG,"List exlist " + exerciceList.size());
        initExerciceAutoComplete(exerciceList);
        Button saveButton = (Button)findViewById(R.id.saveExercice);
        Button cancelButton = (Button)findViewById(R.id.cancel);
        Log.d(LOG_TAG,"planDayId " + planDay );
        cancelButton.setTag(planDay);
        cancelButton.setOnClickListener(OCListener.getOpenPlanDayDirectListener());


    }

    private void initExerciceAutoComplete( List<Exercice> exerciceList){
        ExerciceAdapter exerciceAdapter = new ExerciceAdapter(getApplicationContext(),R.layout.custom_exercice_list_item,exerciceList);
        exercices = (AutoCompleteTextView)findViewById(R.id.exerciceName);
        exercices.setAdapter(exerciceAdapter);
    }

    private View.OnClickListener onSaveListener = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            savePlanDay(v,true);
        }
    };

    private void savePlanDay(View v, boolean showMessage){
        try {
            PlanDayExercice planDayExercice = (PlanDayExercice) v.getTag();
            planDayExercice.setRepeat(Integer.parseInt(repeat.getEditableText().toString()));
            planDayExercice.setSetCount(Integer.parseInt(setCount.getEditableText().toString()));
            //here handle selected Token
            if(null == planDayExercice.getId()) {
                planDayExercice.setId(UUID.randomUUID().toString());
                DAOPlanDayExercice.newPlanDayExercice(planDayExercice,getApplicationContext());
            }else{
                DAOPlanDayExercice.updatePlanDayExercice(planDayExercice,getApplicationContext());
            }
            if(showMessage) {
                Tools.showToast(getApplicationContext(),getString(R.string.saveMessage));
            }
        } catch (Exception e) {
            Log.d(LOG_TAG, "Error in savePlanDay " + e);
        }
    }


}
