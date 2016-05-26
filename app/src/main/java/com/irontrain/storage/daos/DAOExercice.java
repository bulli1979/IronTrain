package com.irontrain.storage.daos;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.irontrain.business.Exercice;
import com.irontrain.storage.DBHelper;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ebi on 07.04.2016.
 * Class to save and get Exercices from and to Databases
 */
public class DAOExercice {

    private static final String LOG_TAG = Exercice.class.getSimpleName();
    private static SQLiteDatabase database;

    public static Exercice getExerciceById(Context context, String id){
        database = DBHelper.getInstance(context).getWritableDatabase();
        Cursor exerciceCursor = database.query(DBHelper.TABLE_EXERCICE, null,DBHelper.COLUMN_ID+"='"+id+"'", null, null, null, null);
        exerciceCursor.moveToFirst();
        Exercice exercice = cursorToExercice(exerciceCursor);
        exerciceCursor.close();
        database.close();
        return exercice;
    }

    public static void createExercice (Exercice exercice,Context context){
        try {
            database = DBHelper.getInstance(context).getWritableDatabase();
            ContentValues cv = getDBValues(exercice);
            database.insert(DBHelper.TABLE_EXERCICE, null, cv);
            database.close();
        }catch(Exception e){
            Log.d(LOG_TAG,"Fehler in saveOrUpdateExercice " + e.getMessage());
        }
    }


    public static List<Exercice> getAllExercices(Context context){
        database = DBHelper.getInstance(context).getWritableDatabase();
        Cursor exercicec = database.query(DBHelper.TABLE_EXERCICE, null,null, null, null, null, null);
        List<Exercice> exerciceList = new ArrayList<>();
        while (exercicec.moveToNext()) {
            exerciceList.add(cursorToExercice(exercicec));
        }
        exercicec.close();
        database.close();
        return exerciceList;
    }

    private static Exercice cursorToExercice(Cursor exerciceCursor){
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
