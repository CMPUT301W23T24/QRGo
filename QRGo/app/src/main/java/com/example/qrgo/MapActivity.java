package com.example.qrgo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.List;


public class MapActivity extends AppCompatActivity {
    double latitude;
    double longitude;
    MapView map;
    FirebaseFirestore db;
    Double range = 0.0;
    String rangeStr;

    GeoPoint startPoint;
    EditText rangeET;
    Button rangeB;
    private final static int LOCATION_PERMISSION_CODE = 100;
    private FusedLocationProviderClient fusedLocationClient;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = FirebaseFirestore.getInstance();


        //handle permissions first, before map is created. not depicted here
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLocation();

        Context context = getApplicationContext();
        Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context));Configuration.getInstance().setUserAgentValue(getPackageName());

        setContentView(R.layout.map_activity);
        map = (MapView) findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE);

        rangeET = findViewById(R.id.rangeET);
        rangeB = findViewById(R.id.rangeB);


        IMapController mapController = map.getController();
        mapController.setZoom(9.5);
        rangeB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rangeStr = rangeET.getText().toString();
                if (rangeStr.isEmpty()){
                    Toast.makeText(context, "Please entire a valid range", Toast.LENGTH_SHORT).show();
                    range = 0.0;
                } else {
                    range = Double.parseDouble(rangeStr);
                }
                map.getOverlays().clear();
                placeMarker(context, startPoint);
                placeQR(range);
            }
        });



        
    }

    public void onResume(){
        super.onResume();
           map.onResume(); //needed for compass, my location overlays, v6.0.0 and up
    }

    public void onPause(){
        super.onPause();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().save(this, prefs);
        map.onPause();  //needed for compass, my location overlays, v6.0.0 and up
    }

    private void askPermission() {
        ActivityCompat.requestPermissions(MapActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_CODE);
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Handle permission not granted
            askPermission();
        } else {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            IMapController mapController = map.getController();
                            mapController.setZoom(9.5);

                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            startPoint = new GeoPoint(latitude, longitude);
                            mapController.setCenter(startPoint);
                            placeMarker(getApplicationContext(), startPoint);
                        }

                    });
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantRequest) {
        if (requestCode == LOCATION_PERMISSION_CODE) {
            if (grantRequest.length > 0 && grantRequest[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation();
            } else {
                Toast.makeText(MapActivity.this, "Location permission required", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantRequest);
    }

    public void placeMarker(Context context, GeoPoint point){

        Marker marker = new Marker(map);
        marker.setPosition(point);
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        marker.setIcon(context.getResources().getDrawable(org.osmdroid.library.R.drawable.ic_menu_mylocation));
        marker.setInfoWindow(null);
        map.getOverlays().add(marker);
        map.invalidate();
    }

    public void placeQR(double range){
        CollectionReference qrs = db.collection("qr");

        qrs.orderBy("locations").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot doc: task.getResult()){

                        List<Object> geoPoints = (List<Object>) doc.get("locations");

                        for (int i = 0; i < geoPoints.size(); i++){
                            com.google.firebase.firestore.GeoPoint geoPointDB = (com.google.firebase.firestore.GeoPoint) geoPoints.get(i);

                            double lati = geoPointDB.getLatitude();
                            double longi = geoPointDB.getLongitude();
                            GeoPoint geoPoint = new GeoPoint(lati, longi);
                            double distance = startPoint.distanceToAsDouble(geoPoint)/1000;
                            if (distance < range) {
                                placeMarker(getApplicationContext(), geoPoint);
                            }
                        }


                    }
                }
            }
        });
    }


}

