/*
 * You can use only script parts but do not copy the application. All rights reserved by Mirko Eberlein.
 */

package com.irontrain;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.irontrain.action.MenuListener;
import com.irontrain.business.GraphObject;
import com.irontrain.business.Train;
import com.irontrain.business.TrainExercice;
import com.irontrain.business.TrainItem;
import com.irontrain.business.TrainSet;
import com.irontrain.tools.Tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * This activity build the graph Object to get a Train overview
 * */
public class StatsDetailActivity extends AppCompatActivity {
    private final String TAG = StatsDetailActivity.class.getSimpleName();
    private List<String> dateLabels = new ArrayList();
    private Map<String,GraphObject> graphMap = new HashMap();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_detail);

        TrainItem item = (TrainItem) getIntent().getSerializableExtra("trainItem");
        createLineDataSets(item);
        LineChart chart = (LineChart)findViewById(R.id.chart);

        List<ILineDataSet> dataSets = new ArrayList<>();
        int count = 0;
        for(Map.Entry e : graphMap.entrySet()){
            GraphObject graphObj = (GraphObject)e.getValue();
            LineDataSet dataset = new LineDataSet(graphObj.getEntries(), graphObj.getExerciceName());
            dataset.setColor(Tools.getInstance().getColor(count,this));
            count++;
            dataSets.add(dataset);

        }
        Log.d(TAG,"Anzahl sets " + dataSets.size() + " und " + dateLabels.size());
        LineData data = new LineData(dateLabels, dataSets);
        chart.setData(data);
        chart.setDescription("Übersicht für " + item.getPlanDay().getName());
    }


    private List<LineDataSet> createLineDataSets(TrainItem item){
        float weight;
        Log.d(TAG,"Anzahl Trainings detail " + item.getTrains().size());
        for(Train train : item.getTrains()){

            dateLabels.add(Tools.getInstance().dateToString(train.getDate()));
            Log.d(TAG,"Anzahl übungen: " + train.getTrainExerciceList().size());
            for(TrainExercice trainExercice : train.getTrainExerciceList()){
                weight=0;
                for(TrainSet set : trainExercice.getTrainSetList()){
                    if(set.getWeight()>weight) {
                        weight = set.getWeight();
                    }
                }
                addToGraphItem(weight,trainExercice.getPlanDayExercice(),trainExercice.getExerciceTitle());
            }
        }
        return null;
    }

    /**
     *     create an Graph entry and push it into the map
     */
    private void addToGraphItem(float weight,String key,String title){
        if(!graphMap.containsKey(key)){
            GraphObject graph = new GraphObject(title);
            graphMap.put(key,graph);
        }
        int size = graphMap.get(key).getEntries().size();
        Entry entry = new Entry(weight,size);
        graphMap.get(key).getEntries().add(entry);
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
