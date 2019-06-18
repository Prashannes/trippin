package com.imperial.project.roadtrip.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.imperial.project.roadtrip.R;
import com.imperial.project.roadtrip.data.Trip;
import com.imperial.project.roadtrip.workers.TrippinHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.geometry.LatLngBounds;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class NavigationActivity extends AppCompatActivity {
    String username;
    Trip trip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));
        setContentView(R.layout.activity_navigation);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        username = (String) bundle.getSerializable("username");
        trip = (Trip) bundle.getSerializable("trip");

        NavigationFragment navigationFragment = (NavigationFragment) getSupportFragmentManager().findFragmentById(R.id.frag_nav);
        navigationFragment.setTrip(trip);
        navigationFragment.setUsername(username);

        Button btn_switchview = findViewById(R.id.btn_viewMembers);
//        btn_switchview.setText(getSupportFragmentManager().getFragments().get(0).getClass().getCanonicalName());


        btn_switchview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MapFragment mapFragment = new MapFragment();
                mapFragment.show(getSupportFragmentManager(), "Dialog_Fragment");
                mapFragment.setTrip(trip);

//                Handler handler = new Handler();
//                int delay = 7000; //milliseconds
//
//                handler.postDelayed(new Runnable(){
//                    public void run(){
//                        mapFragment.updateMap();
//                        handler.postDelayed(this, delay);
//                    }
//                }, delay);


            }
        });

    }

}
