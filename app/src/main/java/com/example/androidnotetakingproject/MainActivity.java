package com.example.androidnotetakingproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    static Document document;
    static List<Document> docsList = new ArrayList<>();
    static String token;
    static boolean checking = false;
    static String owner;
    Intent intent;
    Button userLogin, newAccBtn, forgotPassBtn, registerYourAccBtn;
    EditText userText, passText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userLogin = (Button) findViewById(R.id.userSignUp);
        newAccBtn = (Button) findViewById(R.id.newUserbtn);
        forgotPassBtn = (Button) findViewById(R.id.forPassbtn);
        registerYourAccBtn = (Button) findViewById(R.id.register_btn);

        userText = (EditText) findViewById(R.id.user_name);
        passText = (EditText) findViewById(R.id.user_pass);

        userText.setText("jimmytran1620@gmail.com");
        passText.setText("Testing123");
        ServerModel serverModel = new ServerModel(this);


        registerYourAccBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MainActivity.this, createAccountPartTwo.class);
                startActivity(intent);
            }
        });

        forgotPassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MainActivity.this, forgotPassword.class);
                startActivity(intent);
            }
        });

        newAccBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MainActivity.this, createNewAccount.class);
                startActivity(intent);
            }
        });


        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    JsonObjectRequest requestInfo = (JsonObjectRequest) serverModel.authenticate(userText.getText().toString(), passText.getText().toString(), new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Log.d("Main_act", response + "");
                                    //set the intent
                                    intent = new Intent(getApplicationContext(), DocumentListView.class);
                                    try {
                                        //get the token and ttl
                                        token = response.getString("token");
                                        serverModel.setToken(token);
                                        String ttl = response.getString("ttl");
                                        owner = response.getString("owner");

                                        Log.d("Main_login_request", "Response" + response);
                                        Log.d("Main_login_request", "ttl" + ttl);
                                        Log.d("Main_login_request", "token" + token);
                                        startActivity(intent);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                            , new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.d("Main_act", error + "");
                                }
                            }
                    );

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


    }


}

