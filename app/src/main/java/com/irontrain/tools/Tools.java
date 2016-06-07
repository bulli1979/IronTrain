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
    private int[] colorList;
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

    public int getColor(int nr,Context context){
        if(colorList==null){
            initColorList(context);
        }

        if(nr>colorList.length){
            nr=0;
        }
        return colorList[nr];
    }

    private void initColorList(Context context){
        colorList = new int[15];
        colorList[0]=  context.getResources().getColor(R.color.statsColor1);
        colorList[1]=  context.getResources().getColor(R.color.statsColor2);
        colorList[2]=  context.getResources().getColor(R.color.statsColor3);
        colorList[3]=  context.getResources().getColor(R.color.statsColor4);
        colorList[4]=  context.getResources().getColor(R.color.statsColor5);
        colorList[5]=  context.getResources().getColor(R.color.statsColor6);
        colorList[6]=  context.getResources().getColor(R.color.statsColor7);
        colorList[7]=  context.getResources().getColor(R.color.statsColor8);
        colorList[8]=  context.getResources().getColor(R.color.statsColor9);
        colorList[9]=  context.getResources().getColor(R.color.statsColor10);
        colorList[10]=  context.getResources().getColor(R.color.statsColor11);
        colorList[11]=  context.getResources().getColor(R.color.statsColor12);
        colorList[12]=  context.getResources().getColor(R.color.statsColor13);
        colorList[13]=  context.getResources().getColor(R.color.statsColor14);
        colorList[14]=  context.getResources().getColor(R.color.statsColor15);
    }

    public int booleanToInt(boolean value){
         return value ? 1 : 0;
    }

    public boolean intToBoolean(int value){
        return value==1 ? true : false;
    }

}
