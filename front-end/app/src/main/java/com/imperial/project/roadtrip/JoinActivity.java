package com.imperial.project.roadtrip;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class JoinActivity extends AppCompatActivity {
    public User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        Button btn_join = findViewById(R.id.btn_join);
        EditText et_tripcode = findViewById(R.id.et_destination);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        user = (User) bundle.getSerializable("user");

        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("user", user);
                Intent lobby_intent = new Intent(JoinActivity.this, LobbyActivity.class);
                lobby_intent.putExtras(bundle);
                startActivity(lobby_intent);
            }
        });


    }
}
