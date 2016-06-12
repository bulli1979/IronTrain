package com.irontrain.storage.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.irontrain.business.PlanDay;
import com.irontrain.storage.DBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ebi on 07.04.2016.
 * Class to sotre and get PlanDay Objects from SQLLite DB
 * Verantwortlich: Andreas Züger
 */
public class DAOPlanDay {
    private static final String LOG_TAG = DAOPlanDay.class.getSimpleName();
    private static SQLiteDatabase database;

    public static PlanDay getPlanDayById(Context context,String id){
        database = DBHelper.getInstance(context).getWritableDatabase();
        Cursor planDayCursor = database.query(DBHelper.TABLE_PLANDAY, null,DBHelper.COLUMN_ID+"='"+id+"'", null, null, null, null);
        planDayCursor.moveToFirst();
        PlanDay planDay = cursorToPlanDay(planDayCursor);
        planDayCursor.close();
        database.close();
        return planDay;
    }

    public static void newPlanDay(PlanDay planDay, Context context){
        try{
            database = DBHelper.getInstance(context).getWritableDatabase();
            ContentValues cv = getDBValues(planDay);
            database.insert(DBHelper.TABLE_PLANDAY, null, cv);
            database.close();
        }catch(Exception e){
            Log.e(LOG_TAG,"Error in DAOPlanDay newPlanDay" , e);
        }
    }


    public static void updatePlanDay (PlanDay planDay,Context context){
        try {
            database = DBHelper.getInstance(context).getWritableDatabase();
            String whereClauses = DBHelper.COLUMN_ID + "='" + planDay.getId()+"'";
            database.update(DBHelper.TABLE_PLANDAY, getDBValues(planDay), whereClauses, null);
            database.close();
        }catch(Exception e){
            Log.d(LOG_TAG,"Fehler in DAOPlanDay updatePlan " + e.getMessage());
        }
    }

    public static List<PlanDay> getAllPlanDaysByPlan(Context context,String plan){
        database = DBHelper.getInstance(context).getWritableDatabase();
        String whereClauses = DBHelper.COLUMN_PLAN + "='" + plan +"'";
        Cursor planDayCursor = database.query(DBHelper.TABLE_PLANDAY, null,whereClauses, null, null, null, null);
        List<PlanDay> planDyList = new ArrayList<>();
        while (planDayCursor.moveToNext()) {
            planDyList.add(cursorToPlanDay(planDayCursor));
        }
        planDayCursor.close();
        database.close();
        return planDyList;
    }

    private static PlanDay cursorToPlanDay(Cursor pc){
        String id = pc.getString(pc.getColumnIndex(DBHelper.COLUMN_ID));
        String name = pc.getString(pc.getColumnIndex(DBHelper.COLUMN_NAME));
        String description = pc.getString(pc.getColumnIndex(DBHelper.COLUMN_DESCRIPTION));
        String plan = pc.getString(pc.getColumnIndex(DBHelper.COLUMN_PLAN));
        return new PlanDay.Builder().id(id).name(name).description(description).plan(plan).build();
    }



    private static ContentValues getDBValues(PlanDay planDay){
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.COLUMN_NAME, planDay.getName());
        cv.put(DBHelper.COLUMN_DESCRIPTION, planDay.getDescription());
        cv.put(DBHelper.COLUMN_PLAN,planDay.getPlan());
        cv.put(DBHelper.COLUMN_ID, planDay.getId());
        return cv;
    }
}
