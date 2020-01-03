package com.example.androidnotetakingproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.savedstate.SavedStateRegistry;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class forgotPassword extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        EditText userEmail = (EditText)findViewById(R.id.userEmailForgotPass);
        Button getTempPassBtn = (Button)findViewById(R.id.userSendTempPassBtn);
        ServerModel serverModel = new ServerModel(forgotPassword.this);
        userEmail.setText("jimmytran16@gmail.com");
        getTempPassBtn.setOnClickListener(new View.OnClickListener(){
            Intent intent = new Intent(forgotPassword.this,createAccountPartTwo.class);
            @Override
            public void onClick(View view){
                try {
                    //register account with new pass
                    Request request= (Request) serverModel.forgotPassword(userEmail.getText().toString(),new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            startActivity(intent);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("forgotPassPage",error+"");
                            startActivity(intent);
                        }
                    });


                } catch (JSONException e) {
                }
            }


        });

    }
}