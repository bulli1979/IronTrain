package com.mirko_eberlein.irontrain.storage;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;

import com.mirko_eberlein.irontrain.business.Exercice;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Ebi on 16.02.2016.
 */
public class DBUpdateProcess {
    private List<Exercice> existingList;
    private List<Integer> ids;
    private static final String LOG_TAG = DBUpdateProcess.class.getSimpleName();
    private SQLiteDatabase database;


    public void updateExercices(JSONArray arr,View v){
        database = DBHelper.getInstance(v.getContext()).getWritableDatabase();
        String[] searchColumns = new String[]{"importnumber"};
        Cursor exercices = database.query("Exercice", searchColumns, null, null, null, null, null);
        importExercices(exercices, arr);
        exercices = database.query("Exercice",searchColumns,null,null,null,null,null);
    }
    private void importExercices(Cursor exercices,JSONArray arr) {
        try {

            ids = new ArrayList<>();
            int count = 1;
            while (exercices.moveToNext()) {
                ids.add(exercices.getInt(0));
            }

            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                if(!ids.contains(obj.getInt("importnumber"))) {
                    ContentValues values = new ContentValues();
                    values.put("id", UUID.randomUUID().toString());
                    values.put("name", (String) obj.getJSONArray("name").getString(0));
                    values.put("description", obj.getJSONArray("description").getString(0));
                    values.put("importnumber",obj.getInt("importnumber"));
                    database.insert("Exercice", null, values);
                }
            }
        }catch(Exception e){
            Log.d(LOG_TAG,"Error in importExercices " + e);
        }

    }
}
