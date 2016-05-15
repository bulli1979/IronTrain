package com.irontrain;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.irontrain.business.Exercice;
import com.irontrain.business.Train;
import com.irontrain.business.TrainExercice;
import com.irontrain.business.TrainSet;
import com.irontrain.storage.daos.DAOTrain;
import com.irontrain.storage.daos.DAOTrainExercice;
import com.irontrain.storage.daos.DAOTrainSet;

import java.util.List;

public class DoTrainActivity extends AppCompatActivity {
    TextView trainOverview;
    Train train;
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
    }

    private void createTrainFromPlanDay(String planDayId){



    }

    private void fillTrain(){
        List<TrainExercice> trainExerciceList = DAOTrainExercice.getTrainExercicesForTrain(getApplicationContext(),train.getId());
        for(TrainExercice trainExercice : trainExerciceList){
            List<TrainSet> trainSetList = DAOTrainSet.getTrainSetForTrainExercice(getApplicationContext(),trainExercice.getId());
            trainExercice.setTrainSetList(trainSetList);
        }
        train.setTrainExerciceList(trainExerciceList);
    }
}
