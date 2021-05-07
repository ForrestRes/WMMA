package com.example.wmma;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

public class TimeAwayWorker extends AsyncTask<String, Void, String> {

    Context context;
    TimeAwayActivity activity;
    TimeAwayNewFragment newFragment;
    TimeAwayEditFragment editFragment;
    String type;

    TimeAwayWorker(Context ctx, TimeAwayActivity a){
        context = ctx;
        activity = a;
    }

    TimeAwayWorker(Context ctx, TimeAwayActivity a, TimeAwayNewFragment f){
        context = ctx;
        activity = a;
        newFragment = f;
    }

    TimeAwayWorker(Context ctx, TimeAwayActivity a, TimeAwayEditFragment f){
        context = ctx;
        activity = a;
        editFragment = f;
    }

    @Override
    protected String doInBackground(String... strings) {

        String time_away_initialize_url = "http://192.168.0.13/WMMA/timeAwayInitialize.php/";
        String time_away_delete_url = "http://192.168.0.13/WMMA/timeAwayDelete.php/";
        String time_away_insert_url = "http://192.168.0.13/WMMA/timeAwayInsert.php/";
        String time_away_edit_url = "http://192.168.0.13/WMMA/timeAwayEdit.php/";
        String result = "";

        if(strings[0].equals("initialize")){
            type = "initialize";
            try {
                URL url = new URL(time_away_initialize_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data = URLEncoder.encode("user_id", "UTF-8")+"="+URLEncoder.encode(strings[1],"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String line = "";
                while ((line = bufferedReader.readLine())!=null){
                    result+=line;
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(strings[0].equals("delete")){
            type = "delete";
            try {
                URL url = new URL(time_away_delete_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data = URLEncoder.encode("request_id", "UTF-8")+"="+URLEncoder.encode(strings[1],"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String line = "";
                while ((line = bufferedReader.readLine())!=null){
                    result+=line;
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(strings[0].equals("insert")){
            type = "insert";
            try {
                URL url = new URL(time_away_insert_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data = URLEncoder.encode("user_id", "UTF-8")+"="+URLEncoder.encode(strings[1],"UTF-8")+"&"+URLEncoder.encode("start_date", "UTF-8")+"="+URLEncoder.encode(strings[2],"UTF-8")+"&"+URLEncoder.encode("end_date", "UTF-8")+"="+URLEncoder.encode(strings[3],"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String line = "";
                while ((line = bufferedReader.readLine())!=null){
                    result+=line;
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(strings[0].equals("edit")){
            type = "edit";
            try {
                URL url = new URL(time_away_edit_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data = URLEncoder.encode("request_id", "UTF-8")+"="+URLEncoder.encode(strings[1],"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String line = "";
                while ((line = bufferedReader.readLine())!=null){
                    result+=line;
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        if(type.equals("initialize"))
           activity.finishInitialization(result);
        if(type.equals("insert")){
            newFragment.finishInsert(result);
        }
        if(type.equals("edit")){
            editFragment.finishSetup(result);
        }
    }
}
