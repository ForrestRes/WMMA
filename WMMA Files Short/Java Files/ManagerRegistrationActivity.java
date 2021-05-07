package com.example.wmma;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class ManagerRegistrationActivity extends AppCompatActivity {

    private EditText BusinessNameET, ManagerFirstNameET, ManagerLastNameET, ManagerIDET, PasswordET, RePasswordET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_registration);

        BusinessNameET = (EditText)findViewById(R.id.etManagerRegistrationBusinessName);
        ManagerFirstNameET = (EditText)findViewById(R.id.etManagerRegistrationManagerFirstName);
        ManagerLastNameET = (EditText)findViewById(R.id.etManagerRegistrationManagerLastName);
        ManagerIDET = (EditText)findViewById(R.id.etManagerRegistrationEmployeeID);
        PasswordET = (EditText)findViewById(R.id.etManagerRegistrationPassword);
        RePasswordET = (EditText)findViewById(R.id.etManagerRegistrationRePassword);
    }

    public void OnRegisterManager(View view){
        String BusinessName = BusinessNameET.getText().toString();
        String ManagerFirstName = ManagerFirstNameET.getText().toString();
        String ManagerLastName = ManagerLastNameET.getText().toString();
        String ManagerID = ManagerIDET.getText().toString();
        String Password = PasswordET.getText().toString();
        String RePassword = RePasswordET.getText().toString();
        String Type = "ManagerRegistration";

        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(Type, BusinessName, ManagerFirstName, ManagerLastName, ManagerID, Password, RePassword);
    }

    public void OnBackToLogin(View view){
        startActivity(new Intent(this, LoginActivity.class));
    }
}