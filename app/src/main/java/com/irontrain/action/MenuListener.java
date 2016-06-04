package com.irontrain.action;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.irontrain.HomeActivity;
import com.irontrain.PlanListActivity;
import com.irontrain.R;
import com.irontrain.StatsActivity;
import com.irontrain.TrainActivity;
import com.irontrain.storage.DBUpdateProcess;
import com.irontrain.storage.UpdateCheck;
import com.irontrain.tools.Tools;
import org.json.JSONArray;
/**
 * Created by Ebi on 19.05.2016.
 * Listener for Menu needed in all applications who display menu
 */
public class MenuListener {
    private static MenuListener ourInstance = new MenuListener();
    private static final String LOG_TAG = MenuListener.class.getSimpleName();

    public static MenuListener getInstance() {
        return ourInstance;
    }

    private MenuListener() {
    }

    public static void getActionMenuComplete(AppCompatActivity activity, int itemid){
        Intent nextScreen;
        switch(itemid){
            case R.id.action_plan :
                nextScreen = new Intent(activity, PlanListActivity.class);
                activity.startActivity(nextScreen);
                break;
            case R.id.action_home :
                nextScreen = new Intent(activity, HomeActivity.class);
                activity.startActivity(nextScreen);
                break;
            case R.id.action_stats :
                nextScreen = new Intent(activity, StatsActivity.class);
                activity.startActivity(nextScreen);
                break;
            case R.id.action_train :
                nextScreen = new Intent(activity, TrainActivity.class);
                activity.startActivity(nextScreen);
                break;
            case R.id.action_update :
                try {
                    AsyncTask asyncTask = new UpdateCheck().execute("");
                    JSONArray arr = (JSONArray) asyncTask.get();
                    DBUpdateProcess updater = new DBUpdateProcess();
                    int count = updater.updateExercices(arr, activity.getApplicationContext());
                    Tools.getInstance().showToast(activity.getApplicationContext(), count + " " + activity.getResources().getString(R.string.updateMessages));
                }catch(Exception e) {
                    Log.e(LOG_TAG,"Error in update over menu " + e.toString());
                }
                break;
        }
    }
}
