package com.example.wmma;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;

import androidx.appcompat.app.AlertDialog;

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

public class BackgroundWorker extends AsyncTask<String, Void, String> {
    Context context;
    AlertDialog alertDialog;

    BackgroundWorker(Context ctx){
        context = ctx;
    }

    @Override
    protected String doInBackground(String... strings) {
        String type = strings[0];
        String login_url = "http://192.168.0.13/WMMA/login.php/";
        String manager_registration_url = "http://192.168.0.13/WMMA/managerRegister.php/";
        String employee_registration_url = "http://192.168.0.13/WMMA/employeeRegister.php/";

        if(type.equals("Login")){

            alertDialog.setTitle("Login Status");
            String business_name = strings[1];
            String employee_id = strings[2];
            String password = strings[3];
            String result = "";

            if(business_name.equals("")||employee_id.equals("")||password.equals(""))
            {
                result = "Please fill all fields.";
                return result;
            }

            try {
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data = URLEncoder.encode("business_name", "UTF-8")+"="+URLEncoder.encode(business_name,"UTF-8")+"&"+URLEncoder.encode("employee_id", "UTF-8")+"="+URLEncoder.encode(employee_id,"UTF-8")+"&"+URLEncoder.encode("password", "UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                result = "";
                String line = "";
                while ((line = bufferedReader.readLine())!=null){
                    result+=line;
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return result;
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

        if(type.equals("ManagerRegistration")){
            alertDialog.setTitle("Registration Status");
            String business_name = strings[1].trim();
            String manager_first_name = strings[2].trim();
            String manager_last_name = strings[3].trim();
            String manager_id = strings[4].trim();
            String password = strings[5].trim();
            String re_password = strings[6].trim();
            String result = "";

            if(business_name.equals("")||manager_first_name.equals("")||manager_last_name.equals("")||manager_id.equals("")||password.equals("")||re_password.equals("")) {
                result = "Please fill in all fields before submitting.";
                return result;
            }

            if(!password.equals(re_password)) {
                result = "Passwords do not match.";
                return result;
            }

            try {
                URL url = new URL(manager_registration_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data = URLEncoder.encode("business_name", "UTF-8")+"="+URLEncoder.encode(business_name,"UTF-8")+"&"+URLEncoder.encode("manager_first_name", "UTF-8")+"="+URLEncoder.encode(manager_first_name,"UTF-8")+"&"+URLEncoder.encode("manager_last_name", "UTF-8")+"="+URLEncoder.encode(manager_last_name,"UTF-8")+"&"+URLEncoder.encode("manager_id", "UTF-8")+"="+URLEncoder.encode(manager_id,"UTF-8")+"&"+URLEncoder.encode("password", "UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                result = "";
                String line = "";
                while ((line = bufferedReader.readLine())!=null){
                    result+=line;
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                if(result.contains("REGISTRATION COMPLETE")){
                    alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            context.startActivity(new Intent(context, LoginActivity.class));
                        }
                    });
                }

                return result;
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

        if(type.equals("EmployeeRegistration")){
            alertDialog.setTitle("Registration Status");
            String business_name = strings[1].trim();
            String first_name = strings[2].trim();
            String last_name = strings[3].trim();
            String employee_id = strings[4].trim();
            String password = strings[5].trim();
            String re_password = strings[6].trim();
            String result = "";

            if(business_name.equals("")||first_name.equals("")||last_name.equals("")||employee_id.equals("")||password.equals("")||re_password.equals("")) {
                result = "Please fill in all fields before submitting.";
                return result;
            }

            if(!password.equals(re_password)) {
                result = "Passwords do not match.";
                return result;
            }

            try {
                URL url = new URL(employee_registration_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data = URLEncoder.encode("business_name", "UTF-8")+"="+URLEncoder.encode(business_name,"UTF-8")+"&"+URLEncoder.encode("first_name", "UTF-8")+"="+URLEncoder.encode(first_name,"UTF-8")+"&"+URLEncoder.encode("last_name", "UTF-8")+"="+URLEncoder.encode(last_name,"UTF-8")+"&"+URLEncoder.encode("employee_id", "UTF-8")+"="+URLEncoder.encode(employee_id,"UTF-8")+"&"+URLEncoder.encode("password", "UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                result = "";
                String line = "";
                while ((line = bufferedReader.readLine())!=null){
                    result+=line;
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                if(result.contains("REGISTRATION COMPLETE")){
                    alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            context.startActivity(new Intent(context, LoginActivity.class));
                        }
                    });
                }

                return result;
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

        return null;
    }

    @Override
    protected void onPreExecute(){
        alertDialog = new AlertDialog.Builder(context).create();
    }

    @Override
    protected void onPostExecute(String result){

        if(result.contains("LOGIN SUCCESS")) {
            Intent intent = new Intent(context, MainPageActivity.class);
            String user_id = result.substring(result.indexOf(":")+1);
            intent.putExtra("user_id", user_id);
            context.startActivity(intent);
            return;
        }
        alertDialog.setMessage(result);
        alertDialog.show();


    }

    protected Void onProgressUpdate(Void values){
        super.onProgressUpdate(values);
        return null;
    }
}
