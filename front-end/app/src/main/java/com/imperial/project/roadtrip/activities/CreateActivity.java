package com.imperial.project.roadtrip.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.imperial.project.roadtrip.R;
import com.imperial.project.roadtrip.data.Trip;
import com.imperial.project.roadtrip.workers.TrippinHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Random;

import cz.msebera.android.httpclient.Header;

public class CreateActivity extends AppCompatActivity {
    private String username;
    private Trip trip;
    private final String API_KEY = "AIzaSyBeRMPDdjTGoHSGZgfTbVsrwyxfZh7cMQI";
    Handler createHandler = new Handler();
    Runnable runnable;
    private String destLong;
    private String destLat;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        Button btn_next = findViewById(R.id.btn_next);
        final TextView tv_placeaddress = findViewById(R.id.tv_placeaddress2);
        final TextView tv_placename = findViewById(R.id.tv_placename2);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        username = (String) bundle.getSerializable("username");


        Places.initialize(getApplicationContext(), API_KEY);

        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS));
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NotNull Place place) {
                destLat = Double.toString(place.getLatLng().latitude);
                destLong = Double.toString(place.getLatLng().longitude);
                tv_placename.setText(place.getName());
                tv_placeaddress.setText(place.getAddress());
            }

            @Override
            public void onError(Status status) {
            }
        });



        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trip = new Trip(generateTripCode(), "", "", destLat, destLong);
                createHandler.post(runnable = new Runnable() {
                    @Override
                    public void run() {
                        createTrip();
                    }
                });

                Bundle bundle = new Bundle();
                bundle.putSerializable("username", username);
                bundle.putSerializable("trip", trip);

                Intent lobby_intent = new Intent(CreateActivity.this, LobbyActivity.class);
                lobby_intent.putExtras(bundle);
                startActivity(lobby_intent);
            }
        });
    }

    @Override
    protected void onPause() {
        createHandler.removeCallbacks(runnable);
        super.onPause();
    }

    public void createTrip() {
        RequestParams params = new RequestParams();
        params.put("tripcode", trip.getTripCode());
        params.put("username", username);
        params.put("long", "long");
        params.put("lat", "lat");
        params.put("destLong", trip.getDestLong());
        params.put("destLat", trip.getDestLat());

        TrippinHttpClient.post("trips", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    public static String generateTripCode() {
        StringBuilder result = new StringBuilder();
        Random rand = new Random();
        for (int i = 0; i < 8; i++) {
            int random = rand.nextInt(26);
            char c = ((char) (random + 97));
            result.append(Character.toString(c));
        }
        return result.toString();
    }

}

