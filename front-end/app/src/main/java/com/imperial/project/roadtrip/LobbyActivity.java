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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cz.msebera.android.httpclient.Header;

public class LobbyActivity extends AppCompatActivity {
    public User user;
    public Trip trip;
    ArrayList<TextView> tv_memList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        user = (User) bundle.getSerializable("user");
        trip = ((Trip) bundle.getSerializable("trip"));

        final TextView tv_yourcode = findViewById(R.id.tv_yourcode);
        tv_memList.add((TextView) findViewById(R.id.tv_mem1));
        tv_memList.add((TextView) findViewById(R.id.tv_mem2));
        tv_memList.add((TextView) findViewById(R.id.tv_mem3));
        tv_memList.add((TextView) findViewById(R.id.tv_mem4));
        tv_memList.add((TextView) findViewById(R.id.tv_mem5));
        tv_memList.add((TextView) findViewById(R.id.tv_mem6));

        tv_yourcode.setText(trip.getTripCode());

        updateMembers();

        //------------------------------

//        Bundle bundle = new Bundle();
//        bundle.putSerializable("user", user);
//        Intent lobby_intent = new Intent(CreateActivity.this, LobbyActivity.class);
//        lobby_intent.putExtras(bundle);
//        startActivity(lobby_intent);
    }

    public List<String> getTripInfo() {
        ArrayList<String> result = new ArrayList<>();
        RequestParams params = new RequestParams();
        String email = user.getEmail();
        params.put("tripcode", trip.getTripCode());
        params.put("email", email);

        TrippinHttpClient.get("trips", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });


        return result;
    }

    public List<String> getTripMembers() {
        ArrayList<String> result = new ArrayList<>();
        ArrayList<String> tripInfo = new ArrayList<>();
        return result;
    }

    public void updateMembers() {
        for (TextView tv : tv_memList) {
            tv.setText("");
        }
    }


}
