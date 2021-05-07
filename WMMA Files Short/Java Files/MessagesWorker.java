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

public class MessagesWorker extends AsyncTask<String, Void, String> {

    Context context;
    MessagesActivity activity;
    String type;
    MessagesNewFragment fragment;
    MessagesViewFragment viewFragment;

    MessagesWorker(Context ctx, MessagesActivity a){
        context = ctx;
        activity = a;
    }

    MessagesWorker(Context ctx, MessagesActivity a, MessagesNewFragment f){
        context = ctx;
        activity = a;
        fragment = f;
    }

    MessagesWorker(Context ctx, MessagesActivity a, MessagesViewFragment f) {
        context = ctx;
        activity = a;
        viewFragment = f;
    }

    @Override
    protected String doInBackground(String... strings) {

        String messages_initialize_url = "http://192.168.0.13/WMMA/messagesInitialize.php/";
        String messages_get_recipients_url = "http://192.168.0.13/WMMA/messagesGetRecipients.php/";
        String messages_new_chat_url = "http://192.168.0.13/WMMA/messagesNewChat.php/";
        String messages_refresh_messages_url = "http://192.168.0.13/WMMA/messagesRefreshMessages.php/";
        String messages_send_message_url = "http://192.168.0.13/WMMA/messagesSendMessage.php/";
        String result = "";

        if(strings[0].equals("initialize")){
            type = "initialize";
            try {
                URL url = new URL(messages_initialize_url);
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

        if(strings[0].equals("getRecipients")){
            type = "getRecipients";
            try {
                URL url = new URL(messages_get_recipients_url);
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

        if(strings[0].equals("newChat")){
            type = "newChat";
            try {
                URL url = new URL(messages_new_chat_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data = URLEncoder.encode("user_id", "UTF-8")+"="+URLEncoder.encode(strings[1],"UTF-8") + "&"+URLEncoder.encode("recipient_ids", "UTF-8")+"="+URLEncoder.encode(strings[2],"UTF-8") + "&"+URLEncoder.encode("message", "UTF-8")+"="+URLEncoder.encode(strings[3],"UTF-8");
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

        if(strings[0].equals("refreshMessages")){
            type = "refreshMessages";
            try {
                URL url = new URL(messages_refresh_messages_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data = URLEncoder.encode("user_id", "UTF-8")+"="+URLEncoder.encode(strings[1],"UTF-8")+"&"+URLEncoder.encode("chat_id", "UTF-8")+"="+URLEncoder.encode(strings[2],"UTF-8");
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

        if(strings[0].equals("sendMessage")){
            type = "sendMessage";
            try {
                URL url = new URL(messages_send_message_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data = URLEncoder.encode("user_id", "UTF-8")+"="+URLEncoder.encode(strings[1],"UTF-8")+"&"+URLEncoder.encode("chat_id", "UTF-8")+"="+URLEncoder.encode(strings[2],"UTF-8")+"&"+URLEncoder.encode("message", "UTF-8")+"="+URLEncoder.encode(strings[3],"UTF-8");
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
        else if(type.equals("getRecipients"))
            fragment.finishInitialization(result);
        else if(type.equals("newChat")){
            activity.finishNewChat(result);
        }
        else if(type.equals("refreshMessages")){
            viewFragment.refreshMessages(result);
        }
        else if(type.equals("sendMessage")){
            viewFragment.messageSent(result);
        }

    }
}
