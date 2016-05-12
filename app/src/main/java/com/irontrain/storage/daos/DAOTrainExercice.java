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
import java.util.UUID;

/**
 * Created by Ebi on 07.04.2016.
 */
public class DAOTrainExercice {
    private static final String LOG_TAG = DAOTrainExercice.class.getSimpleName();
    private static SQLiteDatabase database;

    public static TrainExercice getTrainExerciceById(Context context, String id){
        database = DBHelper.getInstance(context).getWritableDatabase();
        Cursor trainExerciceCursor = database.query(DBHelper.TABLE_TRAIN, null,DBHelper.COLUMN_ID+"='"+id+"'", null, null, null, null);
        trainExerciceCursor.moveToFirst();
        TrainExercice trainExercice = cursorToTrainExercice(trainExerciceCursor);
        trainExerciceCursor.close();
        database.close();
        return trainExercice;
    }

    public static void saveOrUpdateTrainExercice (TrainExercice trainExercice,Context context){
        try {
            database = DBHelper.getInstance(context).getWritableDatabase();
            if (trainExercice.getId() != null) {
                String whereClauses = DBHelper.COLUMN_ID + "='" + trainExercice.getId()+"'";
                database.update(DBHelper.TABLE_TRAINEXERCICE, getDBValues(trainExercice), whereClauses, null);
            } else {
                trainExercice.setId(UUID.randomUUID().toString());
                ContentValues cv = getDBValues(trainExercice);
                database.insert(DBHelper.TABLE_TRAINEXERCICE, null, cv);
            }
            database.close();
        }catch(Exception e){
            Log.d(LOG_TAG,"Fehler in saveOrUpdateTrainExercice " + e.getMessage());
        }
    }

    public static List<TrainExercice> getTrainExercicesForTrain(Context context,String trainId){
        database = DBHelper.getInstance(context).getWritableDatabase();
        String whereClauses = DBHelper.COLUMN_TRAIN + "='" + trainId +"'";
        Cursor trainExerciceCursor = database.query(DBHelper.TABLE_TRAINEXERCICE, null,whereClauses, null, null, null, null);
        List<TrainExercice> trainExerciceList = new ArrayList<TrainExercice>();
        while (trainExerciceCursor.moveToNext()) {
            trainExerciceList.add(cursorToTrainExercice(trainExerciceCursor));
        }
        return trainExerciceList;
    }



    private static TrainExercice cursorToTrainExercice(Cursor trainExerciceCursor){
        String id = trainExerciceCursor.getString(trainExerciceCursor.getColumnIndex(DBHelper.COLUMN_ID));
        String planDayExercice = trainExerciceCursor.getString(trainExerciceCursor.getColumnIndex(DBHelper.COLUMN_PLANDAYEXERCICE));
        String train = trainExerciceCursor.getString(trainExerciceCursor.getColumnIndex(DBHelper.COLUMN_TRAIN));
        return new TrainExercice.Builder().id(id).planDayExercice(planDayExercice).train(train).build();
    }

    private static ContentValues getDBValues(TrainExercice trainExercice){
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.COLUMN_PLANDAYEXERCICE,trainExercice.getPlanDayExercice());
        cv.put(DBHelper.COLUMN_TRAIN, trainExercice.getTrain());
        cv.put(DBHelper.COLUMN_ID, trainExercice.getId());
        return cv;
    }

}
