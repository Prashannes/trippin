package com.imperial.project.roadtrip.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.imperial.project.roadtrip.R;
import com.imperial.project.roadtrip.data.Trip;
import com.mapbox.mapboxsdk.Mapbox;

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

        Button btn_switchview = findViewById(R.id.btn_viewMembers);
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
