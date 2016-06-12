package com.irontrain.storage.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.irontrain.business.Plan;
import com.irontrain.storage.DBHelper;
import com.irontrain.tools.Tools;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Mirko Eberlein on 06.03.2016.
 * Class manages all DB Access for the Plan Business Object
 * Verantwortlich: Andreas Züger
 */
public class DAOPlan {
    private static final String LOG_TAG = DAOPlan.class.getSimpleName();
    private static SQLiteDatabase database;

    public static Plan getPlanById(Context context, String id){
        database = DBHelper.getInstance(context).getWritableDatabase();
        Cursor planc = database.query(DBHelper.TABLE_PLAN, null,DBHelper.COLUMN_ID+"='"+id+"'", null, null, null, null);
        planc.moveToFirst();
        Plan plan = cursorToPlan(planc);
        planc.close();
        database.close();
        return plan;
    }


    public static void newPlan (Plan plan,Context context){
        try {
            database = DBHelper.getInstance(context).getWritableDatabase();
            ContentValues cv = getDBValues(plan);
            database.insert(DBHelper.TABLE_PLAN, null, cv);
            Log.d(LOG_TAG, "erstelle Plan " + plan.getName());
            database.close();
        }catch(Exception e){
            Log.e(LOG_TAG,"Error in newPlan ",e);
        }
    }

    public static void updatePlan (Plan plan,Context context){
        try{
            database = DBHelper.getInstance(context).getWritableDatabase();
            ContentValues cv = getDBValues(plan);
            database.insert(DBHelper.TABLE_PLAN, null, cv);
            database.close();
        }catch(Exception e){
            Log.e(LOG_TAG,"Error in updatePlan ",e);
        }
    }


    public static List<Plan> getAllPlans(Context context){
        database = DBHelper.getInstance(context).getWritableDatabase();
        Cursor planc = database.query(DBHelper.TABLE_PLAN, null,null, null, null, null, null);
        List<Plan> planList = new ArrayList<>();
        while (planc.moveToNext()) {
            planList.add(cursorToPlan(planc));
        }
        planc.close();
        database.close();
        return planList;
    }

    private static Plan cursorToPlan(Cursor pc){
        String id = pc.getString(pc.getColumnIndex(DBHelper.COLUMN_ID));
        String name = pc.getString(pc.getColumnIndex(DBHelper.COLUMN_NAME));
        String description = pc.getString(pc.getColumnIndex(DBHelper.COLUMN_DESCRIPTION));
        String createdOnString = pc.getString(pc.getColumnIndex(DBHelper.COLUMN_CREATEDON));
        Date createdOn = Tools.getInstance().stringToDate(createdOnString);
        return new Plan.Builder().createdOn(createdOn).description(description).name(name).id(id).build();
    }



    private static ContentValues getDBValues(Plan plan){
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.COLUMN_NAME, plan.getName());
        cv.put(DBHelper.COLUMN_DESCRIPTION, plan.getDescription());
        cv.put(DBHelper.COLUMN_CREATEDON, Tools.getInstance().dateToString(plan.getCreatedOn()));
        cv.put(DBHelper.COLUMN_ID, plan.getId());
        return cv;
    }



}
