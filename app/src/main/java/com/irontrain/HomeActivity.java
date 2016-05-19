package com.irontrain;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.irontrain.action.MenuListener;
import com.irontrain.action.OCListener;
import com.irontrain.storage.DBUpdateProcess;
import com.irontrain.storage.UpdateCheck;
import com.irontrain.tools.Tools;

import org.json.JSONArray;

public class HomeActivity extends AppCompatActivity {
    private static final String LOG_TAG = HomeActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initialize();
    }

    public void initialize(){
        Button updateButton = (Button) findViewById(R.id.updateButton);
        Button planListButton = (Button) findViewById(R.id.planListButton);
        Button trainButton = (Button) findViewById(R.id.trainButton);
        updateButton.setOnClickListener(OCListener.getUpdateListener());
        planListButton.setOnClickListener(OCListener.getPlanListListener());
        trainButton.setOnClickListener(OCListener.openTrainListener());
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
