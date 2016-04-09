package com.mirko_eberlein.irontrain.storage.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.mirko_eberlein.irontrain.business.TrainExercice;
import com.mirko_eberlein.irontrain.storage.DBHelper;
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
        TrainExercice trainExercice = cursorToPlanDay(trainExerciceCursor);
        trainExerciceCursor.close();
        database.close();
        return trainExercice;
    }

    public static void saveOrUpdateTrainExercice (TrainExercice trainExercice,Context context){
        try {
            database = DBHelper.getInstance(context).getWritableDatabase();
            if (trainExercice.getId() != null) {
                String whereClauses = DBHelper.COLUMN_ID + "='" + trainExercice.getId()+"'";
                database.update(DBHelper.TABLE_TRAIN, getDBValues(trainExercice), whereClauses, null);
            } else {
                trainExercice.setId(UUID.randomUUID().toString());
                ContentValues cv = getDBValues(trainExercice);
                database.insert(DBHelper.TABLE_TRAIN, null, cv);
            }
            database.close();
        }catch(Exception e){
            Log.d(LOG_TAG,"Fehler in saveOrUpdateTrainExercice " + e.getMessage());
        }
    }



    private static TrainExercice cursorToPlanDay(Cursor trainExerciceCursor){
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
