package com.utnproject.myflight;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.utnproject.myflight.view.CreateAccountActivity;
import com.utnproject.myflight.view.MapsActivity;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void goCreateAccount(View view){
       Intent intent = new Intent(this, CreateAccountActivity.class);
       startActivity(intent);
    }

    public void goMap(View view){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
}
