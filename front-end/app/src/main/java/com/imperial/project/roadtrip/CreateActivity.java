package com.imperial.project.roadtrip;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        Button btn_next = findViewById(R.id.btn_next);
        EditText et_destination = findViewById(R.id.et_destination);
        // TextView tv_createtrip = findViewById(R.id.tv_createtrip);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent lobby_intent = new Intent(CreateActivity.this, LobbyActivity.class);
                startActivity(lobby_intent);
            }
        });

    }
}
