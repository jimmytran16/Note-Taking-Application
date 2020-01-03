package com.example.androidnotetakingproject;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.BundleCompat;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;


public class shareDocument extends AppCompatActivity{
    Button shareSubmit;
    EditText shareText;

    @Override
    public void onCreate(Bundle onSavedBundle){
        super.onCreate(onSavedBundle);
        setContentView(R.layout.activity_share_doc);
        shareSubmit = (Button)findViewById(R.id.userShareBtn);
        shareText = (EditText)findViewById(R.id.userShareText);
        Bundle docIDTransfer= getIntent().getExtras();
        String docID_toShare = docIDTransfer.getString("currentDocId");
        Log.d("doc_id_to_share",docID_toShare);
        ServerModel serverModel = new ServerModel(shareDocument.this);




        shareSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    JsonObjectRequest requestInfo = (JsonObjectRequest) serverModel.setDocumentAccessors(docID_toShare,shareText.getText().toString(), new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Log.d("Main_act", response + "");

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
                }
            }
        });
    }
}
