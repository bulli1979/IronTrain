package com.irontrain;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.irontrain.action.MenuListener;
import com.irontrain.adapter.TrainItemAdapter;
import com.irontrain.business.TrainItem;
import com.irontrain.storage.daos.StatsHelper;

import java.util.List;

public class StatsActivity extends AppCompatActivity {
    private static final String LOG_TAG = EditPlanActivity.class.getSimpleName();
    private List<TrainItem> trainItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        trainItems = StatsHelper.getTrainOverView(getApplicationContext());
        initTrainItemList(trainItems);
    }

    private void initTrainItemList(List<TrainItem> trainItemList){
        TrainItemAdapter adapter = new TrainItemAdapter(this,
                R.layout.custom_planday_list_item, trainItemList);
        ListView listView = (ListView)findViewById(R.id.trainOverview);
        listView.setAdapter(adapter);
        listView.setTag(trainItemList);
        listView.setOnItemClickListener(listListener);
        //ADD here onClicklistener
    }

    private AdapterView.OnItemClickListener listListener = new  AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id){
            try {
                Log.d(LOG_TAG,"I am here we will show" + position);

                TrainItem item = trainItems.get(position);
                Intent nextScreen =  new Intent(view.getContext(), StatsDetailActivity.class);
                nextScreen.putExtra("trainItem",item);
                view.getContext().startActivity(nextScreen);
            }catch(Exception e){
                Log.d(LOG_TAG,"Error in listListener " + e );
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
