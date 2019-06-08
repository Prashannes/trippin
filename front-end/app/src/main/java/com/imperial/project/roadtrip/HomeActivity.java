package com.imperial.project.roadtrip;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        Button btn_createtrip = findViewById(R.id.btn_createtrip);
        Button btn_jointrip = findViewById(R.id.btn_jointrip);
        Button btn_logout = findViewById(R.id.btn_logout);

        btn_createtrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent create_intent = new Intent(HomeActivity.this, CreateActivity.class);
                startActivity(create_intent);
            }
        });

        btn_jointrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent join_intent = new Intent(HomeActivity.this, JoinActivity.class);
                startActivity(join_intent);
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}