package com.irontrain.storage.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.irontrain.business.TrainExercice;
import com.irontrain.storage.DBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mirko Eberlein on 07.04.2016.
 * Class to save and get Objects into and from Database
 * Verantwortlich: Fabricio Ruch
 */
public class DAOTrainExercice {
    private static final String LOG_TAG = DAOTrainExercice.class.getSimpleName();
    private static SQLiteDatabase database;


    public static void updateTrainExercice (TrainExercice trainExercice,Context context){
        try {
            database = DBHelper.getInstance(context).getWritableDatabase();
            String whereClauses = DBHelper.COLUMN_ID + "='" + trainExercice.getId()+"'";
            database.update(DBHelper.TABLE_TRAINEXERCICE, getDBValues(trainExercice), whereClauses, null);
            database.close();
        }catch(Exception e){
            Log.d(LOG_TAG,"Fehler in saveOrUpdateTrainExercice " + e.getMessage());
        }
    }

    public static void createTrainExercice (TrainExercice trainExercice,Context context){
        try {
            database = DBHelper.getInstance(context).getWritableDatabase();
            ContentValues cv = getDBValues(trainExercice);
            database.insert(DBHelper.TABLE_TRAINEXERCICE, null, cv);
            database.close();
        }catch(Exception e){
            Log.d(LOG_TAG,"Fehler in saveOrUpdateTrainExercice " + e.getMessage());
        }
    }

    public static List<TrainExercice> getTrainExercicesForTrain(Context context,String trainId){
        database = DBHelper.getInstance(context).getWritableDatabase();
        String whereClauses = DBHelper.COLUMN_TRAIN + "='" + trainId +"'";
        Cursor trainExerciceCursor = database.query(DBHelper.TABLE_TRAINEXERCICE, null,whereClauses, null, null, null, null);
        List<TrainExercice> trainExerciceList = new ArrayList<>();
        Log.d(LOG_TAG,"anzahl " + trainExerciceCursor.getCount());
        while (trainExerciceCursor.moveToNext()) {
            trainExerciceList.add(cursorToTrainExercice(trainExerciceCursor));
        }
        return trainExerciceList;
    }



    private static TrainExercice cursorToTrainExercice(Cursor trainExerciceCursor){
        String id = trainExerciceCursor.getString(trainExerciceCursor.getColumnIndex(DBHelper.COLUMN_ID));
        String planDayExercice = trainExerciceCursor.getString(trainExerciceCursor.getColumnIndex(DBHelper.COLUMN_PLANDAYEXERCICE));
        String train = trainExerciceCursor.getString(trainExerciceCursor.getColumnIndex(DBHelper.COLUMN_TRAIN));
        String exerciceName = trainExerciceCursor.getString(trainExerciceCursor.getColumnIndex(DBHelper.COLUMN_EXERCICENAME));
        String exerciceDescription = trainExerciceCursor.getString(trainExerciceCursor.getColumnIndex(DBHelper.COLUMN_EXERCICEDESCRIPTION));
        return new TrainExercice.Builder().id(id).planDayExercice(planDayExercice).train(train).exerciceTitle(exerciceName).exerciceDescription(exerciceDescription).build();
    }

    private static ContentValues getDBValues(TrainExercice trainExercice){
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.COLUMN_PLANDAYEXERCICE,trainExercice.getPlanDayExercice());
        cv.put(DBHelper.COLUMN_TRAIN, trainExercice.getTrain());
        cv.put(DBHelper.COLUMN_ID, trainExercice.getId());
        cv.put(DBHelper.COLUMN_EXERCICENAME,trainExercice.getExerciceTitle());
        cv.put(DBHelper.COLUMN_EXERCICEDESCRIPTION,trainExercice.getExerciceDescription());
        return cv;
    }

}
