package com.irontrain;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.irontrain.business.Exercice;
import com.irontrain.business.PlanDay;
import com.irontrain.business.PlanDayExercice;
import com.irontrain.business.Train;
import com.irontrain.business.TrainExercice;
import com.irontrain.business.TrainSet;
import com.irontrain.storage.daos.DAOExercice;
import com.irontrain.storage.daos.DAOPlanDay;
import com.irontrain.storage.daos.DAOPlanDayExercice;
import com.irontrain.storage.daos.DAOTrain;
import com.irontrain.storage.daos.DAOTrainExercice;
import com.irontrain.storage.daos.DAOTrainSet;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class DoTrainActivity extends AppCompatActivity {
    private TextView trainOverview;
    private Train train;
    private List<TrainExercice> trainExerciceList;
    private int currentSet = 1;
    private int currentExercice = 0;
    private TextView exerciceTitleBox;
    private TextView exerciceDescription;
    private EditText weight;
    private EditText repat;
    private PlanDay planDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_train);
        Intent i = getIntent();
        trainOverview = (TextView) findViewById(R.id.trainOverview);
        if(i.hasExtra("planDay")){
            String planDayId = i.getExtras().getString("planDay");
            createTrainFromPlanDay(planDayId);

        }else{
            String trainId = i.getExtras().getString("trainId");
            train = DAOTrain.getTrainById(getApplicationContext(),trainId);
            fillTrain();
        }
        initViews();
        initDisplay(currentExercice);

    }

    private void initViews(){
        TextView exerciceTitleText = (TextView) findViewById(R.id.trainTitleView);
        TextView exerciceDescription = (TextView) findViewById(R.id.trainTitleView);
        EditText exerciceWeight = (EditText) findViewById(R.id.weight);
        EditText exerciceRepeat = (EditText) findViewById(R.id.repeat);
    }

    private void createTrainFromPlanDay(String planDayId){
        planDay = DAOPlanDay.getPlanDayById(getApplicationContext(),planDayId);
        //set here TitleField of Exercice
        List<PlanDayExercice> exerciceList = DAOPlanDayExercice.getAllPlanDayExercicesByPlanDay(getApplicationContext(),planDayId);
        train = new Train.Builder().date(new Date()).id(UUID.randomUUID().toString()).planDay(planDayId).build();
        DAOTrain.createTrain(train, getApplicationContext());
        trainExerciceList = new ArrayList<TrainExercice>();
        for(PlanDayExercice planDayExercice : exerciceList){
            Exercice exercice = DAOExercice.getExerciceById(getApplicationContext(),planDayExercice.getExercice());
            TrainExercice trainExercice = new TrainExercice.Builder().planDayExercice(planDayExercice.getId()).exerciceDescription(exercice.getDescription())
                    .exerciceTitle(exercice.getName()).train(train.getId()).id(UUID.randomUUID().toString()).build();
            List<TrainSet> trainSetList = new ArrayList<TrainSet>();
            for(int i = 0;i < planDayExercice.getSetCount();i++){
                //Here we can add the weight from the last Train later TODO
                TrainSet trainSet = new TrainSet.Builder().id(UUID.randomUUID().toString()).setNr(i+1).trainExercice(trainExercice.getId()).build();
                trainSetList.add(trainSet);
            }
            trainExercice.setTrainSetList(trainSetList);
        }
    }

    private void fillTrain(){
        trainExerciceList = DAOTrainExercice.getTrainExercicesForTrain(getApplicationContext(),train.getId());
        for(TrainExercice trainExercice : trainExerciceList){
            List<TrainSet> trainSetList = DAOTrainSet.getTrainSetForTrainExercice(getApplicationContext(),trainExercice.getId());
            trainExercice.setTrainSetList(trainSetList);
        }
        train.setTrainExerciceList(trainExerciceList);
    }
    private void initDisplay(int index){
        TextView titleText = (TextView) findViewById(R.id.trainTitleView);
        titleText.setText(planDay.getName());
        TrainExercice exercice = trainExerciceList.get(index);
        exerciceTitleBox.setText("Satz " + currentSet + " von " + exercice.getTrainSetList().size() + " " + exercice.getExerciceTitle());
        exerciceDescription.setText(exercice.getExerciceDescription());
    }



}
