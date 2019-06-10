package com.imperial.project.roadtrip;

import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.libraries.places.api.model.Place;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cz.msebera.android.httpclient.Header;

public class LobbyActivity extends AppCompatActivity {
    public String username;
    public Trip trip;
    ArrayList<TextView> tv_memList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        username = (String) bundle.getSerializable("username");
        trip = ((Trip) bundle.getSerializable("trip"));



        final TextView tv_yourcode = findViewById(R.id.tv_yourcode);
        tv_memList.add((TextView) findViewById(R.id.tv_mem1));
        tv_memList.add((TextView) findViewById(R.id.tv_mem2));
        tv_memList.add((TextView) findViewById(R.id.tv_mem3));
        tv_memList.add((TextView) findViewById(R.id.tv_mem4));
        tv_memList.add((TextView) findViewById(R.id.tv_mem5));
        tv_memList.add((TextView) findViewById(R.id.tv_mem6));

        tv_yourcode.setText(trip.getTripCode());
        Button btn_refresh = findViewById(R.id.btn_refresh);
        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateMembers();
            }
        });

        updateMembers();



        //------------------------------

//        Bundle bundle = new Bundle();
//        bundle.putSerializable("user", user);
//        Intent lobby_intent = new Intent(CreateActivity.this, LobbyActivity.class);
//        lobby_intent.putExtras(bundle);
//        startActivity(lobby_intent);
    }


    @Override
    public void onBackPressed() {
        RequestParams params = new RequestParams();
        params.put("username", username);
        TrippinHttpClient.put("trips", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
        super.onBackPressed();
    }


    public void updateMembers() {
        RequestParams params = new RequestParams();
        params.put("tripcode", trip.getTripCode());

        TrippinHttpClient.get("trips", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    JSONArray result = new JSONArray(new String(responseBody));
                    TextView tv = findViewById(R.id.tv_members);
                    for (int i = 0; i < 6; i++) {
                            final TextView tv_yourcode = findViewById(R.id.tv_yourcode);
                            try {
                                String str = result.getJSONArray(i).get(0).toString();
                                tv_memList.get(i).setText(str);
                            } catch (Exception e) {
                                tv_memList.get(i).setText(null);
                                tv_memList.get(i).setHint("Empty");

                            }


//                            String nickname = result.getJSONArray(i).get(5).toString();
//                            if (!nickname.equals("")) {
//                                tv_memList.get(i).setText(nickname);
//                            }
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

    }


}
