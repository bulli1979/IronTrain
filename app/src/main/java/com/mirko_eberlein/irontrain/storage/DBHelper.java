package com.mirko_eberlein.irontrain.storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.AutoText;
import android.util.Log;

/**
 * Created by Ebi on 16.02.2016.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final String DATENBANK_NAME = "irontrain.db";
    private static final int DATENBANK_VERSION = 1;
    private static final String LOG_TAG = SQLiteOpenHelper.class.getSimpleName();
    private static DBHelper sINSTANCE;
    private static Object sLOCK = "";

    public DBHelper(Context context) {
        super(context, DATENBANK_NAME, null, DATENBANK_VERSION);
        Log.d(LOG_TAG, "DBHelper hat die Datenbank " + getDatabaseName() + "erstellt");
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(LOG_TAG, "Erstelle Datenbank");
        db.execSQL("CREATE TABLE if not exists `Exercice`(`id` TEXT,`name` TEXT,`description` TEXT,`importnumber` INTEGER ,PRIMARY KEY(id));");
        db.execSQL("CREATE TABLE if not exists `Plan` (`id` TEXT,`name` TEXT,`description` TEXT,`createdon` TEXT,PRIMARY KEY(id));");
        db.execSQL("CREATE TABLE if not exists `Train` (`id` INTEGER PRIMARY KEY AUTOINCREMENT,`date` TEXT,`plan` TEXT);");
        db.execSQL("CREATE TABLE if not exists `PlanDayExercice` (`id` INTEGER PRIMARY KEY AUTOINCREMENT,`planday` TEXT,`exercice` TEXT,`set` INTEGER);");
        db.execSQL("CREATE TABLE if not exists `PlanDay` (`id` TEXT,`name` TEXT,`description` TEXT,`plan` TEXT,PRIMARY KEY(id));");
        db.execSQL("CREATE TABLE if not exists `TrainExercice` (`id` INTEGER PRIMARY KEY AUTOINCREMENT,`planDayExercice` INTEGER,`train` TEXT);");
        db.execSQL("CREATE TABLE `TrainSet` (`id` INTEGER PRIMARY KEY AUTOINCREMENT,`trainexercice` INTEGER,`setnr` INTEGER,`weight` NUMERIC);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static DBHelper getInstance(Context context){
        if(sINSTANCE == null){
            synchronized (sLOCK){
                if(sINSTANCE == null && context != null){
                    sINSTANCE = new DBHelper(context.getApplicationContext());
                }
            }
        }
        return sINSTANCE;
    }

}
