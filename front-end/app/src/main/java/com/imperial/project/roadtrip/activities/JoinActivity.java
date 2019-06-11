package com.imperial.project.roadtrip.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.imperial.project.roadtrip.R;
import com.imperial.project.roadtrip.data.Trip;
import com.imperial.project.roadtrip.workers.TrippinHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;

import cz.msebera.android.httpclient.Header;

public class JoinActivity extends AppCompatActivity {
    public String username;
    public Trip trip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        final Button btn_join = findViewById(R.id.btn_join);
        final EditText et_tripcode = findViewById(R.id.et_destination);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        username = (String) bundle.getSerializable("username");

        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String tripcode = et_tripcode.getText().toString();

                final RequestParams params = new RequestParams();
                params.put("tripcode", tripcode);
                TrippinHttpClient.get("trips", params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        try {
                            JSONArray result = new JSONArray(new String(responseBody));
                            final String destLat = result.getJSONArray(0).get(3).toString();
                            final String destLong = result.getJSONArray(0).get(4).toString();

                            RequestParams params2 = new RequestParams();
                            params2.put("tripcode", tripcode);
                            params2.put("username", username);
                            params2.put("lat", "lat");
                            params2.put("long", "long");
                            params2.put("destLat", destLat);
                            params2.put("destLong", destLong);

                            TrippinHttpClient.post("trips", params2, new AsyncHttpResponseHandler() {
                                @Override
                                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("username", username);
                                    bundle.putSerializable("trip", new Trip(tripcode, "lat", "long", destLat, destLong));
                                    Intent lobby_intent = new Intent(JoinActivity.this, LobbyActivity.class);
                                    lobby_intent.putExtras(bundle);
                                    startActivity(lobby_intent);
                                }

                                @Override
                                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                                }
                            });






                        } catch (Exception e) {

                        }



                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    }
                });
            }
        });
    }

}
