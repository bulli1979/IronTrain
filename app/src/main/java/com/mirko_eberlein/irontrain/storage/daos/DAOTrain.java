package com.mirko_eberlein.irontrain.storage.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.mirko_eberlein.irontrain.business.Train;
import com.mirko_eberlein.irontrain.storage.DBHelper;
import com.mirko_eberlein.irontrain.tools.Tools;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Ebi on 07.04.2016.
 */
public class DAOTrain {
    private static final String LOG_TAG = DAOTrain.class.getSimpleName();
    private static SQLiteDatabase database;

    public static Train getTrainById(Context context, String id){
        database = DBHelper.getInstance(context).getWritableDatabase();
        Cursor planDayCursor = database.query(DBHelper.TABLE_TRAIN, null,DBHelper.COLUMN_ID+"='"+id+"'", null, null, null, null);
        planDayCursor.moveToFirst();
        Train train = cursorToPlanDay(planDayCursor);
        planDayCursor.close();
        database.close();
        return train;
    }

    public static void saveOrUpdateTrain (Train train,Context context){
        try {
            database = DBHelper.getInstance(context).getWritableDatabase();
            if (train.getId() != null) {
                String whereClauses = DBHelper.COLUMN_ID + "='" + train.getId()+"'";
                database.update(DBHelper.TABLE_TRAIN, getDBValues(train), whereClauses, null);
            } else {
                train.setId(UUID.randomUUID().toString());
                ContentValues cv = getDBValues(train);
                database.insert(DBHelper.TABLE_TRAIN, null, cv);
            }
            database.close();
        }catch(Exception e){
            Log.d(LOG_TAG,"Fehler in saveOrUpdateTrain " + e.getMessage());
        }
    }



    private static Train cursorToPlanDay(Cursor trainCursor){
        String id = trainCursor.getString(trainCursor.getColumnIndex(DBHelper.COLUMN_ID));
        String planDay = trainCursor.getString(trainCursor.getColumnIndex(DBHelper.COLUMN_PLANDAY));
        String createdOnString = trainCursor.getString(trainCursor.getColumnIndex(DBHelper.COLUMN_CREATEDON));
        Date date = Tools.stringToDate(createdOnString);
        return new Train.Builder().id(id).planDay(planDay).date(date).build();
    }

    private static ContentValues getDBValues(Train train){
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.COLUMN_PLANDAY, train.getPlanDay());
        cv.put(DBHelper.COLUMN_DATE,Tools.dateToString(train.getDate()));
        cv.put(DBHelper.COLUMN_ID, train.getId());
        return cv;
    }

}
