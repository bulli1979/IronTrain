package com.irontrain.storage.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.irontrain.storage.DBHelper;
import com.irontrain.business.Train;
import com.irontrain.tools.Tools;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Ebi on 07.04.2016.
 * Class to get and save Train Objects from and into Database
 */
public class DAOTrain {
    private static final String LOG_TAG = DAOTrain.class.getSimpleName();
    private static SQLiteDatabase database;

    public static Train getTrainById(Context context, String id){
        database = DBHelper.getInstance(context).getWritableDatabase();
        Cursor trainCursor = database.query(DBHelper.TABLE_TRAIN, null,DBHelper.COLUMN_ID+"='"+id+"'", null, null, null, null);
        trainCursor.moveToFirst();
        Train train = cursorToTrain(trainCursor);
        trainCursor.close();
        database.close();
        return train;
    }

    public static void updateTrain (Train train,Context context){
        try {
            database = DBHelper.getInstance(context).getWritableDatabase();
            String whereClauses = DBHelper.COLUMN_ID + "='" + train.getId()+"'";
            database.update(DBHelper.TABLE_TRAIN, getDBValues(train), whereClauses, null);
            database.close();
        }catch(Exception e){
            Log.d(LOG_TAG,"Fehler in updateTrain " + e.getMessage());
        }
    }

    public static void createTrain(Train train,Context context){
        try {
            database = DBHelper.getInstance(context).getWritableDatabase();
            ContentValues cv = getDBValues(train);
            database.insert(DBHelper.TABLE_TRAIN, null, cv);
            database.close();
        }catch(Exception e){
            Log.d(LOG_TAG,"Fehler in createTrain " + e.getMessage());
        }
    }

    public static List<Train> getAllTrains(Context context){
        database = DBHelper.getInstance(context).getReadableDatabase();
        String whereClauses = DBHelper.COLUMN_TRAINFINISHED + "=1";
        Cursor trainListCursor = database.query(DBHelper.TABLE_TRAIN,null,whereClauses, null, null,null, DBHelper.COLUMN_DATE, null);
        List<Train> trainList = new ArrayList<>();
        Log.i(LOG_TAG,"here " + trainListCursor.getCount());
        while (trainListCursor.moveToNext()) {
            trainList.add(cursorToTrain(trainListCursor));
        }
        database.close();
        return trainList;
    }


    private static Train cursorToTrain(Cursor trainCursor){
        String id = trainCursor.getString(trainCursor.getColumnIndex(DBHelper.COLUMN_ID));
        Log.d(LOG_TAG,"found id " + id);
        String planDay = trainCursor.getString(trainCursor.getColumnIndex(DBHelper.COLUMN_PLANDAY));
        String createdOnString = trainCursor.getString(trainCursor.getColumnIndex(DBHelper.COLUMN_DATE));
        Date date = Tools.getInstance().stringToDate(createdOnString);
        boolean finished = Tools.getInstance().intToBoolean(trainCursor.getInt(trainCursor.getColumnIndex(DBHelper.COLUMN_TRAINFINISHED)));
        return new Train.Builder().id(id).planDay(planDay).date(date).finished(finished).build();
    }

    private static ContentValues getDBValues(Train train){
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.COLUMN_PLANDAY, train.getPlanDay());
        cv.put(DBHelper.COLUMN_DATE,Tools.getInstance().dateToString(train.getDate()));
        cv.put(DBHelper.COLUMN_ID, train.getId());
        cv.put(DBHelper.COLUMN_TRAINFINISHED,Tools.getInstance().booleanToInt(train.isFinished()));
        return cv;
    }

}
