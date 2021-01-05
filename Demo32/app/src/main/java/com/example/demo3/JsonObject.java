package com.example.demo3;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Key;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JsonObject extends AsyncTask<String, String, String> {
    @Override
    protected String doInBackground(String... strings) {

        try {
            URL url = new URL("http://staging.bytehack.io/zodiac");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream stream = httpURLConnection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuffer buffer = new StringBuffer();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String finalContent = buffer.toString();
            JSONObject obj = new JSONObject(finalContent);
            Iterator<String> keys = obj.keys();
            String data = "";
            while (keys.hasNext()) {
                String key = keys.next();
                JSONArray JA = obj.getJSONArray(key);
                data += key + "\n" + JA + '\n';
            }
            return data;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void onPostExecute(String aString) {
        MainActivity.content.setText(aString);
    }
}
