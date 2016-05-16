package com.irontrain.storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_CREATEDON = "createdon";
    public static final String COLUMN_IMPORTNUMBER = "importnumber";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_PLAN = "plan";
    public static final String COLUMN_PLANDAY = "planday";
    public static final String COLUMN_EXERCICE = "exercice";
    public static final String COLUMN_PLANDAYEXERCICE = "plandayexercice";
    public static final String COLUMN_TRAIN = "train";
    public static final String COLUMN_TRAINEXERCICE = "trainexercice";
    public static final String COLUMN_SETNR = "setnr";
    public static final String COLUMN_WEIGHT = "weight";
    public static final String COLUMN_REPEATS = "repeats";
    public static final String COLUMN_SETCOUNT = "setCount";

    public static final String TABLE_PLAN = "Plan";
    public static final String TABLE_PLANDAY = "PlanDay";
    public static final String TABLE_EXERCICE = "Exercice";
    public static final String TABLE_PLANDAY_EXERCICE = "PlanDayExercice";
    public static final String TABLE_TRAIN = "Train";
    public static final String TABLE_TRAINEXERCICE = "TrainExercice";
    public static final String TABLE_TRAINSET = "TrainSet";
    public DBHelper(Context context) {
        super(context, DATENBANK_NAME, null, DATENBANK_VERSION);
        Log.d(LOG_TAG, "DBHelper hat die Datenbank " + getDatabaseName() + "erstellt");
    }
    /**
     * Method run only on the first call of the tb and initialize it. After this the db is all the time on the device and this method will dont call again
     * */
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(LOG_TAG, "Erstelle Datenbank");
        db.execSQL("CREATE TABLE if not exists `" + TABLE_EXERCICE + "`(`" + COLUMN_ID + "` TEXT,`" + COLUMN_NAME + "` TEXT,`" + COLUMN_DESCRIPTION + "` TEXT,`" + COLUMN_IMPORTNUMBER + "` INTEGER ,PRIMARY KEY("+COLUMN_ID+"));");
        db.execSQL("CREATE TABLE if not exists `" + TABLE_PLAN + "` (`" + COLUMN_ID + "` TEXT,`" + COLUMN_NAME + "` TEXT,`" + COLUMN_DESCRIPTION + "` TEXT,`" + COLUMN_CREATEDON + "` TEXT,PRIMARY KEY("+COLUMN_ID+"));");
        db.execSQL("CREATE TABLE if not exists `" + TABLE_PLANDAY + "` (`" + COLUMN_ID + "` TEXT,`" + COLUMN_NAME + "` TEXT,`" + COLUMN_DESCRIPTION + "` TEXT,`"+COLUMN_PLAN+"` TEXT,PRIMARY KEY("+COLUMN_ID+"));");
        db.execSQL("CREATE TABLE if not exists `" + TABLE_PLANDAY_EXERCICE + "` (`" + COLUMN_ID + "` TEXT PRIMARY KEY ,`" + COLUMN_PLANDAY + "` TEXT,`" + COLUMN_EXERCICE + "` TEXT,`" + COLUMN_SETCOUNT + "` INTEGER,`" + COLUMN_REPEATS + "` TEXT,`" + COLUMN_DESCRIPTION + "` TEXT);");
        db.execSQL("CREATE TABLE if not exists `" + TABLE_TRAIN + "` (`"+COLUMN_ID+"` TEXT PRIMARY KEY ,`"+COLUMN_DATE+"` TEXT,`"+COLUMN_PLANDAY+"` TEXT);");
        db.execSQL("CREATE TABLE if not exists `" + TABLE_TRAINEXERCICE + "` (`" + COLUMN_ID + "` TEXT PRIMARY KEY ,`" + COLUMN_PLANDAYEXERCICE + "` TEXT,`" + COLUMN_TRAIN + "` TEXT);");
        db.execSQL("CREATE TABLE if not exists `" + TABLE_TRAINSET + "` (`" + COLUMN_ID + "` TEXT PRIMARY KEY ,`" + COLUMN_TRAINEXERCICE + "` TEXT,`" + COLUMN_SETNR + "` INTEGER,`" + COLUMN_WEIGHT + "` NUMERIC,`" + COLUMN_REPEATS + "` INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    /**
     * Create an instance of the Helper MEB no way to get two time connections
     * */
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
