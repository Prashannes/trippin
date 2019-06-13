package com.imperial.project.roadtrip.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.imperial.project.roadtrip.R;
import com.imperial.project.roadtrip.data.Trip;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncher;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncherOptions;
import com.mapbox.services.android.navigation.ui.v5.NavigationView;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;
import com.mapbox.services.android.navigation.v5.navigation.NavigationService;
import com.mapbox.services.android.navigation.v5.routeprogress.RouteProgress;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        Button btn_switchview = findViewById(R.id.btn_switchview);
//        btn_switchview.setText(getSupportFragmentManager().getFragments().get(0).getClass().getCanonicalName());


        btn_switchview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MapFragment mapFragment = new MapFragment();
                mapFragment.show(getSupportFragmentManager(), "Dialog_Fragment");
            }
        });

    }

}
