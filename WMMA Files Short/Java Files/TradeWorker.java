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

public class TradeWorker extends AsyncTask<String, Void, String> {

    Context context;
    TradeActivity activity;
    String type;

    TradeWorker(Context ctx, TradeActivity a){
        context = ctx;
        activity = a;
    }

    @Override
    protected String doInBackground(String... strings) {

        String trade_offered_shifts_url = "http://192.168.0.13/WMMA/tradeOfferedShifts.php/";
        String trade_take_shift_url = "http://192.168.0.13/WMMA/tradeTakeShift.php/";
        String result = "";

        if(strings[0].equals("offeredShifts")){
            type = "offeredShifts";
            try {
                URL url = new URL(trade_offered_shifts_url);
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

        if(strings[0].equals("takeShift")){
            type = "takeShift";
            try {
                URL url = new URL(trade_take_shift_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data = URLEncoder.encode("user_id", "UTF-8")+"="+URLEncoder.encode(strings[1],"UTF-8")+"&"+URLEncoder.encode("shift_id", "UTF-8")+"="+URLEncoder.encode(strings[2],"UTF-8");
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
        if(type.equals("offeredShifts")){
            activity.finishInitialization(result);
        }
        if(type.equals("takeShift")){
        }

    }
}

