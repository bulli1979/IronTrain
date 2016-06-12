package com.irontrain;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import java.util.List;
import com.irontrain.action.MenuListener;
import com.irontrain.action.OCListener;
import com.irontrain.adapter.PlanAdapter;
import com.irontrain.business.Plan;
import com.irontrain.storage.daos.DAOPlan;

/**
 * created by Mirko Eberlein
 * handle PlanListActivity
 * shoe plans and give chance to create and open plans
 * Verantwortlich: Andreas Züger
 * */
public class PlanListActivity extends AppCompatActivity {
    private List<Plan> planList;
    private static final String LOG_TAG = PlanListActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_list);
        Button newPlanButton = (Button) findViewById(R.id.createPlan);
        if(newPlanButton != null) {
            newPlanButton.setOnClickListener(OCListener.getInstance().getNewPlanListener());
        }
        planList = DAOPlan.getAllPlans(this.getBaseContext());
        TextView tv = (TextView)findViewById(R.id.emptyText);
        if(tv != null) {
            if (planList.isEmpty()) {
                tv.setText(R.string.noItemsFound);
            } else {
                tv.setText("");
                initPlanList();
            }
        }
    }

    private void initPlanList() {
        PlanAdapter adapter = new PlanAdapter(this,
                R.layout.custom_planday_list_item, planList);
        ListView listView = (ListView)findViewById(R.id.planList);
        if(listView != null) {
            listView.setAdapter(adapter);
            listView.setTag(planList);
            listView.setOnItemClickListener(planListlistener);
        }
    }

    private  AdapterView.OnItemClickListener planListlistener =  new  AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id){
            try {
                Plan plan = planList.get(position);
                Intent nextScreen = new Intent(view.getContext(), EditPlanActivity.class);
                nextScreen.putExtra("plan", plan.getId());
                view.getContext().startActivity(nextScreen);
            }catch(Exception e){
                Log.d(LOG_TAG,"Error in getNewPlanListener " + e );
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Menuitem
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        MenuListener.getActionMenuComplete(this,id);
        return super.onOptionsItemSelected(item);
    }

}
