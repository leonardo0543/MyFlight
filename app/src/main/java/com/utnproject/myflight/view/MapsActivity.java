package com.utnproject.myflight.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.utnproject.myflight.R;
import com.utnproject.myflight.login.view.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG ="MapsActivity" ;

    private GoogleMap mMap;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    private String AplicationID = "4e2ca9a8";
    private String AplicationKey= "4693b521dd220c969d65a913481052e9";

    public static ArrayList<String> flightList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        firebaseInitialize();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        showToolbar(getResources().getString(R.string.toolbar_tittle_home),false);

        this.flightList = new ArrayList<>();

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    //resize the map icon
    public Bitmap iconMap(int height, int width){
        BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.mipmap.ic_plane);
        Bitmap b = bitmapdraw.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
        return smallMarker;
    }

    public MarkerOptions planeMarker(double lat, double lon, String flightNumber){

        LatLng position = new LatLng(lat, lon);
        MarkerOptions markerOption = new MarkerOptions().position(position).title(flightNumber).icon(BitmapDescriptorFactory.fromBitmap(iconMap(190,190)));
        return markerOption;
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(19.53, -99.72);
//        MarkerOptions marker = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_plane));
////        mMap.addMarker(marker.position(sydney).title("Marker in Sydney"));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        getFlightsNear(10);


        //todo pintar aviones
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                return false;
            }
        });

        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                getFlightsNear(10);
            }
        });


    }



    public LatLng mapCenter(){
        return mMap.getCameraPosition().target;
    }

    public void getFlightsNear(int maxFlights ){
        float topLat = (float) (mapCenter().latitude+2.5);
        float bottomLat = (float) (mapCenter().latitude-2.5);
        float leftLon = (float) (mapCenter().longitude-2.5);
        float rightLon = (float) (mapCenter().longitude+2.5);

        //just an alert
        AlertDialog alertDialog = new AlertDialog.Builder(MapsActivity.this).create();
        alertDialog.setTitle("Bounds");
        alertDialog.setMessage("TopLat: "+topLat+ "\n"+"BottomLat: "+bottomLat+ "\n"+"LefLon: "+leftLon+ "\n"+"RightLon: "+rightLon+ "\n");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        //alertDialog.show();


        String url = "https://api.flightstats.com/flex/flightstatus/rest/v2/json/flightsNear/"+topLat+"/"+leftLon+"/"+bottomLat+"/"+rightLon+"?appId="+this.AplicationID+"&appKey="+this.AplicationKey+"&maxFlights="+maxFlights;

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String lista = "";

                        try {
                            JSONObject jsonjObject = new JSONObject(response);

                            JSONArray jsonArray = jsonjObject.getJSONArray("flightPositions");
                            for (int i = 0; i < jsonArray.length(); i++)
                            {
                                try {
                                    JSONObject jsonObjectFlight = jsonArray.getJSONObject(i);

                                    String idFlight=jsonObjectFlight.get("flightId").toString();

                                    JSONArray jsonArrayPosition = jsonObjectFlight.getJSONArray("positions");
                                    JSONObject location = jsonArrayPosition.getJSONObject(0);

                                    double lat = (double)location.get("lat");
                                    double lon = (double)location.get("lon");


                                    MapsActivity.flightList.add(idFlight);
                                    mMap.addMarker(planeMarker(lat,lon,idFlight));


                                    lista+=jsonObjectFlight.get("flightId").toString()+"\n";

                                } catch (JSONException e) {
                                    Log.e("Parser JSON", e.toString());
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //just an alert
                        AlertDialog alertDialog = new AlertDialog.Builder(MapsActivity.this).create();
                        alertDialog.setTitle("Bounds");
                        alertDialog.setMessage(lista);
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        //alertDialog.show();
                        // Display the first 500 characters of the response string.


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //just an alert
                AlertDialog alertDialog = new AlertDialog.Builder(MapsActivity.this).create();
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



        //todo API request HERE




        //todo mMap.addMarker(planeMarker());


    }

    public void showToolbar(String tittle, boolean upButton){

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);

//        toolbar = findViewById(R.id.toolbar);
//        toolbar.setTitle(tittle);





    }



    @Override
    public void onBackPressed(){
        moveTaskToBack(true);
        super.onBackPressed();
    }

    private void firebaseInitialize(){
        firebaseAuth =FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) {
                    Log.w(TAG,"User logged"+firebaseUser.getEmail());
                }
                else{
                    Log.w(TAG,"User not logged");

                }
            }
        };
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.option_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.mSingOut:
                firebaseAuth.signOut();
                Intent i = new Intent(MapsActivity.this, LoginActivity.class);
                startActivity(i);
                break;
        }

        return super.onOptionsItemSelected(item);
    }



}
