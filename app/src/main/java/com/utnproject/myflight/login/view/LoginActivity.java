package com.utnproject.myflight.login.view;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.utnproject.myflight.R;
import com.utnproject.myflight.view.CreateAccountActivity;
import com.utnproject.myflight.view.MapsActivity;


public class LoginActivity extends AppCompatActivity implements LoginView{


    private TextInputEditText username, password;
    private Button login;
    private ProgressBar progressBarLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (TextInputEditText) findViewById(R.id.email);
        password = (TextInputEditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        progressBarLogin =(ProgressBar) findViewById(R.id.progressBarLogin);

    }


    @Override
    public void enableInputs() {
        username.setEnabled(true);
        password.setEnabled(true);
        login.setEnabled(true);

    }

    @Override
    public void disableInputs() {
        username.setEnabled(false);
        password.setEnabled(false);
        login.setEnabled(false);
    }

    @Override
    public void showProgressBar() {
        progressBarLogin.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBarLogin.setVisibility(View.GONE);
    }

    @Override
    public void loginError(String error) {

    }

    @Override
    public void goCreateAccount(View view) {
        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
    }

    @Override
    public void goHome(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
}


