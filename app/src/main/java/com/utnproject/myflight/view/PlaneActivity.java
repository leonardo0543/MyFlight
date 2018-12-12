package com.utnproject.myflight.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.utnproject.myflight.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PlaneActivity extends AppCompatActivity {

    private String AplicationID = "4e2ca9a8";
    private String AplicationKey = "4693b521dd220c969d65a913481052e9";


    private TextView flightID, typeOfplane, airLine, cityNameOrigin, airportNameOrigin, departureDate;
    private TextView cityNameArrival, airportNameArrival, arrivalDate;
    private TextView speedValue, altitudeValue, latitudeValue, longitudeValue;

    public void setAplicationID(String aplicationID) {
        AplicationID = aplicationID;
    }

    public void setAplicationKey(String aplicationKey) {
        AplicationKey = aplicationKey;
    }

    public void setFlightID(String flightID) {
        this.flightID.setText(flightID);
    }

    public void setTypeOfplane(String typeOfplane) {
        this.typeOfplane.setText(typeOfplane);
    }

    public void setAirLine(String airLine) {
        this.airLine.setText(airLine);
    }

    public void setCityNameOrigin(String cityNameOrigin) {
        this.cityNameOrigin.setText(cityNameOrigin);
    }

    public void setAirportNameOrigin(String airportNameOrigin) {
        this.airportNameOrigin.setText(airportNameOrigin);
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate.setText(departureDate);
    }

    public void setCityNameArrival(String cityNameArrival) {
        this.cityNameArrival.setText(cityNameArrival);
    }

    public void setAirportNameArrival(String airportNameArrival) {
        this.airportNameArrival.setText(airportNameArrival);
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate.setText(arrivalDate);
    }

    public void setSpeedValue(String speedValue) {
        this.speedValue.setText(speedValue);
    }

    public void setAltitudeValue(String altitudeValue) {
        this.altitudeValue.setText(altitudeValue);
    }

    public void setLatitudeValue(String latitudeValue) {
        this.latitudeValue.setText(latitudeValue);
    }

    public void setLongitudeValue(String longitudeValue) {
        this.longitudeValue.setText(longitudeValue);
    }
//---------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plane);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        showToolbar(getResources().getString(R.string.toolbar_tittle_flight_information), true);
        flightID = findViewById(R.id.flightNumber); //número de vuelo
        typeOfplane = findViewById(R.id.type_of_plane);
        airLine = findViewById(R.id.airline_name);
        cityNameOrigin = findViewById(R.id.CityNameOrigin);
        airportNameOrigin = findViewById(R.id.airportNameOrigin);
        departureDate = findViewById(R.id.departureDate);
        cityNameArrival = findViewById(R.id.CityNameArrival);
        airportNameArrival = findViewById(R.id.airportNameArrival);
        arrivalDate = findViewById(R.id.arrivalDate);
        speedValue = findViewById(R.id.speedValue);
        altitudeValue = findViewById(R.id.altitudValue);
        latitudeValue = findViewById(R.id.latitudeValue);
        longitudeValue = findViewById(R.id.longitudeValue);


        getFlighDataTrack(getFlightID());

    }

    public String getFlightID() {
        return getIntent().getExtras().getString("flightID");
    }

    public void showToolbar(String tittle, boolean upButton) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }


    public void getFlighDataTrack(String flightID) {


        String url = "https://api.flightstats.com/flex/flightstatus/rest/v2/json/flight/track/" + flightID + "?appId=" + AplicationID + "&appKey=" + AplicationKey + "&includeFlightPlan=false&maxPositions=1";

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject jsonjObject = new JSONObject(response);

                            //JSONArray jsonArray = jsonjObject.getJSONArray("flightTrack");
                            JSONObject flightTrackObj = jsonjObject.getJSONObject("flightTrack");
                            JSONObject appendixObj = jsonjObject.getJSONObject("appendix");
                            JSONArray jsonArrayAirports = appendixObj.getJSONArray("airports");
                            JSONArray jsonArrayAirlines = appendixObj.getJSONArray("airlines");
                            JSONArray positionsArr = flightTrackObj.getJSONArray("positions");

                            //------------------Filling Data--------------------------------------------
                            setFlightID(flightTrackObj.get("flightNumber").toString());
                            setTypeOfplane(flightTrackObj.get("equipment").toString());
                            String departureAirportCode = flightTrackObj.get("departureAirportFsCode").toString();
                            JSONObject airport1 = jsonArrayAirports.getJSONObject(0);
                            JSONObject airport2 = jsonArrayAirports.getJSONObject(1);
                            JSONObject airline = jsonArrayAirlines.getJSONObject(0);
                            JSONObject navigationData = positionsArr.getJSONObject(0);


                            if (airport1.get("fs").toString().equals(departureAirportCode)) {
                                setAirportNameOrigin(airport1.get("name").toString());
                                setAirportNameArrival(airport2.get("name").toString());
                                setCityNameOrigin(airport1.get("city").toString());
                                setCityNameArrival(airport2.get("city").toString());
                            } else {
                                setAirportNameOrigin(airport2.get("name").toString());
                                setAirportNameArrival(airport1.get("name").toString());
                                setCityNameOrigin(airport2.get("city").toString());
                                setCityNameArrival(airport1.get("city").toString());
                            }
                            setAirLine(airline.get("name").toString());

                            setSpeedValue(navigationData.get("speedMph").toString());
                            setAltitudeValue(navigationData.get("altitudeFt").toString());
                            setLatitudeValue(navigationData.get("lat").toString());
                            setLongitudeValue(navigationData.get("lon").toString());


                            //just an alert
                            AlertDialog alertDialog = new AlertDialog.Builder(PlaneActivity.this).create();
                            alertDialog.setTitle("Bounds");
                            alertDialog.setMessage(flightTrackObj.toString());
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();

//                            for (int i = 0; i < jsonArray.length(); i++)
//                            {
//                                try {
//                                    JSONObject jsonObjectAirport = jsonArray.getJSONObject(i);
//
//
//
//
//
//                                } catch (JSONException e) {
//                                    Log.e("Parser JSON", e.toString());
//                                }
//                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //just an alert
                AlertDialog alertDialog = new AlertDialog.Builder(PlaneActivity.this).create();
                alertDialog.setTitle("Bounds");
                alertDialog.setMessage(error.toString());
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();

            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);


    }


}
