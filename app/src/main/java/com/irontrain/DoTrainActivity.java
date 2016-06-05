/*
 * You can use only script parts but do not copy the application. All rights reserved by Mirko Eberlein.
 */

package com.irontrain;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.irontrain.action.MenuListener;
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

/**
 * Created by Ebi on 14.05.2016.
 * Activity to handle the do train template. You show all data from train and can do it.
 */
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
    private boolean isNew = true;
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
            isNew = false;
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
        TextView previousSet = (TextView) findViewById(R.id.previousset);
        TextView nextSet = (TextView) findViewById(R.id.nextset);

        next.setOnClickListener(nextListener);
        previous.setOnClickListener(previousListener);
        nextSet.setOnClickListener(nextSetListener);
        previousSet.setOnClickListener(previousSetListener);
        saveButton.setOnClickListener(onSaveListener);
        cancelButton.setOnClickListener(OCListener.getInstance().openTrainListener());
        finish.setOnClickListener(finishListener);
    }

    private void createTrainFromPlanDay(String planDayId){
        planDay = DAOPlanDay.getPlanDayById(getApplicationContext(),planDayId);
        //set here TitleField of Exercice
        List<PlanDayExercice> exerciceList = DAOPlanDayExercice.getAllPlanDayExercicesByPlanDay(getApplicationContext(),planDayId);
        train = new Train.Builder().date(new Date()).id(UUID.randomUUID().toString()).planDay(planDayId).build();
        //DAOTrain.createTrain(train, getApplicationContext());
        trainExerciceList = new ArrayList<>();
        for(PlanDayExercice planDayExercice : exerciceList){
            Exercice exercice = DAOExercice.getExerciceById(getApplicationContext(),planDayExercice.getExercice());
            TrainExercice trainExercice = new TrainExercice.Builder().planDayExercice(planDayExercice.getId()).exerciceDescription(exercice.getDescription())
                    .exerciceTitle(exercice.getName()).train(train.getId()).id(UUID.randomUUID().toString()).build();
            List<TrainSet> trainSetList = new ArrayList<>();
            //DAOTrainExercice.createTrainExercice(trainExercice,getApplicationContext());
            for(int i = 0;i < planDayExercice.getSetCount();i++){
                //Here we can add the weight from the last Train later TODO
                TrainSet trainSet = new TrainSet.Builder().id(UUID.randomUUID().toString()).setNr(i+1).trainExercice(trainExercice.getId()).build();
                trainSetList.add(trainSet);
                //DAOTrainSet.creatTrainSet(trainSet,getApplicationContext());
            }
            trainExercice.setTrainSetList(trainSetList);
            trainExerciceList.add(trainExercice);
        }
    }

    private void checkFields(){
        TrainSet act = trainExerciceList.get(currentExercice).getTrainSetList().get(currentSet-1);
        float actweight = act.getWeight();
        if(currentSet == 1 && actweight == 0){
            //here found last exercice
        }
        exerciceWeight.setText(Float.toString(actweight));
        exerciceRepeat.setText(Integer.toString(act.getRepeat()));
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

        String textOverview = getResources().getString(R.string.exerciceDisplay, (currentExercice+1), trainExerciceList.size());
        trainOverview.setText(textOverview);
        String textSet = getResources().getString(R.string.exerciceSetDisplay, exercice.getExerciceTitle(), currentSet,exercice.getTrainSetList().size());

        exerciceTitleText.setText(textSet);
        exerciceDescription.setText(exercice.getExerciceDescription());
        checkFields();
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

    private View.OnClickListener nextSetListener = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            nextSet();
        }
    };

    private View.OnClickListener previousSetListener = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            previousSet();
        }
    };

    private void saveTrain(View v, boolean showMessage){
        fillSet();
        if(isNew){
            DAOTrain.createTrain(train,getApplicationContext());
        }else{
            DAOTrain.updateTrain(train,getApplicationContext());
        }

        for(TrainExercice exercice : trainExerciceList){
            if(isNew){
                DAOTrainExercice.createTrainExercice(exercice,getApplicationContext());
            }else{
                DAOTrainExercice.updateTrainExercice(exercice,getApplicationContext());
            }

            for(TrainSet trainSet : exercice.getTrainSetList()){
                if(isNew){
                    DAOTrainSet.creatTrainSet(trainSet,getApplicationContext());
                }else{
                    DAOTrainSet.updateTrainSet(trainSet,getApplicationContext());
                }
            }
        }
        isNew = false;
        if(showMessage){
            Tools.getInstance().showToast(v.getContext(),getString(R.string.saveMessage));
        }
    }

    private void fillSet() {
        String repeat = exerciceRepeat.getText().toString();
        String weight = exerciceWeight.getText().toString();
        TrainSet trainSet = trainExerciceList.get(currentExercice).getTrainSetList().get(currentSet - 1);
        Log.d(LOG_TAG,"SetNr. " + trainSet.getSetNr() + " ex " + currentExercice);
        if (!weight.isEmpty()) {
            trainSet.setWeight(Float.parseFloat(weight));
        }
        if (!repeat.isEmpty()) {
            trainSet.setRepeat(Integer.parseInt(repeat));
        }
    }

    private void nextSet(){
        fillSet();
        if(currentSet < trainExerciceList.get(currentExercice).getTrainSetList().size()){
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
        initDisplay();
    }

    private void nextExercice(){
        fillSet();
        if(currentExercice < trainExerciceList.size()-1){
            currentExercice++;
        }else{
            currentExercice = 0;
        }
        currentSet=1;
        initDisplay();
    }

    private void previousExercice(){
        fillSet();
        if(currentExercice > 0){
            currentExercice--;
        }else{
            currentExercice = trainExerciceList.size()-1;
        }
        currentSet=1;
        initDisplay();
    }


    private void finishTrain(View v){
        train.setFinished(true);
        saveTrain(v,false);
        Intent nextScreen = new Intent(v.getContext(), TrainActivity.class);
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
