package com.irontrain.action;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.irontrain.EditPlanActivity;
import com.irontrain.EditPlanDayActivity;
import com.irontrain.business.Plan;
import com.irontrain.business.PlanDay;
import com.irontrain.storage.DBUpdateProcess;
import com.irontrain.PlanListActivity;
import com.irontrain.storage.UpdateCheck;

import org.json.JSONArray;

import java.util.List;

/**
 * Created by Ebi on 16.02.2016.
 */
public class OCListener {
    private static OCListener ourInstance = new OCListener();
    private static final String LOG_TAG = OCListener.class.getSimpleName();
    public static OCListener getInstance() {
        return ourInstance;
    }

    private OCListener() {
    }

    public static View.OnClickListener getUpdateListener(){
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    AsyncTask asyncTask = new UpdateCheck().execute("");
                    JSONArray arr = (JSONArray) asyncTask.get();
                    DBUpdateProcess updater = new DBUpdateProcess();
                    updater.updateExercices(arr,v);

                }catch(Exception e){
                    Log.d(LOG_TAG,"Error in getUpdateListener " + e );
                }
            }
        };
        return listener;
    }

    public static View.OnClickListener getOpenPlanListener(){
        View.OnClickListener listener = new View.OnClickListener(){
            @Override
            public void onClick(View v){
                try {
                    Plan plan = (Plan) v.getTag();
                    Intent nextScreen = new Intent(v.getContext(), EditPlanActivity.class);
                    nextScreen.putExtra("plan", plan.getId());
                    v.getContext().startActivity(nextScreen);
                }catch(Exception e){
                    Intent nextScreen = new Intent(v.getContext(), EditPlanActivity.class);
                    v.getContext().startActivity(nextScreen);
                    Log.d(LOG_TAG,"Error in getNewPlanListener " + e );
                }
            }
        };
        return listener;
    }


    public static View.OnClickListener getNewPlanListener(){
        View.OnClickListener oclGtoNewPlan = new View.OnClickListener(){
            @Override
            public void onClick(View v){
                try {
                    Intent nextScreen = new Intent(v.getContext(), EditPlanActivity.class);
                    v.getContext().startActivity(nextScreen);
                }catch(Exception e){

                    Log.d(LOG_TAG,"Error in getNewPlanListener " + e );
                }
            }
        };
        return oclGtoNewPlan;
    }


    public static  AdapterView.OnItemClickListener getOpenPlanDayListener(){
        AdapterView.OnItemClickListener listener = new  AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                try {
                    List<PlanDay> planDayList = (List<PlanDay>)parent.getTag();
                    PlanDay planDay = planDayList.get(position);
                    Intent nextScreen = new Intent(view.getContext(), EditPlanDayActivity.class);
                    nextScreen.putExtra("planDay", planDay.getId());
                    view.getContext().startActivity(nextScreen);
                }catch(Exception e){
                    Log.d(LOG_TAG,"Error in getNewPlanListener " + e );
                }
            }
        };
        return listener;
    }


    public static View.OnClickListener getNewPlanDayListener(){
        View.OnClickListener listener = new View.OnClickListener(){
            @Override
            public void onClick(View v){
                try {
                    Plan plan = (Plan) v.getTag();
                    Intent nextScreen = new Intent(v.getContext(), EditPlanDayActivity.class);
                    nextScreen.putExtra("plan",plan.getId());
                    v.getContext().startActivity(nextScreen);
                }catch(Exception e){

                    Log.d(LOG_TAG,"Error in getNewPlanListener " + e );
                }
            }
        };
        return listener;
    }


    public static View.OnClickListener getPlanListListener(){
        View.OnClickListener listener = new View.OnClickListener(){
            @Override
            public void onClick(View v){
                try {
                    Intent nextScreen = new Intent(v.getContext(), PlanListActivity.class);
                    v.getContext().startActivity(nextScreen);
                }catch(Exception e){

                    Log.d(LOG_TAG,"Error in oclGtoPlanList " + e );
                }
            }
        };
        return listener;
    }
}
