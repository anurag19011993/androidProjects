package com.example.demo3;

import android.os.AsyncTask;
import android.util.Log;

import androidx.loader.content.AsyncTaskLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class FetchData extends AsyncTask<Void, Void, Void> {
    String data;

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL("http://staging.bytehack.io");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStreamReader inputStream = new InputStreamReader(httpURLConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new BufferedReader(inputStream));
            String line = bufferedReader.readLine();
            data += line;
            Log.d("Resposne", data);


            JSONObject JO=new JSONObject();
//            JO.getString()
//            for(int i=0 ; i<JO.length();i++){
//                JSONArray JA= (JSONArray) JO.get(i);
//                dataParse=JA.get(i);
//
//            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
//        catch (JSONException e) {
//            e.printStackTrace();
//        }
        return null;
    }

    @Override
    public void onPostExecute(Void aVoid) {
        MainActivity.content.setText(this.data);
    }
}
