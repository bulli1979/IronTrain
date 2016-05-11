package com.irontrain.action;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.irontrain.EditPlanDayExerciceActivity;
import com.irontrain.EditPlanActivity;
import com.irontrain.EditPlanDayActivity;
import com.irontrain.R;
import com.irontrain.TrainActivity;
import com.irontrain.business.Plan;
import com.irontrain.business.PlanDay;
import com.irontrain.business.PlanDayExercice;
import com.irontrain.storage.DBUpdateProcess;
import com.irontrain.PlanListActivity;
import com.irontrain.storage.UpdateCheck;
import com.irontrain.tools.Tools;

import org.json.JSONArray;

import java.util.List;
/**
 * Created by Ebi on 16.02.2016.
 * Klasse stellt alle OCL Listener bereit.
 * Speichern wird in der jeweiligen Activity gehandelt.
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
                    int count = updater.updateExercices(arr,v);
                    Tools.showToast(v.getContext(),count + " " + v.getContext().getResources().getString(R.string.updateMessages));
                }catch(Exception e){
                    Log.d(LOG_TAG,"Error in getUpdateListener " + e );
                }
            }
        };
        return listener;
    }

    public static AdapterView.OnItemClickListener getOpenPlanListener(){
        AdapterView.OnItemClickListener listener =  new  AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                try {
                    List<Plan> planDayList = (List<Plan>)parent.getTag();
                    Plan plan = planDayList.get(position);
                    Intent nextScreen = new Intent(view.getContext(), EditPlanActivity.class);
                    nextScreen.putExtra("plan", plan.getId());
                    view.getContext().startActivity(nextScreen);
                }catch(Exception e){
                    Log.d(LOG_TAG,"Error in getNewPlanListener " + e );
                }
            }
        };
        return listener;
    }

    public static AdapterView.OnItemClickListener getOpenPlanDayExerciceListener(){
        AdapterView.OnItemClickListener listener =  new  AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                try {
                    List<PlanDayExercice> planDayExerciceList = (List<PlanDayExercice>)parent.getTag();
                    PlanDayExercice planDayExercice = planDayExerciceList.get(position);
                    Intent nextScreen = new Intent(view.getContext(), EditPlanDayExerciceActivity.class);
                    nextScreen.putExtra("planDayExercice", planDayExercice.getId());
                    view.getContext().startActivity(nextScreen);
                }catch(Exception e){
                    Log.d(LOG_TAG,"Error in getNewPlanListener " + e );
                }
            }
        };
        return listener;
    }

    public static View.OnClickListener getOpenPlanDirectListener(){
        View.OnClickListener listener = new View.OnClickListener(){
            @Override
            public void onClick(View v){
                try {
                    String plan = (String) v.getTag();
                    Intent nextScreen = new Intent(v.getContext(), EditPlanActivity.class);
                    nextScreen.putExtra("plan",plan);
                    v.getContext().startActivity(nextScreen);
                }catch(Exception e){

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

    public static View.OnClickListener getOpenPlanDayDirectListener(){
                View.OnClickListener goToPlanDay =  new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                try {
                    String planDay = (String) v.getTag();
                    Intent nextScreen = new Intent(v.getContext(), EditPlanDayActivity.class);
                    nextScreen.putExtra("planDay", planDay);
                    v.getContext().startActivity(nextScreen);
                }catch(Exception e){
                    Log.d(LOG_TAG,"Error in getNewPlanListener " + e );
                }
            }
        };
        return goToPlanDay;
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

    public static View.OnClickListener getNewExerciceListener(){
        View.OnClickListener listener = new View.OnClickListener(){
            @Override
            public void onClick(View v){
                try {
                    PlanDay planDay = (PlanDay) v.getTag();
                    Intent nextScreen = new Intent(v.getContext(), EditPlanDayExerciceActivity.class);
                    nextScreen.putExtra("planDay", planDay.getId());
                    v.getContext().startActivity(nextScreen);
                }catch(Exception e){
                    Log.d(LOG_TAG,"Error in getNewPlanListener " + e );
                }
            }
        };
        return listener;
    }

    public static View.OnClickListener openTrainListener(){
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextScreen = new Intent(view.getContext(), TrainActivity.class);
                view.getContext().startActivity(nextScreen);
            }
        };
        return listener;
    }

}
