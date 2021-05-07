package com.example.wmma;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText BusinessNameET, EmployeeIDET, PasswordET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        BusinessNameET = (EditText)findViewById(R.id.etBusinessName);
        EmployeeIDET = (EditText)findViewById(R.id.etLoginEmployeeID);
        PasswordET = (EditText)findViewById(R.id.etLoginPassword);
    }

    public void onLogin(View view){
        String BusinessName = BusinessNameET.getText().toString();
        String EmployeeID = EmployeeIDET.getText().toString();
        String Password = PasswordET.getText().toString();
        String Type = "Login";


        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(Type, BusinessName, EmployeeID, Password);

    }

    public void onLoginRegistration(View view){
        startActivity(new Intent(this, PreRegistrationActivity.class));
    }

}