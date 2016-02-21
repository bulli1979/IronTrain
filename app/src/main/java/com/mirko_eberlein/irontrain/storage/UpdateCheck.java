package com.mirko_eberlein.irontrain.storage;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Ebi on 16.02.2016.
 */
public class UpdateCheck extends AsyncTask<String, Void, JSONArray> {

    private static final String LOG_TAG = UpdateCheck.class.getSimpleName();
    private Exception exception;
    public JSONArray doInBackground(String[] params){
        HttpURLConnection urlConnection = null;
        JSONArray json = null;
        try {
            String path = "http://mirko-eberlein.de/fitnessplan.json";
            URL url = new URL(path);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            json = readStream(in);
        }catch(Exception e){
            Log.d(LOG_TAG, "Error in Update Prozess" + e);
            exception = e;
        }
        finally {
            urlConnection.disconnect();
        }
        return json;
    }

    private JSONArray readStream(InputStream in) throws IOException,JSONException{
        BufferedReader streamReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        StringBuilder responseStrBuilder = new StringBuilder();

        String inputStr;
        while ((inputStr = streamReader.readLine()) != null) {
            responseStrBuilder.append(inputStr);
        }
        JSONArray json = new JSONArray(responseStrBuilder.toString());
        Log.d(LOG_TAG,json.toString());
        return json;
    }


    protected void onPostExecute(JSONObject json) {
        // TODO: check this.exception
        // TODO: do something with the feed
    }

}
