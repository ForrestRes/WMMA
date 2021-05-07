package com.example.wmma;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class EmployeeRegistrationActivity extends AppCompatActivity {

    private EditText BusinessNameET, FirstNameET, LastNameET, EmployeeIDET, PasswordET, RePasswordET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_registration);

        BusinessNameET = (EditText)findViewById(R.id.etEmployeeRegistrationBusinessName);
        EmployeeIDET = (EditText)findViewById(R.id.etEmployeeRegistrationEmployeeID);
        FirstNameET = (EditText)findViewById(R.id.etEmployeeRegistrationFirstName);
        LastNameET = (EditText)findViewById(R.id.etEmployeeRegistrationLastName);
        PasswordET = (EditText)findViewById(R.id.etEmployeeRegistrationPassword);
        RePasswordET = (EditText)findViewById(R.id.etEmployeeRegistrationRePassword);
    }

    public void OnBackToLogin(View view){
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void OnRegisterEmployee(View view){
        String BusinessName = BusinessNameET.getText().toString();
        String FirstName = FirstNameET.getText().toString();
        String LastName = LastNameET.getText().toString();
        String EmployeeID = EmployeeIDET.getText().toString();
        String Password = PasswordET.getText().toString();
        String RePassword = RePasswordET.getText().toString();
        String Type = "EmployeeRegistration";

        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(Type, BusinessName, FirstName, LastName, EmployeeID, Password, RePassword);
    }
}