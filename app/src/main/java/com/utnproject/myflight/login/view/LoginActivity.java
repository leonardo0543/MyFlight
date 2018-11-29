package com.utnproject.myflight.login.view;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.utnproject.myflight.R;
import com.utnproject.myflight.login.presenter.LoginPresenter;
import com.utnproject.myflight.login.presenter.LoginPresenterImpl;
import com.utnproject.myflight.view.MapsActivity;


public class LoginActivity extends AppCompatActivity implements LoginView{


    private TextInputEditText username, password;
    private Button btn_login;
    private ProgressBar progressBarLogin;

    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (TextInputEditText) findViewById(R.id.email);
        password = (TextInputEditText) findViewById(R.id.password);
        btn_login = (Button) findViewById(R.id.login);
        progressBarLogin = findViewById(R.id.progressBarLogin);
        hideProgressBar();
        presenter = new LoginPresenterImpl(this);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //validaciones
                presenter.singIn(username.getText().toString(), password.getText().toString());
            }
        });



    }


    @Override
    public void enableInputs() {
        username.setEnabled(true);
        password.setEnabled(true);
        btn_login.setEnabled(true);

    }

    @Override
    public void disableInputs() {
        username.setEnabled(false);
        password.setEnabled(false);
       btn_login.setEnabled(false);
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
        Toast.makeText(this,getString(R.string.login_error),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void goCreateAccount(View view) {
        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
    }

    @Override
    public void goHome() {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
}


