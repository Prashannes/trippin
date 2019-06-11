package com.imperial.project.roadtrip.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.imperial.project.roadtrip.R;
import com.imperial.project.roadtrip.workers.TrippinHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText et_username = findViewById(R.id.et_usernamereg);
        final EditText et_password = findViewById(R.id.et_password);
        final EditText et_repassword = findViewById(R.id.et_repassword);

        Button btn_signup = findViewById(R.id.btn_signup);

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RequestParams params = new RequestParams();
                params.put("username", et_username.getText().toString());
                params.put("password", et_password.getText().toString());
                TrippinHttpClient.post("accounts", params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//                        et_repassword.setText("WAG1GGGGGGGGGGG");
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    }
                });

                toastMsg();
                finish();
            }
        });







    }

    public void toastMsg() {
        Toast.makeText(this, "You have signed up!", Toast.LENGTH_LONG).show();
    }
}
