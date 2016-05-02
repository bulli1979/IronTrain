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
import java.util.UUID;

/**
 * Created by Fabricio on 07.04.2016.
 */
public class DAOPlanDayExercice {
    private static final String LOG_TAG = DAOPlanDayExercice.class.getSimpleName();
    private static SQLiteDatabase database;

    public static PlanDayExercice getPlanDayById(Context context, String id){
        database = DBHelper.getInstance(context).getWritableDatabase();
        Cursor planDayExerciceCursor = database.query(DBHelper.TABLE_PLANDAY, null,DBHelper.COLUMN_ID+"='"+id+"'", null, null, null, null);
        planDayExerciceCursor.moveToFirst();
        PlanDayExercice planDayExercice = cursorToPlanDayExercice(planDayExerciceCursor);
        planDayExerciceCursor.close();
        database.close();
        return planDayExercice;
    }



    public static List<PlanDayExercice> getAllPlanDayByPlanDay(Context context, String planDay){
        database = DBHelper.getInstance(context).getWritableDatabase();
        String whereClauses = DBHelper.COLUMN_PLANDAY + "='" + planDay +"'";
        Cursor planDayCursor = database.query(DBHelper.TABLE_PLANDAY, null,whereClauses, null, null, null, null);
        List<PlanDayExercice> planDyList = new ArrayList<PlanDayExercice>();
        while (planDayCursor.moveToNext()) {
            planDyList.add(cursorToPlanDayExercice(planDayCursor));
        }
        planDayCursor.close();
        database.close();
        return planDyList;
    }


    public static void saveOrUpdatePlanDayExercice (PlanDayExercice planDayExercice,Context context){
        try {
            database = DBHelper.getInstance(context).getWritableDatabase();
            if (planDayExercice.getId() != null) {
                String whereClauses = DBHelper.COLUMN_ID + "='" + planDayExercice.getId()+"'";
                database.update(DBHelper.TABLE_PLAN, getDBValues(planDayExercice), whereClauses, null);
            } else {
                planDayExercice.setId(UUID.randomUUID().toString());
                ContentValues cv = getDBValues(planDayExercice);
                database.insert(DBHelper.TABLE_PLAN, null, cv);
            }
            database.close();
        }catch(Exception e){
            Log.d(LOG_TAG,"Fehler in saveOrUpdatePlan " + e.getMessage());
        }
    }



    private static PlanDayExercice cursorToPlanDayExercice(Cursor planDayCursorc){
        String id = planDayCursorc.getString(planDayCursorc.getColumnIndex(DBHelper.COLUMN_ID));
        int setCount = planDayCursorc.getInt(planDayCursorc.getColumnIndex(DBHelper.COLUMN_SETCOUNT));
        String exercice = planDayCursorc.getString(planDayCursorc.getColumnIndex(DBHelper.COLUMN_EXERCICE));
        String planDay = planDayCursorc.getString(planDayCursorc.getColumnIndex(DBHelper.COLUMN_PLANDAY));

        return new PlanDayExercice.Builder().id(id).planDay(planDay).exercice(exercice).setCount(setCount).build();
    }


    private static ContentValues getDBValues(PlanDayExercice planDayExercice){
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.COLUMN_SETCOUNT, planDayExercice.getSetCount());
        cv.put(DBHelper.COLUMN_EXERCICE, planDayExercice.getExercice());
        cv.put(DBHelper.COLUMN_PLANDAY,planDayExercice.getPlanDay());
        cv.put(DBHelper.COLUMN_ID, planDayExercice.getId());
        return cv;
    }


}
