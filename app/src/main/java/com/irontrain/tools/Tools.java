package com.irontrain.tools;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.irontrain.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Ebi on 06.03.2016.
 * Tools class to define functions which are often in use
 */
public class Tools {
    private static final String LOG_TAG = Tools.class.getSimpleName();
    private static final SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    private static final Tools INSTANCE = new Tools();

    private Tools(){}

    public static synchronized Tools getInstance(){
        return INSTANCE;
    }

    public Date stringToDate(String dateString){
        try{
            return formater.parse(dateString);
        }catch (Exception e){
            Log.d(LOG_TAG,"Error in stringToDate " + e);
            return null;
        }
    }

    public String dateToString(Date d){
        return formater.format(d);
    }

    public void showToast(Context context, String message){
        Toast toast = Toast.makeText(context,message,Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM, 0, 10);
        toast.show();
    }

    /**
     * Have to use depreacted function because supply android version 15 new functions are vor 21+
     * */
    public void setErrorColor(View view, boolean hasError){
        if(hasError){
            view.setBackgroundColor(view.getResources().getColor(R.color.colorErrorBackground));
        }else{
            view.setBackgroundColor(view.getResources().getColor(R.color.colorSoftGrey));
        }
    }

    public int booleanToInt(boolean value){
         return value ? 1 : 0;
    }

    public boolean intToBoolean(int value){
        return value==1 ? true : false;
    }

}
