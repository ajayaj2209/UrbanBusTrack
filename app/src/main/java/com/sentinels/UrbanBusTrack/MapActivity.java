package com.sentinels.UrbanBusTrack;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.Objects;

public class MapActivity extends AppCompatActivity implements
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback,
        GoogleMap.OnMapClickListener
    {
//    CONSTANTS
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    Integer passenger1, passenger2;
    String bno1,bno2;

//    GLOBAL OBJECTS
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    FirebaseDatabase firebaseDatabase;
    private boolean permissionDenied = false;
    private GoogleMap map;
    private FusedLocationProviderClient fusedLocationClient;
    public static double latii,longii;

    public static double someLati,someLongi;
    private ActionBarDrawerToggle drawerToggle;

    private double parsedLat;
    private double parseLong;

    private double parsedLat1;
    private double parseLong1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        Toolbar mtoolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mtoolbar);
        // This will display an Up icon (<-), we will replace it with hamburger later
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();
        // Setup toggle to display hamburger icon with nice animation
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();

        // Tie DrawerLayout events to the ActionBarToggle
        mDrawer.addDrawerListener(drawerToggle);
        NavigationView nvDrawer = (NavigationView) findViewById(R.id.nvView);
        // Setup drawer view
        setupDrawerContent(nvDrawer);

        firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get
        // reference for our database

        DatabaseReference  busInfo = firebaseDatabase.getInstance().getReference("BusInfo");
        DatabaseReference mad1 = busInfo.child("Madurai 1");
        DatabaseReference lat = mad1.child("BusLatitude");
        DatabaseReference busno1 = mad1.child("BusNumber");
        DatabaseReference lon = mad1.child("BusLongitude");
        DatabaseReference pas = mad1.child("BusPopulation");

        DatabaseReference mad2 = busInfo.child("Madurai 2");
        DatabaseReference lat1 = mad2.child("BusLatitude");
        DatabaseReference lon1 = mad2.child("BusLongitude");
        DatabaseReference busno2 = mad2.child("BusNumber");
        DatabaseReference pas1 = mad2.child("BusPopulation");

        lat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                latitude = getCoord((Map<String,String>)dataSnapshot.getValue());
//                Log.d("Latitude",latitude.toString());
//                  String lati =  String.valueOf(latitude.get(latitude.size()-1));
                String value = dataSnapshot.getValue(String.class);
                parsedLat= Double.parseDouble(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
                Toast.makeText(MapActivity.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });

        lon.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                longitude = getCoord((Map<String,String>)dataSnapshot.getValue());
//                Log.d("Longitude",longitude.toString());
//                String longi =  String.valueOf(longitude.get(longitude.size()-1));
                String value = dataSnapshot.getValue(String.class);
                parseLong= Double.parseDouble(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
                Toast.makeText(MapActivity.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });

        lon1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                longitude = getCoord((Map<String,String>)dataSnapshot.getValue());
//                Log.d("Longitude",longitude.toString());
//                String longi =  String.valueOf(longitude.get(longitude.size()-1));
                String value = dataSnapshot.getValue(String.class);
                parseLong1= Double.parseDouble(value);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
                Toast.makeText(MapActivity.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });
        lat1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                latitude = getCoord((Map<String,String>)dataSnapshot.getValue());
//                Log.d("Latitude",latitude.toString());
//                  String lati =  String.valueOf(latitude.get(latitude.size()-1));
                String value = dataSnapshot.getValue(String.class);

                parsedLat1= Double.parseDouble(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
                Toast.makeText(MapActivity.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });

        pas.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                longitude = getCoord((Map<String,String>)dataSnapshot.getValue());
//                Log.d("Longitude",longitude.toString());
//                String longi =  String.valueOf(longitude.get(longitude.size()-1));
                //set global passgenger 1 value to this
                passenger1 = Integer.parseInt(dataSnapshot.getValue(String.class));
                //call onmapready method here
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
                Toast.makeText(MapActivity.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });

        busno1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                longitude = getCoord((Map<String,String>)dataSnapshot.getValue());
//                Log.d("Longitude",longitude.toString());
//                String longi =  String.valueOf(longitude.get(longitude.size()-1));
                //set global passgenger 1 value to this
                bno1= dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
                Toast.makeText(MapActivity.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });

        busno2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                longitude = getCoord((Map<String,String>)dataSnapshot.getValue());
//                Log.d("Longitude",longitude.toString());
//                String longi =  String.valueOf(longitude.get(longitude.size()-1));
                //set global passgenger 1 value to this
                bno2= dataSnapshot.getValue(String.class);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
                Toast.makeText(MapActivity.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });

        pas1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                longitude = getCoord((Map<String,String>)dataSnapshot.getValue());
//                Log.d("Longitude",longitude.toString());
//                String longi =  String.valueOf(longitude.get(longitude.size()-1));
                //set global passgenger 1 value to this
                passenger2 = Integer.parseInt(dataSnapshot.getValue(String.class));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
                Toast.makeText(MapActivity.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });
    }


        @SuppressLint("MissingPermission")
        private void enableMyLocation() {
            // 1. Check if permissions are granted, if so, enable the my location layer
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {

                this.map.setMyLocationEnabled(true);
                return;
            }

            // 2. Otherwise, request location permissions from the user.
            PermissionUtils.requestLocationPermissions(this, LOCATION_PERMISSION_REQUEST_CODE, true);
        }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open,  R.string.drawer_close);
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                menuItem -> selectDrawerItem(menuItem));
    }


    public boolean selectDrawerItem(MenuItem menuItem) {
        switch(menuItem.getItemId()) {
            case R.id.nav_first_fragment:
                Intent intent = new Intent(getApplicationContext(),MainHomeActivity.class);
                startActivity(intent);
                return true;
            case R.id.nav_second_fragment:
                Toast.makeText(getApplicationContext(),"Already in Track!",Toast.LENGTH_LONG).show();
                return true;
            case R.id.nav_third_fragment:
                Intent intent1 = new Intent(getApplicationContext(),LocationActivity.class);
                startActivity(intent1);
                return true;
            case R.id.nav_fourth_fragment:
                Intent intent2 = new Intent(getApplicationContext(),AboutActivity.class);
                startActivity(intent2);
                return true;
            default:

        }

        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
        return true;
    }

    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        else{
            switch (item.getItemId()) {
                case R.id.help:
                    Toast.makeText(getApplicationContext(),"HELP CLICKED!!",Toast.LENGTH_LONG).show();
                    return true;
                case R.id.log:
                    Intent intent2 = new Intent(getApplicationContext(),SignInTypeActivity.class);
                    startActivity(intent2);
                    Toast.makeText(getApplicationContext(),"Logged out successfully",Toast.LENGTH_LONG).show();
                    return true;
                case android.R.id.home:
                    mDrawer.openDrawer(GravityCompat.START);
                    return true;
                default:
                    return super.onContextItemSelected(item);
            }
        }
    }

        @Override
        public void onMapClick(@NonNull LatLng latLng) {
            someLati = latLng.latitude;
            someLongi = latLng.longitude;
            startService(new Intent(MapActivity.this,MyService.class));
            map.addMarker(new MarkerOptions().position(latLng));
        }

        @SuppressLint("MissingPermission")
        @Override
        public boolean onMyLocationButtonClick() {
            Toast.makeText(this, "Current Location Clicked!", Toast.LENGTH_SHORT).show();
            fusedLocationClient.getLastLocation().addOnSuccessListener(this,location->{
                if(location!=null){
                    latii = location.getLatitude();
                    longii = location.getLongitude();
                }
            });
            Log.d("Latitude", String.valueOf(latii));
            Log.d("longitude", String.valueOf(longii));
            return false;
        }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                               @NonNull int[] grantResults) {
            if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                return;
            }

            if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                    Manifest.permission.ACCESS_FINE_LOCATION) || PermissionUtils
                    .isPermissionGranted(permissions, grantResults,
                            Manifest.permission.ACCESS_COARSE_LOCATION)) {
                // Enable the my location layer if the permission has been granted.
                enableMyLocation();

            } else {
                // Permission was denied. Display an error message
                // Display the missing permission error dialog when the fragments resume.
                permissionDenied = true;
            }
        }

        @Override
        protected void onResumeFragments() {
            super.onResumeFragments();
            if (permissionDenied) {
                // Permission was not granted, display error dialog.
                showMissingPermissionError();
                permissionDenied = false;
            }
        }

        /**
         * Displays a dialog with error message explaining that the location permission is missing.
         */
        private void showMissingPermissionError() {
            PermissionUtils.PermissionDeniedDialog
                    .newInstance(true).show(getSupportFragmentManager(), "dialog");
        }

        @Override
        public void onMyLocationClick(@NonNull Location location) {
//            Toast.makeText(this, "Current location\n" + location, Toast.LENGTH_LONG).show();
            Toast.makeText(this, "Current location", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onMapReady(@NonNull GoogleMap googleMap) {
            this.map = googleMap;
            this.map.setOnMyLocationButtonClickListener(this);
            this.map.setOnMyLocationClickListener(this);
            this.map.setOnMapClickListener(this);
            //int buspop1 = Integer.parseInt(passenger1);
           // int buspop2 = Integer.parseInt(passenger2);
            if(passenger1>5)
            {
                this.map.addMarker(new MarkerOptions().position(new LatLng(parsedLat,parseLong)).title(bno1+"("+passenger1+")").icon(BitmapFromVector(getApplicationContext(),R.drawable.ic_busred)));
            }
            if(passenger1<5 && passenger1>=2)
            {
                this.map.addMarker(new MarkerOptions().position(new LatLng(parsedLat,parseLong)).title(bno1+"("+passenger1+")").icon(BitmapFromVector(getApplicationContext(),R.drawable.ic_bus)));
            }
            if(passenger1<2)
            {
                this.map.addMarker(new MarkerOptions().position(new LatLng(parsedLat,parseLong)).title(bno1+"("+passenger1+")").icon(BitmapFromVector(getApplicationContext(),R.drawable.ic_busgreen)));
            }
            if(passenger2>5)
            {
                this.map.addMarker(new MarkerOptions().position(new LatLng(parsedLat1,parseLong1)).title(bno2+"("+passenger2+")").icon(BitmapFromVector(getApplicationContext(),R.drawable.ic_busred)));
            }
            if(passenger2<5 && passenger2>=2)
            {
                this.map.addMarker(new MarkerOptions().position(new LatLng(parsedLat1,parseLong1)).title(bno2+"("+passenger2+")").icon(BitmapFromVector(getApplicationContext(),R.drawable.ic_bus)));
            }
            if(passenger2<2)
            {
                this.map.addMarker(new MarkerOptions().position(new LatLng(parsedLat1,parseLong1)).title(bno2+"("+passenger2+")").icon(BitmapFromVector(getApplicationContext(),R.drawable.ic_busgreen)));
            }

            enableMyLocation();
        }

        private BitmapDescriptor
        BitmapFromVector(Context context, int vectorResId)
        {
            // below line is use to generate a drawable.
            Drawable vectorDrawable = ContextCompat.getDrawable(
                    context, vectorResId);

            // below line is use to set bounds to our vector
            // drawable.
            vectorDrawable.setBounds(
                    0, 0, vectorDrawable.getIntrinsicWidth(),
                    vectorDrawable.getIntrinsicHeight());

            // below line is use to create a bitmap for our
            // drawable which we have added.
            Bitmap bitmap = Bitmap.createBitmap(
                    vectorDrawable.getIntrinsicWidth(),
                    vectorDrawable.getIntrinsicHeight(),
                    Bitmap.Config.ARGB_8888);

            // below line is use to add bitmap in our canvas.
            Canvas canvas = new Canvas(bitmap);

            // below line is use to draw our
            // vector drawable in canvas.
            vectorDrawable.draw(canvas);

            // after generating our bitmap we are returning our
            // bitmap.
            return BitmapDescriptorFactory.fromBitmap(bitmap);
        }
    }
