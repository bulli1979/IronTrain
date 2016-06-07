/*
 * You can use only script parts but do not copy the application. All rights reserved by Mirko Eberlein.
 */
package com.irontrain.storage.daos;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.irontrain.business.Train;
import com.irontrain.business.TrainExercice;
import com.irontrain.business.TrainItem;
import com.irontrain.business.TrainSet;
import com.irontrain.storage.DBHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ebi on 04.06.2016.
 * Class to get information for Stats read only
 */
public class StatsHelper {
    private static final String TAG = StatsHelper.class.getSimpleName();

    public static List<TrainItem> getTrainOverView(Context context){
        Map<String,TrainItem> itemsMap = new HashMap<>();
        List<TrainItem> itemList = new ArrayList<>();
        List<Train> trainList = DAOTrain.getAllTrains(context);
        Log.d(TAG,"anzahl Trainings helper " + trainList.size());
        for(Train train : trainList){
            addSubItems(train,context);
            String planDayId = train.getPlanDay();
            TrainItem item;
            if(itemsMap.containsKey(planDayId)){
                item = itemsMap.get(planDayId);
            }else{
                item = new TrainItem();
                item.setLastTrain(train.getDate());
                item.setPlanDay(DAOPlanDay.getPlanDayById(context,train.getPlanDay()));
                item.setPlan(DAOPlan.getPlanById(context,item.getPlanDay().getPlan()));
                itemsMap.put(planDayId,item);
                itemList.add(item);
                item.setTrains(new ArrayList<Train>());
            }
            item.getTrains().add(train);
        }
        return itemList;
    }

    //get complet trainInformations
    private static void addSubItems(Train train,Context context){
        List<TrainExercice> exerciceList = DAOTrainExercice.getTrainExercicesForTrain(context,train.getId());

        Log.d(TAG,"anzahl Übungen " + exerciceList.size() + " für training " + train.getId() + " " + train.getDate());
        for(TrainExercice te : exerciceList){
            Log.d(TAG,"Übung " + te.getExerciceTitle());

            List<TrainSet> trainSetList = DAOTrainSet.getTrainSetForTrainExercice(context,te.getId());
            te.setTrainSetList(trainSetList);
        }
        train.setTrainExerciceList(exerciceList);
    }




}
