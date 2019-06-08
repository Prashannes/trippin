package com.imperial.project.roadtrip;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class JoinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        Button btn_join = findViewById(R.id.btn_join);
        EditText et_tripcode = findViewById(R.id.et_destination);

        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent lobby_intent = new Intent(JoinActivity.this, LobbyActivity.class);
                startActivity(lobby_intent);
            }
        });


    }
}
