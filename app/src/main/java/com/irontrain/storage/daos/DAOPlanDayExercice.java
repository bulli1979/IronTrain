package com.irontrain.storage.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.irontrain.business.PlanDayExercice;
import com.irontrain.storage.DBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fabricio on 07.04.2016.
 * Class to store and get PlanDayExerciceObjects from Database
 * Verantwortlich: Andreas Züger
 */
public class DAOPlanDayExercice {
    private static final String LOG_TAG = DAOPlanDayExercice.class.getSimpleName();
    private static SQLiteDatabase database;

    public static PlanDayExercice getPlanDayById(Context context, String id){
        database = DBHelper.getInstance(context).getWritableDatabase();
        Cursor planDayExerciceCursor = database.query(DBHelper.TABLE_PLANDAY_EXERCICE, null,DBHelper.COLUMN_ID+"='"+id+"'", null, null, null, null);
        planDayExerciceCursor.moveToFirst();
        PlanDayExercice planDayExercice = cursorToPlanDayExercice(planDayExerciceCursor);
        planDayExerciceCursor.close();
        database.close();
        return planDayExercice;
    }



    public static List<PlanDayExercice> getAllPlanDayExercicesByPlanDay(Context context, String planDay){
        database = DBHelper.getInstance(context).getWritableDatabase();
        String whereClauses = DBHelper.COLUMN_PLANDAY + "='" + planDay +"'";
        Cursor planDayCursor = database.query(DBHelper.TABLE_PLANDAY_EXERCICE, null,whereClauses, null, null, null, null);
        List<PlanDayExercice> planDyList = new ArrayList<>();
        while (planDayCursor.moveToNext()) {
            planDyList.add(cursorToPlanDayExercice(planDayCursor));
        }
        planDayCursor.close();
        database.close();
        return planDyList;
    }


    public static void updatePlanDayExercice (PlanDayExercice planDayExercice,Context context){
        try {
            database = DBHelper.getInstance(context).getWritableDatabase();
            String whereClauses = DBHelper.COLUMN_ID + "='" + planDayExercice.getId()+"'";
            database.update(DBHelper.TABLE_PLANDAY_EXERCICE, getDBValues(planDayExercice), whereClauses, null);
            database.close();
        }catch(Exception e){
            Log.d(LOG_TAG,"Fehler in updatePlanDayExercice " + e.getMessage());
        }
    }

    public static void newPlanDayExercice(PlanDayExercice planDayExercice,Context context){
        try {
            database = DBHelper.getInstance(context).getWritableDatabase();
            ContentValues cv = getDBValues(planDayExercice);
            database.insert(DBHelper.TABLE_PLANDAY_EXERCICE, null, cv);
            database.close();
        }catch(Exception exception){
            Log.e(LOG_TAG,"Error in newPlanDayExercice" + exception );
        }
    }





    private static PlanDayExercice cursorToPlanDayExercice(Cursor planDayCursor){
        String id = planDayCursor.getString(planDayCursor.getColumnIndex(DBHelper.COLUMN_ID));
        int setCount = planDayCursor.getInt(planDayCursor.getColumnIndex(DBHelper.COLUMN_SETCOUNT));
        String repeats = planDayCursor.getString(planDayCursor.getColumnIndex(DBHelper.COLUMN_REPEATS));
        String exercice = planDayCursor.getString(planDayCursor.getColumnIndex(DBHelper.COLUMN_EXERCICE));
        String planDay = planDayCursor.getString(planDayCursor.getColumnIndex(DBHelper.COLUMN_PLANDAY));
        String description = planDayCursor.getString(planDayCursor.getColumnIndex(DBHelper.COLUMN_DESCRIPTION));
        return new PlanDayExercice.Builder().id(id).planDay(planDay).exercice(exercice).setCount(setCount).setRepeatr(repeats).setDescription(description).build();
    }


    private static ContentValues getDBValues(PlanDayExercice planDayExercice){
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.COLUMN_SETCOUNT, planDayExercice.getSetCount());
        cv.put(DBHelper.COLUMN_EXERCICE, planDayExercice.getExercice());
        cv.put(DBHelper.COLUMN_DESCRIPTION, planDayExercice.getDescription());
        cv.put(DBHelper.COLUMN_REPEATS, planDayExercice.getRepeat());
        cv.put(DBHelper.COLUMN_PLANDAY,planDayExercice.getPlanDay());
        cv.put(DBHelper.COLUMN_ID, planDayExercice.getId());
        return cv;
    }


}
