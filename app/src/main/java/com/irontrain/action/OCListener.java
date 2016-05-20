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
import com.irontrain.storage.DBUpdateProcess;
import com.irontrain.PlanListActivity;
import com.irontrain.storage.UpdateCheck;
import com.irontrain.tools.Tools;
import org.json.JSONArray;
import java.util.List;
/**
 * Created by Ebi on 16.02.2016.
 * Klasse stellt alle OCL Listener bereit welche eventuell noch woanders verwendet werden könnten.
 * Listener welche nicht in anderen Constellationen eingesetzt werden sollen sind hier nicht erfasst.
 * Singelton Design
 */
public class OCListener {
    private static final OCListener ourInstance = new OCListener();
    private static final String LOG_TAG = OCListener.class.getSimpleName();
    private View.OnClickListener oclistener;
    public static OCListener getInstance() {
        return ourInstance;
    }
    //mark constructor as final will need a default private constructor
    private OCListener(){}

    public View.OnClickListener getPlanListListener(){
        oclistener = new View.OnClickListener(){
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
        return oclistener;
    }

    public View.OnClickListener getNewPlanDayListener(){
        oclistener = new View.OnClickListener(){
            @Override
            public void onClick(View v){
                try {
                    Plan plan = (Plan) v.getTag();
                    if(plan.getId()==null){
                        Tools.showToast(v.getContext(),v.getContext().getString(R.string.firstSave));
                        return;
                    }
                    Intent nextScreen = new Intent(v.getContext(), EditPlanDayActivity.class);
                    nextScreen.putExtra("plan",plan.getId());
                    v.getContext().startActivity(nextScreen);
                }catch(Exception e){

                    Log.d(LOG_TAG,"Error in getNewPlanListener " + e );
                }
            }
        };
        return oclistener;
    }

    public View.OnClickListener getOpenPlanByIdListener(){
        oclistener = new View.OnClickListener(){
            @Override
            public void onClick(View v){
                try {
                    String planId = (String)v.getTag();

                    Intent nextScreen = new Intent(v.getContext(), EditPlanActivity.class);
                    nextScreen.putExtra("plan",planId);
                    v.getContext().startActivity(nextScreen);
                }catch(Exception e){

                    Log.d(LOG_TAG,"Error in getOpenPlanByIdListener " + e );
                }
            }
        };
        return oclistener;
    }


    public View.OnClickListener getUpdateListener(){
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    AsyncTask asyncTask = new UpdateCheck().execute("");
                    JSONArray arr = (JSONArray) asyncTask.get();
                    DBUpdateProcess updater = new DBUpdateProcess();
                    int count = updater.updateExercices(arr,v.getContext());
                    Tools.showToast(v.getContext(),count + " " + v.getContext().getResources().getString(R.string.updateMessages));
                }catch(Exception e){
                    Log.d(LOG_TAG,"Error in getUpdateListener " + e );
                }
            }
        };
        return listener;
    }

    public View.OnClickListener getNewPlanListener(){
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

    public View.OnClickListener getOpenPlanDayDirectListener(){
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





    public View.OnClickListener getNewExerciceListener(){
        View.OnClickListener listener = new View.OnClickListener(){
            @Override
            public void onClick(View v){
                try {
                    PlanDay planDay = (PlanDay) v.getTag();
                    if(null == planDay.getId()){
                        Tools.showToast(v.getContext(),v.getContext().getString(R.string.firstSave));
                        return;
                    }
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
