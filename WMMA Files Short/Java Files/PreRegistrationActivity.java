package com.example.wmma;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PreRegistrationActivity extends AppCompatActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_registration);
    }

    public void OnEmployeeRegistration(View view){
        startActivity(new Intent(this, EmployeeRegistrationActivity.class));
    }

    public void OnManagerRegistration(View view){
        startActivity(new Intent(this, ManagerRegistrationActivity.class));
    }

    public void OnBackToLogin(View view){
        startActivity(new Intent(this, LoginActivity.class));
    }
}