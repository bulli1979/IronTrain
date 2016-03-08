package com.mirko_eberlein.irontrain.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;

import com.mirko_eberlein.irontrain.business.Plan;
import com.mirko_eberlein.irontrain.tools.Tools;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Ebi on 06.03.2016.
 */
public class DAOPlan {
    private static final String LOG_TAG = DAOPlan.class.getSimpleName();
    private static SQLiteDatabase database;

    public static Plan getPlanById(Context context,String id){
        database = DBHelper.getInstance(context).getWritableDatabase();
        Cursor planc = database.query(DBHelper.TABLE_PLAN, null,DBHelper.COLUMN_ID+"="+id, null, null, null, null);
        Plan plan = cursorToPlan(planc);
        planc.close();
        database.close();
        return plan;
    }

    public static void saveOrUpdatePlan (Plan plan,Context context){
        try {
            database = DBHelper.getInstance(context).getWritableDatabase();
            if (plan.getId() != null) {
                Log.d(LOG_TAG,"Id is not null " + plan.getId());
                String whereClauses = DBHelper.COLUMN_ID + "=" + plan.getId();
                database.update(DBHelper.TABLE_PLAN, getDBValues(plan), whereClauses, null);
                Log.d(LOG_TAG, "update Plan " + plan.getName());
            } else {
                Log.d(LOG_TAG, "Id is null " + plan.getId());
                plan.setId(UUID.randomUUID().toString());
                ContentValues cv = getDBValues(plan);
                Log.d(LOG_TAG,"name:" + cv.get("name"));
                database.insert(DBHelper.TABLE_PLAN, null, cv);
                Log.d(LOG_TAG, "erstelle Plan " + plan.getName());
            }
            database.close();
        }catch(Exception e){
            Log.d(LOG_TAG,"Fehler in saveOrUpdatePlan " + e.getMessage());
        }
    }

    public static List<Plan> getAllPlans(Context context){
        database = DBHelper.getInstance(context).getWritableDatabase();
        Cursor planc = database.query(DBHelper.TABLE_PLAN, null,null, null, null, null, null);
        List<Plan> planList = new ArrayList<Plan>();
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
        Date createdon = Tools.stringToDate(createdOnString);
        return new Plan(id,name,description,createdon);
    }



    private static ContentValues getDBValues(Plan plan){
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.COLUMN_NAME, plan.getName());
        cv.put(DBHelper.COLUMN_DESCRIPTION, plan.getDescription());
        cv.put(DBHelper.COLUMN_CREATEDON, Tools.dateToString(plan.getCreatedon()));
        cv.put(DBHelper.COLUMN_ID, plan.getId());
        return cv;
    }



}
