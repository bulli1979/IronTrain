package com.irontrain.tools;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.irontrain.R;

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

    public static void showToast(Context context, String message){
        Toast toast = Toast.makeText(context,message,Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM, 0, 10);
        toast.show();
    }

}
