package com.mirko_eberlein.irontrain;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

import java.util.List;
import com.mirko_eberlein.irontrain.action.OCListener;
import com.mirko_eberlein.irontrain.business.Plan;
import com.mirko_eberlein.irontrain.storage.DAOPlan;

public class PlanListActivity extends AppCompatActivity {
    private Button newPlanButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_list);
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

        for (Plan plan : planList) {
            TableRow row = new TableRow(table.getContext());
            TextView tv = new TextView(table.getContext());
            tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT));

            tv.setPadding(5, 5, 5, 5);
            tv.setText("Plan " + plan.getName());
            row.addView(tv);
        }
    }
}
