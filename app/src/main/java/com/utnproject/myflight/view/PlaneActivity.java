package com.utnproject.myflight.view;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.utnproject.myflight.R;

public class PlaneActivity extends AppCompatActivity {

    private TextView flightID;

    //---------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plane);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        showToolbar(getResources().getString(R.string.toolbar_tittle_flight_information),true);
        flightID= findViewById(R.id.flightNumber); //número de vuelo
        flightID.setText(getFlightID() );

    }

    public String getFlightID(){
        return getIntent().getExtras().getString("flightID");
    }

    public void showToolbar(String tittle, boolean upButton){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }



}
