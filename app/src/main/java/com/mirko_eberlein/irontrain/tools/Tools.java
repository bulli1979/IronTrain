package com.mirko_eberlein.irontrain.tools;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ebi on 06.03.2016.
 */
public class Tools {
    private static final String LOG_TAG = Tools.class.getSimpleName();
    private static final SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
    public static Date stringToDate(String dateString){
        try{
            return formater.parse(dateString);
        }catch (Exception e){
            Log.d(LOG_TAG,"Error in stringToDate " + e);
            return null;
        }
    }

    public static String dateToString(Date d){
        return formater.format(d);
    }
}
