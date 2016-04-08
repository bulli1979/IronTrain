package com.mirko_eberlein.irontrain;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

import java.util.List;
import com.mirko_eberlein.irontrain.action.OCListener;
import com.mirko_eberlein.irontrain.business.Plan;
import com.mirko_eberlein.irontrain.storage.daos.DAOPlan;

public class PlanListActivity extends AppCompatActivity {
    private Button newPlanButton;
    TableLayout table;
    private static final String LOG_TAG = PlanListActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_list);
        table = (TableLayout) findViewById(R.id.planList);

        newPlanButton = (Button) findViewById(R.id.createPlan);
        newPlanButton.setOnClickListener(OCListener.getNewPlanListener());

        List<Plan> planList = DAOPlan.getAllPlans(this.getBaseContext());
        TableLayout table = (TableLayout)findViewById(R.id.planList);
        TextView tv = (TextView)findViewById(R.id.emptyText);
        if(planList.isEmpty()){
            tv.setText(R.string.noItemsFound);
        }else{
            tv.setText("");
            buildTable(planList, table);
        }

    }

    private void buildTable(List<Plan> planList,TableLayout table) {

        // outer for loop
        //TODO build a Adapter
        int count=1;
        for (Plan plan : planList) {
            TableRow row = new TableRow(table.getContext());
            row.setTag(plan);
            if(count % 2 == 0){

            }

            row.setOnClickListener(OCListener.getEditPlanListener());
            TextView tv = new TextView(table.getContext());
            tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT));
            Log.d(LOG_TAG,plan.getName());
            Log.d(LOG_TAG,plan.getDescription());
            tv.setPadding(5, 5, 5, 5);
            tv.setText("Plan " + plan.getName());
            row.addView(tv);
            table.addView(row);
        }
    }


}