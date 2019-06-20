package com.imperial.project.roadtrip.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.imperial.project.roadtrip.R;
import com.imperial.project.roadtrip.data.Trip;
import com.imperial.project.roadtrip.workers.TrippinHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mapbox.mapboxsdk.Mapbox;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class NavigationActivity extends AppCompatActivity {
    String username;
    Trip trip;
    Handler msgHandler = new Handler();
    Runnable runnable;
    Handler msgRcvHandler = new Handler();
    Runnable runnableRcv;
    int msgDelay = 5*1000;
    Map<String, String> lastMessages = new HashMap<>();

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

        msgRcvHandler.postDelayed(runnableRcv = new Runnable() {
            @Override
            public void run() {
                getMsgs();
                msgRcvHandler.postDelayed(this, msgDelay);
            }
        }, msgDelay);

        Button btn_switchview = findViewById(R.id.btn_viewMembers);
        btn_switchview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MapFragment mapFragment = new MapFragment();
                mapFragment.show(getSupportFragmentManager(), "Dialog_Fragment");
                mapFragment.setTrip(trip);
            }
        });

        Button btn_sendMessage = findViewById(R.id.btn_sendMessage);
        btn_sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(NavigationActivity.this);
                alertDialog.setTitle("Enter message");
//                alertDialog.setMessage("Enter message");
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT)
                        ;
                final EditText input = new EditText(getBaseContext());
                input.setLayoutParams(lp);
                alertDialog.setView(input);
                alertDialog.setPositiveButton("Send",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int which) {
                                msgHandler.post(runnable = new Runnable() {
                                    @Override
                                    public void run() {
                                        sendMessage(input.getText().toString());
//                                        msgHandler.post(this);
                                    }
                                });
                            }
                        });
                alertDialog.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                alertDialog.show();
            }
        });

    }

    public void getMsgs() {
        final RequestParams params = new RequestParams();
        params.put("tripcode", trip.getTripCode());
        TrippinHttpClient.get("trips", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    JSONArray result = new JSONArray(new String(responseBody));
                    for (int i = 0; i < result.length(); i++) {
                        String name = result.getJSONArray(i).get(0).toString();
                        String msg = result.getJSONArray(i).get(6).toString();
                        if (msg.length() > 0) {
                            if (lastMessages.containsKey(name)) {
                                if (!lastMessages.get(name).equals(msg)) {
                                    lastMessages.put(name, msg);
//                                    displayMsg(name, msg, "");
                                    displayMsg(name, msg);
                                }
                            } else {
                                lastMessages.put(name, msg);
                                displayMsg(name, msg);
//                                displayMsg(name, msg, "");
                            }
                        }

                    }
                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    public void displayMsg(String name, String msg) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.fragment_chat,
                (ViewGroup) findViewById(R.id.layout_toast));

        TextView tv_msg = (TextView) layout.findViewById(R.id.tv_msg);
        tv_msg.setText(name + ": " + msg);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.TOP, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    public void displayMsg(String name, String msg, String a) {
        Toast toast = Toast.makeText(this, "message", Toast.LENGTH_LONG);
        toast.setText(name + ": " + msg);
        toast.show();
    }

    public void sendMessage(String msg) {
        final RequestParams params = new RequestParams();
        params.put("username", username);
        params.put("tripcode", trip.getTripCode());
        params.put("msg", msg);
        TrippinHttpClient.put("trips", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

    }

}
