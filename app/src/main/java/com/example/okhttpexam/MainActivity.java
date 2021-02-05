package com.example.okhttpexam;


import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.OutputKeys;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new HttpAsyncTask().execute("https://goo.gl/eIXu9l");
    }

    private static class HttpAsyncTask extends AsyncTask<String, Void, String> {

        OkHttpClient client = new OkHttpClient();

        @Override
        protected String doInBackground(String... params) {
            List<Weather> weatherList = new ArrayList<>();
            String result = null;
            String strUrl = params[0];
            try {
            Request request = new Request.Builder()
                    .url(strUrl)
                    .build();
                Response response = client.newCall(request).execute();
                Gson gson = new Gson();

                Type listType = new TypeToken<ArrayList<Weather>>() {}.getType();
                weatherList = gson.fromJson(response.body().string(), listType);

                Log.d(TAG, "doInBackground: " + weatherList.toString());

            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s != null) {
                Log.d(TAG, s );
            }
        }
    }
 }