package com.irontrain.storage.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.irontrain.business.TrainSet;
import com.irontrain.storage.DBHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Ebi on 07.04.2016.
 */
public class DAOTrainSet {

    private static final String LOG_TAG = TrainSet.class.getSimpleName();
    private static SQLiteDatabase database;

    public static TrainSet getExerciceById(Context context, String id){
        database = DBHelper.getInstance(context).getWritableDatabase();
        Cursor cursor = database.query(DBHelper.TABLE_TRAINSET, null,DBHelper.COLUMN_ID+"='"+id+"'", null, null, null, null);
        cursor.moveToFirst();
        TrainSet trainSet = cursorToTrainSet(cursor);
        cursor.close();
        database.close();
        return trainSet;
    }

    public static void updateTrainSet (TrainSet trainSet,Context context){
        try {
            database = DBHelper.getInstance(context).getWritableDatabase();
            String whereClauses = DBHelper.COLUMN_ID + "='" + trainSet.getId()+"'";
            database.update(DBHelper.TABLE_TRAINSET, getDBValues(trainSet), whereClauses, null);
            database.close();
        }catch(Exception e){
            Log.d(LOG_TAG,"Fehler in saveOrUpdateExercice " + e.getMessage());
        }
    }
    public static void creatTrainSet (TrainSet trainSet,Context context){
        try {
            database = DBHelper.getInstance(context).getWritableDatabase();
            ContentValues cv = getDBValues(trainSet);
            database.insert(DBHelper.TABLE_TRAINSET, null, cv);
            database.close();
        }catch(Exception e){
            Log.d(LOG_TAG,"Fehler in saveOrUpdateExercice " + e.getMessage());
        }
    }
    public static List<TrainSet> getTrainSetForTrainExercice(Context context, String trainExerciceId){
        database = DBHelper.getInstance(context).getWritableDatabase();
        String whereClauses = DBHelper.COLUMN_TRAINEXERCICE + "='" + trainExerciceId +"'";
        Cursor trainSetCursor = database.query(DBHelper.TABLE_TRAINSET, null,whereClauses, null, null, null, null);
        List<TrainSet> trainSetList = new ArrayList<TrainSet>();
        while (trainSetCursor.moveToNext()) {
            trainSetList.add(cursorToTrainSet(trainSetCursor));
        }
        return trainSetList;
    }

    private static TrainSet cursorToTrainSet(Cursor exerciceCursor){
        String id = exerciceCursor.getString(exerciceCursor.getColumnIndex(DBHelper.COLUMN_ID));
        int repeat = exerciceCursor.getInt(exerciceCursor.getColumnIndex(DBHelper.COLUMN_REPEATS));
        float weight = exerciceCursor.getFloat(exerciceCursor.getColumnIndex(DBHelper.COLUMN_WEIGHT));
        int setNr = exerciceCursor.getInt(exerciceCursor.getColumnIndex(DBHelper.COLUMN_SETNR));
        String trainExercice = exerciceCursor.getString(exerciceCursor.getColumnIndex(DBHelper.COLUMN_TRAINEXERCICE));
        return new TrainSet.Builder().id(id).setNr(setNr).trainExercice(trainExercice).weight(weight).repeat(repeat).build();
    }

    private static ContentValues getDBValues(TrainSet trainSet){
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.COLUMN_TRAINEXERCICE,trainSet.getTrainExercice());
        cv.put(DBHelper.COLUMN_SETNR, trainSet.getSetNr());
        cv.put(DBHelper.COLUMN_WEIGHT, trainSet.getWeight());
        cv.put(DBHelper.COLUMN_REPEATS, trainSet.getRepeat());
        cv.put(DBHelper.COLUMN_ID, trainSet.getId());
        return cv;
    }


}
