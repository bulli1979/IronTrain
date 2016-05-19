package com.irontrain;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

import java.util.List;

import com.irontrain.action.MenuListener;
import com.irontrain.action.OCListener;
import com.irontrain.adapter.PlanAdapter;
import com.irontrain.business.Plan;
import com.irontrain.storage.daos.DAOPlan;

public class PlanListActivity extends AppCompatActivity {
    private Button newPlanButton;
    private static final String LOG_TAG = PlanListActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_list);
        newPlanButton = (Button) findViewById(R.id.createPlan);
        newPlanButton.setOnClickListener(OCListener.getNewPlanListener());
        List<Plan> planList = DAOPlan.getAllPlans(this.getBaseContext());
        TextView tv = (TextView)findViewById(R.id.emptyText);
        if(planList.isEmpty()){
            tv.setText(R.string.noItemsFound);
        }else{
            tv.setText("");
            buildTable(planList);
        }
    }



    private void buildTable(List<Plan> planList) {
        PlanAdapter adapter = new PlanAdapter(this,
                R.layout.custom_planday_list_item, planList);
        ListView listView = (ListView)findViewById(R.id.planList);
        listView.setAdapter(adapter);
        listView.setTag(planList);
        listView.setOnItemClickListener(OCListener.getOpenPlanListener());
    }

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
