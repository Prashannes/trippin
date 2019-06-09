package com.imperial.project.roadtrip;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btn_login = findViewById(R.id.btn_login);
        Button btn_register = findViewById(R.id.btn_register);
        final EditText et_email = findViewById(R.id.et_username);
        final EditText et_password = findViewById(R.id.et_password);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                test@example.com","test","nicktest
//                test@example.com","test","nicktest
//                        [["test@example.com\",\"test\",\"nicktest"]]
                RequestParams params = new RequestParams();
                String email = et_email.getText().toString();
                String password = et_password.getText().toString();
                params.put("email", email);
                params.put("password", password);
                TrippinHttpClient.get("accounts", params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        Bundle bundle = new Bundle();
                        User user = new User();
                        try {
                            JSONArray jsonArray = new JSONArray(new String(responseBody));
                            user = new User(jsonArray.getJSONArray(0).get(0).toString(), jsonArray.getJSONArray(0).get(1).toString());


                        } catch (Exception e) {

                        }

                        bundle.putSerializable("user", user);
                        Intent home_intent = new Intent(LoginActivity.this, HomeActivity.class);
                        home_intent.putExtras(bundle);
                        startActivity(home_intent);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    }
                });


            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reg_intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(reg_intent);
            }
        });


    }
}
