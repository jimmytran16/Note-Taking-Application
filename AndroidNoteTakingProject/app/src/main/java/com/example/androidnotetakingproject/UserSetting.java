package com.example.androidnotetakingproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UserSetting extends AppCompatActivity {
    Button updateBtn,deleteAccBtn;
    EditText firstName , lastName;
    ServerModel serverModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);
        deleteAccBtn  = (Button) findViewById(R.id.deleteAccBtn);
        updateBtn = (Button) findViewById(R.id.updateAccBtn);

        firstName = (EditText) findViewById(R.id.firstname_setting);
        lastName = (EditText) findViewById(R.id.lastname_setting);


        serverModel= new ServerModel(UserSetting.this);
        //get the all user INFORMATION and then find owner first/last by comparing owner email address in the array
        try {
            JsonArrayRequest requestInfo = (JsonArrayRequest) serverModel.getAllAccounts(new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int x =0;x<response.length();x++){
                            try{
                            JSONObject accountsJsonObject = (JSONObject) response.get(x);
                            if(accountsJsonObject.getString("email").equals(MainActivity.owner)){
                                firstName.setText(accountsJsonObject.getString("first_name"));
                                lastName.setText(accountsJsonObject.getString("last_name"));
                            }


                        }catch(JSONException e){}
                    }
                }
                }
                    , new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
            );
        }catch(JSONException e){}

        //deletes the account
        deleteAccBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    JsonObjectRequest requestInfo = (JsonObjectRequest) serverModel.deleteAccount(new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    //go to second page for temp pass and set new password
                                    Log.d("user_setting","DELETE account SUCCESS");
                                }
                            }
                            , new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.d("user_setting","DELETE account FAILED");
                                }
                            }
                    );
                    Intent myIntent = new Intent(UserSetting.this, MainActivity.class);
                    startActivity(myIntent);
                }catch(JSONException e){}
            }
        });
        //updates the account
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    JsonObjectRequest request = (JsonObjectRequest) serverModel.setAccount(firstName.getText().toString(),lastName.getText().toString(),new Response.Listener<JSONObject>(){
                                @Override
                                public void onResponse(JSONObject response) {
                                    Log.d("user_setting","account update SUCCESS");
                                    Intent thisIntent = new Intent(UserSetting.this,DocumentListView.class);
                                    startActivity(thisIntent);
                                }
                            }
                            , new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.d("user_setting","account update FAILED");
                                }
                            }
                    );
                }catch(JSONException e){}
            }
        });



    }
}
