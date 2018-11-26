package com.utnproject.myflight.view;

import android.os.Bundle;
import com.utnproject.myflight.R;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class CreateAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        showToolbar(getResources().getString(R.string.toolbar_tittle_create_account),true);
    }

     public void showToolbar(String tittle, boolean upButton){
         Toolbar toolbar = findViewById(R.id.toolbar);
         setSupportActionBar(toolbar);
         getSupportActionBar().setTitle(tittle);
         getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }
}
