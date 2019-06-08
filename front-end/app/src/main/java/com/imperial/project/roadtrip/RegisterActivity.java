package com.imperial.project.roadtrip;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText et_email = findViewById(R.id.et_email);
        final EditText et_password = findViewById(R.id.et_password);
        EditText et_repassword = findViewById(R.id.et_repassword);
        final EditText et_nickname = findViewById(R.id.et_nickname);

        Button btn_signup = findViewById(R.id.btn_signup);

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String dburl = "pn716@cloud-vm-47-204.doc.ac.ic.uk";
                String del = "";

                OkHttpClient client = new OkHttpClient();

                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("email", et_email.getText().toString())
                        .addFormDataPart("password", et_password.getText().toString())
                        .addFormDataPart("nickname", et_nickname.getText().toString())
                        .build();

                Request request = new Request.Builder()
                        .url(dburl)
                        .post(requestBody)
                        .build();

                try {
                    Response response = client.newCall(request).execute();

                    if (!response.isSuccessful()) {
                        del = "FCUKED IT";

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }




//                toastMsg();
//                et_email.setText(del);
//                finish();
            }
        });







    }

    public void toastMsg() {
        Toast.makeText(this, "You have signed up!", Toast.LENGTH_LONG).show();
    }
}
