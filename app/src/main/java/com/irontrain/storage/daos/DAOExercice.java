package com.irontrain.storage.daos;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.irontrain.business.Exercice;
import com.irontrain.storage.DBHelper;

import java.util.UUID;

/**
 * Created by Ebi on 07.04.2016.
 */
public class DAOExercice {

    private static final String LOG_TAG = Exercice.class.getSimpleName();
    private static SQLiteDatabase database;

    public static Exercice getExerciceById(Context context, String id){
        database = DBHelper.getInstance(context).getWritableDatabase();
        Cursor exerciceCursor = database.query(DBHelper.TABLE_EXERCICE, null,DBHelper.COLUMN_ID+"='"+id+"'", null, null, null, null);
        exerciceCursor.moveToFirst();
        Exercice exercice = cursorToPlanDay(exerciceCursor);
        exerciceCursor.close();
        database.close();
        return exercice;
    }

    public static void saveOrUpdateExercice (Exercice exercice,Context context){
        try {
            database = DBHelper.getInstance(context).getWritableDatabase();
            if (exercice.getId() != null) {
                String whereClauses = DBHelper.COLUMN_ID + "='" + exercice.getId()+"'";
                database.update(DBHelper.TABLE_EXERCICE, getDBValues(exercice), whereClauses, null);
            } else {
                exercice.setId(UUID.randomUUID().toString());
                ContentValues cv = getDBValues(exercice);
                database.insert(DBHelper.TABLE_EXERCICE, null, cv);
            }
            database.close();
        }catch(Exception e){
            Log.d(LOG_TAG,"Fehler in saveOrUpdateExercice " + e.getMessage());
        }
    }



    private static Exercice cursorToPlanDay(Cursor exerciceCursor){
        String id = exerciceCursor.getString(exerciceCursor.getColumnIndex(DBHelper.COLUMN_ID));
        String name = exerciceCursor.getString(exerciceCursor.getColumnIndex(DBHelper.COLUMN_NAME));
        String description = exerciceCursor.getString(exerciceCursor.getColumnIndex(DBHelper.COLUMN_DESCRIPTION));
        int importNumber = exerciceCursor.getInt(exerciceCursor.getColumnIndex(DBHelper.COLUMN_IMPORTNUMBER));
        return new Exercice.Builder().id(id).importNumber(importNumber).description(description).name(name).build();
    }

    private static ContentValues getDBValues(Exercice exercice){
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.COLUMN_DESCRIPTION,exercice.getDescription());
        cv.put(DBHelper.COLUMN_IMPORTNUMBER, exercice.getImportnumber());
        cv.put(DBHelper.COLUMN_NAME, exercice.getName());
        cv.put(DBHelper.COLUMN_ID, exercice.getId());
        return cv;
    }


}
