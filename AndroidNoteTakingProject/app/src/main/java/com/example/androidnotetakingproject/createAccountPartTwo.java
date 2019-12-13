package com.example.androidnotetakingproject;

import android.app.DownloadManager;
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

import org.json.JSONException;
import org.json.JSONObject;

public class createAccountPartTwo extends AppCompatActivity {
        @Override
        protected void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_new_user_2);
            Button setPassBtn= (Button)findViewById(R.id.setPassBtn);
            Button logInBtn= (Button)findViewById(R.id.logInBtn);
            EditText userEmail, userTempPass, userNewPass;
            userEmail = (EditText)findViewById(R.id.userEmailAddress);
            userTempPass=(EditText)findViewById(R.id.tempPass);
            userNewPass=(EditText)findViewById(R.id.newPass);
            ServerModel serverModel = new ServerModel(createAccountPartTwo.this);

            setPassBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    try {
                        //register account with new pass
                        JsonObjectRequest request = (JsonObjectRequest) serverModel.registerAccount(userEmail.getText().toString(), userNewPass.getText().toString(), userTempPass.getText().toString(), new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("createAccPart2","response: "+response);

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("createAccPart2",error+"");
                            }
                        });


                    } catch (JSONException e) {
                    }
                }

            });

            logInBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    Intent intent = new Intent(createAccountPartTwo.this,MainActivity.class);
                    startActivity(intent);
                }
            });



        }
}
