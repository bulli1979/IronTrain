package com.irontrain.storage;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.irontrain.business.Exercice;
import com.irontrain.storage.daos.DAOExercice;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Ebi on 16.02.2016.
 * Class handle Importet Json and update all Exercices who dosend exist
 *
 */
public class DBUpdateProcess {
    private List<Integer> ids;
    private static final String LOG_TAG = DBUpdateProcess.class.getSimpleName();

    public int updateExercices(JSONArray arr,View v){
        Context context = v.getContext();
        List<Exercice> exercices = DAOExercice.getAllExercices(context);
        return importExercices(exercices, arr,context);
    }
    private int importExercices(List<Exercice> exercices,JSONArray jsonArray,Context context) {
        int count = 0;
        try {

            ids = new ArrayList<>();
            for (Exercice e : exercices) {
                if(e.getImportnumber() >0) {
                    ids.add(e.getImportnumber());
                }
            }

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                if(!ids.contains(obj.getInt("importnumber"))) {

                    Exercice exercice = new Exercice.Builder().id(UUID.randomUUID().toString()).name((String) obj.getJSONArray("name").getString(0))
                            .description( obj.getJSONArray("description").getString(0)).importNumber(obj.getInt("importnumber")).build();
                    DAOExercice.createExercice(exercice,context);
                    count++;
                }
            }
        }catch(Exception e){
            Log.d(LOG_TAG,"Error in importExercices " + e);
        }
        return count;
    }
}
