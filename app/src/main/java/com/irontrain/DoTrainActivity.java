package com.irontrain;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.irontrain.action.OCListener;
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
import com.irontrain.tools.Tools;

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
    private TextView exerciceTitleText;
    private TextView exerciceDescription;
    private EditText exerciceWeight;
    private EditText exerciceRepeat;
    private PlanDay planDay;
    private static final String LOG_TAG = DoTrainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_train);
        Intent i = getIntent();
        if(i.hasExtra("planDay")){
            String planDayId = i.getExtras().getString("planDay");
            createTrainFromPlanDay(planDayId);

        }else{
            String trainId = i.getExtras().getString("trainId");
            train = DAOTrain.getTrainById(getApplicationContext(),trainId);
            fillTrain();
        }
        initViews();
        initDisplay();

    }

    private void initViews(){
        trainOverview = (TextView) findViewById(R.id.trainOverview);
        exerciceTitleText = (TextView) findViewById(R.id.exerciceTitleBox);
        exerciceDescription = (TextView) findViewById(R.id.exerciceDescription);
        exerciceWeight = (EditText) findViewById(R.id.weight);
        exerciceRepeat = (EditText) findViewById(R.id.repeat);
        Button saveButton = (Button)findViewById(R.id.saveTrain);
        Button cancelButton = (Button) findViewById(R.id.cancelTrain);
        Button finish = (Button) findViewById(R.id.finishTrain);
        TextView previous = (TextView) findViewById(R.id.previous);
        TextView next = (TextView) findViewById(R.id.next);
        next.setOnClickListener(nextListener);
        previous.setOnClickListener(previousListener);
        saveButton.setOnClickListener(onSaveListener);
        cancelButton.setOnClickListener(OCListener.openTrainListener());
        finish.setOnClickListener(finishListener);


    }

    private void createTrainFromPlanDay(String planDayId){
        planDay = DAOPlanDay.getPlanDayById(getApplicationContext(),planDayId);
        //set here TitleField of Exercice
        List<PlanDayExercice> exerciceList = DAOPlanDayExercice.getAllPlanDayExercicesByPlanDay(getApplicationContext(),planDayId);
        train = new Train.Builder().date(new Date()).id(UUID.randomUUID().toString()).planDay(planDayId).build();
        DAOTrain.createTrain(train, getApplicationContext());
        trainExerciceList = new ArrayList<>();
        for(PlanDayExercice planDayExercice : exerciceList){
            Exercice exercice = DAOExercice.getExerciceById(getApplicationContext(),planDayExercice.getExercice());
            TrainExercice trainExercice = new TrainExercice.Builder().planDayExercice(planDayExercice.getId()).exerciceDescription(exercice.getDescription())
                    .exerciceTitle(exercice.getName()).train(train.getId()).id(UUID.randomUUID().toString()).build();
            List<TrainSet> trainSetList = new ArrayList<>();
            DAOTrainExercice.createTrainExercice(trainExercice,getApplicationContext());
            for(int i = 0;i < planDayExercice.getSetCount();i++){
                //Here we can add the weight from the last Train later TODO
                TrainSet trainSet = new TrainSet.Builder().id(UUID.randomUUID().toString()).setNr(i+1).trainExercice(trainExercice.getId()).build();
                trainSetList.add(trainSet);
                DAOTrainSet.creatTrainSet(trainSet,getApplicationContext());
            }
            trainExercice.setTrainSetList(trainSetList);
            trainExerciceList.add(trainExercice);
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
    private void initDisplay(){
        TextView titleText = (TextView) findViewById(R.id.trainTitleView);
        titleText.setText(planDay.getName());
        Log.d(LOG_TAG,"PlanDayName " + planDay.getName());
        TrainExercice exercice = trainExerciceList.get(currentExercice);
        trainOverview.setText("Übung " + (currentExercice+1) + " von " +trainExerciceList.size());
        exerciceTitleText.setText("Satz " + currentSet + " von " + exercice.getTrainSetList().size() + " " + exercice.getExerciceTitle());

        exerciceDescription.setText(exercice.getExerciceDescription());
    }

    private View.OnClickListener onSaveListener = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            saveTrain(v,true);
        }
    };

    private View.OnClickListener nextListener = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            nextExercice();
        }
    };

    private View.OnClickListener previousListener = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            previousExercice();
        }
    };

    private View.OnClickListener finishListener = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            finishTrain(v);
        }
    };

    private void saveTrain(View v, boolean showMessage){
        fillSet();
        DAOTrain.updateTrain(train,getApplicationContext());
        for(TrainExercice exercice : trainExerciceList){
            DAOTrainExercice.updateTrainExercice(exercice,getApplicationContext());
            for(TrainSet trainSet : exercice.getTrainSetList()){
                DAOTrainSet.updateTrainSet(trainSet,getApplicationContext());
            }
        }
        if(showMessage){
            Tools.showToast(v.getContext(),getString(R.string.saveMessage));
        }
    }

    private void fillSet() {
        String repeat = exerciceRepeat.getText().toString();
        String weight = exerciceWeight.getText().toString();
        TrainSet trainSet = trainExerciceList.get(currentExercice).getTrainSetList().get(currentSet - 1);
        if (!weight.isEmpty()) {
            trainSet.setWeight(Float.parseFloat(weight));
        }
        if (!repeat.isEmpty()) {
            trainSet.setRepeat(Integer.parseInt(repeat));
        }
    }

    private void nextSet(){
        fillSet();
        if(currentSet-1 < trainExerciceList.get(currentExercice).getTrainSetList().size()){
            currentSet++;
        }else{
            currentSet=1;
        }
        initDisplay();
    }
    private void previousSet(){
        fillSet();
        if(currentSet > 1){
            currentSet--;
        }else{
            currentSet = trainExerciceList.get(currentExercice).getTrainSetList().size();
        }
    }

    private void nextExercice(){
        fillSet();
        if(currentExercice < trainExerciceList.size()-1){
            currentExercice++;
        }else{
            currentExercice = 0;
        }
        initDisplay();
    }

    private void previousExercice(){
        fillSet();
        if(currentExercice > 0){
            currentExercice--;
        }else{
            currentExercice = trainExerciceList.size()-1;
        }
        initDisplay();
    }


    private void finishTrain(View v){
        saveTrain(v,false);
        Intent nextScreen = new Intent(v.getContext(), TrainActivity.class);
        v.getContext().startActivity(nextScreen);
    }


}
