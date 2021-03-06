package com.irontrain.storage;

import android.content.Context;
import android.util.Log;
import com.irontrain.business.Exercice;
import com.irontrain.storage.daos.DAOExercice;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Mirko Eberlein on 16.02.2016.
 * Class handle Importet Json and update all Exercices who dosend exist
 * get an json String and works with this
 * Verantwortlich: Mirko Eberlein
 */
public class DBUpdateProcess {
    private static final String LOG_TAG = DBUpdateProcess.class.getSimpleName();

    public int updateExercices(JSONArray arr,Context  context){
        List<Exercice> exercices = DAOExercice.getAllExercices(context);
        return dbImport(exercices, arr,context);
    }

    private int dbImport(List<Exercice> exercices,JSONArray jsonArray,Context context) {
        List<Integer> ids;
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

                    Exercice exercice = new Exercice.Builder().id(UUID.randomUUID().toString()).name(obj.getJSONArray("name").getString(0))
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
