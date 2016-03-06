package com.mirko_eberlein.irontrain.action;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.mirko_eberlein.irontrain.EditPlan;
import com.mirko_eberlein.irontrain.R;
import com.mirko_eberlein.irontrain.business.Plan;
import com.mirko_eberlein.irontrain.storage.DBUpdateProcess;
import com.mirko_eberlein.irontrain.storage.UpdateCheck;

import org.json.JSONArray;

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
        View.OnClickListener oclBtnUpdate = new View.OnClickListener() {
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
        return oclBtnUpdate;
    }

    public static View.OnClickListener getNewPlanListener(){
        View.OnClickListener oclBtnNewPlan = new View.OnClickListener(){
            @Override
            public void onClick(View v){
                try {
                    Intent nextScreen = new Intent(v.getContext(), EditPlan.class);
                    v.getContext().startActivity(nextScreen);
                }catch(Exception e){

                    Log.d(LOG_TAG,"Error in getNewPlanListener " + e );
                }
            }
        };
        return oclBtnNewPlan;
    }

    public static View.OnClickListener getPlanDayListener(){
        View.OnClickListener oclBtnNewPlanDay = new View.OnClickListener(){
            @Override
            public void onClick(View v){
                try {
                    //TODO: new Activity
                    Intent nextScreen = new Intent(v.getContext(), EditPlan.class);
                    nextScreen.putExtra("plan","");
                    v.getContext().startActivity(nextScreen);

                }catch(Exception e){
                    Log.d(LOG_TAG,"Error in getNewPlanListener " + e );
                }
            }
        };
        return oclBtnNewPlanDay;
    }

    public static View.OnClickListener getPlanSaveListener(Plan p){
        final Plan plan = p;
        View.OnClickListener oclBtnNewPlanSave = new View.OnClickListener(){
            @Override
            public void onClick(View v){
                try {
                    EditText name = (EditText) v.findViewById(R.id.planname);
                    EditText description = (EditText) v.findViewById(R.id.plandescription);
                    plan.setName(name.getText().toString());
                    plan.setDescription(description.getText().toString());
                }catch(Exception e){
                    Log.d(LOG_TAG,"Error in getNewPlanListener " + e );
                }
            }
        };
        return oclBtnNewPlanSave;
    }

}
