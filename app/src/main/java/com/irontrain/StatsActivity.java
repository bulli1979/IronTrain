package com.irontrain;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.irontrain.action.MenuListener;
import com.irontrain.adapter.TrainItemAdapter;
import com.irontrain.business.TrainItem;
import com.irontrain.storage.daos.StatsHelper;

import java.util.List;

public class StatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        List<TrainItem> trainItems = StatsHelper.getTrainOverView(getApplicationContext());
        initTrainItemList(trainItems);
    }

    private void initTrainItemList(List<TrainItem> trainItemList){
        TrainItemAdapter adapter = new TrainItemAdapter(this,
                R.layout.custom_planday_list_item, trainItemList);
        ListView listView = (ListView)findViewById(R.id.trainOverview);
        listView.setAdapter(adapter);
        listView.setTag(trainItemList);
        //ADD here onClicklistener
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
