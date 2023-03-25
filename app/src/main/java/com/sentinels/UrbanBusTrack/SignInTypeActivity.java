package com.sentinels.UrbanBusTrack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class SignInTypeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signintype);
    }
    public void operator(View view) {
        //send the type of user to the next activity with putextra
        Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
        intent.putExtra("type", "operator");
        startActivity(intent);
    }
    public void user(View view) {
        Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
        intent.putExtra("type", "user");
        startActivity(intent);
    }

}
