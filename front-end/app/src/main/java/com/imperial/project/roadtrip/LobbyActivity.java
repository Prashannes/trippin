package com.imperial.project.roadtrip;

import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.libraries.places.api.model.Place;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.Random;

import cz.msebera.android.httpclient.Header;

public class LobbyActivity extends AppCompatActivity {
    public User user;
    public Place place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        user = (User) bundle.getSerializable("user");
        place = ((PlaceSerializable) bundle.getSerializable("dest")).getPlace();

        final TextView tv_yourcode = findViewById(R.id.tv_yourcode);
        final String tripcode = generateTripCode();

        RequestParams params = new RequestParams();
        String email = user.getEmail();
        params.put("tripcode", tripcode);
        params.put("email", email);
        params.put("lat", "lat");
        params.put("long", "long");
        params.put("destLong", Double.toString(place.getLatLng().longitude));
        params.put("destLat", Double.toString(place.getLatLng().latitude));

        TrippinHttpClient.post("trips", params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        tv_yourcode.setText(tripcode);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    }
                });

//        Bundle bundle = new Bundle();
//        bundle.putSerializable("user", user);
//        Intent lobby_intent = new Intent(CreateActivity.this, LobbyActivity.class);
//        lobby_intent.putExtras(bundle);
//        startActivity(lobby_intent);
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
