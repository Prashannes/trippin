package com.imperial.project.roadtrip;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;

public class CreateActivity extends AppCompatActivity {
    public User user;
    private String destination;
    private Place dest;
    private final String API_KEY = "AIzaSyBeRMPDdjTGoHSGZgfTbVsrwyxfZh7cMQI";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        Button btn_next = findViewById(R.id.btn_next);
        EditText et_destination = findViewById(R.id.et_destination);
        // TextView tv_createtrip = findViewById(R.id.tv_createtrip);


        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        user = (User) bundle.getSerializable("user");


        Places.initialize(getApplicationContext(), API_KEY);

        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                dest = place;
                destination = place.getName();
            }


            @Override
            public void onError(Status status) {
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("user", user);
                bundle.putSerializable("dest", new PlaceSerializable(dest));
                Intent lobby_intent = new Intent(CreateActivity.this, LobbyActivity.class);
                lobby_intent.putExtras(bundle);
                startActivity(lobby_intent);
            }
        });





    }
}
