package com.sentinels.UrbanBusTrack;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.SignInButton;
import com.google.android.material.navigation.NavigationView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;
import java.util.logging.ConsoleHandler;

public class BusinfoActivity extends AppCompatActivity {

    private DrawerLayout mDrawer;
    private Toolbar toolbar;

    private ActionBarDrawerToggle drawerToggle;
    FirebaseDatabase firebaseDatabase;
    // Write a message to the database

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_businfo);

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

        Button BusSubmit = findViewById(R.id.btnSubmit);
        EditText busid = findViewById(R.id.BusId);
        EditText busnumber = findViewById(R.id.BusNumber);
        EditText busoperator = findViewById(R.id.BusOperator);
        EditText busstartstop = findViewById(R.id.BusStart);
        EditText busendstop = findViewById(R.id.BusEnd);

        System.out.println("*********  "+((EditText)findViewById(R.id.BusId)).getText().toString()+"<==============================" );

        FirebaseDatabase database = FirebaseDatabase.getInstance();


        BusSubmit.setOnClickListener(v -> {
            //convert all above view to string
                String busid1 = busid.getText().toString();
                String busnumber1 = busnumber.getText().toString();
                String busoperator1 = busoperator.getText().toString();
                String busstartstop1 = busstartstop.getText().toString();
                String busendstop1 = busendstop.getText().toString();

                //    Toast.makeText(BusinfoActivity.this, "Bus ID: "+busid1+" Bus Number: "+busnumber1+" Bus Operator: "+busoperator1+" Bus Start Stop: "+busstartstop1+" Bus End Stop: "+busendstop1, Toast.LENGTH_SHORT).show();
                //push the values to databse with bus id as parent and others as child
                DatabaseReference myRef = database.getReference("BusInfo").child(busid1);
                myRef.child("BusNumber").setValue(busnumber1);
                myRef.child("BusOperator").setValue(busoperator1);
                myRef.child("BusStartStop").setValue(busstartstop1);
                myRef.child("BusPopulation").setValue("");
                myRef.child("BusLatitude").setValue("");
                myRef.child("BusLongitude").setValue("");

                myRef.child("BusEndStop").setValue(busendstop1);
                Toast.makeText(BusinfoActivity.this, "Bus Info Added Successfully", Toast.LENGTH_SHORT).show();


        });


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
                Intent intent1 = new Intent(getApplicationContext(),MapActivity.class);
                startActivity(intent1);
                return true;
            case R.id.nav_third_fragment:
                Intent intent2 = new Intent(getApplicationContext(),LocationActivity.class);
                startActivity(intent2);
            case R.id.nav_fourth_fragment:
                Toast.makeText(getApplicationContext(),"Already in About Us!",Toast.LENGTH_LONG).show();
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
                    Intent intent2 = new Intent(getApplicationContext(),SignInActivity.class);
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

}
