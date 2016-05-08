package com.irontrain;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.irontrain.action.OCListener;
import com.irontrain.adapter.ExerciceAdapter;
import com.irontrain.business.Exercice;
import com.irontrain.business.PlanDayExercice;
import com.irontrain.storage.daos.DAOExercice;
import com.irontrain.storage.daos.DAOPlanDayExercice;

import java.util.List;

public class EditExerciceActivity extends AppCompatActivity {
    private static final String LOG_TAG = EditExerciceActivity.class.getSimpleName();
    private String planDay;
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
        AutoCompleteTextView textView = (AutoCompleteTextView)findViewById(R.id.exerciceName);
        Log.d(LOG_TAG,"textView " + textView);
        textView.setAdapter(exerciceAdapter);
    }
}
