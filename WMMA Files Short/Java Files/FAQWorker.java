package com.example.wmma;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class FAQWorker extends AsyncTask<String, Void, String> {

    Context context;
    SettingsFAQFragment fragment;

    FAQWorker(Context ctx, SettingsFAQFragment f){
        context = ctx;
        fragment = f;
    }

    @Override
    protected String doInBackground(String... strings) {

        String FAQ_url = "http://192.168.0.13/WMMA/FAQ.txt";
        String result = "";

        try {
            URL url = new URL(FAQ_url);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line = "";
            while ((line = bufferedReader.readLine())!=null){
                result+=line;result+=";";
            }

            bufferedReader.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        fragment.setupFAQ(result);
    }
}
