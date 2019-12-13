package com.example.androidnotetakingproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class createNewAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_new_user);
        EditText userEmail = (EditText) findViewById(R.id.signUpEmail);
        EditText userFirst = (EditText) findViewById(R.id.userFirstName);
        EditText userLast = (EditText) findViewById(R.id.userLastName);
        Button userSignUp = (Button) findViewById(R.id.userSignUp);
        ServerModel serverModel = new ServerModel(createNewAccount.this);



        userSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    JsonObjectRequest requestInfo = (JsonObjectRequest) serverModel.createAccount(userEmail.getText().toString(),userFirst.getText().toString(),userLast.getText().toString(), new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    //go to second page for temp pass and set new password
                                    Log.d("createAcc", response + "");
                                }
                            }
                            , new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.d("createAcc", error + "");
                                }
                            }
                    );
                    Intent myIntent = new Intent(createNewAccount.this, createAccountPartTwo.class);
                    startActivity(myIntent);
                }catch(JSONException e){}
            }
        });



    }
}
